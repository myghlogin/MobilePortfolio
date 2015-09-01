package com.interactiveball.model.gamelogic;

import android.graphics.Rect;
import android.util.Log;

import com.interactiveball.api.common.Direction;
import com.interactiveball.api.common.DirectionExtended;
import com.interactiveball.api.common.FieldComposer;
import com.interactiveball.api.common.FloatPoint;

public class Puck implements PuckCollision
{
	public static class Builder
	{
		private FloatPoint speed = new FloatPoint();
		private FloatPoint position = new FloatPoint();
		private int radius;
		private PuckType puckType;
		private FieldComposer<FloatPoint, Float> composer;
		private int check = 0;
		
		public Puck create()
		{
			if(check != 0x1f)
				throw new IllegalStateException();
			
			check = 0;
		
			return new Puck(position.getX(), position.getY(), speed.getX(), speed.getY(), radius, puckType, composer);
		}

		public Builder setSpeed(float x, float y) {
			this.speed.setX(x);
			this.speed.setY(y);
			check = check | 0x1;
			return this;
		}

		public Builder setPosition(float x, float y) {
			this.position.setX(x);
			this.position.setY(y);
			check = check | 0x2;
			return this;
		}

		public Builder setRadius(int radius) {
			this.radius = radius;
			check = check | 0x4;
			return this;
		}

		public Builder setPuckType(PuckType puckType) {
			this.puckType = puckType;
			check = check | 0x8;
			return this;
		}

		public Builder setComposer(FieldComposer<FloatPoint, Float> composer) {
			this.composer = composer;
			check = check | 0x10;
			return this;
		}

	}
	
	private FloatPoint speed;
	private FloatPoint position;
	private int radius;
	private PuckType puckType;
	private FieldComposer<FloatPoint, Float> composer;
	private Rect rect;
	private int xDelayTimer = 1;
	private int yDelayTimer = 1;
	
	public Puck(float startX, float startY, float speedX, float speedY, int radius, PuckType puckType, FieldComposer<FloatPoint, Float> composer)
	{
		this.speed = new FloatPoint((int)(speedX), (int)(speedY));
		this.position = new FloatPoint(startX, startY);
		this.radius = Math.abs(radius);
		this.puckType = puckType;
		this.composer = composer;
		this.rect = new Rect((int)this.position.x-this.radius, (int)this.position.y-this.radius, (int)this.position.x+this.radius, (int)this.position.y+this.radius);
	}
	
	public FloatPoint getSpeed() {
		return speed;
	}

	public FloatPoint getPosition() {
		return position;
	}
	
	public int getRadius() {
		return radius;
	}

	public PuckType getPuckType() {
		return puckType;
	}
	
	public float getSpeedValue()
	{
		return (float) Math.sqrt(speed.x*speed.x + speed.y*speed.y); 
	}

	public void update()
	{
		float value, currentValue, newValue;
		
		if(puckType == PuckType.GAME_PUCK)
		{
			Log.d("GOAL_P", "Game puck update IN");

			if(composer != null)
			{
				value = composer.composite(position);
				currentValue = getSpeedValue();
				newValue = currentValue + value;

				if(newValue <= 0 || currentValue  == 0)
				{
					speed.x = 0f;
					speed.y = 0f;
					return;
				}
				
				float dX = Math.abs(speed.x)*newValue/currentValue - Math.abs(speed.x);
				float dY = Math.abs(speed.y)*newValue/currentValue - Math.abs(speed.y);
				
				if(speed.x > 0)
					speed.x += dX;
				else if(speed.x < 0)
					speed.x -= dX;
				
				if(speed.y > 0)
					speed.y += dY;
				else if(speed.y < 0)
					speed.y -= dY;
				
/*				b.setLength(0);
				b.append(currentValue);
				b.append(":");
				b.append(value);
				b.append(":");
				b.append(newValue);
				b.append(":");
				b.append(dX);
				b.append(":");
				b.append(dY);
				b.append(":");
				b.append(speed.x);
				b.append(":");
				b.append(speed.y);
				Log.d("GOAL_P", b.toString());
	*/
				/*
				if(speed.x >= -dX && speed.x <= dX)
					speed.x = 0f;
				if(speed.y >= -dY && speed.y <= dY)
					speed.y = 0f;
					*/
			}
		}
		
		if(puckType != PuckType.STATIC_PUCK)
		{
			position.x += speed.x;
			position.y += speed.y;
			
			/*
			Log.d("GOAL_DEBUG", "POSITION");
			Log.d("GOAL_DEBUG", String.valueOf(position.x));
			Log.d("GOAL_DEBUG", String.valueOf(position.y));
			*/
			
			/*if(xDelayTimer == 1)
			{
				if(speed.x < 0)
					position.x--;
				else if(speed.x > 0)
					position.x++;
				
				xDelayTimer = (int) Math.abs(speed.x)+1;
			}
			else
				xDelayTimer--;
			
			if(yDelayTimer == 1)
			{
				if(speed.y < 0)
					position.y--;
				else if(speed.y > 0)
					position.y++;
				
				yDelayTimer = (int) Math.abs(speed.y)+1;
			}
			else
				yDelayTimer--;
			*/
			
		}
	}
	
	public void stop()
	{
		speed.x = 0;
		speed.y = 0;
	}
	
	public Rect getRect()
	{
		rect.left = (int)position.x-radius;
		rect.top = (int)position.y-radius;
		rect.right = (int)position.x+radius;
		rect.bottom = (int)position.y+radius;

		return rect;
	}
	
	@Override
	public void onBorderCollision(Direction borderSide) 
	{
		DirectionExtended direction = getDirection();
		
		switch(borderSide)
		{
			case BOTTOM:
				switch(direction)
				{
					case BOTTOM:
					case LEFT_BOTTOM:
					case RIGHT_BOTTOM:
						speed.y = -speed.y;
						break;
				}
				
				break;
			case LEFT:
				switch(direction)
				{
					case LEFT:
					case LEFT_BOTTOM:
					case LEFT_TOP:
						speed.x = -speed.x;
						break;
				}

				break;
			case RIGHT:
				switch(direction)
				{
					case RIGHT:
					case RIGHT_BOTTOM:
					case RIGHT_TOP:
						speed.x = -speed.x;
						break;
				}

				break;
			case TOP:
				switch(direction)
				{
					case TOP:
					case LEFT_TOP:
					case RIGHT_TOP:
						speed.y = -speed.y;
						break;
				}

				break;
		}
	}
	
	private DirectionExtended getDirection()
	{
		DirectionExtended direction = null;
		
		if(speed.x > 0 && speed.y > 0)
			direction = DirectionExtended.RIGHT_BOTTOM;
		else if(speed.x > 0 && speed.y < 0)
			direction = DirectionExtended.RIGHT_TOP;
		else if(speed.x < 0 && speed.y > 0)
			direction = DirectionExtended.LEFT_BOTTOM;
		else if(speed.x < 0 && speed.y < 0)
			direction = DirectionExtended.LEFT_TOP;
		else if(speed.x < 0 && speed.y == 0)
			direction = DirectionExtended.LEFT;
		else if(speed.x > 0 && speed.y == 0)
			direction = DirectionExtended.RIGHT;
		else if(speed.x == 0 && speed.y < 0)
			direction = DirectionExtended.TOP;
		else if(speed.x == 0 && speed.y > 0)
			direction = DirectionExtended.BOTTOM;
		
		return direction;
	}	
	
	
	
}
