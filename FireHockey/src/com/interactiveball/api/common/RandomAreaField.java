package com.interactiveball.api.common;

public class RandomAreaField extends PortableField<FloatPoint, FloatPoint> 
{
	private FloatPoint solidValue;
	private FloatPoint compositeValue;
	private RandomArea randomArea;
	
	public RandomAreaField(float x, float y, boolean sensitive, float solidValueX, float solidValueY, int minRadius, int maxRadius, float angleStep, int pixelStep) 
	{
		super(x, y, sensitive);
		randomArea = new RandomArea((int)x, (int)y, minRadius, maxRadius, angleStep, pixelStep);
		solidValue = new FloatPoint(solidValueX, solidValueY);
		compositeValue = new FloatPoint();
	}

	@Override
	public void move() 
	{
	}

	@Override
	public FloatPoint fieldFunction(FloatPoint param) 
	{
		if(randomArea.isPointInside((int)param.x, (int)param.y))
			return solidValue;
		
		return null;
	}

	@Override
	public FloatPoint composite(FloatPoint param, FloatPoint value) 
	{
		if(value == null)
		{
			compositeValue.x = solidValue.x; 
			compositeValue.y = solidValue.y; 
		}
		else
		{
			compositeValue.x = solidValue.x + value.x; 
			compositeValue.y = solidValue.y + value.y; 
		}
		
		return compositeValue;
	}




}
