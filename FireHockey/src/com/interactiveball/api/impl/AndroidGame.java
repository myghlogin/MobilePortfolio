package com.interactiveball.api.impl;

import com.interactiveball.api.common.Logger;
import com.interactiveball.api.common.ScreenConfig;
import com.interactiveball.api.common.ScreenScaleCalculator;
import com.interactiveball.api.interfaces.Audio;
import com.interactiveball.api.interfaces.FileIO;
import com.interactiveball.api.interfaces.Game;
import com.interactiveball.api.interfaces.Graphics;
import com.interactiveball.api.interfaces.Input;
import com.interactiveball.api.interfaces.Screen;
import com.interactiveball.model.gamelogic.GC;
import com.interactiveball.view.LoadingScreen;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Point;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;

public class AndroidGame extends Activity implements Game
{
	AndroidFastRenderView renderView;
	Graphics graphics;
	Audio audio;
	Input input;
	FileIO fileIO;
	Screen screen;
	WakeLock wakeLock;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		int frameBufferWidth = GC.getFrameBufferWidth();
		int frameBufferHeight = GC.getFrameBufferHeight();
		Bitmap frameBuffer  = Bitmap.createBitmap(frameBufferWidth, frameBufferHeight, Config.RGB_565);
		
		Point screenResolution = new Point();
		getWindowManager().getDefaultDisplay().getRealSize(screenResolution);

		Log.d("GOAL_DEBUG", "start1");
		ScreenScaleCalculator ssc = new ScreenScaleCalculator(screenResolution.x, screenResolution.y, frameBufferWidth, frameBufferHeight);
		Log.d("GOAL_DEBUG", String.valueOf(ssc));
		ScreenConfig screenConfig = ssc.getScaledBounds();
		Log.d("GOAL_DEBUG", "start3");

		float scaleX = (float)frameBufferWidth / screenConfig.getDrawingRect().width(); 
		float scaleY = (float)frameBufferHeight / screenConfig.getDrawingRect().height();
		
		Log.d("GOAL_DEBUG", "window wh");
		Log.d("GOAL_DEBUG", String.valueOf(screenResolution.x));
		Log.d("GOAL_DEBUG", String.valueOf(screenResolution.y));
		Log.d("GOAL_DEBUG", screenConfig.getDrawingRect().toShortString());
		
		renderView = new AndroidFastRenderView(this,  frameBuffer, screenConfig.getDrawingRect());
		graphics = new AndroidGraphics(getAssets(), frameBuffer);
		fileIO = new AndroidFileIO(this);
		audio = new AndroidAudio(this);
		input = new AndroidInput(renderView, scaleX, scaleY, screenConfig);
		screen = getStartScreen();
		setContentView(renderView);
		
	
		PowerManager powerManager = (PowerManager)getSystemService(Context.POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "GLGame");
		Log.d("GOAL_DEBUG", "Constructor was executed");
	}
	
	@Override
	protected void onResume() 
	{
		super.onResume();
		wakeLock.acquire();
		screen.resume();
		renderView.resume();
		Log.d("GOAL_DEBUG", "OnResume was executed");
	}

	@Override
	protected void onPause() 
	{
		Log.d("GOAL_DEBUG", "OnPause begins");
		
		super.onPause();
		wakeLock.release();
		renderView.pause();
		screen.pause();
		
		if(isFinishing())
			screen.dispose();

		Log.d("GOAL_DEBUG", "OnPause was executed");
	}

	@Override
	public Input getInput() {
		return input;
	}

	@Override
	public FileIO getFileIO() {
		return fileIO;
	}

	@Override
	public Graphics getGraphics() {
		return graphics;
	}

	@Override
	public Audio getAudio() {
		return audio;
	}

	@Override
	public void setScreen(Screen screen) 
	{
		if(screen == null)
			throw new IllegalArgumentException("Screen must be not null");
		
		Log.d("GOAL_DEBUG", "setScreen was called to " + screen.getClass().getName());
		
		this.screen.pause();
		this.screen.dispose();
		screen.resume();
		screen.update(0);
		this.screen = screen;
	}

	@Override
	public Screen getCurrentScreen() 
	{
		return screen;
	}

	@Override
	public Screen getStartScreen() {
		return null;
	}
	
	
}
