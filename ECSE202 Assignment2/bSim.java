import java.awt.Color;					//Necessary imports
import acm.graphics.*;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class bSim extends GraphicsProgram {

	private static final int WIDTH = 1200; 		// n.b. screen coords
	private static final int HEIGHT = 800;
	private static final int OFFSET = 200;
	private static final int NUMBALLS = 100; 	// # balls to sim.
	private static final double MINSIZE = 3; 	// Min ball size
	private static final double MAXSIZE = 20;	// Max ball size
	private static final double XMIN = 10; 		// Min X start location
	private static final double XMAX = 50; 		// Max X start location
	private static final double YMIN = 50; 		// Min Y start location
	private static final double YMAX = 100; 	// Max Y start location
	private static final double EMIN = 0.1; 	// Min loss coeff.
	private static final double EMAX = 0.3; 	// Max loss coeff.
	private static final double VMIN = 0.5; 	// Min X velocity
	private static final double VMAX = 3.0; 	// Max Y velocity
	private static final double GROUND_BEGIN = 0.0; //X coordinates for the start of ground
	private static final double GROUND_END = 1200;  // X coordinates for the end of ground
	
	RandomGenerator rgen = new RandomGenerator(); 		//Creates a random generator to use for creating values
	
	gBall[] BallArray;
	
	public void run() {
		this.resize(WIDTH, HEIGHT);						//Adjusts canvas dimensions
		BallArray = new gBall[NUMBALLS]; 				//Creating a gBall array with 100 spaces
		
		GLine Ground = new GLine(GROUND_BEGIN, HEIGHT - OFFSET, GROUND_END, HEIGHT - OFFSET);   	//Creates the ground
		add(Ground); 
		
		for (int i = 0; i < BallArray.length; i++) { 	//Iterates over the array, and creates 100 gBalls
			double Xi = rgen.nextDouble(XMIN, XMAX); 	//Random generation of variables values within given limits
			double Yi = rgen.nextDouble(YMIN, YMAX);
			double bSize = rgen.nextDouble(MINSIZE, MAXSIZE);
			Color bColor = rgen.nextColor();
			double bLoss = rgen.nextDouble(EMIN, EMAX);
			double bVel = rgen.nextDouble(VMIN, VMAX); //The six adjusts for metres
			
			BallArray[i] = new gBall(Xi, Yi, bSize, bColor, bLoss, bVel); //Creates a gBall with given values and stores it in the BallArray
			
		}
		
		for (int i = 0; i < BallArray.length; i++) {	//Iterating over array again
			add(BallArray[i].BouncingBall);				//Adds a ball from the Array onto the canvas
			BallArray[i].start();						//Starts the thread that controls the physics of the ball and changes it's location in gBall Class
		}
	}
	
	
}
