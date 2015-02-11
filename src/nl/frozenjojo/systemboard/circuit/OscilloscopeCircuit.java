package nl.frozenjojo.systemboard.circuit;

import java.awt.Color;
import java.awt.Graphics;

public class OscilloscopeCircuit extends Circuit
{
	private static final long serialVersionUID = 1L;

	private float[] values;
	private int currentPoint;
	
	public OscilloscopeCircuit(int x, int y)
	{
		super(x, y, 150, 140, "Oscilloscope", "Advanced", 1, 0);
		values = new float[100];
		for(int i=0;i<values.length;i++)
		{
			values[i] = 0;
		}
		currentPoint = 0;
	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		g.setColor(Color.black);
		g.fillRect(getX()+40, getY()+20, 100, 100);
		g.setColor(Color.green);
		//draw the values
		for(int i=1;i<values.length;i++)
		{
			int c = (i-currentPoint)%values.length;
			if(c < 0)
			{
				c = values.length+c;
			}
			if(c == 0)
			{
				continue;
			}
			g.drawLine(getX()+40+c, getY()+120-(int)(values[i-1]/5f*100f), getX()+41+c, getY()+120-(int)(values[i]/5f*100f));
		}
		int c = (values.length-1-currentPoint)%values.length;
		if(c < 0)
		{
			c = values.length+c;
		}
		g.drawLine(getX()+40+c, getY()+120-(int)(values[values.length-1]/5f*100f), getX()+41+c, getY()+120-(int)(values[0]/5f*100f));
		g.setColor(Color.cyan);
		g.drawRect(getX()+40, getY()+20, 100, 100);
	}

	@Override
	public void update()
	{
		super.update();
		values[currentPoint] = getValue(0);
		currentPoint++;
		currentPoint = currentPoint%values.length;
	}
	
	@Override
	public float getOutputValue(int outputID)
	{
		return 0;
	}

}
