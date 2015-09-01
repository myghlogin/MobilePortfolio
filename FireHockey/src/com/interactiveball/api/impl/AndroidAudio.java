package com.interactiveball.api.impl;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

import com.interactiveball.api.interfaces.Audio;
import com.interactiveball.api.interfaces.Sound;

public class AndroidAudio implements Audio 
{
	SoundPool pool;
	AssetManager assetManager;
	
	public AndroidAudio(Activity activity)
	{
		activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		pool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
		assetManager = activity.getAssets();
	}
	
	@Override
	public Sound newSound(String fileName) 
	{
		AssetFileDescriptor descriptor = null;
		int soundId = 0;
		
		try
		{
			descriptor = assetManager.openFd(fileName);
		}
		catch(IOException e)
		{
			throw new RuntimeException("Cannot load sound from file \'" + fileName + "\'");
		}
		
		soundId = pool.load(descriptor, 1);

		return new AndroidSound(pool, soundId);
	}
	
}
