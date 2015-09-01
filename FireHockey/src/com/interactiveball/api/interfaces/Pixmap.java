package com.interactiveball.api.interfaces;

import android.graphics.Bitmap;

public interface Pixmap 
{
	int getWidth();
	int getHeight();
	PixelFormat getFormat();
	Bitmap getBitmap();
	void dispose();
}
