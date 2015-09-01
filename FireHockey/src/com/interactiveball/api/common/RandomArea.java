package com.interactiveball.api.common;


import java.util.Random;
import android.graphics.Point;
import android.graphics.Rect;


public class RandomArea 
{
	private Point centerPoint;
	private Point[] points;
	private int minRadius;
	private int maxRadius;
	private float angleStep;
	private int pixelStep;
	private int pointCount;
	private Rect rect;
	private Random random;
	
	public RandomArea(int centerX, int centerY, int minRadius, int maxRadius, float angleStep, int pixelStep) 
	{
		this.angleStep = Math.abs(angleStep);
		
		if(this.angleStep >= 360f)  // make angle step within interval (0;360)
			this.angleStep = this.angleStep - ((int)(this.angleStep/360f))*360f;
		if(this.angleStep == 0f)
			throw new IllegalArgumentException("angleStep must be more then zero");

		this.pointCount = 360f%this.angleStep == 0 ? (int)(360f/this.angleStep) : (int)(360f/this.angleStep) + 1;
		this.centerPoint = new Point(centerX, centerY);
		this.minRadius = minRadius;
		this.maxRadius = maxRadius;
		this.pixelStep = Math.abs(pixelStep);
		this.rect = null;
		this.random = new Random();

		this.points = new Point[pointCount];

		for(int i = 0; i < pointCount; i++)
			points[i] = new Point();
		
		prepare();
	}
	
	public Point getCenterPoint() {
		return centerPoint;
	}

	public int getMinRadius() {
		return minRadius;
	}

	public int getMaxRadius() {
		return maxRadius;
	}

	public float getAngleStep() {
		return angleStep;
	}

	public int getPointCount() {
		return pointCount;
	}

	public int getRectX()
	{
		getOutsideRect();
		return rect.left;
	}

	public int getRectY()
	{
		getOutsideRect();
		return rect.top;
	}
	
	public int getRectWidth()
	{
		getOutsideRect();
		return Math.abs(rect.width());
	}

	public int getRectHeight()
	{
		getOutsideRect();
		return Math.abs(rect.height());
	}
	
	public Point getPoint(int index)
	{
		return points[index]; 
	}
	
	public Rect getOutsideRect()
	{
		if(rect == null)
		{
			rect = new Rect();
		
			rect.left = points[0].x;
			rect.top = points[0].y;
			rect.right = points[0].x;
			rect.bottom = points[0].y;
			
			for(int i = 1; i < pointCount; i++)
			{
				if(points[i].x < rect.left)
					rect.left = points[i].x; 
				if(points[i].x > rect.right)
					rect.right = points[i].x; 
				if(points[i].y < rect.top)
					rect.top = points[i].y; 
				if(points[i].y > rect.bottom)
					rect.bottom = points[i].y; 
			}
		}
		
		return rect;
	}

