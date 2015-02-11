package nl.frozenjojo.systemboard.circuit;

import java.awt.Toolkit;

public class BuzzerCircuit extends Circuit {
	private static final long serialVersionUID = 1L;

	public BuzzerCircuit(int x, int y)
	{
		super(x, y, 60, 40, "Buzzer", "Outputs", 1, 0);
	}

	@Override
	public void update() 
	{
		if(getValue(0) == 5)
		{
			Toolkit.getDefaultToolkit().beep();
		}
	}
	
	
	@Override
	public float getOutputValue(int outputID) {
		return 0;
	}

}
