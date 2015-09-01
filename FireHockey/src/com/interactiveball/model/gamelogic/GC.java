package com.interactiveball.model.gamelogic;

import android.graphics.Point;
import android.graphics.Rect;

public class GC 
{
	private static final String settingsFileName = "golgame";
	private static final int puckFinishingTime = 10;
	private static final int puckFinishingTimeInGateway = 50;
	private static final int borderCollisionMaxCount = 5;
	private static final int gamePuckMass = 10;
	private static final int firePuckMass = 10;
	private static final int gamePuckRadius = 30;
	private static final int firePuckRadius = 11;
	private static final float solidFieldValue = -0.1f;
	private static final int speedMaxLevel = 30;
	private static final int speedStartLevel = 10;
	private static final int angleMinLevel = 3;
	private static final int angleMaxLevel = 173;
	private static final int angleStartLevel = 90;
	private static final float volume = 0.5f;
	private static final float tick = 0.05f;

	private static final int frameBufferWidth = 480;
	private static final int frameBufferHeight = 800;
	private static final int firePuckRectPadding = 20;
	private static final int rectTouchFirePuckPadding = 2*firePuckRadius + 30;
	private static final Point pointAngleCtrl1 = new Point(5, 11);
	private static final Point pointSpeedCtrl1 = new Point(5, 11); //new Point(446, 11);
	private static final Point pointAngleCtrl2 = new Point(446, 400);
	private static final Point pointSpeedCtrl2 = new Point(446, 400); //new Point(5, 400);
	private static final Point pointGateway1 = new Point(39, 11);
	private static final Point pointGateway2 = new Point(39, 755);
	private static final Rect rectGameField = new Rect(39, 44, 442, 756);
	private static final Rect rectFirePuck1 = new Rect(240-firePuckRadius, 44-firePuckRadius, 240+firePuckRadius, 44+firePuckRadius);
	private static final Rect rectFirePuck2 = new Rect(240-firePuckRadius, 756-firePuckRadius, 240+firePuckRadius, 756+firePuckRadius);
	private static final Rect rectTouchFirePuck1 = new Rect(rectFirePuck1.left-firePuckRectPadding, rectFirePuck1.top-firePuckRectPadding, rectFirePuck1.right+firePuckRectPadding, rectFirePuck1.bottom+firePuckRectPadding);
	private static final Rect rectTouchFirePuck2 = new Rect(rectFirePuck2.left-firePuckRectPadding, rectFirePuck2.top-firePuckRectPadding, rectFirePuck2.right+firePuckRectPadding, rectFirePuck2.bottom+firePuckRectPadding);
	private static final Rect rectTouchFirePuck = new Rect(rectGameField.left+rectTouchFirePuckPadding, rectGameField.top+rectTouchFirePuckPadding, rectGameField.right-rectTouchFirePuckPadding, rectGameField.bottom-rectTouchFirePuckPadding);
	private static final Rect rectGamePuck = new Rect(240-gamePuckRadius, 400-gamePuckRadius, 240+gamePuckRadius, 400+gamePuckRadius);
	private static final Rect rectGateway1 = new Rect(180, 44, 300, 44);
	private static final Rect rectGateway2 = new Rect(180, 756, 300, 756);
	private static final int ctrlWidth = 30;
	private static final int ctrlHeight = 390;
	
	private static final Rect loadingProgressBarRect = new Rect(40, 365, 440, 435);
	private static final Point loadingTextPoint = new Point(130, 335);
	private static final Rect mmsStartGameBtnRect = new Rect(50, 325, 427, 431);
	private static final Rect mmsHelpBtnRect = new Rect(50, 464, 427, 570);
	private static final Rect mmsSoundBtnRect = new Rect(205, 640, 277, 711);
	private static final Rect helpBackBtnRect = new Rect(0, 0, 0, 0);
	private static final Rect helpNextBtnRect = new Rect(0, 0, 0, 0);
	private static final Rect helpMainMenuBtnRect = new Rect(146, 726, 331, 779);
	
	public static String getSettingsFileName() {
		return settingsFileName;
	}

	public static int getPuckFinishingTime() {
		return puckFinishingTime;
	}

	public static int getBorderCollisionMaxCount() {
		return borderCollisionMaxCount;
	}

	public static int getGamePuckMass() {
		return gamePuckMass;
	}

	public static int getFirePuckMass() {
		return firePuckMass;
	}
	
	public static float getFireGamePuckRatio()
	{
		return firePuckMass/gamePuckMass;
	}

	public static int getGamePuckRadius() {
		return gamePuckRadius;
	}

	public static int getFirePuckRadius() {
		return firePuckRadius;
	}

	public static float getSolidFieldValue() {
		return solidFieldValue;
	}

	public static int getFrameBufferWidth() {
		return frameBufferWidth;
	}

	public static int getFrameBufferHeight() {
		return frameBufferHeight;
	}

	public static Point getPointAngleCtrl1() {
		return pointAngleCtrl1;
	}

	public static Point getPointSpeedCtrl1() {
		return pointSpeedCtrl1;
	}

	public static Point getPointAngleCtrl2() {
		return pointAngleCtrl2;
	}

	public static Point getPointSpeedCtrl2() {
		return pointSpeedCtrl2;
	}

	public static Point getPointGateway1() {
		return pointGateway1;
	}

	public static Point getPointGateway2() {
		return pointGateway2;
	}

	public static Rect getRectFirePuck1() {
		return rectFirePuck1;
	}

	public static Rect getRectFirePuck2() {
		return rectFirePuck2;
	}

	public static Rect getRectGamePuck() {
		return rectGamePuck;
	}

	public static Rect getRectGateway1() {
		return rectGateway1;
	}

	public static Rect getRectGateway2() {
		return rectGateway2;
	}

	public static int getCtrlWidth() {
		return ctrlWidth;
	}

	public static int getCtrlHeight() {
		return ctrlHeight;
	}

	public static int getSpeedMaxLevel() {
		return speedMaxLevel;
	}

	public static int getSpeedStartLevel() {
		return speedStartLevel;
	}

	public static int getAngleStartLevel() {
		return angleStartLevel;
	}

	public static int getAngleMaxLevel() {
		return angleMaxLevel;
	}

	public static Rect getRectGameField() {
		return rectGameField;
	}

	public static int getAngleMinLevel() {
		return angleMinLevel;
	}

	public static float getTick() {
		return tick;
	}

	public static Rect getRectTouchFirePuck1() {
		return rectTouchFirePuck1;
	}

	public static Rect getRectTouchFirePuck2() {
		return rectTouchFirePuck2;
	}

	public static int getPuckFinishingTimeInGateway() {
		return puckFinishingTimeInGateway;
	}

	public static Rect getLoadingProgressBarRect() {
		return loadingProgressBarRect;
	}

	public static float getVolume() {
		return volume;
	}
	
	public static Rect getMmsStartGameBtnRect() {
		return mmsStartGameBtnRect;
	}

	public static Rect getMmsHelpBtnRect() {
		return mmsHelpBtnRect;
	}

	public static Rect getMmsSoundBtnRect() {
		return mmsSoundBtnRect;
	}

	public static Rect getHelpBackBtnRect() {
		return helpBackBtnRect;
	}

	public static Rect getHelpNextBtnRect() {
		return helpNextBtnRect;
	}

	public static Rect getHelpMainMenuBtnRect() {
		return helpMainMenuBtnRect;
	}

	public static Point getLoadingTextPoint() {
		return loadingTextPoint;
	}

	public static Rect getRectTouchFirePuck() {
		return rectTouchFirePuck;
	}
	
	
	
	
	
	
	
}
