package com.interactiveball.model.gamelogic;

import java.util.Random;

import android.util.Log;

import com.interactiveball.api.common.Direction;
import com.interactiveball.api.common.FieldComposer;
import com.interactiveball.api.common.FloatPoint;
import com.interactiveball.api.common.RandomAreaField;
import com.interactiveball.api.common.SolidField;
import com.interactiveball.api.gui.ParameterControl;
import com.interactiveball.api.interfaces.Game;
import com.interactiveball.view.Assets;
import com.interactiveball.view.Settings;

public class World 
{
	static final int WORLD_WIDTH = GC.getFrameBufferWidth();
	static final int WORLD_HEIGHT = GC.getFrameBufferHeight();
	
	private Puck gamePuck;
	private Puck puck1;
	private Puck puck2;
	private Random random;
	private FieldComposer<FloatPoint, Float> composer;
	private SolidField mainField;
	private RandomAreaField[] randomFields = null;
	private int randomAreaFieldCount;
	
	private float tickTime = 0;
	private float tick = GC.getTick();

	public boolean gameOver = false;
	private GamerType gamerType = GamerType.GAMER_1;
	private GameMode mode = GameMode.MODE_SET;
	private int borderCollisionCount = 0;
	private boolean wasCollisionWithGamePuck = false;
	private boolean fixBorderCollision = false;
	private float puckFinishingTime = GC.getPuckFinishingTime();
	private int puckFinishingTimeInGateway = GC.getPuckFinishingTimeInGateway();
	private boolean firePuckVisibility = true;
	
	public int count = 0;
	public Settings settings;
	
	public World()
	{
		random = new Random();
		composer = new FieldComposer<FloatPoint, Float>();
		mainField = new SolidField(false, GC.getSolidFieldValue());
		composer.add(mainField);
		
		/*if(Settings.getInstance().isResistanceEnabled())
			initRandomAreaFields();
				
		if(Settings.getInstance().isObstacleEnabled())
			initStaticPucks();
*/
		Puck.Builder b = new Puck.Builder();

		b.setPosition(WORLD_WIDTH/2 + generateStartIndent(), WORLD_HEIGHT/2)
		 .setSpeed(0, 0)
		 .setRadius(GC.getGamePuckRadius())
		 .setPuckType(PuckType.GAME_PUCK)
		 .setComposer(composer);

		gamePuck = b.create();

		b.setPosition(WORLD_WIDTH/2, GC.getRectGameField().top/*0*/)
		 .setSpeed(0, 0)
		 .setRadius(GC.getFirePuckRadius())
		 .setPuckType(PuckType.FIRE_PUCK)
		 .setComposer(null);
		
		puck1 = b.create();
		
		b.setPosition(WORLD_WIDTH/2, GC.getRectGameField().bottom/*WORLD_HEIGHT - 1*/)
		 .setSpeed(0, 0)
		 .setRadius(GC.getFirePuckRadius())
		 .setPuckType(PuckType.FIRE_PUCK)
		 .setComposer(null);
	
		puck2 = b.create();
		
		settings = Settings.getInstance();
	}
	
	private int generateStartIndent()
	{
		boolean direction = random.nextBoolean();
		int value = random.nextInt(GC.getRectGameField().width()/2 - GC.getGamePuckRadius() - 1);
		
		return direction ? value : -value; 		
	}

	private void initRandomAreaFields()
	{
		randomAreaFieldCount = 2 + random.nextInt(4);
		randomFields = new RandomAreaField[randomAreaFieldCount];
		float solidValue;
		boolean signFlag;
		int minRadius, maxRadius;
		
		for(int i = 0; i < randomAreaFieldCount; i++)
		{
			// generate solid value for random fields as random value in range [0;1) with sign 

			signFlag = random.nextBoolean();
			solidValue = signFlag ? random.nextFloat() : -random.nextFloat();
			
			minRadius = 0;
			maxRadius = 0;
			
			randomFields[i] = new RandomAreaField(0, 0, false, solidValue, solidValue, minRadius, maxRadius, 2f, 1);
		}
	}
	
	private void initStaticPucks()
	{
		
	}
	