	public boolean isPointInside(int x, int y)
	{
		/*float xDiff = x - centerPoint.x;
		float yDiff = y - centerPoint.y;
		float dist = (float) Math.sqrt(xDiff*xDiff + yDiff*yDiff);
		float sinL = yDiff/dist;
		float cosL = xDiff/dist;
		float buffSinL, buffCosL;
		Point borderPoint1 = null, borderPoint2 = null; 
		int i = 0, sectorNumber = 0;
		
		for(; i < pointCount; i++)
		{
			xDiff = points[i].x - centerPoint.x;
			yDiff = points[i].y - centerPoint.y;
			dist = (float) Math.sqrt(xDiff*xDiff + yDiff*yDiff);
			buffSinL = yDiff/dist;
			buffCosL = xDiff/dist;

			if(sinL >= 0 && cosL > 0)  // I sector
			{
				sectorNumber = 1;
				
				if(buffSinL >= sinL && buffCosL <= cosL) // if step over angle defined by (x;y)
				{
					borderPoint1 = points[i];
					break;
				}
			}
			else if(sinL > 0 && cosL <= 0)  // II sector
			{
				sectorNumber = 2;
				
				if(buffSinL <= sinL && buffCosL <= cosL)
				{
					borderPoint1 = points[i];
					break;
				}
			}
			else if(sinL <= 0 && cosL < 0) // III sector
			{
				sectorNumber = 3;
				
				if(buffSinL <= sinL && buffCosL >= cosL)
				{
					borderPoint1 = points[i];
					break;
				}
			}
			else // if(sinL < 0 && cosL >= 0) - IV sector
			{
				sectorNumber = 4;
				
				if(buffSinL >= sinL && buffCosL >= cosL)
				{
					borderPoint1 = points[i];
					break;
				}
			}
		}

		if(i == 0)
			borderPoint2 = borderPoint1;
		else
			borderPoint2 = points[i-1];		
		
		if(borderPoint1 != null && borderPoint2 != null)
		{
			if(sectorNumber == 1 || sectorNumber == 2)
				return y >= (borderPoint1.y + borderPoint2.y)/2;
			else if(sectorNumber == 3 || sectorNumber == 4)
				return y <= (borderPoint1.y + borderPoint2.y)/2;			
		}
		
		return false;*/
		int delta = -1;
		int dx, dy, dx2, dy2, ix = 0, iy = 0, ix2 = 0, iy2 = 0;
		Point pointX = points[0], pointY = points[0], pointX2 = null, pointY2 = null;
		int sectorNumber = 0;
		boolean resX = false, resY = false;
		
		getOutsideRect();
		
		if(x < rect.left || x > rect.right || y < rect.top || y > rect.bottom)
			return false;
		
		dx = Math.abs(points[0].x - x);
		dy = Math.abs(points[0].y - y);

		for(int i = 1; i < pointCount; i++)
		{
			dx2 = Math.abs(points[i].x - x);
			dy2 = Math.abs(points[i].y - y);

			if(dx2 < dx)
			{
				pointX = points[i];
				ix = i;
			}
			if(dy2 < dy)
			{
				pointY = points[i];
				iy = i;
			}
			
			dx = dx2;
			dy = dy2;
		}
		
		if(x > centerPoint.x && y <= centerPoint.y)
			sectorNumber = 1;
		else if(x <= centerPoint.x && y < centerPoint.y)
			sectorNumber = 2;
		else if(x < centerPoint.x && y >= centerPoint.y)
			sectorNumber = 3;
		else // if(x >= centerPoint.x && y > centerPoint.y)
			sectorNumber = 4;
		
		if(ix == pointCount - 1 || pointCount == 1)	{
			dx = Math.abs(x - points[0].x);
			ix2 = 0;
		}
		else {
			dx = Math.abs(x - points[ix+1].x);
			ix2 = ix+1;
		}
		
		if(pointCount == 1)	{
			dx2 = Math.abs(x - points[0].x);
			ix = 0;
		}
		else if(ix == 0) {
			dx2 = Math.abs(x - points[pointCount-1].x);
			ix = pointCount-1;
		}
		else {
			dx2 = Math.abs(x - points[ix-1].x);
			ix = ix-1;
		}
		
		if(iy == pointCount - 1 || pointCount == 1)	{
			dy = Math.abs(y - points[0].y);
			iy2 = 0;
		}
		else {
			dy = Math.abs(y - points[iy+1].y);
			iy2 = iy+1;
		}
		
		if(pointCount == 1)	{
			dy2 = Math.abs(y - points[0].y);
			iy = 0;
		}
		else if(iy == 0) {
			dy2 = Math.abs(y - points[pointCount-1].y);
			iy = pointCount-1;
		}
		else {
			dy2 = Math.abs(y - points[iy-1].y);
			iy = iy-1;
		}
		
		if(dx < dx2)
			pointX2 = points[ix2];
		else
			pointX2 = points[ix];

		if(dy < dy2)
			pointY2 = points[iy2];
		else
			pointY2 = points[iy];
		
		if(sectorNumber == 1 || sectorNumber == 2)
			resY = y >= (pointY.y + pointY2.y)/2;
		else if(sectorNumber == 3 || sectorNumber == 4)
			resY = y <= (pointY.y + pointY2.y)/2;

		if(sectorNumber == 1 || sectorNumber == 4)
			resX = x <= (pointX.x + pointX2.x)/2;
		else if(sectorNumber == 2 || sectorNumber == 3)
			resX = x >= (pointX.x + pointX2.x)/2;
			
		return resX && resY;
	}
	
	private void prepare()
	{
		float angle = 0f;
		int radiusDiff = maxRadius - minRadius;
		int dist = random.nextInt(radiusDiff) + minRadius;
		
		for(int i = 0; i < pointCount; i++)
		{
			points[i].x = (int)(centerPoint.x + dist*Math.cos(Math.PI*angle/180f));
			points[i].y = (int)(centerPoint.y - dist*Math.sin(Math.PI*angle/180f)); 
			
			dist = random.nextBoolean() ? dist + pixelStep : dist - pixelStep;
			
			if(dist <= minRadius)
				dist = minRadius + pixelStep;
			else if(dist >= maxRadius)
				dist = maxRadius - pixelStep;
				
			angle += angleStep; 
		}
	}
}
