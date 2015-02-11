package nl.frozenjojo.systemboard.circuit;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class PulseGeneratorCircuit extends Circuit
{
	private static final long serialVersionUID = 1L;
	private long previousTime;
	private boolean state;
	private boolean click;
	private float hertz;
	
	public PulseGeneratorCircuit(int x, int y)
	{
		super(x, y, 100, 50, "Pulse Generator", "Inputs", 0, 1);
		previousTime = System.currentTimeMillis();
		state = true;
		click = false;
		hertz = 1;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		//int fh = g.getFontMetrics().getHeight();
		g.drawString("Frequency: " + hertz, getX() + 5, getY()+getHeight() - 3);
	}
	
	@Override
	public void update()
	{
		super.update();
		if(System.currentTimeMillis()-previousTime > (1/hertz) * 1000)
		{
			state = !state;
			previousTime = System.currentTimeMillis();
		}
	}
	
	@Override
	public void click() {
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
		                    	"Enter a frequency: ",
		                    	"Enter a frequency",
		                    	JOptionPane.INFORMATION_MESSAGE,
		                    	null,
		                    	null,
		                    	""+hertz);
			
				//If a string was returned, say so.
				if ((s != null) && (s.length() > 0)) {
					try {
						hertz = Float.parseFloat(s);
						hertz = (hertz > 30) ? 30 : hertz;
					}
					catch(NumberFormatException e) {
					}
				}
		}
		
	}

	@Override
	public float getOutputValue(int outputID)
	{
		return state?5:0;
	}

}
