package com.interactiveball.api.gui;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.interactiveball.api.interfaces.Graphics;
import com.interactiveball.api.interfaces.TouchEvent;

import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint.Style;

public abstract class ParameterControl extends TouchControl
{
	public abstract static class Builder
	{
		protected int left;
		protected int top;
		protected int width;
		protected int height;
		protected Config config;
		protected Graphics graphics;
		protected int startLevel;
		protected int minLevel = 0;
		protected int maxLevel;
		protected boolean rotated;
		private int check = 0;
		
		public ParameterControl.Builder setLeft(int left) {
			this.left = left;
			check |= 0x1;
			return this;
		}
		public ParameterControl.Builder setTop(int top) {
			this.top = top;
			check |= 0x2;
			return this;
		}
		public ParameterControl.Builder setWidth(int width) {
			this.width = width;
			check |= 0x4;
			return this;
		}
		public ParameterControl.Builder setHeight(int height) {
			this.height = height;
			check |= 0x8;
			return this;
		}
		public ParameterControl.Builder setConfig(Config config) {
			this.config = config;
			check |= 0x10;
			return this;
		}
		public ParameterControl.Builder setGraphics(Graphics graphics) {
			this.graphics = graphics;
			check |= 0x20;
			return this;
		}
		public ParameterControl.Builder setStartLevel(int startLevel) {
			this.startLevel = startLevel;
			check |= 0x40;
			return this;
		}
		public ParameterControl.Builder setMaxLevel(int maxLevel) {
			this.maxLevel = maxLevel;
			check |= 0x80;
			return this;
		}
		public ParameterControl.Builder setRotated(boolean rotated) {
			this.rotated = rotated;
			check |= 0x100;
			return this;
		}
		
		public ParameterControl.Builder setMinLevel(int minLevel) {
			this.minLevel = minLevel;
			return this;
		}

		public ParameterControl create()
		{
			if(check != 0x1ff)
				throw new IllegalStateException();
			
			return createInstance(); 
		}
		
		protected abstract ParameterControl createInstance();
	}
	
	protected int level;
	protected int maxLevel;
	protected int minLevel;
	protected boolean rotated;
	
	public ParameterControl(int left, int top, int width, int height, Config config, Graphics graphics, int startLevel, int maxLevel, int minLevel, boolean rotated) 
	{
		super(left, top, width, height, config, graphics);
		this.level = Math.abs(startLevel);
		this.maxLevel = Math.abs(maxLevel);
		this.minLevel = Math.abs(minLevel);
		this.rotated = rotated;
		
	}
	
	public int getLevel() {
		return level;
	}
}
