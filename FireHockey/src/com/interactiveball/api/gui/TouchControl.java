package com.interactiveball.api.gui;

import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.util.Log;

import com.interactiveball.api.interfaces.Graphics;
import com.interactiveball.api.interfaces.TouchEvent;
import com.interactiveball.api.interfaces.TouchType;

public abstract class TouchControl extends Control 
{
	private TouchEvent event;
	private OnClickListener onClickListener;
	private OnTouchListener onTouchListener;
	private OnInsideListener onInsideListener;
	private boolean isInside = false;
	
	public TouchControl(int left, int top, int width, int height, Config config, Graphics graphics) 
	{
		super(left, top, width, height, config, graphics);
		this.event = new TouchEvent();
	}

	public void onTouch(TouchEvent e)
	{
		event.setX(e.getX() - left);
		event.setY(e.getY() - top);
		event.setType(e.getType());
		event.setPointerId(e.getPointerId());
		
		if( e.getX() >= left && e.getY() >= top &&
		    e.getX() < left + width && e.getY() < top + height )
		{
			if(!isInside){
				isInside = true;
				if(onInsideListener != null)
					onInsideListener.onEnter(this);
			}

			doTouch(event);
			
			if(e.getType() == TouchType.TOUCH_DOWN){
				if(onTouchListener != null)
					onTouchListener.onTouchDown(this, e);
			}
			else if(e.getType() == TouchType.TOUCH_UP)
			{
				if(onClickListener != null)
					onClickListener.onClick(this);	
				if(onTouchListener != null)
					onTouchListener.onTouchUp(this, e);	
			}
			else if(e.getType() == TouchType.TOUCH_MOVE){
				if(onTouchListener != null)
					onTouchListener.onTouchMove(this, e);	
			}
		}
		else
		{
			if(isInside){
				isInside = false;
				if(onInsideListener != null)
					onInsideListener.onExit(this);
			}
		}
	}
	
	public void setOnClickListener(OnClickListener listener){
		this.onClickListener = listener;	
	}
	
	public void setOnTouchListener(OnTouchListener listener){
		this.onTouchListener = listener;	
	}
	
	public void setOnInsideListener(OnInsideListener onInsideListener) {
		this.onInsideListener = onInsideListener;
	}

	protected abstract void doTouch(TouchEvent e);
}
