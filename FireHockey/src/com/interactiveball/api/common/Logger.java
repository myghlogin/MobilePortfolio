package com.interactiveball.api.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import android.os.Environment;
import android.util.Log;

public class Logger 
{
	private String logFileName;
	private boolean fileOutput;
	private boolean logCatOutput = true;
	private LoggingLevel level = LoggingLevel.DEBUG;
	private String tag = "";
	
	private String externalDirectory;
	private PrintWriter writer = null; 
	
	public Logger()  
	{
		fileOutput = false;
	}
	
	public Logger(String logFileName) throws IOException 
	{
		this.logFileName = logFileName;
		this.fileOutput = true;
		this.externalDirectory = Environment.getExternalStorageDirectory().getAbsolutePath();
		
		File file = new File(this.externalDirectory + logFileName);
		file.createNewFile();
		writer = new PrintWriter(file);
	}

	public boolean isFileOutput() {
		return fileOutput;
	}

	public void setFileOutput(boolean fileOutput) {
		this.fileOutput = fileOutput;
	}

	public boolean isLogCatOutput() {
		return logCatOutput;
	}

	public void setLogCatOutput(boolean logCatOutput) {
		this.logCatOutput = logCatOutput;
	}

	public LoggingLevel getLevel() {
		return level;
	}

	public void setLevel(LoggingLevel level) {
		this.level = level;
	}
	
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public void log(String message)
	{
		log(level, tag, message);
	}

	public void log(LoggingLevel level, String message)
	{
		log(level, tag, message);
	}
	
	public void log(String tag, String message)
	{
		log(level, tag, message);
	}

	public void log(LoggingLevel level, String tag, String message)
	{
		switch(level)
		{
			case INFO: 
				if(logCatOutput)
					Log.i(tag, message);
			break; 
			case DEBUG: 
				if(logCatOutput)
					Log.d(tag, message); 
			break; 
			case WARNING: 
				if(logCatOutput)
					Log.w(tag, message); 
			break; 
			case ERROR: 
				if(logCatOutput)
					Log.e(tag, message); 
			break; 
		}

		if(fileOutput)
		{
			writer.println(level + ":" + tag + ": " + message);
			writer.flush();
		}
	}
	
	public void close()
	{
		if(writer != null)
			writer.close();
	}
}
