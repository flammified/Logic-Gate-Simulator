package nl.frozenjojo.systemboard.circuit;

@SuppressWarnings("serial")
public class TanCircuit extends Circuit {
private int i;
	public TanCircuit(int x, int y) {
		super(x, y, 40, 40, "Tan", "Advanced", 0, 1);
	}

	@Override
	public float getOutputValue(int outputID) {
			if (i < 360) i++; else i = 0;
		return (float) Math.abs(Math.tan(Math.toRadians(i))*5);
	}

}
