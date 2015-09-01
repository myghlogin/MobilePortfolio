package com.interactiveball.api.interfaces;

import android.graphics.Rect;

public interface Graphics 
{
	Pixmap newPixmap(String fileName, PixelFormat format);
	void drawPixmap(Pixmap pixmap, int x, int y);
	void drawPixmap(Pixmap pixmap, Rect src, Rect dest);
	void drawPixmap(Pixmap pixmap, int srcX, int srcY, int srcWidth, int srcHeight, int destX, int destY, int destWidth, int destHeight);
	void drawRect(int x, int y, int x2, int y2, boolean filled);
	void drawText(String text, int x, int y);
	void rotate(float degree);
	void restore();
}
