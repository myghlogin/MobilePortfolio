package com.interactiveball.view;

import java.util.List;

import android.graphics.Bitmap.Config;
import android.util.Log;

import com.interactiveball.api.gui.Button;
import com.interactiveball.api.gui.OnClickListener;
import com.interactiveball.api.gui.OnTouchListener;
import com.interactiveball.api.gui.TouchControl;
import com.interactiveball.api.interfaces.Game;
import com.interactiveball.api.interfaces.Graphics;
import com.interactiveball.api.interfaces.Pixmap;
import com.interactiveball.api.interfaces.Screen;
import com.interactiveball.api.interfaces.TouchEvent;
import com.interactiveball.api.interfaces.TouchType;
import com.interactiveball.model.gamelogic.GC;

public class MainMenuScreen extends Screen 
{
	private Button startGameBtn;
	private Button helpBtn;
	private Button soundBtn;
	
	public MainMenuScreen(final Game game) 
	{
		super(game);
		
		startGameBtn = new Button(GC.getMmsStartGameBtnRect().left, GC.getMmsStartGameBtnRect().top,
				                  GC.getMmsStartGameBtnRect().width(), GC.getMmsStartGameBtnRect().height(),
				                  Config.RGB_565, game.getGraphics(), Assets.getBtnPlayUp(), Assets.getBtnPlayDown());
		
		helpBtn = new Button(GC.getMmsHelpBtnRect().left, GC.getMmsHelpBtnRect().top,
  			                 GC.getMmsHelpBtnRect().width(), GC.getMmsHelpBtnRect().height(),
 			                 Config.RGB_565, game.getGraphics(), Assets.getBtnHelpUp(), Assets.getBtnHelpDown());
		
		Pixmap soundPixmap;
		
		if(Settings.getInstance().isSoundEnabled())
			soundPixmap = Assets.getBtnSoundOn();
		else
			soundPixmap = Assets.getBtnSoundOff();
		
		soundBtn = new Button(GC.getMmsSoundBtnRect().left, GC.getMmsSoundBtnRect().top,
	                          GC.getMmsSoundBtnRect().width(), GC.getMmsSoundBtnRect().height(),
                              Config.RGB_565, game.getGraphics(), soundPixmap, soundPixmap);
		
		startGameBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(TouchControl control)
			{
				if(Settings.getInstance().isSoundEnabled())
					Assets.getButtonPressSound().play(GC.getVolume());
			
				game.setScreen(new GameScreen(game));
			}
		});
		helpBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(TouchControl control)
			{
				if(Settings.getInstance().isSoundEnabled())
					Assets.getButtonPressSound().play(GC.getVolume());
				
				game.setScreen(new HelpScreen(game));
			}
		});	
		soundBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(TouchControl control)
			{
				boolean value = Settings.getInstance().isSoundEnabled(); 
				Settings.getInstance().setSoundEnabled(!value);
				
				if(value){
					((Button)control).setBackgroundUp(Assets.getBtnSoundOff());
					((Button)control).setBackgroundDown(Assets.getBtnSoundOff());
				}
				else{
					if(Settings.getInstance().isSoundEnabled())
						Assets.getButtonPressSound().play(GC.getVolume());

					((Button)control).setBackgroundUp(Assets.getBtnSoundOn());
					((Button)control).setBackgroundDown(Assets.getBtnSoundOn());
				}
			}
		});	
	}

	@Override
	public void update(float deltaTime) 
	{
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		TouchEvent event;
	
		for(int i = 0; i < touchEvents.size(); i++)
		{ 
			event = touchEvents.get(i);
			
			startGameBtn.onTouch(event);
			helpBtn.onTouch(event);
			soundBtn.onTouch(event);
			
			/*if(event.getType() == TouchType.TOUCH_UP)
			{
				if(event.getX() >= 72 && event.getX() <= 372)
				{
					if(event.getY() >= 220 && event.getY() <= 340)
					{
						game.setScreen(new GameScreen(game));
					}
					else if(event.getY() >= 340 && event.getY() <= 460)
					{
						game.setScreen(new SettingsScreen(game));
					}
					else if(event.getY() >= 460 && event.getY() <= 580)
					{
						game.setScreen(new HelpScreen(game));
					}
				}
			}*/
		}
		
	}

	@Override
	public void present(float deltaTime) 
	{
		Graphics g = game.getGraphics();
		
		g.drawPixmap(Assets.getScreenBackground(), 0, 0);
		g.drawPixmap(Assets.getLogotype(), 0, 0);
		startGameBtn.draw();
		helpBtn.draw();
		soundBtn.draw();
	}

	@Override
	public void pause() 
	{
		
	}

	@Override
	public void resume() 
	{
		
	}

	@Override
	public void dispose() 
	{
		
	}

}
