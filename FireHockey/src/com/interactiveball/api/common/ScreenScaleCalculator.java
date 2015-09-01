package com.interactiveball.api.common;

import java.util.*;
import java.lang.*;

import android.graphics.Rect;
import android.util.Log;

import com.interactiveball.api.exception.UnknownCaseException;

public class ScreenScaleCalculator 
{
	private final Map<String, Integer> screenScalingCases = new HashMap<String, Integer>();
	private final StringBuilder compareStringBuilder = new StringBuilder();
	private int sw;
	private int sh;
	private int fw;
	private int fh;
	
	public ScreenScaleCalculator(int screenWidth, int screenHeight, int frameWidth, int frameHeight)
	{
		Log.d("GOAL_DEBUG", "in");
		sw = screenWidth;
		sh = screenHeight;
		fw = frameWidth;
		fh = frameHeight;
		
		screenScalingCases.put("00", 4);
		screenScalingCases.put("-10", 1);
		screenScalingCases.put("10", 2);
		screenScalingCases.put("01", 1);
		screenScalingCases.put("0-1", 2);
		screenScalingCases.put("-1-1", 3);
		screenScalingCases.put("-11", 1);
		screenScalingCases.put("1-1", 2);
		screenScalingCases.put("11", 3);
		Log.d("GOAL_DEBUG", "out");
	}

	public ScreenConfig getScaledBounds(int screenWidth, int screenHeight, int frameWidth, int frameHeight)
	{
		setState(screenWidth, screenHeight, frameWidth, frameHeight);
		return getScaledBounds();
	}
	
	public ScreenConfig getScaledBounds()
	{
		Log.d("GOAL_DEBUG", "in proc");
		compareStringBuilder.append(compare(sw, fw));
		compareStringBuilder.append(compare(sh, fh));
		Log.d("GOAL_DEBUG", "in proc1:"+compareStringBuilder.toString());
		
		Integer scaleCase = Integer.valueOf(screenScalingCases.get(compareStringBuilder.toString()));
		compareStringBuilder.setLength(0);
		if(scaleCase == null)
			throw new NullPointerException("Incorrect case integer value");
		Log.d("GOAL_DEBUG", "in proc2");
		
		Rect rect;
		ScreenConfig.Builder config = new ScreenConfig.Builder();
		config.setScreenWidth(sw).setScreenHeight(sh);
		
		if(scaleCase == 1)
		{ // free space at the top and at the bottom
			int delta = (int)getScaledValue(fw, fh, sw-fw);
			rect = new Rect();
			calcScaledRect(rect, delta, false);			
			config.setDrawingRect(rect)
			      .setPadding(rect.top)
			      .setPaddingOrientation(Orientation.VERTICAL);

			Log.d("GOAL_DEBUG", "RENDER CASE 1");
		}
		else if(scaleCase == 2)
		{ // free space at the left and at the right
			int delta = (int)getScaledValue(fh, fw, sh-fh);
			rect = new Rect();
			calcScaledRect(rect, delta, true);			
			config.setDrawingRect(rect)
			      .setPadding(rect.left)
			      .setPaddingOrientation(Orientation.HORIZONTAL);

			Log.d("GOAL_DEBUG", "RENDER CASE 2");
		}
		else if(scaleCase == 3)
		{ // free space whether horizontal or vertical
		  // d - delta, r - rect, ss - screen square, s - square	
			int dh = (int)getScaledValue(fw, fh, sw-fw);  // vertical
			int dw = (int)getScaledValue(fh, fw, sh-fh);  // horizontal 
			Rect rh = new Rect();	
			Rect rw = new Rect();	
			calcScaledRect(rh, dh, false);
			calcScaledRect(rw, dw, true);
			int ss = sw*sh;
			int srh = rh.width()*rh.height();
			int srw = rw.width()*rw.height();
			int dsrh = ss - srh;
			int dsrw = ss - srw;
			
			Log.d("GOAL_DEBUG", "CASE3 RH, RW");
			Log.d("GOAL_DEBUG", rh.toShortString());
			Log.d("GOAL_DEBUG", rw.toShortString());
			Log.d("GOAL_DEBUG", "CASE3 dh, dw, dsrh, dsrw");
			Log.d("GOAL_DEBUG", String.valueOf(dh));
			Log.d("GOAL_DEBUG", String.valueOf(dw));
			Log.d("GOAL_DEBUG", String.valueOf(dsrh));
			Log.d("GOAL_DEBUG", String.valueOf(dsrw));
			
			if(dsrh >= 0 && dsrw >= 0){
				if(dsrh < dsrw){
					Log.d("GOAL_DEBUG", "RENDER CASE 3.1");
					config.setDrawingRect(rh).setPaddingOrientation(Orientation.VERTICAL).setPadding(rh.top);
				}
				else{
					Log.d("GOAL_DEBUG", "RENDER CASE 3.2");
					config.setDrawingRect(rw).setPaddingOrientation(Orientation.HORIZONTAL).setPadding(rw.left);
				}
			}
			else{
				if(dsrh < dsrw){
					Log.d("GOAL_DEBUG", "RENDER CASE 3.3");
					config.setDrawingRect(rw).setPaddingOrientation(Orientation.HORIZONTAL).setPadding(rw.left);
				}
				else{
					Log.d("GOAL_DEBUG", "RENDER CASE 3.4");
					config.setDrawingRect(rh).setPaddingOrientation(Orientation.VERTICAL).setPadding(rh.top);
				}
			}
		}
		else if(scaleCase == 4) // the same both of width and height
		{
			config.setDrawingRect(new Rect(0, 0, sw, sh));
			config.setPadding(0);
			config.setPaddingOrientation(Orientation.HORIZONTAL);
		}
		else
			throw new UnknownCaseException("Unknown case of scaling");
		Log.d("GOAL_DEBUG", "config obj: "+String.valueOf(config));
		return config.create();
	}
	
	private void setState(int screenWidth, int screenHeight, int frameWidth, int frameHeight)
	{
		sw = screenWidth;
		sh = screenHeight;
		fw = frameWidth;
		fh = frameHeight;
	}

	private void calcScaledRect(Rect rect, int d, boolean horizontal)
	{
		if(horizontal){
			rect.left = (sw - fw - d)/2;
			rect.right = fw + d + rect.left;
			rect.top = 0;
			rect.bottom = sh;			
		}
		else{
			rect.left = 0;
			rect.right = sw;
			rect.top = (sh - fh - d)/2;
			rect.bottom = fh + d + rect.top;
		}
	}
	
	private int compare(int a, int b)
	{
		if(a < b)
			return -1;
		if(a > b)
			return 1;
		
		return 0;
	}
	
	private float getScaledValue(float a, float b, float dA)
	{
		Log.d("GOAL_DEBUG", "get scaled value");
		Log.d("GOAL_DEBUG", "a:b:dA = " + String.valueOf(a)+":"+String.valueOf(b)+":"+String.valueOf(dA));
		Log.d("GOAL_DEBUG", "dA>0 => " + String.valueOf(((a + dA)*b - a*b)/a));
		Log.d("GOAL_DEBUG", "dA<0 => " + String.valueOf(-((a - dA)*b + a*b)/a));

		if(dA == 0)
			return 0;
		if(dA >= a)
			return b;
		
		a = Math.abs(a);
		b = Math.abs(b);
		
		return ((a + dA)*b - a*b)/a;
	}

}
