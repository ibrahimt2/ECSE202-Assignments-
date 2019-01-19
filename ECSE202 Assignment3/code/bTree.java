
public class bTree {

	public static double gBallxPosition = 0; // static variable to determine the position of ball's xCoords after sorting
	public gBall rootNode; 					 // The current root node 
	public bTree left; 						 // The left binary tree connected to the root node
	public bTree right; 					 // The right binary tree connected to the root node

	public void addNode(gBall inputBall) { //A method to add a node to the binary tree to create a binary search tree
		if (rootNode == null) {
			this.rootNode = inputBall; 
			this.left = new bTree();
			this.right = new bTree();
		} else if (inputBall.bSize < rootNode.bSize) {
			left.addNode(inputBall);
		} else {
			right.addNode(inputBall);
		}
	}


	public void moveSort() {  			//This method traverses through the tree via in-order traversal.
		if (left.rootNode != null) {
			left.moveSort();
		}	
		if (rootNode != null) {
			rootNode.moveTo(gBallxPosition, rootNode.bSize); //Moves the ball to the correct location 
			gBallxPosition += rootNode.bSize;				 //Increments the xPosition by the diameter, so the balls don't overlap	 
		}	
		if (right.rootNode != null) {
			right.moveSort();
		}
	}
}


