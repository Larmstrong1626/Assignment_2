/*************
 Developer: Robert Armstrong/Justin Duff
 App: Assignment_2
 OriginalSimon.java
 Tested on Samsung Galaxy 5
 5.1"x2.85"
 Marshmallow 6.0.1
 API 23

 **************/

package com.oc.assignment02;

import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

public class OriginalSimon extends Activity {

    public static final int S1 = R.raw.one;
    public static final int S2 = R.raw.two;
    public static final int S3 = R.raw.three;
    public static final int S4 = R.raw.four;
    final Handler handler = new Handler();
    private SoundPool soundPool;
    private Set<Integer> soundsLoaded;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        soundsLoaded = new HashSet<Integer>();

        setContentView(R.layout.activity_original_simon);

    }


    private void playSound(int id) {
        int my_id = id;
        SoundsClass my_sound = new SoundsClass(soundPool, my_id);
        my_sound.play();


    }

    @Override
    protected void onResume() {
        super.onResume();

        AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
        attrBuilder.setUsage(AudioAttributes.USAGE_GAME);

        SoundPool.Builder spBuilder = new SoundPool.Builder();
        spBuilder.setAudioAttributes(attrBuilder.build());
        spBuilder.setMaxStreams(1);
        soundPool = spBuilder.build();

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                if (status == 0) { // success
                    soundsLoaded.add(sampleId);
                    Log.i("SOUND", "Sound loaded " + sampleId);

                } else {
                    Log.i("SOUND", "Error cannot load sound status = " + status);
                }
            }
        });
        final int oneId = soundPool.load(this, R.raw.one, 1);
        findViewById(R.id.button_tl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(oneId);
                ButtonOpacity newopacity = new ButtonOpacity(handler, v);
                newopacity.makeOpaque(handler, v);
            }
        });


        final int twoId = soundPool.load(this, R.raw.two, 1);
        findViewById(R.id.button_tr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(twoId);
                ButtonOpacity newopacity = new ButtonOpacity(handler, v);
                newopacity.makeOpaque(handler, v);
            }
        });
        final int threeId = soundPool.load(this, R.raw.three, 1);
        findViewById(R.id.button_bl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(threeId);
                ButtonOpacity newopacity = new ButtonOpacity(handler, v);
                newopacity.makeOpaque(handler, v);
            }
        });
        final int fourId = soundPool.load(this, R.raw.four, 1);
        findViewById(R.id.button_br).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(fourId);
                ButtonOpacity newopacity = new ButtonOpacity(handler, v);
                newopacity.makeOpaque(handler, v);
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;

            soundsLoaded.clear();
        }
    }


}