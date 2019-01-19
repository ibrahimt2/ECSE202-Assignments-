import java.awt.Color;
import javax.swing.*;
import acm.gui.TableLayout;

public class sliderBox {
	private JPanel myPanel;  	
	private JLabel nameLabel;
	private JLabel minLabel;
	private JLabel maxLabel;
	private JSlider mySlider;
	private JLabel sReadout;
	
	public sliderBox(String name, Integer min, Integer max, Integer value) {  //Constructor for the integer values slider box
		myPanel = new JPanel();							//Creates new JPanel
		nameLabel = new JLabel(name);					//Creates name label
		minLabel = new JLabel(min.toString());			//Creates minimum value label
		maxLabel = new JLabel("  "+max.toString());		//Creates maximum value label
		mySlider = new JSlider(min,max,value);			//Creates the actual slider
		sReadout = new JLabel(value.toString());		//Creates current value slider
		sReadout.setForeground(Color.blue );			//Sets the current value to Blue
		myPanel.setLayout(new TableLayout(1,5));
		myPanel.add(nameLabel,"width=100");				//Sets all the widths
		myPanel.add(minLabel,"width=25");				
		myPanel.add(mySlider,"width=100");
		myPanel.add(maxLabel,"width=100");
		myPanel.add(sReadout,"width=80");				//Note: This structure is the same for all others
	}
	
	public sliderBox(String name, Integer min, Integer max, Integer value, Color color, String colorName) { //Constructor for the color slider
		myPanel = new JPanel();
		nameLabel = new JLabel(name);
		minLabel = new JLabel("");
		maxLabel = new JLabel("");
		mySlider = new JSlider(min,max,value);
		sReadout = new JLabel(colorName);
		sReadout.setForeground(color);
		myPanel.setLayout(new TableLayout(1,5));
		myPanel.add(nameLabel,"width=100");
		myPanel.add(minLabel,"width=25");
		myPanel.add(mySlider,"width=100");
		myPanel.add(maxLabel,"width=100");
		myPanel.add(sReadout,"width=80");
	}
	
	public sliderBox(String name, Double min, Double max, Double value) {  //Constructor for the double value sliders 
		
		myPanel = new JPanel();
		nameLabel = new JLabel(name);
		minLabel = new JLabel(min.toString());
		maxLabel = new JLabel("  "+max.toString());
		sReadout = new JLabel(value.toString());
		min = (Double)(min * 100);						//Increases value by a factor of 100
		max = (Double)(max * 100);
		value = (Double)(value * 100);
		mySlider = new JSlider(min.intValue(), max.intValue(), value.intValue()); //Stores the double values as integers because JSlider doesn't take doubles.
		sReadout.setForeground(Color.blue );
		myPanel.setLayout(new TableLayout(1,5));
		myPanel.add(nameLabel,"width=100");
		myPanel.add(minLabel,"width=25");
		myPanel.add(mySlider,"width=100");
		myPanel.add(maxLabel,"width=100");
		myPanel.add(sReadout,"width=80");
	}

	public JPanel getPanel() {			//Getter for the panel
		return myPanel;		
	}

	public JSlider getSlider() {		//Getter for the slider
		return mySlider;
	}	
	
	
	public void setIntegerSliderValue(Integer input) {  //Gets value of integer slider
		sReadout.setText(input.toString());
	}
	
	public void setDoubleSliderValue (Integer input) { 	//Gets value of the double slider
		Double printedDouble = input.doubleValue();	   	//Converts stored int into double
		printedDouble = printedDouble / 100;		   	//Unscales the stored value
		sReadout.setText(printedDouble.toString());
	}
	
	public void setReadValueString(String str, Color color) {
		sReadout.setText(str);
		sReadout.setForeground(color);
	}
}
