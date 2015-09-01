package com.interactiveball.api.impl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

import com.interactiveball.api.interfaces.FileIO;

public class AndroidFileIO implements FileIO 
{
	private String externalDirectory;
	private AssetManager assetManager;
	
	public AndroidFileIO(Context context)
	{
		if(context == null)
			throw new IllegalArgumentException();
		
		externalDirectory = Environment.getExternalStorageDirectory().getAbsolutePath();
		assetManager = context.getAssets();
	}
	
	@Override
	public String getExternalDirectory() {
		return externalDirectory;
	}

	@Override
	public InputStream readAsset(String fileName) throws IOException 
	{
		return assetManager.open(fileName);
	}

	@Override
	public InputStream readFile(String fileName) throws IOException 
	{
		return new FileInputStream(externalDirectory + fileName);
	}

	@Override
	public OutputStream writeFile(String fileName) throws IOException 
	{
		return new FileOutputStream(externalDirectory + fileName);
	}

}
