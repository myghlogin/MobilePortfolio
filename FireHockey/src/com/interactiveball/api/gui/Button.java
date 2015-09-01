package com.interactiveball.api.gui;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.util.Log;

import com.interactiveball.api.exception.UnknownCaseException;
import com.interactiveball.api.interfaces.Graphics;
import com.interactiveball.api.interfaces.Pixmap;
import com.interactiveball.api.interfaces.TouchEvent;
import com.interactiveball.api.interfaces.TouchType;

public class Button extends TouchControl implements OnInsideListener
{
	public enum ButtonState {UP, DOWN}
	
	private Pixmap backgroundUp;
	private Pixmap backgroundDown;
	private ButtonState state; 
	
	public Button(int left, int top, int width, int height, Config config,
			Graphics graphics, Pixmap backgroundUp, Pixmap backgroundDown) {
		super(left, top, width, height, config, graphics);
		
		this.backgroundUp = backgroundUp;
		this.backgroundDown = backgroundDown;
		this.state = ButtonState.UP;
	}

	@Override
	protected void doTouch(TouchEvent e) 
	{
		Log.d("touch", "in doTouch: " + e.getType());
		if(e.getType() == TouchType.TOUCH_UP)
			state = ButtonState.UP;
		else if(e.getType() == TouchType.TOUCH_DOWN || e.getType() == TouchType.TOUCH_MOVE)
			state = ButtonState.DOWN;
		
		Log.d("touch", "state = " + state);
	}

	@Override
	protected void doDraw() 
	{
		Bitmap bitmap = null;
		//int color = Color.argb(255, 0, 255, 0);
		
		//canvas.drawColor(color, Mode.OVERLAY);
		//canvas.drawColor(color, mode) drawARGB(0, 255, 0, 0);
		
		if(state == ButtonState.UP){
			if(backgroundUp != null)
				bitmap = backgroundUp.getBitmap();
		}
		else if (state == ButtonState.DOWN){
			if(backgroundDown != null)
				bitmap = backgroundDown.getBitmap();
		}
		else 
			throw new UnknownCaseException();
		
		if(bitmap != null)
			canvas.drawBitmap(bitmap, 0, 0, null);
	}

	@Override
	public void onEnter(TouchControl control) {
	}

	@Override
	public void onExit(TouchControl control) 
	{
		state = ButtonState.UP;
		Log.d("touch", "onExit");
	}

	public Pixmap getBackgroundUp() {
		return backgroundUp;
	}

	public void setBackgroundUp(Pixmap backgroundUp) {
		this.backgroundUp = backgroundUp;
	}

	public Pixmap getBackgroundDown() {
		return backgroundDown;
	}

	public void setBackgroundDown(Pixmap backgroundDown) {
		this.backgroundDown = backgroundDown;
	}

}
