package nl.frozenjojo.systemboard.circuit;

import java.awt.Color;
import java.awt.Graphics;

public class ButtonCircuit extends Circuit
{
	private static final long serialVersionUID = 1L;
	private final Color on = Color.green;
	private final Color off = Color.red;
	private boolean state;

	public ButtonCircuit(int x, int y)
	{
		super(x, y, 100, 50, "Button", "Inputs", 0, 1);
		state = false;
	}

	public void paint(Graphics g) 
	{
		super.paint(g);
			
		if (state == false) g.setColor(off);
		else g.setColor(on);
		
		g.fillRect(getX() + (getWidth()/10), getY() + (getHeight()/10 * 3), (getWidth()*4)/10, (getHeight()*4)/10);
		
		g.setColor(Color.black);
		g.drawRect(getX() + (getWidth()/10), getY() + (getHeight()/10 * 3), (getWidth()*4)/10, (getHeight()*4)/10);
		String caption = state?"On":"Off";
		g.drawString(caption, getX() + (getWidth()/10)*2, getY() + (getHeight()/10 * 6));
	}
	@Override
	public float getOutputValue(int outputID)
	{
		return state ? 5 : 0;
	}
	
	public void click() {
		state = !state;
	}
	

}
