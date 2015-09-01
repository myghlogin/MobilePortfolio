package com.interactiveball.api.impl;

import java.util.List;

import android.view.View;

import com.interactiveball.api.interfaces.Input;
import com.interactiveball.api.interfaces.TouchEvent;
import com.interactiveball.api.interfaces.TouchHandler;
import com.interactiveball.api.common.ScreenConfig;

public class AndroidInput implements Input 
{
	TouchHandler touchHandler;
	
	public AndroidInput(View view, float scaleX, float scaleY, ScreenConfig screenConfig)
	{
		touchHandler = new AndroidTouchHandler(view, scaleX, scaleY, screenConfig);
	}
	
	@Override
	public boolean isTouched(int pointerId) 
	{
		return touchHandler.isTouched(pointerId);
	}

	@Override
	public int getX(int pointerId) 
	{
		return touchHandler.getX(pointerId);
	}

	@Override
	public int getY(int pointerId) 
	{
		return touchHandler.getY(pointerId);
	}

	@Override
	public List<TouchEvent> getTouchEvents() 
	{
		return touchHandler.getTouchEvents();
	}
	
}
