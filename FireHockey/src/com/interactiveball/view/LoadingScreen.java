package com.interactiveball.view;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;

import android.graphics.Rect;
import android.util.Log;

import com.interactiveball.api.gui.ProgressBar;
import com.interactiveball.api.impl.AndroidPixmap;
import com.interactiveball.api.interfaces.Game;
import com.interactiveball.api.interfaces.PixelFormat;
import com.interactiveball.api.interfaces.Pixmap;
import com.interactiveball.api.interfaces.Screen;
import com.interactiveball.model.gamelogic.GC;

public class LoadingScreen extends Screen 
{
	private final ProgressBar progressBar;
	private final int progressStep = 100/28;
	private int progress = 2*progressStep;

	public LoadingScreen(Game game) 
	{
		super(game);
		
		Rect rect = GC.getLoadingProgressBarRect();
		progressBar = new ProgressBar(rect.left, rect.top, rect.width(), rect.height(), 
				                      Config.RGB_565, game.getGraphics(), 
				                      Assets.getLoadingProgressBarBackground(),
				                      Assets.getLoadingProgressBarForeground());
	}

	@Override
	public void update(float deltaTime) 
	{
		Assets.setLoadingText(game.getGraphics().newPixmap("images/loading_text.png", PixelFormat.FORMAT_RGB565));
		Assets.setScreenBackground(game.getGraphics().newPixmap("images/screen_background.jpg", PixelFormat.FORMAT_RGB565));

		game.getGraphics().drawPixmap(Assets.getLoadingText(), GC.getLoadingTextPoint().x, GC.getLoadingTextPoint().y);
		game.getGraphics().drawPixmap(Assets.getScreenBackground(), 0, 0);
		doProgress();
		
		Assets.setBackground(game.getGraphics().newPixmap("images/background.png", PixelFormat.FORMAT_RGB565));
		doProgress();
		Assets.setFireGateway(game.getGraphics().newPixmap("images/fire_gateway.png", PixelFormat.FORMAT_RGB565));
		doProgress();
		Assets.setFireGateway2(game.getGraphics().newPixmap("images/fire_gateway2.png", PixelFormat.FORMAT_RGB565));
		doProgress();
		Assets.setFirePuck(game.getGraphics().newPixmap("images/fire_puck.png", PixelFormat.FORMAT_RGB565));
		doProgress();
		Assets.setGamePuck(game.getGraphics().newPixmap("images/game_puck.png", PixelFormat.FORMAT_RGB565));
		doProgress();
		Assets.setGateway(game.getGraphics().newPixmap("images/gateway.png", PixelFormat.FORMAT_RGB565));
		doProgress();
		Assets.setGateway2(game.getGraphics().newPixmap("images/gateway2.png", PixelFormat.FORMAT_RGB565));
		doProgress();
		Assets.setFireGatewayFilled(game.getGraphics().newPixmap("images/fire_gateway_filled.png", PixelFormat.FORMAT_RGB565));
		doProgress();
		Assets.setFireGatewayFilled2(game.getGraphics().newPixmap("images/fire_gateway2_filled.png", PixelFormat.FORMAT_RGB565));
		doProgress();
		Assets.setLoadingProgressBarBackground(game.getGraphics().newPixmap("images/loading_progressbar_background.jpg", PixelFormat.FORMAT_RGB565));
		doProgress();
		Assets.setLoadingProgressBarForeground(game.getGraphics().newPixmap("images/loading_progressbar_foreground.jpg", PixelFormat.FORMAT_RGB565));
		doProgress();
		Assets.setBtnPlayUp(game.getGraphics().newPixmap("images/btn_play_up.jpg", PixelFormat.FORMAT_RGB565));
		doProgress();
		Assets.setBtnPlayDown(game.getGraphics().newPixmap("images/btn_play_down.jpg", PixelFormat.FORMAT_RGB565));
		doProgress();
		Assets.setBtnHelpUp(game.getGraphics().newPixmap("images/btn_help_up.jpg", PixelFormat.FORMAT_RGB565));
		doProgress();
		Assets.setBtnHelpDown(game.getGraphics().newPixmap("images/btn_help_down.jpg", PixelFormat.FORMAT_RGB565));
		doProgress();
		Assets.setBtnSoundOn(game.getGraphics().newPixmap("images/btn_sound_on.jpg", PixelFormat.FORMAT_RGB565));
		doProgress();
		Assets.setBtnSoundOff(game.getGraphics().newPixmap("images/btn_sound_off.jpg", PixelFormat.FORMAT_RGB565));
		doProgress();
		Assets.setLogotype(game.getGraphics().newPixmap("images/logotype.png", PixelFormat.FORMAT_RGB565));
		doProgress();
		Assets.setBtnMenuUp(game.getGraphics().newPixmap("images/btn_menu_up.jpg", PixelFormat.FORMAT_RGB565));
		doProgress();
		Assets.setBtnMenuDown(game.getGraphics().newPixmap("images/btn_menu_down.jpg", PixelFormat.FORMAT_RGB565));
		doProgress();
		Assets.setHelpText(game.getGraphics().newPixmap("images/help_text.png", PixelFormat.FORMAT_RGB565));
		doProgress();

		Assets.setFirePuckBorderCollisionSound(game.getAudio().newSound("sounds/firepuck_collision.ogg"));
		doProgress();
		Assets.setGamePuckBorderCollisionSound(game.getAudio().newSound("sounds/gamepuck_collision.ogg"));
		doProgress();
		//Assets.setFirePuckInGatewaySound(game.getAudio().newSound("sounds/firepuck_in_gateway.ogg"));
		//doProgress();
		Assets.setPucksCollisionSound(game.getAudio().newSound("sounds/pucks_collision.ogg"));
		doProgress();
		//Assets.setNextStepSound(game.getAudio().newSound("sounds/next_step.ogg"));
		//doProgress();
		Assets.setWinSound(game.getAudio().newSound("sounds/win.ogg"));
		doProgress();
		//Assets.setFireSound(game.getAudio().newSound("sounds/fire.ogg"));
		//doProgress();
		Assets.setButtonPressSound(game.getAudio().newSound("sounds/button_press.ogg"));
		doProgress();
		
		Settings.getInstance().load(game.getFileIO());
		Log.d("GOAL_DEBUG", "LoadingScreen: end loading");
		game.setScreen(new MainMenuScreen(game));
	}

	@Override
	public void present(float deltaTime) 
	{
		progressBar.setProgress(progress);
		progressBar.draw();
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
	
	private void doProgress()
	{
		present(0.0f);
		progress += progressStep;
	}

}
