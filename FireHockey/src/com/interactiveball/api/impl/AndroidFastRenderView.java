package com.interactiveball.api.impl;

import java.util.HashMap;
import java.util.Map;

import com.interactiveball.api.common.ScreenScaleCalculator;
import com.interactiveball.api.exception.UnknownCaseException;
import com.interactiveball.model.gamelogic.GC;

import android.graphics.*;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class AndroidFastRenderView extends SurfaceView implements Runnable
{
	AndroidGame game;
	Bitmap frameBuffer;
	Thread renderThread = null;
	SurfaceHolder holder;
	volatile boolean running = false;
	Rect drawingRect;
	
	public AndroidFastRenderView(AndroidGame game, Bitmap frameBuffer, Rect drawingRect)
	{
		super(game);
		this.game = game;
		this.frameBuffer = frameBuffer;
		this.holder = getHolder();
		this.drawingRect = drawingRect;
	}

	public void resume()
	{
		running = true;
		renderThread = new Thread(this);
		renderThread.start();
	}
	
	public void pause()
	{
		running = false;
		
		while(true)
		{
			try
			{
				renderThread.join();
				break;
			}
			catch(InterruptedException e)
			{
				
			}
		}
	}
	
	@Override
	public void run() 
	{
		long startTime = System.nanoTime();
		
		while(running)
		{
			if(!holder.getSurface().isValid())
				continue;
			
			float deltaTime = (System.nanoTime() - startTime) / 1000000000.0f;
			startTime = System.nanoTime();
			
			game.getCurrentScreen().update(deltaTime);
			game.getCurrentScreen().present(deltaTime);
			
			Canvas canvas = holder.lockCanvas();
			//Log.d("GOAL_DEBUG", drawingRect.toShortString());
			canvas.drawBitmap(frameBuffer, null, drawingRect, null);
			holder.unlockCanvasAndPost(canvas);
		}		
	}
	
	
}
