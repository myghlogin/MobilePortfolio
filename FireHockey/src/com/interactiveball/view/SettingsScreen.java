package com.interactiveball.view;

import com.interactiveball.api.interfaces.Game;
import com.interactiveball.api.interfaces.Screen;

public class SettingsScreen extends Screen 
{

	public SettingsScreen(Game game) {
		super(game);
	}

	@Override
	public void update(float deltaTime) {
		
	}

	@Override
	public void present(float deltaTime) 
	{
		game.getGraphics().drawRect(0, 0, 444, 800, true);
		game.getGraphics().drawText("Настройки", 50, 50);
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		
	}

}
