package nl.frozenjojo.systemboard.circuit;

import java.awt.Graphics;


public class SensorCircuit extends Circuit
{
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static int sensors = 0;
	private static MicrophoneThread m;
	private float value;
	
	public SensorCircuit(int x, int y)
	{
		super(x, y, 120, 50, "Sensor", "Inputs", 0, 1);
		sensors++;
		if(m == null)
		{
				m = new MicrophoneThread();
		}
	}

	public void update() 
	{
		value = Math.min(5, Math.max(0, m.getVolume()/20f));
	}
	
	@Override
	public void paint(Graphics g) 
	{
		super.paint(g);
		g.drawString("Value: " + value + "V",getX() + 1,getY() + 40);
	
	}
	
	@Override
	public float getOutputValue(int outputID)
	{

		return value;
	}

}
