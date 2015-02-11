package nl.frozenjojo.systemboard.circuit;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class MicrophoneThread extends Thread
{
	private int volume;
	public MicrophoneThread()
	{
		start();
	}

	@Override
	public void run()
	{
		AudioFormat format = new AudioFormat(22000,16,1,true,true);
		TargetDataLine line = null;
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, 
		    format); // format is an AudioFormat object
		if (!AudioSystem.isLineSupported(info)) {
		    // Handle the error ... 

		}
		// Obtain and open the line.
		try {
		    line = (TargetDataLine) AudioSystem.getLine(info);
		    line.open(format);
		} catch (LineUnavailableException ex) {
		    // Handle the error ... 
		}
		// Assume that the TargetDataLine, line, has already
		// been obtained and opened.
		//ByteArrayOutputStream out  = new ByteArrayOutputStream();
		int numBytesRead;
		byte[] data = new byte[line.getBufferSize() / 5];
		// Begin audio capture.
		line.start();

		// Here, stopped is a global boolean set by another thread.
		while (true)
		{
		   // Read the next chunk of data from the TargetDataLine.
		   numBytesRead =  line.read(data, 0, data.length);
		   //System.out.println("" + line.getLevel());
		   // Save this chunk of data.
		   //System.out.write(data, 0, numBytesRead);
		   	volume = 0;
			for(int i=0;i<numBytesRead;i++)
			{
				volume += Math.abs(data[i]);
			}
			volume /= data.length/2;
		}
	}

	public int getVolume()
	{
		return volume;
	}
}
