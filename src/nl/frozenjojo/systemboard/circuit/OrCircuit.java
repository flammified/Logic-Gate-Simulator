package nl.frozenjojo.systemboard.circuit;

public class OrCircuit extends Circuit{
	private static final long serialVersionUID = 1L;

	public OrCircuit(int x, int y)
	{
		super(x, y, 170, 120, "OR-Gate", "Processors", 2, 1);
	}

	@Override
	public float getOutputValue(int outputID)
	{
		if(getValue(0) == 5 || getValue(1) == 5)
		{
			return 5;
		}
		return 0;
	}

}
