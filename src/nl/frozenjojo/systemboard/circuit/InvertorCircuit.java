package nl.frozenjojo.systemboard.circuit;

@SuppressWarnings("serial")
public class InvertorCircuit extends Circuit {

	public InvertorCircuit(int x, int y)
	{
		super(x, y, 100,50, "Invertor", "Processors", 1, 1);
	}

	@Override
	public float getOutputValue(int outputID) {
		return getValue(0) == 5 ? 0 : 5;
	}
	
	

}
