package nl.frozenjojo.systemboard.circuit;

import java.awt.Graphics;

public class ADConvertorCircuit extends Circuit {

	private static final long serialVersionUID = 1L;

	public ADConvertorCircuit(int x, int y) {
		super(x, y, 100, 150, "AD-Convertor", "Processors", 1, 3);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawString("1", this.getOutputX(0) - Circuit.IO_SIZE, this.getOutputY(0) + (Circuit.IO_SIZE/2));
		g.drawString("2", this.getOutputX(1) - Circuit.IO_SIZE, this.getOutputY(1) + (Circuit.IO_SIZE/2));
		g.drawString("4", this.getOutputX(2) - Circuit.IO_SIZE, this.getOutputY(2) + (Circuit.IO_SIZE/2));
	}

	@Override
	public float getOutputValue(int outputID) 
	{
		switch (outputID) {
			case 0:
				if (((int)(Math.floor(getValue(0))) & 1) == 1) return 5;
				else return 0;
			case 1:
				if (((int)(Math.floor(getValue(0))) & 2) == 2) return 5;
				else return 0;
			case 2:
				if (((int)(Math.floor(getValue(0))) & 4) == 4) return 5;
				else return 0;
			default:
				return 0;
		}
	}

}
