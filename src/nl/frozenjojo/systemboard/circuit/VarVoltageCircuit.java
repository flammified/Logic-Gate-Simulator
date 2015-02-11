package nl.frozenjojo.systemboard.circuit;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class VarVoltageCircuit extends Circuit {
	private float voltage;
	private float previousTime;
	private boolean click;
	
	public VarVoltageCircuit(int x, int y) {
		super(x,y,130,50,"Variable Voltage", "Inputs", 0, 1);
	}

	/** Check for double clicks and raise a dialog when it is */
	public void click() {	
		super.update();
		if(System.currentTimeMillis()-previousTime > 500)
		{
			previousTime = System.currentTimeMillis();
			click = false;
		}
		if (click == false) click = true;
		else if(click == true) {
			JFrame frame = new JFrame();
			String s = (String)JOptionPane.showInputDialog(
		                    	frame,
		                    	"Enter a voltage: ",
		                    	"Enter a voltage",
		                    	JOptionPane.PLAIN_MESSAGE,
		                    	null,
		                    	null,
		                    	""+voltage);
			
				//If a string was returned, say so.
				if ((s != null) && (s.length() > 0)) {
					try {
						voltage = Float.parseFloat(s);
						voltage = (voltage > 5) ? 5 : voltage;
						voltage = (voltage < 0) ? 0 : voltage;
					}
					catch(NumberFormatException e) {
					}
				}
		}
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		int fh = g.getFontMetrics().getHeight();
		g.drawString("Voltage: " + voltage + "V", getX() + 5, getY()+getHeight() - fh);
	}

	@Override
	public float getOutputValue(int outputID) {
		return voltage;
	}
}
