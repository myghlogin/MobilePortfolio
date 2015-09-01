package com.interactiveball.api.gui;

import com.interactiveball.api.interfaces.TouchEvent;

public interface OnTouchListener 
{
	void onTouchUp(TouchControl control, TouchEvent event);
	void onTouchDown(TouchControl control, TouchEvent event);
	void onTouchMove(TouchControl control, TouchEvent event);
}
