import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.util.ArrayList;
import acm.graphics.*;
import acm.gui.DoubleField;
import acm.gui.TableLayout;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class bSim extends GraphicsProgram implements ChangeListener, ItemListener{
	
	public void run() {
		this.resize(WIDTH, HEIGHT + OFFSET); //Resizes window
	}

	public void init() {    		//Initializes all slider, combo boxes, listeners etc.
		
		JPanel panelActionChoosers = new JPanel();			//Creation of main ComboBox
		ACTIONBOX.addItemListener((ItemListener)this);
		panelActionChoosers.add(new JLabel("Action to perform : "));
		panelActionChoosers.add(ACTIONBOX);
		ACTIONBOX.addItem("None");
		ACTIONBOX.addItem("Run");
		ACTIONBOX.addItem("Sort");

		add(panelActionChoosers, NORTH);
		
		add(new JLabel("Single Ball Instance Parameters:"), EAST);  //Adds the sliders and adds Change Listeners to them
		add(BALLSIZE_slider.getPanel(), EAST);
		BALLSIZE_slider.getSlider().addChangeListener((ChangeListener)this);
		
		add(LOSS_slider.getPanel(), EAST);
		LOSS_slider.getSlider().addChangeListener((ChangeListener)this);
		
		add(HORIZONTALVELOCITY_slider.getPanel(), EAST);
		HORIZONTALVELOCITY_slider.getSlider().addChangeListener((ChangeListener)this);
		
		add(COLOR_slider.getPanel(), EAST);
		COLOR_slider.getSlider().addChangeListener((ChangeListener)this);
		
		add(new JLabel("General Simulation Parameters:"), EAST);
		add(NUMBALLS_slider.getPanel(), EAST);
		NUMBALLS_slider.getSlider().addChangeListener((ChangeListener)this);
		
		add(MINSIZE_slider.getPanel(), EAST);
		MINSIZE_slider.getSlider().addChangeListener((ChangeListener)this);
		
		add(MAXSIZE_slider.getPanel(), EAST);
		MAXSIZE_slider.getSlider().addChangeListener((ChangeListener)this);
		
		add(XMIN_slider.getPanel(), EAST);
		XMIN_slider.getSlider().addChangeListener((ChangeListener)this);
		
		add(XMAX_slider.getPanel(), EAST);
		XMAX_slider.getSlider().addChangeListener((ChangeListener)this);
		
		add(YMIN_slider.getPanel(), EAST);
		YMIN_slider.getSlider().addChangeListener((ChangeListener)this);
		
		add(YMAX_slider.getPanel(), EAST);
		YMAX_slider.getSlider().addChangeListener((ChangeListener)this);
		
		add(LOSSMIN_slider.getPanel(), EAST);
		LOSSMIN_slider.getSlider().addChangeListener((ChangeListener)this);
		
		add(LOSSMAX_slider.getPanel(), EAST);
		LOSSMAX_slider.getSlider().addChangeListener((ChangeListener)this);
		
		add(XVELOCITYMAX_slider.getPanel(), EAST);
		XVELOCITYMAX_slider.getSlider().addChangeListener((ChangeListener)this);
		
		add(XVELOCITYMAX_slider.getPanel(), EAST);
		XVELOCITYMAX_slider.getSlider().addChangeListener((ChangeListener)this);
		
		add(COLOR_slider.getPanel(), EAST);
		COLOR_slider.getSlider().addChangeListener((ChangeListener)this);

		addMouseListeners();     
		addActionListeners();
	}
	

	private void startSimulation() {
		myTree = new bTree();
		NUMBALLS =  NUMBALLS_slider.getSlider().getValue(); //Gets number of balls directly from slider
		
		ground.setFilled(true);                     		//Creates ground
		add(ground);
				
		for(int i = 0; i < NUMBALLS ; i++) {
			
			//This block of code gets maximum and minimum ball variable values from the sliders
			//and then generates a random value between min and max.
			
			double xValue = rgen.nextDouble(XMIN_slider.getSlider().getValue()/100, XMAX_slider.getSlider().getValue()/100);
			double yValue = rgen.nextDouble(YMIN_slider.getSlider().getValue()/100, YMAX_slider.getSlider().getValue()/100);
			double ballSize = rgen.nextDouble(MINSIZE_slider.getSlider().getValue()/100*2*SCALE_FACTOR, MAXSIZE_slider.getSlider().getValue()/100*2*SCALE_FACTOR);
			Color color = rgen.nextColor();
			double loss = rgen.nextDouble(LOSSMIN_slider.getSlider().getValue()/100, LOSSMAX_slider.getSlider().getValue()/100);
			double Xvel = rgen.nextDouble(XVELOCITYMIN_slider.getSlider().getValue()/100*SCALE_FACTOR, XVELOCITYMAX_slider.getSlider().getValue()/100*SCALE_FACTOR);
			gBall iBall = new gBall(xValue, yValue, ballSize, color, loss, Xvel); //Creates ball using randomly generated parameters
			add(iBall.getMyBall());   //Adds to canvas
			iBall.start();			  //Starts the simulation of the ball
			myTree.addNode(iBall); 	  //Adds ball to bTree
		}
	
	}
	

	public void mousePressed(MouseEvent e) {
		clickedObj = getElementAt(e.getX(), e.getY()); 	 //Selects object where mouse was clicked
		if(clickedObj != null && clickedObj != ground) { //Doesn't store ground or background if clicked
			clickedGOval = (GOval) clickedObj;
			clickedNode = myTree.findNode(clickedGOval);
			clickedBall = clickedNode.data;
			clickedBall.setFreeze(true); 				//Freezes the clicked ball
		}
	}

	public void mouseReleased(MouseEvent e) { //Basically removes the clicked ball and replaces it with a new ball creating with the new parameters

		if(clickedBall != null) {  				//Makes sure a ball was clicked
			clickedBall.setFreeze(true);			//Stopped ball from moving
			remove(clickedBall.getMyBall());    //Removes the ball
			
			double BallSize = (double)BALLSIZE_slider.getSlider().getValue()/100 * 2 * SCALE_FACTOR;
			double y = (HEIGHT-e.getY()) / SCALE_FACTOR - BallSize/ 2 / SCALE_FACTOR;
			if(y < 0) {  //Makes sure the ball doesn't go below the ground
				y = 0;
			}
			
			gBall ball = new gBall((double)e.getX()/SCALE_FACTOR - BallSize/2/SCALE_FACTOR,  //Creates the new ball 
								   y,
								   BallSize,
								   colorArray[COLOR_slider.getSlider().getValue()],
								   (double)LOSS_slider.getSlider().getValue()/100,
								   (double)HORIZONTALVELOCITY_slider.getSlider().getValue()/100*SCALE_FACTOR);
			
			add(ball.getMyBall());
			ball.start();		   //Starts the ball
			myTree.addNode(ball);  //Adds Ball to the bTree
			myTree = myTree.changeTree(clickedBall);
			clickedBall = null;
		}
	}
	
	
	public void mouseDragged(MouseEvent e) {  //Moves the ball when dragged
		if(clickedBall != null) {
			clickedBall.getMyBall().setLocation(e.getX()-clickedBall.getbSize()/2, e.getY()-clickedBall.getbSize()/2);
		}
	}
	
	public void itemStateChanged(ItemEvent e) {  //Finds out which option was selected in the action box and takes appropriate action
		if(ACTIONBOX.getSelectedIndex() == 1) {
			removeAll();
			if(myTree != null) myTree.clearTree();
			startSimulation();
			ACTIONBOX.setSelectedIndex(0);
		}
		if(ACTIONBOX.getSelectedIndex() == 2) {
			try {
				if(!myTree.isRunning()) {
					myTree.moveSort();
				}
			}catch(NullPointerException ex) {
				
			}finally {
				ACTIONBOX.setSelectedIndex(0);
			}
		}
		
	}
	
	public void stateChanged(ChangeEvent e) { //Finds out which slider was changed and changes the Double/Integer value
		JSlider source = (JSlider)e.getSource();
		
		if(source == NUMBALLS_slider.getSlider()) {
			NUMBALLS_slider.setIntegerSliderValue(NUMBALLS_slider.getSlider().getValue());
		} else if(source == MINSIZE_slider.getSlider()) {
			MINSIZE_slider.setDoubleSliderValue(MINSIZE_slider.getSlider().getValue());
		} else if(source == MAXSIZE_slider.getSlider()) {
			MAXSIZE_slider.setDoubleSliderValue(MAXSIZE_slider.getSlider().getValue());
		} else if(source == XMIN_slider.getSlider()) {
			XMIN_slider.setDoubleSliderValue(XMIN_slider.getSlider().getValue());
		} else if(source == XMAX_slider.getSlider()) {
			XMAX_slider.setDoubleSliderValue(XMAX_slider.getSlider().getValue());
		} else if(source == YMIN_slider.getSlider()) {
			YMIN_slider.setDoubleSliderValue(YMIN_slider.getSlider().getValue());
		} else if(source == YMAX_slider.getSlider()) {
			YMAX_slider.setDoubleSliderValue(YMAX_slider.getSlider().getValue());
		} else if(source == LOSSMIN_slider.getSlider()) {
			LOSSMIN_slider.setDoubleSliderValue(LOSSMIN_slider.getSlider().getValue());
		} else if(source == LOSSMAX_slider.getSlider()) {
			LOSSMAX_slider.setDoubleSliderValue(LOSSMAX_slider.getSlider().getValue());
		} else if(source == XVELOCITYMAX_slider.getSlider()) {
			XVELOCITYMAX_slider.setDoubleSliderValue(XVELOCITYMAX_slider.getSlider().getValue());
		} else if(source == XVELOCITYMAX_slider.getSlider()) {
			XVELOCITYMAX_slider.setDoubleSliderValue(XVELOCITYMAX_slider.getSlider().getValue());
		} else if(source ==  COLOR_slider.getSlider()) {
			COLOR_slider.setReadValueString(colorArrayNames[COLOR_slider.getSlider().getValue()], colorArray[COLOR_slider.getSlider().getValue()]);
		} else if(source == BALLSIZE_slider.getSlider()) {
			NUMBALLS_slider.setIntegerSliderValue(NUMBALLS_slider.getSlider().getValue());
		} else if(source == LOSS_slider.getSlider()) {
			LOSS_slider.setDoubleSliderValue(LOSS_slider.getSlider().getValue());
		} else if(source == HORIZONTALVELOCITY_slider.getSlider()) {
			HORIZONTALVELOCITY_slider.setDoubleSliderValue(HORIZONTALVELOCITY_slider.getSlider().getValue());
		} else if(source == BALLSIZE_slider.getSlider()) {
			BALLSIZE_slider.setDoubleSliderValue(BALLSIZE_slider.getSlider().getValue());
		}
	}
	
	private RandomGenerator rgen = new RandomGenerator();
	private bTree myTree;
	private GRect ground = new GRect(0, HEIGHT, WIDTH, 3);
	private GObject clickedObj;
	private GOval clickedGOval;
	private gBall clickedBall;
	private bNode clickedNode;
	private Color[] colorArray = {Color.GRAY, Color.BLUE, Color.PINK, Color.DARK_GRAY, Color.BLACK, Color.MAGENTA, Color.WHITE, Color.GREEN, Color.RED, Color.CYAN, Color.ORANGE, Color.LIGHT_GRAY, Color.YELLOW};
	private String[] colorArrayNames = {"GRAY", "BLUE", "PINK", "DARK GRAY", "BLACK", "MAGENTA", "WHITE", "GREEN", "RED","CYAN", "ORANGE", "LIGHTGRAY", "YELLOW"};
	
	/* Creates all of the sliders needed in the program */
	
	sliderBox NUMBALLS_slider = new sliderBox("NUMBALLS", NUMBALLS_MIN, NUMBALLS_MAX, (NUMBALLS_MIN+NUMBALLS_MAX)/2);
	sliderBox MINSIZE_slider = new sliderBox("MIN SIZE", MINSIZE, MAXSIZE, MINSIZE);
	sliderBox MAXSIZE_slider = new sliderBox("MAX SIZE", MINSIZE, MAXSIZE, MAXSIZE);
	sliderBox XMIN_slider = new sliderBox("X MIN", XMIN, XMAX, XMIN);
	sliderBox XMAX_slider = new sliderBox("X MAX", XMIN, XMAX, XMAX);
	sliderBox YMIN_slider = new sliderBox("Y MIN", YMIN, YMAX, YMIN);
	sliderBox YMAX_slider = new sliderBox("Y MAX", YMIN, YMAX, YMAX);
	sliderBox LOSSMIN_slider = new sliderBox("LOSS MIN", EMIN, EMAX, EMIN);
	sliderBox LOSSMAX_slider = new sliderBox("LOSS MAX", EMIN, EMAX, EMAX);
	sliderBox XVELOCITYMIN_slider = new sliderBox("X VEL MIN", VMIN, VMAX, VMIN);
	sliderBox XVELOCITYMAX_slider = new sliderBox("X VEL MAX", VMIN, VMAX, VMAX);
	sliderBox BALLSIZE_slider = new sliderBox("Ball Size", MINSIZE, MAXSIZE, (MINSIZE+MAXSIZE) / 2);
	sliderBox HORIZONTALVELOCITY_slider = new sliderBox("X Vel", VMIN, VMAX, (VMIN+VMAX)/2);
	sliderBox LOSS_slider = new sliderBox("E Loss", EMIN, EMAX, (EMIN+EMAX)/2);
	sliderBox COLOR_slider = new sliderBox("Color", 0, colorArray.length-1, colorArray.length/2, colorArray[colorArray.length/2], colorArrayNames[colorArray.length/2]);
	JComboBox ACTIONBOX = new JComboBox();
	
	/*Declares all variables needed in the program */
	
	private static final int WIDTH = 1800; 		// WIDTH, HEIGHT, and OFFSET
	private static final int HEIGHT = 600; 		// define the screen paramaters
	private static final int OFFSET = 200; 		// and represend PIXEL coordinates
	private static final double MINSIZE = 1;	// Min ball size (MKS from here down)
	private static final double MAXSIZE = 8;	// Max ball size
	private static final double XMIN = 10; 		// Min X starting location
	private static final double XMAX = 50; 		// Max X starting location
	private static final double YMIN = 50; 		// Min Y starting location
	private static final double YMAX = 100; 	// Max Y starting location
	private static final double EMIN = 0.0; 	// Minimum loss coefficient
	private static final double EMAX = 1.0; 	// Maximum loss coefficient
	private static final double VMIN = 0.0; 	// Minimum X velocity
	private static final double VMAX = 5.0; 	// Maximum Y velocity
	private static final int NUMBALLS_MIN = 1;
	private static final int NUMBALLS_MAX = 25;
	public static final double SCALE_FACTOR = (double) HEIGHT / YMAX;
	public static final double H = HEIGHT;
	int NUMBALLS;
	
	
}
