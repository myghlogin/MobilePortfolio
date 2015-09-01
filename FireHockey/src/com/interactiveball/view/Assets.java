package com.interactiveball.view;

import java.util.LinkedList;

import com.interactiveball.api.interfaces.Pixmap;
import com.interactiveball.api.interfaces.Sound;

public class Assets 
{
	private static Pixmap background;
	private static Pixmap gamePuck;
	private static Pixmap firePuck;
	private static Pixmap gateway;
	private static Pixmap fireGateway;
	private static Pixmap fireGatewayFilled;
	private static Pixmap gateway2;
	private static Pixmap fireGateway2;	
	private static Pixmap fireGatewayFilled2;
	private static Pixmap loadingProgressBarBackground;
	private static Pixmap loadingProgressBarForeground;
	private static Pixmap loadingText;
	private static Pixmap screenBackground;
	private static Pixmap logotype;
	private static Pixmap btnPlayUp;
	private static Pixmap btnPlayDown;
	private static Pixmap btnHelpUp;
	private static Pixmap btnHelpDown;
	private static Pixmap btnSoundOn;
	private static Pixmap btnSoundOff;
	private static Pixmap btnMenuUp;
	private static Pixmap btnMenuDown;
	private static Pixmap helpText;
	
	private static Sound firePuckBorderCollisionSound;  //+
	private static Sound gamePuckBorderCollisionSound;  //+
	private static Sound pucksCollisionSound;           //+ 
	//private static Sound firePuckInGatewaySound;        +
	//private static Sound nextStepSound;                 +
	//private static Sound fireSound;                     +
	private static Sound winSound;                      //+  
	private static Sound buttonPressSound;
	
	public static Pixmap getGamePuck() {
		return gamePuck;
	}

	public static void setGamePuck(Pixmap gamePuck) {
		Assets.gamePuck = gamePuck;
	}

	public static Pixmap getFirePuck() {
		return firePuck;
	}

	public static void setFirePuck(Pixmap firePuck) {
		Assets.firePuck = firePuck;
	}

	public static Pixmap getBackground() {
		return background;
	}

	public static void setBackground(Pixmap background) {
		Assets.background = background;
	}

	public static Pixmap getGateway() {
		return gateway;
	}

	public static void setGateway(Pixmap gateway) {
		Assets.gateway = gateway;
	}

	public static Pixmap getFireGateway() {
		return fireGateway;
	}

	public static void setFireGateway(Pixmap fireGateway) {
		Assets.fireGateway = fireGateway;
	}

	public static Pixmap getGateway2() {
		return gateway2;
	}

	public static void setGateway2(Pixmap gateway2) {
		Assets.gateway2 = gateway2;
	}

	public static Pixmap getFireGateway2() {
		return fireGateway2;
	}

	public static void setFireGateway2(Pixmap fireGateway2) {
		Assets.fireGateway2 = fireGateway2;
	}

	public static Pixmap getFireGatewayFilled() {
		return fireGatewayFilled;
	}

	public static void setFireGatewayFilled(Pixmap fireGatewayFilled) {
		Assets.fireGatewayFilled = fireGatewayFilled;
	}

	public static Pixmap getFireGatewayFilled2() {
		return fireGatewayFilled2;
	}

	public static void setFireGatewayFilled2(Pixmap fireGatewayFilled2) {
		Assets.fireGatewayFilled2 = fireGatewayFilled2;
	}

	public static Pixmap getLoadingProgressBarBackground() {
		return loadingProgressBarBackground;
	}

	public static void setLoadingProgressBarBackground(
			Pixmap loadingProgressBarBackground) {
		Assets.loadingProgressBarBackground = loadingProgressBarBackground;
	}

	public static Pixmap getLoadingProgressBarForeground() {
		return loadingProgressBarForeground;
	}

	public static void setLoadingProgressBarForeground(
			Pixmap loadingProgressBarForeground) {
		Assets.loadingProgressBarForeground = loadingProgressBarForeground;
	}

	public static Sound getFirePuckBorderCollisionSound() {
		return firePuckBorderCollisionSound;
	}

