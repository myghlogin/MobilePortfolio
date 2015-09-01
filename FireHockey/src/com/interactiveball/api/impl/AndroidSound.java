package com.interactiveball.api.impl;

import com.interactiveball.api.interfaces.Sound;

import android.content.res.AssetManager;
import android.media.SoundPool;

public class AndroidSound implements Sound 
{
	private SoundPool pool;
	private int soundId;

	public AndroidSound(SoundPool pool, int soundId)
	{
		this.pool = pool;
		this.soundId = soundId;		
	}
	
	@Override
	public void play(float volume) 
	{
		pool.play(soundId, volume, volume, 0, 0, 1);
	}

	@Override
	public void dispose() 
	{
		pool.unload(soundId);
	}
	
}