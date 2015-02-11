package nl.frozenjojo.systemboard.circuit;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JColorChooser;

@SuppressWarnings("serial")
public class InputColorLampCircuit extends LampCircuit {
	private long previousTime;
	private boolean click;
	private Color color;


	public InputColorLampCircuit(int x, int y) {
		super(x, y, 100,100,"Color-Input Lamp","Advanced",1,0);	
		previousTime = System.currentTimeMillis();
		click = false;
		color = Color.WHITE;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.BLACK);
		//int fh = g.getFontMetrics().getHeight();
		g.drawString("Color: " + color.getRed() + "," + color.getGreen() + "," + color.getBlue(), getX() + 5, getY()+getHeight() - 3);
	}
	
	public void click() {
	if(System.currentTimeMillis()-previousTime > 500)
	{
		previousTime = System.currentTimeMillis();
		click = false;
	}
	if (click == false) click = true;
	else if(click == true) {
		/*JFrame frame = new JFrame();
		String s = (String)JOptionPane.showInputDialog(
	                    	frame,
	                    	"Enter a color (RGB): ",
	                    	"Enter a color (RGB)",
	                    	JOptionPane.INFORMATION_MESSAGE,
	                    	null,
	                    	null,
	                    	""+R + "," + G + "," + B);
		if(s == null)
		{
			return;
		}
		String[] args = s.split(",");
		System.out.println(args);
		
			//If a string was returned, say so.
			if ((args != null) && (args.length > 2)) {
				try {
					R = Integer.parseInt(args[0]);
					System.out.println("" + B);
					G = Integer.parseInt(args[1]);
					System.out.println("" + G);
					B = Integer.parseInt(args[2]);
					System.out.println("" + B);
				}
				catch(NumberFormatException e) {
					R = 0;
					G = 0;
					B = 0;
				}
			}*/
		Color c = JColorChooser.showDialog(null, "Choose your color", color);
			if(c != null) {
			color = c;
			}
		}
	}
	
	@Override
	public Color getColor() {
		if (this.getValue(0) != 0) return color;
		else return Color.BLACK;
		
	}

}
