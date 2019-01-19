import java.awt.Color;
import acm.graphics.*;



public class gBall extends Thread {

	private static final int HEIGHT = 600; 		// n.b. screen coords
	private static final int OFFSET = 200;
	private static final double INTERVAL_TIME = 0.1;
	private static final double SCALE_FACTOR = 6;


	double Xi; 		//The X position of the ball
	double Yi; 		// The Y position of the ball
	double bSize; 	// The Radius of the ball
	Color bColor; 	//The color of the ball
	double bLoss; 	//The energy loss of the ball on every bounce
	double bVel; 	//The horizontal velocity of the ball
	public GOval BouncingBall;
	public boolean isBouncing;


	public gBall(double Xi, double Yi, double bSize, Color bColor, double bLoss, double bVel) { //Constructor for the gBall
		this.Xi = Xi * SCALE_FACTOR;		//Sets X position, and the 6 adjusts for metres
		this.Yi = Yi * SCALE_FACTOR;		//Sets Y position, and the 6 adjusts for metres
		this.bSize = bSize * 2 * SCALE_FACTOR;	//Sets radius of ball, and the 6 adjusts for metres
		this.bColor = bColor;				//Sets color of ball
		this.bLoss = bLoss;					//Sets energy loss of ball
		this.bVel = bVel * SCALE_FACTOR;					//Sets horizontal velocity

		BouncingBall = new GOval(Xi, Yi, this.bSize, this.bSize); 	//Creates instance of gBall		
		BouncingBall.setFilled(true);			//Fills in ball
		BouncingBall.setLocation(Xi, Yi);		//Sets initial location of ball
		BouncingBall.setColor(bColor);			//Sets color
		isBouncing = true;
	}

	public void moveTo (double x, double y) { // moves a ball to the given coordinates
		double xPosition = x;
		double yPosition = HEIGHT - bSize; 
		BouncingBall.setLocation(xPosition, yPosition);
	}

	public void run () {  							//The run part of the thread. Contains all of the physics of the ball

		double initialUpPositon = 0;  				//Declarations for all the variables needed in the program
		double h0 = Yi; 							//Height of the ball				
		double loss = bLoss;						//Energy loss of the ball
		double t = 0;					
		boolean directionUp = false;				//Direction checker
		double g = 9.81;							//Acceleration of gravity
		double vy = Math.pow(2.0 * g * h0, 0.5); 	//Vertical velocity of the ball 
		double lossfactor = Math.pow(1 - loss, 0.5); 
		double h;		
		double vx = bVel; 				//Horizontal velocity of the ball
		double totalTime = 0;
		double xPos = Xi;

		while (vy > 2) { 					//Stops the while loop if the vertical velocity of the ball drops below a 0.05, which is essentially motionless
			double initialUpPosition = 0;
			if (directionUp == false) { 		//Behavior of the ball when it's going down
				h = h0 - 0.5 * g * t * t;

				if (h <= 0) {						    
					h0 = 0;
					initialUpPosition = h;
					directionUp = true;
					t = 0;
					vy = vy * lossfactor;

				}
			} else {							//Behavior of the ball when it's going up
				h = initialUpPosition + vy * t - 0.5 * g * t * t;

				if (h > h0) {
					h0 = h;

				} else {
					directionUp = false;
					t = 0;
				}
			}

			xPos = xPos + vx * INTERVAL_TIME; 	//Changes the X-axis position of the ball
			t += INTERVAL_TIME;               	//Increments time
			totalTime += INTERVAL_TIME;       	//Increments totalTime

			try { 								// pauses for 25 milliseconds
				Thread.sleep(25);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}


			if (h >= 0) {
				BouncingBall.setLocation(xPos, HEIGHT- h - bSize); //Changes the location of the balls when they are in the air
			}

			if (vy <= 2) {
				isBouncing = false;
			}

		}														   		 
	}

}
