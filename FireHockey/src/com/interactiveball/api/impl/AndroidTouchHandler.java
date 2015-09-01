package com.interactiveball.api.impl;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.interactiveball.api.common.Orientation;
import com.interactiveball.api.common.Pool;
import com.interactiveball.api.common.PoolObjectFactory;
import com.interactiveball.api.common.ScreenConfig;
import com.interactiveball.api.exception.UnknownCaseException;
import com.interactiveball.api.interfaces.TouchEvent;
import com.interactiveball.api.interfaces.TouchHandler;
import com.interactiveball.api.interfaces.TouchType;

public class AndroidTouchHandler implements TouchHandler, OnTouchListener
{
	boolean isTouched;
	int touchX;
	int touchY;
	Pool<TouchEvent> touchEventPool;
	List<TouchEvent> touchEvents = new ArrayList<TouchEvent>();
	List<TouchEvent> touchEventBuffer = new ArrayList<TouchEvent>();
	float scaleX;
	float scaleY;
	ScreenConfig screenConfig;
	
	public AndroidTouchHandler(View view, float scaleX, float scaleY, ScreenConfig screenConfig) 
	{
		if(screenConfig == null)
			throw new NullPointerException("Parameter screenConfig in AndroidTouchHandler constructor is passed null");
		
		PoolObjectFactory<TouchEvent> factory = new PoolObjectFactory<TouchEvent>()
		{
			@Override
			public TouchEvent createObject() 
			{
				return new TouchEvent();
			}
		};
		
		touchEventPool = new Pool<TouchEvent>(factory, 100);
		view.setOnTouchListener(this);
		
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.screenConfig = screenConfig;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		Log.d("GOAL_DEBUG", "OnTouch: ok");
		
		if(screenConfig.getPaddingOrientation() == Orientation.VERTICAL)
		{
			if( event.getY() < screenConfig.getDrawingRect().top || 
				event.getY() > screenConfig.getDrawingRect().bottom )
				return true;
			
			event.setLocation(event.getX(), event.getY() - screenConfig.getPadding());
		}
		else if(screenConfig.getPaddingOrientation() == Orientation.HORIZONTAL)
		{
			if( event.getX() < screenConfig.getDrawingRect().left || 
				event.getX() > screenConfig.getDrawingRect().right )
				return true;
			
			Log.d("GOAL_DEBUG", "touch event");
			Log.d("GOAL_DEBUG", String.valueOf(event.getX()) + ":" + String.valueOf(event.getY()));
			Log.d("GOAL_DEBUG", String.valueOf(screenConfig.getPadding()) + ":" + String.valueOf(event.getX() - screenConfig.getPadding()));
			event.setLocation(event.getX() - screenConfig.getPadding(), event.getY());
			Log.d("GOAL_DEBUG", String.valueOf(event.getX()) + ":" + String.valueOf(event.getY()));
		}
		else
			throw new UnknownCaseException("Unknown value in enumeration Orientation");
			
		synchronized(this)
		{
			TouchEvent touchEvent = touchEventPool.newObject();
			
			switch(event.getAction())
			{
				case MotionEvent.ACTION_DOWN:
					touchEvent.setType(TouchType.TOUCH_DOWN);
					isTouched = true;
					break;
				case MotionEvent.ACTION_MOVE:
					touchEvent.setType(TouchType.TOUCH_MOVE);
					isTouched = true;
					break;
				case MotionEvent.ACTION_CANCEL:
				case MotionEvent.ACTION_UP:
					touchEvent.setType(TouchType.TOUCH_UP);
					isTouched = false;
					break;
			}
			
			touchX = (int)(event.getX() * scaleX);
			touchY = (int)(event.getY() * scaleY);

			Log.d("GOAL_DEBUG", String.valueOf(touchX) + ":" + String.valueOf(touchY));

			touchEvent.setX(touchX);
			touchEvent.setY(touchY);
			touchEventBuffer.add(touchEvent);
			Log.d("GOAL_DEBUG", "OnTouch: touched");

			return true;
		}
	}

	@Override
	public boolean isTouched(int pointer) 
	{
		synchronized(this)
		{
			if(pointer == 0)
				return isTouched;
			else
				return false;
		}
	}

	@Override
	public int getX(int pointer) 
	{
		synchronized(this)
		{
			return touchX;
		}
	}

	@Override
	public int getY(int pointer) 
	{
		synchronized(this)
		{
			return touchY;
		}
	}

	@Override
	public List<TouchEvent> getTouchEvents() 
	{
		synchronized(this)
		{
			int len = touchEvents.size();
			
			for(int i = 0; i < len; i++)
				touchEventPool.free(touchEvents.get(i));
			
			touchEvents.clear();
			touchEvents.addAll(touchEventBuffer);
			touchEventBuffer.clear();
			
			return touchEvents;
		}
	}


}
