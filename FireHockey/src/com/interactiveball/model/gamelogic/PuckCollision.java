package com.interactiveball.model.gamelogic;

import com.interactiveball.api.common.Direction;

public interface PuckCollision 
{
	void onBorderCollision(Direction borderSide);
}
