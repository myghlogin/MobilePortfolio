package com.interactiveball.api.common;

public class SolidField extends MathematicField<FloatPoint, Float> 
{
	private Float solidValue;
	
	public SolidField(boolean sensitive, Float solidValue) 
	{
		super(sensitive);
		this.solidValue = solidValue;
	}

	@Override
	public Float fieldFunction(FloatPoint param) 
	{
		return solidValue;
	}

	@Override
	public Float composite(FloatPoint param, Float value) 
	{
		if(value == null)
			return solidValue; 
		else
			return solidValue + value; 
	}




}
