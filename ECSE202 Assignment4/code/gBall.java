import java.awt.Color;
import acm.graphics.GOval;


public class gBall extends Thread {
	
	private GOval myBall;
	private boolean isRunning = true; //Stores information about whether the ball is running
	private double t = 0; 
	private double totalTime = 0; 	  //Total time
	private double vt; 
	private double StoppedCycle = 0;  //Number of cycles ball has been on the ground
	private static final double g = 9.8;
	private static final double INTERVAL_TIME = 0.01;
	private boolean stopped = false;

	private double Xi;  	//The X position of the ball
	private double Yi;		//The Y position of the ball	
	private double bSize;	//Ball Size
	private Color bColor;	//Color of the ball
	private double bLoss;	//Energy loss of the ball
	private double bVel;	//Ball Velocity
	
	
	public gBall(double Xi, double Yi, double bSize, Color bColor, double bLoss, double bVel) {	//Constructor for the gBall
		this.Xi = Xi;		
		this.Yi = Yi;
		this.bSize = bSize;
		this.bColor = bColor;
		this.bLoss = bLoss;
		this.bVel = bVel;

		myBall = new GOval(Xi, Yi, bSize, bSize);	//Creates GOval for the ball
		myBall.setFilled(true);						
		myBall.setFillColor(bColor);				//Sets color of the ball
	}

	
	public void moveTo(double x, double y) {
		myBall.setLocation(x * bSim.SCALE_FACTOR, (bSim.H - bSize) - y * bSim.SCALE_FACTOR);
	}


	public void run() {  //Simulation of the physics of each ball
		double height = 0;
		double initialUpPosition = 0;    	
		boolean directionDown = true; 

		vt = Math.sqrt(2 * g * Yi);

		while (!stopped) {

			if (directionDown) {			//Checks if ball is going down
				height = Yi - (0.5 * g * t * t); //Calculates current xPosition of the Ball

				if (height <= 0) { 			// Checks if ball is on the ground
					Yi = height;   			//Sets current height as Yi
					initialUpPosition = 0;
					directionDown = false; 	//As ball has touched the ground, is must no longer be going down
					t = 0; 
					vt = vt * Math.sqrt(1 - bLoss); //Applies energy loss to ball
				}
			} else {
				height = initialUpPosition + (vt * t) - (0.5 * g * t * t); 

				if (height > Yi) { 			//Checks if initial height is smaller than current height
					Yi = height; 			
				} 							
				else { 						
					directionDown = true; 	
					t = 0;
				}
			}

			
			if (height < 0) {  		//Checks if height is negative
				height = 0; 		//If it is, sets it to 0
			}

			
			if (height == 0) {  	//sets isRunning to false if ball has been on the ground for 2 consecutive cycles
				StoppedCycle = StoppedCycle + 1;
				if (StoppedCycle == 2) {
					isRunning = false; 
				}
			} else {
				StoppedCycle = 0;
			}

			t += INTERVAL_TIME; 		//Increments time
			totalTime += INTERVAL_TIME; 

			if (!stopped && isRunning) {  	//Ensures that ball is still running and has not been frozen by external methods
				myBall.setLocation(Xi * bSim.SCALE_FACTOR + bVel * totalTime, (bSim.H - bSize) - height * bSim.SCALE_FACTOR);
			}

			try { // pauses for 5 milliseconds
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	//A whole bunch of preliminary getters & setters to make coding easier and make sure I don't need to stop 
	//and keep creating getters and some setters 

	public void setXi(double xi) {
		Xi = xi;
	}

	public void setYi(double yi) {
		Yi = yi;
	}

	public void setbSize(double bSize) {
		this.bSize = bSize;
	}

	public void setbLoss(double bLoss) {
		this.bLoss = bLoss;
	}

	public void setbVel(double bVel) {
		this.bVel = bVel;
	}

	public void startBouncing(boolean inMovement) {
		this.isRunning = inMovement;
	}

	public void setTimeY(double timeY) {
		this.t = timeY;
	}

	public void setTimeX(double timeX) {
		this.totalTime = timeX;
	}

	public void setFreeze(boolean freeze) {
		this.stopped = freeze;
	}

	public GOval getMyBall() {
		return myBall;
	}

	public double getbSize() {
		return bSize;
	}

	public double getYi() {
		return Yi;
	}


	public boolean isInMovement() {
		return isRunning;
	}


}
