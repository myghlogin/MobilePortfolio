package com.interactiveball.api.common;

public class FloatPoint 
{
	public float x;
	public float y;
	
	public FloatPoint() 
	{
		this.x = 0;
		this.y = 0;
	}
	
	public FloatPoint(float x, float y) 
	{
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	@Override
	public String toString() 
	{
		StringBuilder b = new StringBuilder();
		b.append("{");
		b.append(x);
		b.append(";");
		b.append(y);
		b.append("}");
		
		return b.toString();
	}
	
	
	
}
