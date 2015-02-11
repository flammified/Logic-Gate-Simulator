package nl.frozenjojo.systemboard.circuit;

import java.awt.Color;
import java.awt.Graphics;

public class LampCircuit extends Circuit
{
	private static final long serialVersionUID = 1L;

	public LampCircuit(int x, int y)
	{
		super(x, y, 100, 50, "Lamp", "Outputs", 1, 0);
	}
	
	public LampCircuit(int x, int y, int width, int height, String name, String ct, int inputs, int outputs) {
		super(x,y,width,height,name,ct,inputs,outputs);
	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		g.setColor(this.getColor());
		g.fillRect(getX()+getWidth()/2+10, getY()+getHeight()/2-10, 20, 20);
	}

	protected Color getColor() {
		return new Color((int)((getValue(0) * 255 / 5)), 0, 0);
		//return Color.blue;
	}
	
	@Override
	public float getOutputValue(int outputID)
	{
		return 0;
	}

}
