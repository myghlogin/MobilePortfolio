package com.interactiveball.api.gui;

import android.graphics.Bitmap.Config;
import android.graphics.Rect;

import com.interactiveball.api.interfaces.Graphics;
import com.interactiveball.api.interfaces.Pixmap;

public class ProgressBar extends Control 
{
	private Pixmap background;
	private Pixmap foreground;
	private int progress;
	private Rect buffRect;
	
	public ProgressBar(int left, int top, int width, int height, Config config,
			Graphics graphics, Pixmap background, Pixmap foreground) {
		super(left, top, width, height, config, graphics);
		
		this.background = background;
		this.foreground = foreground;
		this.progress = 0;
		this.buffRect = new Rect(0, 0, 0, height);
	}

	@Override
	protected void doDraw() 
	{
		if(background != null)
			canvas.drawBitmap(background.getBitmap(), 0, 0, null);
		
		if(foreground != null){
			buffRect.right = progress*width/100;
			canvas.drawBitmap(foreground.getBitmap(), buffRect, buffRect, null);
		}
		
	}

	public Pixmap getBackground() {
		return background;
	}

	public void setBackground(Pixmap background) {
		this.background = background;
	}

	public Pixmap getForeground() {
		return foreground;
	}

	public void setForeground(Pixmap foreground) {
		this.foreground = foreground;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) 
	{
		if(progress < 0 || progress > 100)
			throw new IllegalArgumentException("Progress value must be in interval [0; 100]");
		
		this.progress = progress;
	}
	
	

}
