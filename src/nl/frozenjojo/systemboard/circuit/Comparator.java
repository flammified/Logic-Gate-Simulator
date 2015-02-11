package nl.frozenjojo.systemboard.circuit;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Comparator extends Circuit {
	private static final long serialVersionUID = 1L;
	private float refVol;
	private long previousTime;
	private boolean click; //Click boolean for double click detection.
	
	public Comparator(int x,int y)
	{
		super(x, y, 170, 70, "Comparator", "Processors", 1, 1);
		refVol = 2.5f;
		previousTime = System.currentTimeMillis();
		click = false;
	}
	
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
		                    	"Enter the Reference Voltage: ",
		                    	"Enter a voltage",
		                    	JOptionPane.PLAIN_MESSAGE,
		                    	null,
		                    	null,
		                    	""+refVol + "V");
			
				//If a string was returned, say so.
				if ((s != null) && (s.length() > 0)) {
					try {
						refVol = Float.parseFloat(s);
						refVol = (refVol > 5) ? 5 : refVol;
						refVol = (refVol < 0) ? 0 : refVol;
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
		g.drawString("Reference Voltage: " + refVol, getX() + 5, getY()+getHeight() - fh);
	}

	@Override
	public float getOutputValue(int outputID) 
	{
		if (this.getValue(0) < this.refVol)
		{
			return 0;
		}
		else return 5;
	}
}
