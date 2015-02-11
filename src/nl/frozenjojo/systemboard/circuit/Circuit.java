package nl.frozenjojo.systemboard.circuit;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.Serializable;



public abstract class Circuit implements Serializable
{	
	private static final long serialVersionUID = 1L;
	public static final int IO_SIZE = 20;
	private String name;
	private String category;
	private int x;
	private int y;
	private int width;
	private int height;
	private Link[] links;
	private int inputs;
	private int outputs;
	
	public Circuit(int x, int y,int w,int h, String n, String ct, int inputs, int outputs)
	{
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		this.name = n;
		this.category = ct;
		this.inputs = inputs;
		this.outputs = outputs;
		links = new Link[inputs];
	}
	
	public void paint(Graphics g)
	{
		g.setColor(Color.lightGray);
		g.fillRect(getX(), getY(), getWidth(),getHeight());
		//draw border
		g.setColor(Color.gray);
		g.drawRect(getX(), getY(), getWidth(), getHeight());
		paintIO(g);
		Font f = new Font("Serif", Font.BOLD , 11);
		FontMetrics fm = g.getFontMetrics(f);
		g.setFont(f);
		g.drawString(this.getName(),getX() + (getWidth()/2)- (fm.stringWidth(this.getName())/2), getY() + 10);
	}
	
	public Link[] getLinks()
	{
		return links;
	}

	private void paintIO(Graphics g)
	{
		//draw inputs
		for(int i=0;i<inputs;i++)
		{
			if(getValue(i) > 0)
			{
				g.setColor(Color.cyan);
			}
			else
			{
				g.setColor(Color.blue);
			}
			g.fillRect(getInputX(i), getInputY(i), IO_SIZE, IO_SIZE);
			g.setColor(Color.black);
			g.drawRect(getInputX(i), getInputY(i), IO_SIZE, IO_SIZE);
		}
		//draw outputs
		for(int i=0;i<outputs;i++)
		{
			if(getOutputValue(i) > 0)
			{
				g.setColor(Color.yellow);
			}
			else
			{
				g.setColor(Color.green);
			}
			g.fillRect(getOutputX(i), getOutputY(i), IO_SIZE, IO_SIZE);
			g.setColor(Color.black);
			g.drawRect(getOutputX(i), getOutputY(i), IO_SIZE, IO_SIZE);
		}
	}

	public void click() {}
	
	public int getOutputX(int outputID)
	{
		return getX()+getWidth()-(int)(IO_SIZE*1.5);
	}

	public int getInputX(int inputID)
	{
		return getX()+IO_SIZE/2;
	}

	public int getInputY(int inputID)
	{
		if(inputs == 1)
		{
			return getY()+getHeight()/2-IO_SIZE/2;
		}
		return (int) (getY()+IO_SIZE/2+(getHeight()-(IO_SIZE*2))/((float)inputs-1)*inputID);
	}
	
	public int getOutputY(int inputID)
	{
		if(outputs == 1)
		{
			return getY()+getHeight()/2-IO_SIZE/2;
		}
		return (int) (getY()+IO_SIZE/2+(getHeight()-60)/((float)outputs-1)*inputID);
	}

	public void update() {}
	
	public boolean contains(int nx, int ny)
	{
		boolean xin = nx > this.x && nx < (this.x + this.width);
		boolean yin = ny > this.y && ny < (this.y + this.height);
		return xin && yin;
	}
	
	public float getValue(int inputID)
	{
		if(links[inputID] == null)
		{
			return 0;
		}
		try
		{
			return links[inputID].getValue();
		}
		catch(StackOverflowError e)
		{
			return 0;
		}
	}
	
	public void setLink(int inputID, Link l)
	{
		links[inputID] = l;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	public abstract float getOutputValue(int outputID);

	public int getInputs()
	{
		return inputs;
	}

	public int getOutputs()
	{
		return outputs;
	}

}
