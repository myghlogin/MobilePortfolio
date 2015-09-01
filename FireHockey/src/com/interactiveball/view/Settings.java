package com.interactiveball.view;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.interactiveball.api.interfaces.FileIO;
import com.interactiveball.model.gamelogic.GC;

public final class Settings implements Serializable 
{
	private static final long serialVersionUID = 5334482608635819615L;
	private static final Settings instance = new Settings();
 	private boolean soundEnabled = false;
	private boolean resistanceEnabled = false;
	private boolean obstacleEnabled = false;
	
	private Settings()
	{
	}
	
	public static Settings getInstance()
	{
		return instance;
	}
	
	public boolean isSoundEnabled() {
		return soundEnabled;
	}
	
	public void setSoundEnabled(boolean soundEnabled) {
		this.soundEnabled = soundEnabled;
	}
	
	public boolean isResistanceEnabled() {
		return resistanceEnabled;
	}
	
	public void setResistanceEnabled(boolean resistanceEnabled) {
		this.resistanceEnabled = resistanceEnabled;
	}
	
	public boolean isObstacleEnabled() {
		return obstacleEnabled;
	}
	
	public void setObstacleEnabled(boolean obstacleEnabled) {
		this.obstacleEnabled = obstacleEnabled;
	}
	
	public void save(FileIO file)
	{
		/*ObjectOutputStream out = null;
		
		try 
		{
			out = new ObjectOutputStream(file.writeFile(GC.getSettingsFileName()));
			out.writeObject(this);
		} 
		catch (IOException e) 
		{
			throw new RuntimeException("Settings saving erorr in process of data writting: " + e.getMessage());
		}
		finally
		{
			if(out != null)
			{
				try 
				{
					out.close();
				} 
				catch (IOException e) 
				{
					throw new RuntimeException("Settings saving erorr in process of stream closing");
				}
			}
		}*/
	}
	
	public void load(FileIO file)
	{
		/*ObjectInputStream in = null;
		
		try 
		{
			File configFile = new File(file.getExternalDirectory() + GC.getSettingsFileName());
			
			if(configFile.exists())
			{
				in = new ObjectInputStream(file.readFile(GC.getSettingsFileName()));
				assign((Settings)in.readObject());
			}
		} 
		catch (IOException e) 
		{
			throw new RuntimeException("Settings loading erorr in process of data reading");
		}
		catch(ClassNotFoundException e)
		{
			throw new RuntimeException("Settings loading erorr in process of data reading. Incorrect data");
		}
		finally
		{
			if(in != null)
			{
				try 
				{
					in.close();
				} 
				catch (IOException e) 
				{
					throw new RuntimeException("Settings loading erorr in process of stream closing");
				}
			}
		}*/
	}
	
	private void assign(Settings value)
	{
		this.soundEnabled = value.soundEnabled;
		this.resistanceEnabled = value.resistanceEnabled;
		this.obstacleEnabled = value.obstacleEnabled;
		
	}
	
}
