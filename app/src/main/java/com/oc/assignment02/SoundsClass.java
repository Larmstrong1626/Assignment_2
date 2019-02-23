package com.oc.assignment02;

import android.media.SoundPool;

public class SoundsClass{
    int soundId;
    SoundPool soundPool;

    public SoundsClass(SoundPool soundPool, int soundId) {
        this.soundId = soundId;
        this.soundPool = soundPool;
    }


    public void play() {
        //soundPool.play(soundId, volume, volume, 0, 0, 1);
        soundPool.play(soundId, 1.0f, 1.0f, 0, 0, 1.0f);

    }


    public void dispose() {
        soundPool.unload(soundId);
    }
}
