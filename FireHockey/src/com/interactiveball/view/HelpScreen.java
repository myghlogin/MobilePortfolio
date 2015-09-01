package com.interactiveball.view;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;

import com.interactiveball.api.gui.Button;
import com.interactiveball.api.gui.OnClickListener;
import com.interactiveball.api.gui.TouchControl;
import com.interactiveball.api.interfaces.Game;
import com.interactiveball.api.interfaces.Screen;
import com.interactiveball.api.interfaces.TouchEvent;
import com.interactiveball.model.gamelogic.GC;

public class HelpScreen extends Screen 
{
	private Button mainMenuBtn;
	
	public HelpScreen(final Game game) 
	{
		super(game);
		
		mainMenuBtn = new Button(GC.getHelpMainMenuBtnRect().left, GC.getHelpMainMenuBtnRect().top,
   						     GC.getHelpMainMenuBtnRect().width(), GC.getHelpMainMenuBtnRect().height(),
 							 Config.RGB_565, game.getGraphics(), Assets.getBtnMenuUp(), Assets.getBtnMenuDown());
		
		mainMenuBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(TouchControl control)
			{
				if(Settings.getInstance().isSoundEnabled())
					Assets.getButtonPressSound().play(GC.getVolume());
				
				game.setScreen(new MainMenuScreen(game));
			}
		});	
	}

	@Override
	public void update(float deltaTime) 
	{
		for(TouchEvent event : game.getInput().getTouchEvents())
		{
			mainMenuBtn.onTouch(event);	
		}
	}

	@Override
	public void present(float deltaTime) 
	{
		game.getGraphics().drawPixmap(Assets.getScreenBackground(), 0, 0);
		game.getGraphics().drawPixmap(Assets.getHelpText(), 0, 0);
				
		mainMenuBtn.draw();
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
