import java.awt.Color;					//Necessary imports
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import acm.graphics.*;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class bSim extends GraphicsProgram {

	private static final int WIDTH = 1200; 		// n.b. screen coords
	private static final int HEIGHT = 600;
	private static final int OFFSET = 200;
	private static final int NUMBALLS = 15; 	// # balls to sim.
	private static final double MINSIZE = 1; 	// Min ball size
	private static final double MAXSIZE = 8;	// Max ball size
	private static final double XMIN = 10; 		// Min X start location
	private static final double XMAX = 50; 		// Max X start location
	private static final double YMIN = 50; 		// Min Y start location
	private static final double YMAX = 100; 	// Max Y start location
	private static final double EMIN = 0.4; 	// Min loss coeff.
	private static final double EMAX = 0.9; 	// Max loss coeff.
	private static final double VMIN = 0.5; 	// Min X velocity
	private static final double VMAX = 3.0; 	// Max Y velocity
	private static final double GROUND_BEGIN = 0.0; //X coordinates for the start of ground
	private static final double GROUND_END = 1400;  // X coordinates for the end of ground

	RandomGenerator rgen = new RandomGenerator(); 	//Creates a random generator to use for creating values
	public gBall[] BallArray;			//Generates array of gBalls
	bTree gBallTree = new bTree();		//Creates a new tree
	boolean isRunning; 					//Checks if any of the balls are bouncing 
	public int sortCount = 0;			//Keeps track of how many times the balls have been sorted
	GLabel prompt;						

	public void init() {
		addMouseListeners();
		addKeyListeners();
	}


	public void run() {
		this.resize(WIDTH, HEIGHT + OFFSET);						//Adjusts canvas dimensions
		BallArray = new gBall[NUMBALLS]; 				//Creating a gBall array with 100 spaces
		isRunning = true;

		GLine Ground = new GLine(GROUND_BEGIN, HEIGHT, GROUND_END, HEIGHT);   	//Creates the ground
		add(Ground); 

		for (int i = 0; i < BallArray.length; i++) { 	//Iterates over the array, and creates 100 gBalls
			double Xi = rgen.nextDouble(XMIN, XMAX); 	//Random generation of variables values within given limits
			double Yi = rgen.nextDouble(YMIN, YMAX);
			double bSize = rgen.nextDouble(MINSIZE, MAXSIZE);
			Color bColor = rgen.nextColor();
			double bLoss = rgen.nextDouble(EMIN, EMAX);
			double bVel = rgen.nextDouble(VMIN, VMAX); //The six adjusts for metres

			gBall inputBall = new gBall(Xi, Yi, bSize, bColor, bLoss, bVel); //Creates a gBall with given values and stores it in the BallArray
			BallArray[i] = inputBall;
			add(inputBall.BouncingBall);
			gBallTree.addNode(inputBall);

		}

		for (int i = 0; i < BallArray.length; i++) {	//Iterating over array again
			add(BallArray[i].BouncingBall);				//Adds a ball from the Array onto the canvas
			BallArray[i].start();						//Starts the thread that controls the physics of the ball and changes it's location in gBall Class
		}

		while (isRunning) { 	//This continously checks if all the balls are stopped yet
			int BallsStopped = 0;
			for (int i = 0; i < BallArray.length; i++) {
				if ((BallArray[i].isBouncing) == false) {
					BallsStopped++;
				} if (BallsStopped == BallArray.length) {
					isRunning = false;
				}
			}
		}


		if (!isRunning) {
			prompt = new GLabel ("CR to continue...", this.getWidth()/2, this.getHeight()/2);
			add(prompt); // adds a label
		}


	}

	public void mouseClicked (MouseEvent e) {
		if (!isRunning && sortCount < 2) { 				//Checks if the simulation has stopped Running, and also that balls have not been sorted before
			gBallTree.moveSort(); 						//Traverses through tree, and moves balls to correct location
			sortCount++; 								// ensures that the user can sort the balls only once
			GLabel alldone = new GLabel ("All Sorted!", this.getWidth()/2, this.getHeight()/2);
			remove(prompt);
			add(alldone);
		}
	}

	public void keyPressed (KeyEvent e) { 
		if (!isRunning && sortCount < 1) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_ENTER: 
			case KeyEvent.VK_SPACE:  // if space or enter is pressed and the simulation is done
				gBallTree.moveSort(); // sorts the list of balls
				sortCount++; // ensures the balls are sorted only once
				GLabel alldone = new GLabel ("All Sorted!", this.getWidth()/2, this.getHeight()/2);
				remove(prompt);
				add(alldone);
				break;
			}
		}
	}



}
