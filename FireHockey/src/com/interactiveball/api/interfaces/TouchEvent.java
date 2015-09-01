package com.interactiveball.api.interfaces;

import android.view.MotionEvent;

public class TouchEvent 
{
	private int x;
	private int y;
	private TouchType type; 
	private int pointerId;
	
	public TouchEvent() 
	{
		
	}

	public TouchEvent(int x, int y, TouchType type, int pointerId) 
	{
		this.x = x;
		this.y = y;
		this.type = type;
		this.pointerId = pointerId;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public TouchType getType() {
		return type;
	}
	
	public void setType(TouchType type) {
		this.type = type;
	}
	
	public int getPointerId() {
		return pointerId;
	}
	
	public void setPointerId(int pointerId) {
		this.pointerId = pointerId;
	}
}
