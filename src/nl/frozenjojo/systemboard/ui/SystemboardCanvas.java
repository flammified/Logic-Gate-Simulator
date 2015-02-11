package nl.frozenjojo.systemboard.ui;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JComponent;

import nl.frozenjojo.systemboard.circuit.*;



@SuppressWarnings("serial")
public class SystemboardCanvas extends JComponent implements MouseListener, MouseMotionListener, KeyListener, Runnable
{
	private final int TARGET_FPS = 30;
	
	private ArrayList<Circuit> circuits;
	private Circuit holding;
	/**The ID of the input or output that is being dragged*/
	private int dragID;
	private int xoffset = 0;
	private int yoffset = 0;
	private Thread t;
	private DragType dragType;
	
	/**Variables for drawing lines while dragging*/
	private int lastKnownX;
	private int lastKnownY;
	private int clickX;
	private int clickY;
	
	public SystemboardCanvas()
	{
		super();
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addKeyListener(this);
		holding = null;
		
		/** Used for dragging and other mouse operations*/
		lastKnownX = 0;
		lastKnownY = 0;
		clickX = 0;
		clickY = 0;
		
		setBackground(Color.white);
		circuits = new ArrayList<Circuit>();
		t = new Thread(this);
		t.start();
		dragType = DragType.NONE;
	}

	@Override
	public void paint(Graphics g) 
	{
		requestFocusInWindow();
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		//draw grid
		g.setColor(new Color(0,0,0,100));
		for(int i=0;i<getWidth()/10+1;i++)
		{
			g.fillRect(i*10, 0, 1, getHeight());
		}
		for(int i=0;i<getHeight()/10+1;i++)
		{
			g.fillRect(0, i*10, getWidth(), 1);
		}
		//draw circuits
		for(Circuit c: circuits)
		{
			c.paint(g);
		}
		//draw links
		g.setColor(Color.red);
		for(Circuit c: circuits)
		{
			int i=0;
			for(Link l: c.getLinks())
			{
				if(l == null)
				{
					i++;
					continue;
				}
				Circuit c2 = l.getCircuit();
				g.setColor(new Color(255,50,50));
				g.drawLine(c.getInputX(i)+Circuit.IO_SIZE/2, c.getInputY(i)+Circuit.IO_SIZE/2, c2.getOutputX(i)+Circuit.IO_SIZE/2, c2.getOutputY(l.getOutputID())+Circuit.IO_SIZE/2);
				i++;
			}
		}
		
		if (dragType == DragType.INPUT || dragType == DragType.OUTPUT)
		{
			g.drawLine(clickX, clickY, lastKnownX, lastKnownY);
		}
	}

	@Override
	public void run() 
	{
		while(true)
		{
			for (Circuit c : circuits) 
			{
				c.update();
			}
			repaint();
			try {
			Thread.sleep(1000/TARGET_FPS);
			} catch (InterruptedException e) {
			}
		}
	}

	private void remove(Circuit circuit)
	{
		//remove links from other circuits to this circuit
		for(Circuit c: circuits)
		{
			for(int i=0;i<c.getInputs();i++)
			{
				System.out.println(c.getLinks()[i]);
				if(c.getLinks()[i] != null && c.getLinks()[i].getCircuit() != null && c.getLinks()[i].getCircuit()==circuit)
				{
					c.setLink(i, null);
				}
			}
		}
		circuits.remove(circuit);
	}

