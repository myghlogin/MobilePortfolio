package com.interactiveball.api.impl;

import android.graphics.Bitmap;

import com.interactiveball.api.interfaces.PixelFormat;
import com.interactiveball.api.interfaces.Pixmap;

public class AndroidPixmap implements Pixmap 
{
	Bitmap bitmap;
	private PixelFormat format;
	
	public AndroidPixmap(Bitmap bitmap, PixelFormat format) 
	{
		if(bitmap == null || format == null)
			throw new IllegalArgumentException();
		
		this.bitmap = bitmap;
		this.format = format;
	}

	@Override
	public int getWidth() 
	{
		return bitmap.getWidth();
	}

	@Override
	public int getHeight() 
	{
		return bitmap.getHeight();
	}

	@Override
	public PixelFormat getFormat() 
	{
		return format;
	}

	@Override
	public Bitmap getBitmap() 
	{
		return bitmap;
	}
	
	@Override
	public void dispose() 
	{
		bitmap.recycle();
	}


	
}
