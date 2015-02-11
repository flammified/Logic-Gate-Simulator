package nl.frozenjojo.systemboard.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;


import nl.frozenjojo.systemboard.circuit.Circuit;
import nl.frozenjojo.systemboard.circuit.CircuitList;
import nl.frozenjojo.systemboard.circuit.Link;
import nl.frozenjojo.systemboard.plugin.Plugin;


@SuppressWarnings("serial")
public class SystemboardContainer extends JPanel implements TreeSelectionListener, MouseListener
{
	private JTree componentList;
	//private JMenuBar menu;
	private SystemboardCanvas s;
	private JMenuBar menu;
	private File file;
	private ArrayList<Plugin> plugins;
	private DefaultMutableTreeNode top;
	private ArrayList<DefaultMutableTreeNode> categories;
	
	public SystemboardContainer()
	{
		plugins = new ArrayList<Plugin>();
		initUI();	
	}


	private void initUI()
	{
		setLayout(new BorderLayout());
		menu = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		menu.add(fileMenu);
		JMenuItem it = new JMenuItem("New");
		it.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				s.getCircuits().clear();
			}
			
		});
		fileMenu.add(it);
		it = new JMenuItem("Open...");
		it.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				//Create a file chooser
				final JFileChooser fc = new JFileChooser();
				//In response to a button click:
				int returnVal = fc.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
	                file = fc.getSelectedFile();
	                try
					{
						s.setCircuits(read(file));
					} catch (IOException e1)
					{
						JOptionPane.showMessageDialog(null, "Unable to read the file!");
						e1.printStackTrace();
					} catch (Exception e1)
					{
						if(e1.getClass().equals(Exception.class))
						{
							JOptionPane.showMessageDialog(null, e1.getMessage());
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Unable to open the file.\nSomething wierd happend.\nCode:\n"+e1);
						}
						e1.printStackTrace();
					}
	            }
			}
			
		});
		fileMenu.add(it);
		it = new JMenuItem("Save");
		it.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				save();
			}
			
		});
		fileMenu.add(it);
		it = new JMenuItem("Save as...");
		it.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				saveAs();
			}
			
		});
		fileMenu.add(it);
		it = new JMenuItem("Add plugin...");
		final SystemboardContainer me = this;
		it.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				//Create a file chooser
				final JFileChooser fc = new JFileChooser();
				//In response to a button click:
				int returnVal = fc.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
	                file = fc.getSelectedFile();
	                try
					{
	                	String[] classNames = JOptionPane.showInputDialog("What are the names of the classes (including package names) seperated with a ;").split(";");
						plugins.add(new Plugin(me, file, classNames));
					}
	                catch (FileNotFoundException e1)
					{
						JOptionPane.showMessageDialog(null, "Can't find class "+e1);
					}
	                catch (IOException e1)
					{
						JOptionPane.showMessageDialog(null, "Unable to read the file!");
						e1.printStackTrace();
					}
	                catch (Exception e1)
					{
						if(e1.getClass().equals(Exception.class))
						{
							JOptionPane.showMessageDialog(null, e1.getMessage());
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Unable to open the file.\nSomething weird happend.\nCode:\n"+e1);
						}
						e1.printStackTrace();
					}
	            }
			}
			
		});
		fileMenu.add(it);
		add(menu, BorderLayout.NORTH);
		top = new DefaultMutableTreeNode("Components");
		categories = new ArrayList<DefaultMutableTreeNode>();
		for(int i=0;i<CircuitList.CIRCUITS.length;i++)
		{
			Circuit c = CircuitList.createInstance(i, 0, 0);
			addComponent(c);
		}
		componentList = new JTree(top);
		componentList.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		componentList.addMouseListener(this);
		add(componentList, BorderLayout.WEST);
		s = new SystemboardCanvas();
		add(s, BorderLayout.CENTER);
	}


	public void addComponent(Circuit c)
	{
		DefaultMutableTreeNode d = new DefaultMutableTreeNode(c.getName());
		boolean found = false;
		for(int j=0;j<categories.size();j++) //loop trough all categories
		{
			if(categories.get(j).toString().equals(c.getCategory())) //if the category already exists 
			{
				categories.get(j).add(d); //add it to the category
				found = true;
				break;
			}
		}
		if(!found) //the category doesn't exist
		{
				DefaultMutableTreeNode category = new DefaultMutableTreeNode(c.getCategory());
				top.add(category);
				category.add(d);
				categories.add(category);
		}
		componentList = new JTree(top);
	}

	private void save()
	{
		if(file == null)
		{
			saveAs();
		}
		try
		{
			write(s.getCircuits(), file);
		}
		catch(SecurityException e)
		{
			JOptionPane.showMessageDialog(this, "Sorry, you can't save from an applet!");
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(this, "Unable to write the file!");
			e.printStackTrace();
		}
	}
	
	private void saveAs()
	{
		try
		{
			JFileChooser f = new JFileChooser();
			int result = f.showSaveDialog(this);
			if(result != JFileChooser.APPROVE_OPTION)
			{
				return; //stop if the user presses cancel
			}
			file = f.getSelectedFile();
			try
			{
				write(s.getCircuits(), file);
			} catch (IOException e)
			{
				JOptionPane.showMessageDialog(this, "Unable to write the file!");
				e.printStackTrace();
			}
		}
		catch(SecurityException e)
		{
			JOptionPane.showMessageDialog(this, "Sorry, you can't save from an applet!");
		}
	}
	
	private void write(ArrayList<Circuit> circuits, File f) throws IOException
	{
		BufferedWriter w = new BufferedWriter(new FileWriter(f));
		w.write("1.0\r\n");
		for(Circuit c: circuits)
		{
			w.write(""+c.getX()+"#"+c.getY()+"#"+c.getName()+"#");
			for(Link l: c.getLinks())
			{
				if(l == null)
				{
					w.write("e;"); //empty
					continue;
				}
				int outputID = l.getOutputID();
				Circuit c2 = l.getCircuit();
				int circuitID = 0;
				for(int i=0;i<circuits.size();i++)
				{
					Circuit c3 = circuits.get(i);
					if(c2 == c3)
					{
						circuitID = i;
					}
				}
				w.write(""+circuitID+":"+outputID+";");
			}
			if(c.getLinks().length == 0)
			{
				w.write("e"); //add and empty link to make sure tring.split("#") reads the argument
			}
			w.write("\r\n");
		}
		w.flush();
		w.close();
	}
	
	private ArrayList<Circuit> read(File f) throws IOException, Exception
	{
		ArrayList<Circuit> cir = new ArrayList<Circuit>();
		ArrayList<String> links = new ArrayList<String>();
		BufferedReader r = new BufferedReader(new FileReader(f));
		if(!r.readLine().equals("1.0")) //read first line and check it
		{
			r.close();
			throw new Exception("This file is not a valid circuit or is made by a newer version of this program. Please choose another file or update your version of this program.");
		}
		String line;
		int circuitID = 0;
		while((line = r.readLine()) != null)
		{
			String[] args = line.split("#");
			int x = Integer.parseInt(args[0]);
			int y = Integer.parseInt(args[1]);
			String type = args[2];
			//look for the circuit with that name
			for(int i=0;i<CircuitList.CIRCUITS.length;i++)
			{
				Circuit c = CircuitList.createInstance(i, 0, 0);
				if(c.getName().equals(type))
				{
					cir.add(CircuitList.createInstance(i, x, y));
					links.add(""+circuitID+";"+args[3]); //add the circuit d to the string to get it later in the link list
					circuitID++;
				}
			}
		}
		//add links afterwards (since the circuit with that id maybe isn't created yet)
		for(int i=0;i<links.size();i++) {
			String[] circuitLinks = links.get(i).split(";");
			int circuitFromID = Integer.parseInt(circuitLinks[0]);
			for(int j=1;j<circuitLinks.length;j++) {
				String link = circuitLinks[j];
				if(link.equals("e")) { //the link is empty
					continue;
				}
				String[] args = link.split(":");
				int circuitToID = Integer.parseInt(args[0]);
				int outputID = Integer.parseInt(args[1]);
				cir.get(circuitFromID).setLink(j-1, new Link(cir.get(circuitToID), outputID));
			}
		}
		r.close();
		return cir;
	}

	@Override
	public void valueChanged(TreeSelectionEvent e)
	{
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)componentList.getLastSelectedPathComponent();
		if(node == null)
		{
			//Nothing is selected.     
			return;
		}
		for(int i=0;i<CircuitList.CIRCUITS.length;i++)
		{
			Circuit c = CircuitList.createInstance(i, 0, 0);
			if(c.getName().equals(node.getUserObject()))
			{
				s.add(CircuitList.createInstance(i, 100, 10));
				return;
			}
		}
	}


	@Override
	public void mouseClicked(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent e)
	{
		int selRow = componentList.getRowForLocation(e.getX(), e.getY());
        TreePath selPath = componentList.getPathForLocation(e.getX(), e.getY());
        if(selRow != -1)
        {
            if(e.getClickCount() == 2)
            {
	            String name = selPath.getLastPathComponent().toString();
	    		for(int i=0;i<CircuitList.CIRCUITS.length;i++)
	    		{
	    			Circuit c = CircuitList.createInstance(i, 0, 0);
	    			if(c.getName().equals(name))
	    			{
	    				s.add(CircuitList.createInstance(i,0,0));
	    				return;
	    			}
	    		}
            }
        }
	}


	@Override
	public void mouseReleased(MouseEvent arg0)
	{	
	}
	
}