	public void update(float deltaTime)
	{
		Puck firePuck = null;
		
		if(gameOver)
			return;
		
		tickTime += deltaTime;
		
		while(tickTime > tick)
		{
			count++;
			tickTime -= tick;
			
			if(gamerType == GamerType.GAMER_1)
				firePuck = puck1;
			else
				firePuck = puck2;

			if(mode == GameMode.MODE_ANIMATION)
			{
				firePuck.update();
				
				if((firePuck.getRect().top > GC.getRectGameField().top && firePuck == puck1) ||
				   (firePuck.getRect().bottom < GC.getRectGameField().bottom && firePuck == puck2))   // условие проверяет вышел ли ударный шар со стартовой позиции. Разрешаем проверку на столкновения с границами только, если шаг полностью находится в игровом поле.
				{
					fixBorderCollision = true;
				}

				if(fixBorderCollision){
					fixCollisionWithBorders(firePuck);
				}
			
				if(borderCollisionCount == GC.getBorderCollisionMaxCount())
				{
					mode = GameMode.MODE_FINISHING;
					firePuck.getSpeed().x = 0;
					Log.d("GOAL_C", "in" + String.valueOf(borderCollisionCount));
				}
				
				if(isPuckInGateway(firePuck))
				{
					mode = GameMode.MODE_FIREPUCK_IN_GATEWAY;
					firePuck.getSpeed().x = 0;
					/*if(settings.isSoundEnabled())
						Assets.getNextStepSound().play(GC.getVolume());*/
				}
				
				if(fixCollisionWithGamePuck(firePuck))
				{
					mode = GameMode.MODE_FINISHING;
					firePuckVisibility = false;
					
					gamePuck.getSpeed().x = GC.getFireGamePuckRatio() * firePuck.getSpeed().x;
					gamePuck.getSpeed().y = GC.getFireGamePuckRatio() * firePuck.getSpeed().y;
					Log.d("GOAL_DEBUG", "Init speed: x = " + firePuck.getSpeed().x + " : y = " + firePuck.getSpeed().y);
					Log.d("GOAL_DEBUG", "Init speed on GP: x = " + gamePuck.getSpeed().x + " : y = " + gamePuck.getSpeed().y);
					Log.d("GOAL_DEBUG", "Init: x = " + gamePuck.getPosition().x + " : y = " + gamePuck.getPosition().y);
					
					if(settings.isSoundEnabled())
						Assets.getPucksCollisionSound().play(GC.getVolume());
				}
			}
			else if(mode == GameMode.MODE_FINISHING)
			{
				if(wasCollisionWithGamePuck)
				{
					gamePuck.update();
					Log.d("GOAL_P", "Gateway");	

					Log.d("GOAL_P", gamePuck.getPosition().toString());	
					Log.d("GOAL_P", String.valueOf(gamePuck.getPosition().x-GC.getGamePuckRadius()));	
					Log.d("GOAL_P", String.valueOf(GC.getRectGateway1().left));	
					Log.d("GOAL_P", String.valueOf(gamePuck.getPosition().x+GC.getGamePuckRadius()));	
					Log.d("GOAL_P", String.valueOf(GC.getRectGateway1().right));	
					Log.d("GOAL_P", String.valueOf(gamePuck.getPosition().y));	
					Log.d("GOAL_P", String.valueOf(GC.getRectGateway1().bottom));	
					Log.d("GOAL_P", String.valueOf(GC.getRectGateway2().top));	

					if(isPuckInGateway(gamePuck))
					{	
						gamePuck.getSpeed().x = 0;
						mode = GameMode.MODE_GAMEPUCK_IN_GATEWAY;
						if(settings.isSoundEnabled())
							Assets.getWinSound().play(GC.getVolume());
						Log.d("GOAL_P", "GAME OVER");
					}

					fixCollisionWithBorders(gamePuck);
				}
				
				Log.d("GOAL_DEBUG", "Finishing: x = " + gamePuck.getPosition().x + " : y = " + gamePuck.getPosition().y);
				puckFinishingTime--;

				if(gamePuck.getSpeedValue() == 0f && puckFinishingTime <= 0)
				{
					/*if(settings.isSoundEnabled())
						Assets.getNextStepSound().play(GC.getVolume());*/
					mode = GameMode.MODE_SET;
					reset();
				}
			}
			else if(mode == GameMode.MODE_GAMEPUCK_IN_GATEWAY)
			{
				gamePuck.update();
				
				puckFinishingTimeInGateway--;
				if(puckFinishingTimeInGateway == 0)
				{
					mode = GameMode.MODE_SET;
					reset();
					gameOver = true;
				}
			}
			else if(mode == GameMode.MODE_FIREPUCK_IN_GATEWAY)
			{
				firePuck.update();
				
				puckFinishingTimeInGateway--;
				if(puckFinishingTimeInGateway == 0)
				{
					/*if(settings.isSoundEnabled())
						Assets.getNextStepSound().play(GC.getVolume());*/
					mode = GameMode.MODE_SET;
					reset();
				}
					
				
			}
		}
	}
	
	public void setParameters(float speedX, float speedY)
	{
		Puck firePuck = null;
		
		if(gamerType == GamerType.GAMER_1)
			firePuck = puck1;
		else
			firePuck = puck2;
		
		Log.d("GOAL_DEBUG", "SPEED:");
		Log.d("GOAL_DEBUG", String.valueOf((int)speedX));
		Log.d("GOAL_DEBUG", String.valueOf((int)speedY));
		firePuck.getSpeed().x = /*(int)*/speedX;  
		firePuck.getSpeed().y = /*(int)*/speedY;		
	}

