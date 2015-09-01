package com.interactiveball.view;

import java.util.List;

import android.graphics.Bitmap.Config;
import android.graphics.Point;
import android.util.Log;

import com.interactiveball.api.gui.AngleControl;
import com.interactiveball.api.gui.ParameterControl;
import com.interactiveball.api.gui.SpeedControl;
import com.interactiveball.api.interfaces.Game;
import com.interactiveball.api.interfaces.Graphics;
import com.interactiveball.api.interfaces.Pixmap;
import com.interactiveball.api.interfaces.Screen;
import com.interactiveball.api.interfaces.TouchEvent;
import com.interactiveball.api.interfaces.TouchType;
import com.interactiveball.model.gamelogic.GC;
import com.interactiveball.model.gamelogic.GameMode;
import com.interactiveball.model.gamelogic.GameState;
import com.interactiveball.model.gamelogic.GamerType;
import com.interactiveball.model.gamelogic.World;

public class GameScreen extends Screen 
{
	private GameState state = GameState.Running;	
	private World world; 
	private ParameterControl speedCtrl1;
	private ParameterControl speedCtrl2;
//	private ParameterControl angleCtrl1;
//	private ParameterControl angleCtrl2;
	private ParameterControl angleAltCtrl1;
	private ParameterControl angleAltCtrl2;
	private final int xGameFieldIndent = 38;
	private final int yGameFieldIndent = 10;
	private final int yFirePuckIndent = 33;
	private int count = 0;
	
	public GameScreen(Game game) 
	{
		super(game);
		this.world = new World();
		ParameterControl.Builder builder = new SpeedControl.Builder();
		builder.setLeft(GC.getPointSpeedCtrl1().x).setTop(GC.getPointSpeedCtrl1().y)
		       .setWidth(GC.getCtrlWidth()).setHeight(GC.getCtrlHeight())
		       .setConfig(Config.RGB_565)
		       .setGraphics(game.getGraphics())
		       .setStartLevel(GC.getSpeedStartLevel()).setMaxLevel(GC.getSpeedMaxLevel())
		       .setRotated(true);
		this.speedCtrl1 = builder.create();
		
		builder.setLeft(GC.getPointSpeedCtrl2().x).setTop(GC.getPointSpeedCtrl2().y)
	       .setWidth(GC.getCtrlWidth()).setHeight(GC.getCtrlHeight())
	       .setConfig(Config.RGB_565)
	       .setGraphics(game.getGraphics())
	       .setStartLevel(GC.getSpeedStartLevel()).setMaxLevel(GC.getSpeedMaxLevel())
	       .setRotated(false);
		this.speedCtrl2 = builder.create();
		/*	
		builder.setLeft(GC.getPointAngleCtrl1().x).setTop(GC.getPointAngleCtrl1().y)
	       .setWidth(GC.getCtrlWidth()).setHeight(GC.getCtrlHeight())
	       .setConfig(Config.RGB_565)
	       .setGraphics(game.getGraphics())
	       .setStartLevel(GC.getAngleStartLevel())
	       .setMinLevel(GC.getAngleMinLevel()).setMaxLevel(GC.getAngleMaxLevel())
	       .setRotated(true);
		this.angleCtrl1 = builder.create();
		
		builder.setLeft(GC.getPointAngleCtrl2().x).setTop(GC.getPointAngleCtrl2().y)
		   .setWidth(GC.getCtrlWidth()).setHeight(GC.getCtrlHeight())
		   .setConfig(Config.RGB_565)
	       .setGraphics(game.getGraphics())
	       .setStartLevel(GC.getAngleStartLevel())
	       .setMinLevel(GC.getAngleMinLevel()).setMaxLevel(GC.getAngleMaxLevel())	       
	       .setRotated(false);
		this.angleCtrl2 = builder.create();
		*/
		builder = new AngleControl.Builder();
		builder.setLeft(GC.getPointGateway1().x).setTop(GC.getPointGateway1().y)
		   .setWidth(404).setHeight(34)
		   .setConfig(Config.RGB_565)
	       .setGraphics(game.getGraphics())
	       .setStartLevel(GC.getAngleStartLevel())
	       .setMinLevel(GC.getAngleMinLevel()).setMaxLevel(GC.getAngleMaxLevel())	       
	       .setRotated(true);
		this.angleAltCtrl1 = builder.create();

		builder.setLeft(GC.getPointGateway2().x).setTop(GC.getPointGateway2().y)
		   .setWidth(404).setHeight(34)
		   .setConfig(Config.RGB_565)
	       .setGraphics(game.getGraphics())
	       .setStartLevel(GC.getAngleStartLevel())
	       .setMinLevel(GC.getAngleMinLevel()).setMaxLevel(GC.getAngleMaxLevel())	       
	       .setRotated(false);
		this.angleAltCtrl2 = builder.create();
	}

