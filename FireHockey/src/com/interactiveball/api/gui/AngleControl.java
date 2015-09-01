package com.interactiveball.api.gui;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.Log;

import com.interactiveball.api.common.FloatPoint;
import com.interactiveball.api.interfaces.Graphics;
import com.interactiveball.api.interfaces.TouchEvent;
import com.interactiveball.view.Assets;

public class AngleControl extends ParameterControl 
{
	public static class Builder extends ParameterControl.Builder
	{
		@Override
		protected ParameterControl createInstance() {
			return new AngleControl(left, top, width, height, config, graphics, startLevel, maxLevel, minLevel, rotated);
		}
	}

	private static final float ellipsisVerticalParameter = 30;     // a
	private static final float ellipsisHorizontalParameter = 202;  // b (b > a)
	private final FloatPoint centerPoint;
	private final FloatPoint centerPointRotated;
	private float lineA;
	private float lineB;
	private Matrix rotatedMatrix;
	
	public AngleControl(int left, int top, int width, int height,
			Config config, Graphics graphics, int startLevel, int maxLevel,
			int minLevel, boolean rotated) 
	{
		super(left, top, width, height, config, graphics, startLevel, maxLevel,
				minLevel, rotated);
		
		centerPoint = new FloatPoint(width/2, 0);
		centerPointRotated = new FloatPoint(width/2, ellipsisVerticalParameter);
		
		// variant for 90
		lineA = -30f;
		lineB = 6059f;
		
		rotatedMatrix = new Matrix();
		rotatedMatrix.reset();
		rotatedMatrix.setRotate(180.0f, width/2, height/2);
	}

	@Override
	protected void doTouch(TouchEvent e) 
	{
		float x = e.getX() - ellipsisHorizontalParameter;
		float a = ellipsisHorizontalParameter*ellipsisHorizontalParameter;
		float b = ellipsisVerticalParameter*ellipsisVerticalParameter;
		float ellipsisY = (float) Math.sqrt(( (1 - x*x/a)*b ));
		Log.d("angleCtrl", "x = " + String.valueOf(x));
		Log.d("angleCtrl", "Y = " + String.valueOf(ellipsisY));
		
		if(x < 0)
			level = (int) (Math.atan(ellipsisY/(Math.abs(x)))*180f/Math.PI);
		else if(x > 0)
			level = (int) (180f - Math.atan(ellipsisY/(Math.abs(x)))*180f/Math.PI);
		else if(x >= ellipsisHorizontalParameter - 2)
			level = 180;
		else
			level = 90;
		
		FloatPoint cp = (rotated) ? centerPointRotated : centerPoint;
		ellipsisY = (rotated) ? (ellipsisVerticalParameter - ellipsisY) : ellipsisY;
		
		lineA = (ellipsisY-cp.y)/(e.getX() - cp.x);
		lineB = -lineA*cp.x + cp.y;

		if(rotated)
			level = 180 - level;
		
		Log.d("angleCtrl", "LineCoords" + lineA + ":" + lineB);
	}

	@Override
	protected void doDraw() 
	{	
		Bitmap background, foreground;
		canvas.drawARGB(0, 0, 0, 0);
		
		if(rotated){
			foreground = Assets.getFireGatewayFilled().getBitmap();
			background = Assets.getFireGateway().getBitmap();
		}
		else{
			foreground = Assets.getFireGatewayFilled2().getBitmap();
			background = Assets.getFireGateway2().getBitmap();
		}

		canvas.drawBitmap(background, 0, 0, null);
		
		int bw = foreground.getWidth();
		int bh = foreground.getHeight();
		Paint pixelPaint = new Paint();
		float by;
		
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++)
			{
				by = height - y;  
				if(rotated){
					if( (level <= 90 && by >= lineA*x + lineB) ||
						(level > 90 && by <= lineA*x + lineB) ){
							pixelPaint.setColor(foreground.getPixel(x, y));
							canvas.drawPoint(x, y, pixelPaint);
					}
				}
				else{
					if( (level <= 90 && by <= lineA*x + lineB) ||
						(level > 90 && by >= lineA*x + lineB) ){
							pixelPaint.setColor(foreground.getPixel(x, y));
							canvas.drawPoint(x, y, pixelPaint);
					}
				}
			}
		}
		
		pixelPaint.setColor(Color.BLACK);
		pixelPaint.setTextSize(20);
		
		if(rotated){
			Matrix currentMatrix = canvas.getMatrix();
			canvas.setMatrix(rotatedMatrix);
			//canvas.drawText(String.valueOf(level), width/2, height/2+10, pixelPaint);
			canvas.setMatrix(currentMatrix);
		}
		//else
		//	canvas.drawText(String.valueOf(level), width/2, height/2+10, pixelPaint);
		
		/*pixelPaint.setStyle(Style.STROKE);
		
		if(rotated)
			canvas.drawOval(new RectF(0, -30, width, ellipsisVerticalParameter*2), pixelPaint);
		else
			canvas.drawOval(new RectF(0, 4, width, ellipsisVerticalParameter*2), pixelPaint);
			*/	
		/*Paint paint = new Paint();
		paint.setColor(Color.RED);
		canvas.drawRect(0, 0, width, height, paint);
		paint.setColor(Color.BLACK);
		canvas.drawText(String.valueOf(level), 20, 20, paint);
		Log.d("angleCtrl", String.valueOf(level));
		*/
	}

	
}
