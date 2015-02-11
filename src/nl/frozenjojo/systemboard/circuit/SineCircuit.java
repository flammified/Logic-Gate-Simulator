package nl.frozenjojo.systemboard.circuit;

@SuppressWarnings("serial")
public class SineCircuit extends Circuit {
private int i;
	public SineCircuit(int x, int y) {
		super(x, y, 40, 40, "Sine", "Advanced", 0, 1);
	}

	@Override
	public float getOutputValue(int outputID) {
			if (i < 360) i++; else i = 0;
		return (float) Math.abs(Math.sin(Math.toRadians(i))*5);
	}

}
