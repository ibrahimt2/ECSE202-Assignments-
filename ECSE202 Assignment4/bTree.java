import java.util.ArrayList;

import acm.graphics.GOval;

public class bTree {
	
	private double prevXposition = 0; 	//Keeps track of the previous Xposition
	private bNode root = null; 			//Creates root node
	private int BallsStopped; 			//Keeps track of balls that have stopped
	private bSim obj = new bSim();
	
	
	public void addNode(gBall iBall) {
		root = rNode(root, iBall);
	}

	
	/*
	 * rNode Method which adds every ball to a binary search tree using their 
	 * size as the key value used for sorthing
	 */
	
	private bNode rNode(bNode input_root, gBall inputBall) {
		if(input_root == null) {   			//Checks if node is null. If it is, a new one is created
			bNode node = new bNode();
			node.data = inputBall;
			node.left = null;
			node.right =  null;	 
			return node;
		}
		else if(inputBall.getbSize() < input_root.data.getbSize()) {  //Checks size. If smaller than root gBall size, creates left child
			input_root.left = rNode(input_root.left, inputBall);
		}
		else {														  //Checks size. If bigger than root gBall size, creates right child
			input_root.right = rNode(input_root.right, inputBall);
		}
		return input_root; 
	}
	
	
	public bNode findNode(GOval clickedOval) { //Wrapper for findNodeRecursive
		return findNodeRecursive(root, clickedOval);
	}
	
	
	private bNode findNodeRecursive(bNode input_root, GOval clickedOval) { //Finds the ball which has been clicked within the bTree
				if(!(input_root.left == null)) {						   //Using binary search to traverse through the tree
					bNode result = findNodeRecursive(input_root.left, clickedOval);
					if(result != null) return result;
				}
				
				if(clickedOval == input_root.data.getMyBall()) {  //If clicked Oval has the same size as a ball stored within bTree,
					return input_root;							  //We have found our ball, hence the matching gBall in root is returned 
				}
				
				//Right
				if(!(input_root.right == null)) {
					bNode result = findNodeRecursive(input_root.right, clickedOval);
					if(result != null) return result;
				}
				return null;
	}
	
	
	
	private void clearTreeRecursive(bNode input_root) {   //Clears the tree to prepare for the next run of the simulation
		if(!(input_root.left == null)) clearTreeRecursive(input_root.left);

		input_root.data.setFreeze(true);

		if(!(input_root.right == null)) clearTreeRecursive(input_root.right);
	}
	
	public void clearTree() {  //Wrapper for clearTreeRecursive
		clearTreeRecursive(root);
	}
	
	public boolean isRunning() { //Wrapper for isRunning_inorder, also sets BallsStopped = 0 to reset ball counter
		BallsStopped = 0;
		return isRunning_inorder(root);
	}

	/*
	 * isRunning method checks if all of the simulation balls have stopped moving
	 */
	
	private boolean isRunning_inorder(bNode input_root) {
		
		/*
		 * If the ball that is being checked is stopped, 1 is added to the countdown
		 * If even one ball is moving, the countdown is set to 0.
		 * If countdown reaches the number of balls, then we know that the simulation 
		 * has stopped running
		 * 
		 */
		
		if(!(input_root.left == null)) isRunning_inorder(input_root.left);
		
		if(input_root.data.isInMovement() == false) ++BallsStopped;
		else BallsStopped = 0;
		
		if(!(input_root.right == null)) isRunning_inorder(input_root.right);

		if(BallsStopped >= obj.NUMBALLS) return false;
		
		return true;
	}
	
		public void moveSort() {   //Wrapper for inorderTransversal_moveSort() method
		moveAndTransverseRecursive(root);
		prevXposition = 0; //reset it to 0 every time the sorting is done
	}

		
	/*
	 * The moveSort_transverse_inorder method does a binary search through the tree and 
	 * also moves every ball in ascending order to the correct location, according to bSize
	 */
	private void moveAndTransverseRecursive(bNode input_root) {
		//We go left, then root, then right
		//Left
		if(!(input_root.left == null)) moveAndTransverseRecursive(input_root.left);
		
		//Root --> move ball to where it belongs (sorted)
		input_root.data.moveTo(prevXposition, 0);
		prevXposition += (input_root.data.getbSize() / bSim.SCALE_FACTOR);
		
		//Right
		if(!(input_root.right == null)) moveAndTransverseRecursive(input_root.right);
	}
	
	
	public bTree changeTree(gBall input_ball) { //Wrapper for changebTreeRecursive
		bTree tree = new bTree();
		return changebTreeRecursive(root, input_ball, tree);
	}
	
	private bTree changebTreeRecursive(bNode input_root, gBall input_ball, bTree tree) {  //Inserts the newly created ball into bTree
		
		if(!(input_root.left == null)) changebTreeRecursive(input_root.left, input_ball, tree);
		
		if(!(input_root.data == input_ball)) {
			tree.addNode(input_root.data);
		}
		
		if(input_root.right != null) changebTreeRecursive(input_root.right, input_ball, tree);
		
		return tree;
	}
}

class bNode{
	gBall data;
	bNode left;
	bNode right;
}


