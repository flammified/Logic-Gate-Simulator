package nl.frozenjojo.systemboard.circuit;

import java.awt.Graphics;

@SuppressWarnings("serial")
public class CounterCircuit extends Circuit 
{
	private boolean state; 
	private int count;
	
	
	public CounterCircuit(int x, int y) 
	{
		super(x, y, 170,200,"Counter", "Processors",3,4);
		state = false;
		count = 0;
	}
	
	@Override
	public void update() 
	{
		if (getValue(0) == 5 && state == false) state = true;
		else if (getValue(0) == 0 && state == true)
		{
			if (getValue(1) != 0) {
				state = false;
				count++;
				if (count > 15) count = 0;
			}
		}
		if (this.getValue(2) == 5) count = 0;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawString("Pulse", this.getInputX(0) + Circuit.IO_SIZE + 10, this.getInputY(0) + (Circuit.IO_SIZE/2));
		g.drawString("On/off", this.getInputX(1) + Circuit.IO_SIZE + 10, this.getInputY(1) + (Circuit.IO_SIZE/2));
		g.drawString("Reset", this.getInputX(2) + Circuit.IO_SIZE + 10, this.getInputY(2) + (Circuit.IO_SIZE/2));
		g.drawString("1", this.getOutputX(0) - Circuit.IO_SIZE, this.getOutputY(0) + (Circuit.IO_SIZE/2));
		g.drawString("2", this.getOutputX(1) - Circuit.IO_SIZE, this.getOutputY(1) + (Circuit.IO_SIZE/2));
		g.drawString("4", this.getOutputX(2) - Circuit.IO_SIZE, this.getOutputY(2) + (Circuit.IO_SIZE/2));
		g.drawString("8", this.getOutputX(3) - Circuit.IO_SIZE, this.getOutputY(3) + (Circuit.IO_SIZE/2));
	}
	
	@Override
	public float getOutputValue(int outputID) 
	{
		switch (outputID) {
			case 0:
				if ((count & 1) == 1) return 5;
				else return 0;
			case 1:
				if ((count & 2) == 2) return 5;
				else return 0;
			case 2:
				if ((count & 4) == 4) return 5;
				else return 0;
			case 3:
				if ((count & 8) == 8) return 5;
				else return 0;
			default:
				return 0;
		}
				
	}

}
