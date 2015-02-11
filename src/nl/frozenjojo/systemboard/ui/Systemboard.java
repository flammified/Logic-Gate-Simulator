package nl.frozenjojo.systemboard.ui;
import java.applet.Applet;
import java.awt.BorderLayout;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


@SuppressWarnings("serial")
public class Systemboard extends Applet
{

	@Override
	public void init()
	{
	    try 
	    {
			UIManager.setLookAndFeel(
			UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) 
		{
		} catch (InstantiationException e) 
		{
		} catch (IllegalAccessException e) 
		{
		} catch (UnsupportedLookAndFeelException e) 
		{
		}
		super.init();
		setLayout(new BorderLayout());
		add(new SystemboardContainer(), BorderLayout.CENTER);
	}
	
}