	@Override
	public void update(float deltaTime) 
	{
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		TouchEvent event;
		float speedX, speedY;
		
		if(world.getMode() == GameMode.MODE_SET)
		{
			for(int i = 0; i < touchEvents.size(); i++)
			{
				event = touchEvents.get(i);
				
				speedCtrl1.onTouch(event);
				speedCtrl2.onTouch(event);
				//angleCtrl1.onTouch(event);
				//angleCtrl2.onTouch(event);
				angleAltCtrl1.onTouch(event);
				angleAltCtrl2.onTouch(event);
				
				if(event.getType() == TouchType.TOUCH_UP)
				{
					if(GC.getRectTouchFirePuck().contains(event.getX(), event.getY()) && world.getGamerType() == GamerType.GAMER_1)
					{
						speedX = (float) (Math.cos(Math.toRadians(angleAltCtrl1.getLevel()))*(speedCtrl1.getLevel()+0f)/1.0);
						speedY = (float) (Math.sin(Math.toRadians(angleAltCtrl1.getLevel()))*(speedCtrl1.getLevel()+0f)/1.0);
						world.setParameters(speedX, speedY);
						world.startAnimation();
					}
					else if(GC.getRectTouchFirePuck().contains(event.getX(), event.getY()) && world.getGamerType() == GamerType.GAMER_2)
					{
						speedX = (float) (-Math.cos(Math.toRadians(angleAltCtrl2.getLevel()))*(speedCtrl2.getLevel()+0f)/1.0);
						speedY = (float) (-Math.sin(Math.toRadians(angleAltCtrl2.getLevel()))*(speedCtrl2.getLevel()+0f)/1.0);
						world.setParameters(speedX, speedY);
						world.startAnimation();
					}
				}
			}
		}
		
		world.update(deltaTime);
		
		if(world.isGameOver())
		{
			Log.d("GOAL_DEBUG", "Before set to main menu from game");
			game.setScreen(new MainMenuScreen(game));
			Log.d("GOAL_DEBUG", "After set to main menu from game");
		}
	}

	@Override
	public void present(float deltaTime) 
	{
		Graphics g = game.getGraphics();
		
		g.drawPixmap(Assets.getBackground(), 0, 0);
		
		if(world.getGamerType() == GamerType.GAMER_1)
		{
			angleAltCtrl1.draw();
			g.drawPixmap(Assets.getGateway2(), GC.getPointGateway2().x, GC.getPointGateway2().y);

			if(world.isFirePuckVisibility())
				g.drawPixmap(Assets.getFirePuck(), (int)(world.getPuck1().getPosition().x - world.getPuck1().getRadius()), (int)(world.getPuck1().getPosition().y - world.getPuck1().getRadius()));
		}
		else
		{
			angleAltCtrl2.draw();
			g.drawPixmap(Assets.getGateway(), GC.getPointGateway1().x, GC.getPointGateway1().y);

			if(world.isFirePuckVisibility())
				g.drawPixmap(Assets.getFirePuck(), (int)(world.getPuck2().getPosition().x - world.getPuck2().getRadius()), (int)(world.getPuck2().getPosition().y - world.getPuck2().getRadius()));
		}
		
		//if(world.getMode() == GameMode.MODE_SET)
		//{
			if(world.getGamerType() == GamerType.GAMER_1){
				speedCtrl1.draw();
			}
			else{
				speedCtrl2.draw();
			}
		//}

		g.drawPixmap(Assets.getGamePuck(), (int)(world.getGamePuck().getPosition().x - world.getGamePuck().getRadius()), (int)(world.getGamePuck().getPosition().y - world.getGamePuck().getRadius()));
		
	/*	if(world.getGamerType() == GamerType.GAMER_1)
			g.drawPixmap(Assets.getGateway2(), GC.getPointGateway2().x, GC.getPointGateway2().y);
		else
			g.drawPixmap(Assets.getGateway(), GC.getPointGateway1().x, GC.getPointGateway1().y);
*/
		//g.drawText(String.valueOf(count++), 200, 200);
		//g.drawText(String.valueOf(world.count), 200, 250);	
		//g.drawRect(xGameFieldIndent + 120, yGameFieldIndent, xGameFieldIndent + 247, yGameFieldIndent + 5, true);
	//	g.drawRect(xGameFieldIndent + 120, yGameFieldIndent + 780, xGameFieldIndent + 247, yGameFieldIndent + 785, true);
		//g.drawRect(450, 770, 479, 799, false);
	}

	@Override
	public void pause() 
	{
		if(world.isGameOver())
		{
			Log.d("GOAL_DEBUG", "Save settings");
			Settings.getInstance().save(game.getFileIO());
		}
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
