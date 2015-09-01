package com.interactiveball.api.common;

import android.graphics.Rect;
import android.util.Log;

public class ScreenConfig 
{
	public static class Builder
	{
		private int screenWidth;
		private int screenHeight;
		private Rect drawingRect;
		private int padding;
		private Orientation paddingOrientation;
		private int check = 0;
		
		public Builder(){}

		public Builder(int screenWidth, int screenHeight) 
		{
			super();
			this.screenWidth = screenWidth;
			this.screenHeight = screenHeight;
		}
			
		public ScreenConfig create()
		{
			Log.d("GOAL_DEBUG", "Check");
			Log.d("GOAL_DEBUG", String.valueOf(check));
			if(check != 0xf8)
				throw new IllegalStateException("Object of ScreenConfig class is not initialized at all");
			
			return new ScreenConfig(screenWidth, screenHeight, drawingRect, padding, paddingOrientation);
		}
		
		public int getScreenWidth() {
			return screenWidth;
		}

		public Builder setScreenWidth(int screenWidth) {
			this.screenWidth = screenWidth;
			check = check | 0x80; 
			Log.d("GOAL_DEBUG", "Set SW");
			Log.d("GOAL_DEBUG", String.valueOf(check));
			return this;
		}

		public int getScreenHeight() {
			return screenHeight;
		}

		public Builder setScreenHeight(int screenHeight) {
			this.screenHeight = screenHeight;
			check = check | 0x40; 
			return this;
		}

		public Rect getDrawingRect() {
			return drawingRect;
		}

		public Builder setDrawingRect(Rect drawingRect) {
			this.drawingRect = drawingRect;
			check = check | 0x20; 
			return this;
		}

		public int getPadding() {
			return padding;
		}

		public Builder setPadding(int padding) {
			this.padding = padding;
			check = check | 0x10; 
			return this;
		}

		public Orientation getPaddingOrientation() {
			return paddingOrientation;
		}

		public Builder setPaddingOrientation(Orientation paddingOrientation) {
			this.paddingOrientation = paddingOrientation;
			check = check | 0x8; 
			return this;
		}
	}
	
	private int screenWidth = 0;
	private int screenHeight = 0;
	private Rect drawingRect;
	private int padding;
	private Orientation paddingOrientation;
	
	private ScreenConfig(int screenWidth, int screenHeight, Rect drawingRect,
			int padding, Orientation paddingOrientation) 
	{
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.drawingRect = drawingRect;
		this.padding = padding;
		this.paddingOrientation = paddingOrientation;
	}
	
	public int getScreenWidth() {
		return screenWidth;
	}

	public ScreenConfig setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
		return this;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public ScreenConfig setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
		return this;
	}

	public Rect getDrawingRect() {
		return drawingRect;
	}

	public ScreenConfig setDrawingRect(Rect drawingRect) {
		this.drawingRect = drawingRect;
		return this;
	}

	public int getPadding() {
		return padding;
	}

	public ScreenConfig setPadding(int padding) {
		this.padding = padding;
		return this;
	}

	public Orientation getPaddingOrientation() {
		return paddingOrientation;
	}

	public ScreenConfig setPaddingOrientation(Orientation paddingOrientation) {
		this.paddingOrientation = paddingOrientation;
		return this;
	}
	
	
	
}
