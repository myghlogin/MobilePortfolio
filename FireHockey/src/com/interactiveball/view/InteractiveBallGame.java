package com.interactiveball.view;

import com.interactiveball.api.impl.AndroidGame;
import com.interactiveball.api.interfaces.Screen;

public class InteractiveBallGame extends AndroidGame 
{
	@Override
	public Screen getStartScreen() 
	{
		return new LoadingScreen(this);
	}

}
