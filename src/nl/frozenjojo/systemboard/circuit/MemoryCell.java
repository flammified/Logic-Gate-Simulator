package nl.frozenjojo.systemboard.circuit;

import java.awt.Graphics;

public class MemoryCell extends Circuit {
	private static final long serialVersionUID = 1L;
	private float mem;
	
	public MemoryCell(int x, int y)
	{
		super(x, y, 110, 100, "Memory-Cell", "Processors", 2, 1);
		mem = 0;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawString("Set", this.getInputX(0) + Circuit.IO_SIZE + 10, this.getInputY(0) + (Circuit.IO_SIZE/2));
		g.drawString("Reset", this.getInputX(1) + Circuit.IO_SIZE + 10, this.getInputY(1) + (Circuit.IO_SIZE/2));
	}
	@Override
	public void update() 
	{
		if (this.getValue(0) == 5) mem = 5;
		if (this.getValue(1) == 5) mem = 0;
	}
	
	@Override
	public float getOutputValue(int outputID) 
	{
		return mem;
	}

	
}
