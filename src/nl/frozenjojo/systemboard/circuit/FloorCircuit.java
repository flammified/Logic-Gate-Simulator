package nl.frozenjojo.systemboard.circuit;

public class FloorCircuit extends Circuit {

	public FloorCircuit(int x, int y, int w, int h, String n, String ct,
			int inputs, int outputs) {
		super(x, y, 50, 50, "Processors", "Floor", 2, 1);
	}

	@Override
	public float getOutputValue(int outputID) {
		return (float) Math.floor(this.getValue(0));
	}

}
