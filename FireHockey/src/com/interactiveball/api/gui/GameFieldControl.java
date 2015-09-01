package com.interactiveball.api.gui;

import android.graphics.Bitmap.Config;

import com.interactiveball.api.interfaces.Graphics;
import com.interactiveball.api.interfaces.TouchEvent;

public class GameFieldControl extends TouchControl 
{
	public GameFieldControl(int left, int top, int width, int height, Config config, Graphics graphics) 
	{
		super(left, top, width, height, config, graphics);
	}

	@Override
	protected void doTouch(TouchEvent e) 
	{
		
	}

	@Override
	protected void doDraw() 
	{
		
	}

}
