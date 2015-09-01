package com.interactiveball.api.impl;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;

import com.interactiveball.api.interfaces.Graphics;
import com.interactiveball.api.interfaces.Pixmap;
import com.interactiveball.api.interfaces.PixelFormat;

public class AndroidGraphics implements Graphics 
{
	private AssetManager assetManager;
	private Bitmap frameBuffer;
	private Canvas canvas;
	private Rect srcRect;
	private Rect destRect;
	private Paint paint;
	
	public AndroidGraphics(AssetManager assetManager, Bitmap frameBuffer) 
	{
		if(assetManager == null || frameBuffer == null)
			throw new IllegalArgumentException();
		
		this.assetManager = assetManager;
		this.frameBuffer = frameBuffer;
		this.canvas = new Canvas(frameBuffer);
		this.srcRect = new Rect();
		this.destRect = new Rect();
		this.paint = new Paint();
	}

	@Override
	public void drawPixmap(Pixmap pixmap, int x, int y) 
	{
		canvas.drawBitmap(pixmap.getBitmap(), x, y, null);
	}

	@Override
	public void drawPixmap(Pixmap pixmap, Rect src, Rect dest) 
	{
		canvas.drawBitmap(pixmap.getBitmap(), src, dest, null);	
	}

	@Override
	public void drawPixmap(Pixmap pixmap, int srcX, int srcY, int srcWidth,
			int srcHeight, int destX, int destY, int destWidth, int destHeight) 
	{
		srcRect.left = srcX;
		srcRect.top = srcY;
		srcRect.right = srcX + srcWidth - 1;
		srcRect.bottom = srcY + srcHeight -1;
		
		destRect.left = destX;
		destRect.top = destY;
		destRect.right = destX + destWidth - 1;
		destRect.bottom = destY + destHeight -1;		
	}
	
	@Override
	public Pixmap newPixmap(String fileName, PixelFormat format) 
	{
		Pixmap pixmap = null;
		Config config = null;
		InputStream in = null;
		Options options = new Options(); 
		Bitmap bitmap = null;
		
		if(format == PixelFormat.FORMAT_RGB565)
			config = Config.RGB_565;
		else if(format == PixelFormat.FORMAT_ARGB4444)
			config = Config.ARGB_4444;
		else
			config = Config.ARGB_8888;
		
		options.inPreferredConfig = config;
		
		try {
			in = assetManager.open(fileName);
			bitmap = BitmapFactory.decodeStream(in, null, options);
			
			if(bitmap == null) {
				throw new RuntimeException("Couldn't load bitmap from asset \'" + fileName + "\'");
			}
		}
		catch(IOException e) {
			throw new RuntimeException("Couldn't load bitmap from asset \'" + fileName + "\'");
		}
		finally 
		{
			if(in != null) {
				try {
					in.close();
				}
				catch(IOException e) {
					throw new RuntimeException("Couldn't close input stream for bitmap from asset \'" + fileName + "\'");
				}
			}
		}

		if(bitmap.getConfig() == Config.RGB_565)
			format = PixelFormat.FORMAT_RGB565;
		else if(bitmap.getConfig() == Config.ARGB_4444)
			format = PixelFormat.FORMAT_ARGB4444;
		else
			format = PixelFormat.FORMAT_ARGB8888;
		
		return new AndroidPixmap(bitmap, format);
	}
	
	public void drawRect(int x, int y, int x2, int y2, boolean filled)
	{
		if(filled)
			paint.setStyle(Style.FILL);
		else
			paint.setStyle(Style.STROKE);
	
		paint.setColor(Color.RED);
		canvas.drawRect(x, y, x2, y2, paint);
	}
	
	public void drawText(String text, int x, int y)
	{
		paint.setTextSize(15);
		paint.setColor(Color.BLACK);
		canvas.drawText(text, x, y, paint);
	}
	
	public void rotate(float degree)
	{
		canvas.save();
		Matrix m = canvas.getMatrix();
		m.preRotate(degree);
		canvas.setMatrix(m);
		
		canvas.rotate(degree);
		
	}
	
	public void restore(){
		canvas.restore();
	}

}
