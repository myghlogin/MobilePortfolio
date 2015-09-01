package com.interactiveball.api.gui;

import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint.Style;

import com.interactiveball.api.interfaces.Graphics;
import com.interactiveball.api.interfaces.TouchEvent;

public class SpeedControl extends ParameterControl 
{
	private final int lightBlueColor = Color.rgb(149, 193, 251);
	private final int blueColor = Color.rgb(79, 155, 255);
	private final Matrix rotatedMatrix;
	private int yPos;
	
	public static class Builder extends ParameterControl.Builder
	{
		@Override
		protected ParameterControl createInstance() {
			return new SpeedControl(left, top, width, height, config, graphics, startLevel, maxLevel, minLevel, rotated);
		}
	}
	
	public SpeedControl(int left, int top, int width, int height,
			Config config, Graphics graphics, int startLevel, int maxLevel,
			int minLevel, boolean rotated) 
	{
		super(left, top, width, height, config, graphics, startLevel, maxLevel,
				minLevel, rotated);
		
		if(rotated)
			yPos = (int)(((float)startLevel/maxLevel)*(height - 30) + 30f);	
		else
			yPos = height - 30 - (int)(((float)startLevel/maxLevel)*(height - 30));
		
		rotatedMatrix = new Matrix();
		rotatedMatrix.reset();
		rotatedMatrix.setRotate(180.0f, 15, 15);
	}
	
	@Override
	public void doDraw() 
	{
		paint.setColor(lightBlueColor);
		paint.setStyle(Style.FILL);
		canvas.drawRect(0, 0, width, height, paint);

		paint.setStyle(Style.FILL);
		paint.setColor(blueColor);

		if(rotated)
			canvas.drawRect(1, 0, width, yPos, paint);
		else
			canvas.drawRect(1, yPos, width, height, paint);
		
		/*paint.setColor(Color.BLACK);
		paint.setStyle(Style.STROKE);
		canvas.drawRect(0, 0, width - 1, height - 1, paint);
		canvas.drawLine(0, 353, width - 1, 353, paint);
		canvas.drawLine(0, 30, width - 1, 30, paint);
		*/
		
		paint.setColor(Color.BLACK);
		
		if(rotated){
			Matrix currentMatrix = canvas.getMatrix();
			canvas.setMatrix(rotatedMatrix);
			//canvas.drawText(String.valueOf(level), 10, 10, paint);
			canvas.setMatrix(currentMatrix);
		}
		//else
		//	canvas.drawText(String.valueOf(level), 15, 353, paint);
		
		/*if(rotated)
			canvas.rotate(180);*/
			//graphics.drawRect(left, (int) (top + (maxLevel-level)/((float)maxLevel/(height-30)) + 30), left + width - 1, top + height - 1, true);
		//	graphics.drawRect(left, 30, left + width - 1, top + yPos, true);
			//graphics.drawText(String.valueOf(level), left, top);
//		}
	//	else
		//{
			//graphics.drawRect(left, (int) (top + level/((float)maxLevel/(height-30))), left + width - 1, top + height - 1, true);
		//}
	}

	@Override
	protected void doTouch(TouchEvent e) 
	{
		if(rotated)
		{
			if(e.getY() >  30)
			{
				level = (int) (((float)(maxLevel-minLevel)/(height-30))*(e.getY()-30)) + minLevel + 1;
				yPos = e.getY();
			}
		}
		else
		{
			if(e.getY() < 353)
			{
				level = (int) (((float)(maxLevel-minLevel)/(height-30))*e.getY());
				level = maxLevel - level;
				yPos = e.getY();
			}
		}
		
	}
}