	private boolean fixCollisionWithGamePuck(Puck firePuck)
	{
		float diffX = Math.abs(gamePuck.getPosition().x - firePuck.getPosition().x);
		float diffY = Math.abs(gamePuck.getPosition().y - firePuck.getPosition().y);
		float distance = (float) Math.sqrt(diffX*diffX + diffY*diffY);
		
		wasCollisionWithGamePuck = false;

		if(distance <= gamePuck.getRadius() + firePuck.getRadius())
			wasCollisionWithGamePuck = true;
		
		return wasCollisionWithGamePuck;
	}
	
	private void fixCollisionWithBorders(Puck puck)
	{
		Direction direction = null;
		
		if(puck.getRect().left <= GC.getRectGameField().left){
			direction = Direction.LEFT;
			puck.getPosition().x = GC.getRectGameField().left + puck.getRadius() + 1;
		}
		else if(puck.getRect().right >= GC.getRectGameField().right){
			direction = Direction.RIGHT;
			puck.getPosition().x = GC.getRectGameField().right - puck.getRadius() - 1;
		}
		
		if(puck.getRect().top <= GC.getRectGameField().top)
		{
			if( gamerType == GamerType.GAMER_1 || 
				(gamerType == GamerType.GAMER_2 && 
				(puck.getRect().left < GC.getRectGateway1().left ||
				 puck.getRect().right > GC.getRectGateway1().right)))
			{
				direction = Direction.TOP;
				puck.getPosition().y = GC.getRectGameField().top + puck.getRadius() + 1;
			}
		}
		else if(puck.getRect().bottom >= GC.getRectGameField().bottom)
		{	
			if( gamerType == GamerType.GAMER_2 || 
				(gamerType == GamerType.GAMER_1 &&
			    (puck.getRect().left < GC.getRectGateway2().left ||
				 puck.getRect().right > GC.getRectGateway2().right)))
			{	
				direction = Direction.BOTTOM;
				puck.getPosition().y = GC.getRectGameField().bottom - puck.getRadius() - 1;
			}
		}
		
		if(direction != null)
		{
			puck.onBorderCollision(direction);
			borderCollisionCount++;

			if(settings.isSoundEnabled())
			{
				if(puck == gamePuck)
					Assets.getGamePuckBorderCollisionSound().play(GC.getVolume());
				else
					Assets.getFirePuckBorderCollisionSound().play(GC.getVolume());
			}	
			//Log.d("GOAL_C", "c1");
			
		}
	}
	
	public boolean isPuckInGateway(Puck puck)
	{
		if(gamerType == GamerType.GAMER_1)
		{
			if( puck.getRect().left >= GC.getRectGateway2().left &&
				puck.getRect().right <= GC.getRectGateway2().right &&
				puck.getRect().bottom > GC.getRectGameField().bottom)
			{
				return true;
			}
		}
		else if(gamerType == GamerType.GAMER_2)
		{
			if( puck.getRect().left >= GC.getRectGateway1().left &&
				puck.getRect().right <= GC.getRectGateway1().right &&
				puck.getRect().top < GC.getRectGameField().top)
			{
				return true;
			}
		}
		
		return false;
	}
	
	private void reset()
	{
		Log.d("GOAL_DEBUG", "Last: x = " + gamePuck.getPosition().x + " : y = " + gamePuck.getPosition().y);

		borderCollisionCount = 0;
		fixBorderCollision = false;
		puckFinishingTime = GC.getPuckFinishingTime();
		puckFinishingTimeInGateway = GC.getPuckFinishingTimeInGateway();
		gamerType = gamerType == GamerType.GAMER_1 ? GamerType.GAMER_2 : GamerType.GAMER_1;
		firePuckVisibility = true;
		
		puck1.getPosition().x = WORLD_WIDTH/2;
		puck1.getPosition().y = GC.getRectGameField().top/*0*/;
		puck1.stop();
		
		puck2.getPosition().x = WORLD_WIDTH/2;
		puck2.getPosition().y = GC.getRectGameField().bottom/*WORLD_HEIGHT - 1*/;
		puck2.stop();
		
		Log.d("GOAL_C", "finish" + String.valueOf(borderCollisionCount));
		
	}
	
	public void startAnimation() 
	{
		/*if(settings.isSoundEnabled())
			Assets.getFireSound().play(GC.getVolume());*/
		this.mode = GameMode.MODE_ANIMATION;
	}
	
	public GamerType getGamerType() {
		return gamerType;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public GameMode getMode() {
		return mode;
	}

	public Puck getGamePuck() {
		return gamePuck;
	}

	public Puck getPuck1() {
		return puck1;
	}

	public Puck getPuck2() {
		return puck2;
	}

	public boolean isFirePuckVisibility() {
		return firePuckVisibility;
	}
	
	
	
}
