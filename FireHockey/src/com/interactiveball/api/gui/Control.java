package com.interactiveball.api.gui;

import com.interactiveball.api.impl.AndroidPixmap;
import com.interactiveball.api.interfaces.Graphics;
import com.interactiveball.api.interfaces.PixelFormat;
import com.interactiveball.api.interfaces.Pixmap;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class Control 
{
	protected int left;
	protected int top;
	protected int width;
	protected int height;
	protected boolean enabled;
	protected boolean visible;
	protected Canvas canvas;
	protected Bitmap bitmap;
	protected Paint paint;
	protected Pixmap pixmap;
	protected PixelFormat pixelFormat;
	protected Config config;
	protected Graphics graphics;
	
	public Control(int left, int top, int width, int height, Config config, Graphics graphics)
	{
		this.left = left;
		this.top = top;
		this.width = width;
		this.height = height;
		this.enabled = true;
		this.visible = true;
		this.bitmap = Bitmap.createBitmap(width, height, config);
		this.canvas = new Canvas(bitmap);
		this.paint = new Paint();
		
		if(config == Config.ARGB_4444)
			pixelFormat = PixelFormat.FORMAT_ARGB4444;
		else if(config == Config.ARGB_8888)
			pixelFormat = PixelFormat.FORMAT_ARGB8888;
		else
			pixelFormat = PixelFormat.FORMAT_RGB565;
		
		this.pixmap = new AndroidPixmap(bitmap, pixelFormat);
		this.graphics = graphics;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) 
	{
		this.left = left;
		draw();
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) 
	{
		this.top = top;
		draw();
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) 
	{
		this.width = width;
		draw();
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) 
	{
		this.height = height;
		draw();
	}
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) 
	{
		this.enabled = enabled;
		draw();
	}
	
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) 
	{
		this.visible = visible;
		draw();
	}

	public void draw()
	{
		if(visible)
		{
			doDraw();
			graphics.drawPixmap(pixmap, left, top);
		}
	}
	
	protected abstract void doDraw();
}
