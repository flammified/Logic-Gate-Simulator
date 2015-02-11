package nl.frozenjojo.systemboard.circuit;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;



public class CircuitList {
	public final static Class<?>[] CIRCUITS = new Class[]
			{
				PulseGeneratorCircuit.class,
				AndCircuit.class,
				LampCircuit.class,
				SensorCircuit.class,
				OrCircuit.class,
				Comparator.class,
				MemoryCell.class,
				ButtonCircuit.class,
				BuzzerCircuit.class,
				InvertorCircuit.class,
				CounterCircuit.class,
				ColorLampCircuit.class,
				SineCircuit.class,
				OscilloscopeCircuit.class,
				VarVoltageCircuit.class,
				ADConvertorCircuit.class, 
				InputColorLampCircuit.class,
				TanCircuit.class
			};
	
	public static Circuit createInstance(int type, int x, int y) 
	{
		 Constructor<?> c = CIRCUITS[type].getConstructors()[0];
         try
         {
             return (Circuit)(c.newInstance(x, y));
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
         return null;
	}
}
