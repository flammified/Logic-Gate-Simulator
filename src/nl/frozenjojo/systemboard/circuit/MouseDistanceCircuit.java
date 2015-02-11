package nl.frozenjojo.systemboard.circuit;

@SuppressWarnings("serial")
public class MouseDistanceCircuit extends Circuit {

	public MouseDistanceCircuit(int x, int y) {
		super(x, y, 40, 40, "Mouse Distance Sensor", "Inputs", 0, 1);
	}

	@Override
	public float getOutputValue(int outputID) {
		//Hoe krijg ik de muis positie zonder de desbetreffende Class?
		return 0;
	}

}
