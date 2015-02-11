package nl.frozenjojo.systemboard.ui;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


@SuppressWarnings("serial")
public class Main extends JFrame 
{
	public Main() 
	{
		super("Logic gate board");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(640, 480);
		setLayout(new BorderLayout());
		add(new SystemboardContainer(), BorderLayout.CENTER);
		setVisible(true);
	}
	
	public static void main(String[] args) 
	{
	    try 
	    {
			//UIManager.setLookAndFeel(
			//UIManager.getSystemLookAndFeelClassName());
	    	UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e) 
		{
		} catch (InstantiationException e) 
		{
		} catch (IllegalAccessException e) 
		{
		} catch (UnsupportedLookAndFeelException e) 
		{
		}
		new Main();
	}
}
