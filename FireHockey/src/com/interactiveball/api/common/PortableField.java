package com.interactiveball.api.common;

public abstract class PortableField<T, V> extends MathematicField<T, V>
{
	protected FloatPoint3D position = null;
	
	public PortableField(float x, float y, float z, boolean sensitive) 
	{
		super(sensitive);
		this.position = new FloatPoint3D(x, y, z);
	}

	public PortableField(float x, float y, boolean sensitive) 
	{
		super(sensitive);
		this.position = new FloatPoint3D(x, y, 0);
	}
	
	public FloatPoint3D getPosition() {
		return position;
	}

	public abstract void move();
}
