package com.interactiveball.api.common;

public class FloatPoint3D extends FloatPoint 
{
	public float z;

	public FloatPoint3D() 
	{
		super(0, 0);
		this.z = 0;
	}
	
	public FloatPoint3D(float x, float y, float z) 
	{
		super(x, y);
		this.z = z;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}
	
	
}
