package com.interactiveball.api.interfaces;

import java.util.List;

public interface Input 
{
	boolean isTouched(int pointerId);
	int getX(int pointerId);
	int getY(int pointerId);
	List<TouchEvent> getTouchEvents();	
}
