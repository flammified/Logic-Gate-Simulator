package nl.frozenjojo.systemboard.circuit;

import java.awt.Color;
import java.awt.Graphics;

@SuppressWarnings("serial")
public class ColorLampCircuit extends LampCircuit {
	
	public ColorLampCircuit(int x, int y) {
		super(x, y, 100,140,"Color-Lamp","Advanced",4,0);
	}
	
	@Override
	public Color getColor() {
		if (this.getValue(3) != 0) {
			Color c = new Color((int)(255*(int)getValue(0)/5), (int)(255*getValue(1)/5), (int)(255*(int)getValue(2)/5));
			return c;
		}
		else {
			return Color.BLACK;
		}
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.BLACK);
		g.drawString("R", this.getInputX(0) + Circuit.IO_SIZE + 10, this.getInputY(0) + (Circuit.IO_SIZE/2));
		g.drawString("G", this.getInputX(1) + Circuit.IO_SIZE + 10, this.getInputY(1) + (Circuit.IO_SIZE/2));
		g.drawString("B", this.getInputX(2) + Circuit.IO_SIZE + 10, this.getInputY(2) + (Circuit.IO_SIZE/2));
		g.drawString("On/off", this.getInputX(0) + Circuit.IO_SIZE + 10, this.getInputY(3) + (Circuit.IO_SIZE/2));
	}
	
	@Override
	public float getOutputValue(int outputID) {
		return 0;
	}

}