	public static void setFirePuckBorderCollisionSound(Sound firePuckBorderCollisionSound) {
		Assets.firePuckBorderCollisionSound = firePuckBorderCollisionSound;
	}

	public static Sound getGamePuckBorderCollisionSound() {
		return gamePuckBorderCollisionSound;
	}

	public static void setGamePuckBorderCollisionSound(Sound gamePuckBorderCollisionSound) {
		Assets.gamePuckBorderCollisionSound = gamePuckBorderCollisionSound;
	}

	public static Sound getPucksCollisionSound() {
		return pucksCollisionSound;
	}

	public static void setPucksCollisionSound(Sound pucksCollisionSound) {
		Assets.pucksCollisionSound = pucksCollisionSound;
	}

	/*public static Sound getNextStepSound() {
		return nextStepSound;
	}

	public static void setNextStepSound(Sound nextStepSound) {
		Assets.nextStepSound = nextStepSound;
	}*/

	/*public static Sound getFireSound() {
		return fireSound;
	}

	public static void setFireSound(Sound fireSound) {
		Assets.fireSound = fireSound;
	}*/

	public static Sound getWinSound() {
		return winSound;
	}

	public static void setWinSound(Sound winSound) {
		Assets.winSound = winSound;
	}

	public static Sound getButtonPressSound() {
		return buttonPressSound;
	}

	public static void setButtonPressSound(Sound buttonPressSound) {
		Assets.buttonPressSound = buttonPressSound;
	}

	/*public static Sound getFirePuckInGatewaySound() {
		return firePuckInGatewaySound;
	}

	public static void setFirePuckInGatewaySound(Sound firePuckInGatewaySound) {
		Assets.firePuckInGatewaySound = firePuckInGatewaySound;
	}*/

	public static Pixmap getHelpText() {
		return helpText;
	}

	public static void setHelpText(Pixmap helpText) {
		Assets.helpText = helpText;
	}

	public static Pixmap getScreenBackground() {
		return screenBackground;
	}

	public static void setScreenBackground(Pixmap screenBackground) {
		Assets.screenBackground = screenBackground;
	}

	public static Pixmap getLogotype() {
		return logotype;
	}

	public static void setLogotype(Pixmap logotype) {
		Assets.logotype = logotype;
	}

	public static Pixmap getBtnPlayUp() {
		return btnPlayUp;
	}

	public static void setBtnPlayUp(Pixmap btnPlayUp) {
		Assets.btnPlayUp = btnPlayUp;
	}

	public static Pixmap getBtnPlayDown() {
		return btnPlayDown;
	}

	public static void setBtnPlayDown(Pixmap btnPlayDown) {
		Assets.btnPlayDown = btnPlayDown;
	}

	public static Pixmap getBtnHelpUp() {
		return btnHelpUp;
	}

	public static void setBtnHelpUp(Pixmap btnHelpUp) {
		Assets.btnHelpUp = btnHelpUp;
	}

	public static Pixmap getBtnHelpDown() {
		return btnHelpDown;
	}

	public static void setBtnHelpDown(Pixmap btnHelpDown) {
		Assets.btnHelpDown = btnHelpDown;
	}

	public static Pixmap getBtnSoundOn() {
		return btnSoundOn;
	}

	public static void setBtnSoundOn(Pixmap btnSoundOn) {
		Assets.btnSoundOn = btnSoundOn;
	}

	public static Pixmap getBtnSoundOff() {
		return btnSoundOff;
	}

	public static void setBtnSoundOff(Pixmap btnSoundOff) {
		Assets.btnSoundOff = btnSoundOff;
	}

	public static Pixmap getBtnMenuUp() {
		return btnMenuUp;
	}

	public static void setBtnMenuUp(Pixmap btnMenuUp) {
		Assets.btnMenuUp = btnMenuUp;
	}

	public static Pixmap getBtnMenuDown() {
		return btnMenuDown;
	}

	public static void setBtnMenuDown(Pixmap btnMenuDown) {
		Assets.btnMenuDown = btnMenuDown;
	}

	public static Pixmap getLoadingText() {
		return loadingText;
	}

	public static void setLoadingText(Pixmap loadingText) {
		Assets.loadingText = loadingText;
	}
	
	
	
}
