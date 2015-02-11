package nl.frozenjojo.systemboard.plugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;

import nl.frozenjojo.systemboard.circuit.Circuit;
import nl.frozenjojo.systemboard.ui.SystemboardContainer;

public class Plugin
{
	private String[] classNames;
	private File file;
	
	public Plugin(SystemboardContainer container, File file, String[] classNames) throws IOException, ClassNotFoundException
	{
		this.classNames = classNames;
		this.file = file;
		URLClassLoader l = new URLClassLoader(new URL[]{new URL("file://"+file.getAbsolutePath())});
		for(String className: classNames)
		{
			Class<?> circuitClass = l.loadClass(className);
			Constructor<?> con = circuitClass.getConstructors()[0];
			Circuit c = null;
	         try
	         {
	             c = (Circuit)(con.newInstance(0, 0));
	             container.addComponent(c);
	         } catch (InstantiationException e)
	         {
	             e.printStackTrace();
	         } catch (IllegalAccessException e)
	         {
	             e.printStackTrace();
	         } catch (IllegalArgumentException e)
	         {
	             e.printStackTrace();
	         } catch (InvocationTargetException e)
	         {
	             e.printStackTrace();
	         }
	         finally
	         {
	        	 l.close();
	         }
		}
	}

	public String[] getClassNames()
	{
		return classNames;
	}

	public void setClassNames(String[] classNames)
	{
		this.classNames = classNames;
	}

	public File getFile()
	{
		return file;
	}

	public void setFile(File file)
	{
		this.file = file;
	}
}