	@Override
	public void mouseDragged(MouseEvent e) 
	{
		if(holding != null && dragType == DragType.CIRCUIT)
		{
			holding.setX((e.getX() - this.xoffset)/10*10);
			holding.setY((e.getY() - this.yoffset)/10*10);
		}
		lastKnownX = e.getX();
		lastKnownY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) 
	{
		lastKnownX = e.getX();
		lastKnownY = e.getY();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_DELETE) {
			for (int i = circuits.size()-1;i>=0;i--)
			{
				if (circuits.get(i).contains(lastKnownX, lastKnownY)) 
				{
					remove(circuits.get(i));
				}
			}
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) 
	{
		for (int i = circuits.size()-1;i>=0;i--) //in reverse order to get the top one
		{
			if (circuits.get(i).contains(e.getX(),e.getY())) //OK, you clicked on this circuit
			{
			circuits.get(i).click();	
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		for (int i = circuits.size()-1;i>=0;i--) //in reverse order to get the top one
		{
			if (circuits.get(i).contains(e.getX(),e.getY())) //OK, you clicked on this circuit
			{
				Circuit c = circuits.get(i);
				holding = c;
				//check if you clicked on one of the inputs
				for(int j=0;j<c.getInputs();j++)
				{
					int x = c.getInputX(j);
					int y = c.getInputY(j);
					if(e.getX() >= x && e.getX() <= x+Circuit.IO_SIZE && e.getY() >= y && e.getY() <= y+Circuit.IO_SIZE)
					{
						//OK, you clicked on one of the inputs
						dragType = DragType.INPUT;
						dragID = j;
						clickX = e.getX();
						clickY = e.getY();
						return;
					}
				}
				
				//check if you clicked on one of the outputs
				for(int j=0;j<c.getOutputs();j++)
				{
					int x = c.getOutputX(j);
					int y = c.getOutputY(j);
					if(e.getX() >= x && e.getX() <= x+Circuit.IO_SIZE && e.getY() >= y && e.getY() <= y+Circuit.IO_SIZE)
					{
						//OK, you clicked on one of the outputs
						dragType = DragType.OUTPUT;
						dragID = j;
						clickX = e.getX();
						clickY = e.getY();
						return;
					}
				}
				
				//None of the above, drag the circuit
				dragType = DragType.CIRCUIT;
				xoffset = e.getX() - c.getX();
				yoffset = e.getY() - c.getY();
				break;
			}
		}
	
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		switch(dragType)
		{
			case CIRCUIT:
				holding = null;
				break;
			case INPUT:
				//check all the circuits to see if you released on an output
				for (int i = circuits.size()-1;i>=0;i--) //in reverse order to get the top one
				{
					if (circuits.get(i).contains(e.getX(),e.getY())) //OK, you clicked on this circuit
					{
						Circuit c = circuits.get(i);
						//check if you clicked on one of the outputs
						for(int j=0;j<c.getOutputs();j++)
						{
							int x = c.getOutputX(j);
							int y = c.getOutputY(j);
							if(e.getX() >= x && e.getX() <= x+Circuit.IO_SIZE && e.getY() >= y && e.getY() <= y+Circuit.IO_SIZE)
							{
								holding.setLink(dragID, new Link(c, j)); //bind the first circuit to the second
								dragType = DragType.NONE;
								return;
							}
						}
						dragType = DragType.CIRCUIT;
						xoffset = e.getX() - c.getX();
						yoffset = e.getY() - c.getY();
						break;
					}
				}
				//nothing found, remove the link
				holding.setLink(dragID, null);
				break;
			case OUTPUT:
				//check all the circuits to see if you released on an input
				for (int i = circuits.size()-1;i>=0;i--) //in reverse order to get the top one
				{
					if (circuits.get(i).contains(e.getX(),e.getY())) //OK, you clicked on this circuit
					{
						Circuit c = circuits.get(i);
						//check if you clicked on one of the inputs
						for(int j=0;j<c.getInputs();j++)
						{
							int x = c.getInputX(j);
							int y = c.getInputY(j);
							if(e.getX() >= x && e.getX() <= x+Circuit.IO_SIZE && e.getY() >= y && e.getY() <= y+Circuit.IO_SIZE)
							{
								c.setLink(j, new Link(holding, dragID)); //bind the second circuit to the first
								dragType = DragType.NONE;
								return;
							}
						}
						dragType = DragType.CIRCUIT;
						xoffset = e.getX() - c.getX();
						yoffset = e.getY() - c.getY();
						break;
					}
				}
				break;
			default:
				break;
		}
		dragType = DragType.NONE;
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{}

	@Override
	public void mouseExited(MouseEvent e) 
	{}

	public void add(Circuit c)
	{ 
		circuits.add(c);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	public ArrayList<Circuit> getCircuits()
	{
		return circuits;
	}

	public void setCircuits(ArrayList<Circuit> circuits)
	{
		this.circuits = circuits;
	}
}
