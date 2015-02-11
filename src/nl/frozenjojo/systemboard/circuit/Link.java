package nl.frozenjojo.systemboard.circuit;

public class Link
{
	private Circuit c;
	private int outputID;
	
	public Link(Circuit c, int outputID)
	{
		super();
		this.c = c;
		this.outputID = outputID;
	}
	
	public Circuit getCircuit()
	{
		return c;
	}
	public void setCircuit(Circuit c)
	{
		this.c = c;
	}
	public int getOutputID()
	{
		return outputID;
	}
	public void setOutputID(int outputID)
	{
		this.outputID = outputID;
	}

	public float getValue()
	{
		return Math.max(0,Math.min(5,c.getOutputValue(outputID)));
	}
}
