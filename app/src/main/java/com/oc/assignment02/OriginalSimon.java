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
import android.media.AudioManager;
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
import java.util.Random;
import java.util.Set;

public class OriginalSimon extends Activity {

    public static final int S1 = R.raw.one;
    public static final int S2 = R.raw.two;
    public static final int S3 = R.raw.three;
    public static final int S4 = R.raw.four;
    final Handler handler = new Handler();
    private SoundPool soundPool;
    private Set<Integer> soundsLoaded;
    final int MAX_LENGTH = 1000;
    int AI_moves[] = new int[MAX_LENGTH];
    int human_moves[] = new int[MAX_LENGTH];
    Random r = new Random();
    int moves = 0;
    public SoundPool sp = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
    public int tl_sound, tr_sound, bl_sound, br_sound;
    boolean AI_Turn = true;
    //final int fourId = soundPool.load(this, R.raw.four, 1);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tl_sound = sp.load(this, R.raw.one, 1);
        tr_sound = sp.load(this, R.raw.two, 1);
        bl_sound = sp.load(this, R.raw.three, 1);
        br_sound = sp.load(this, R.raw.four, 1);

        soundsLoaded = new HashSet<Integer>();

        setContentView(R.layout.activity_original_simon);

    }


    private void playSound(int id) {
        int my_id = id;
        sp.play(my_id, 1, 1, 1, 0, 1f);


    }


    @Override
    protected void onResume() {
        super.onResume();

        findViewById(R.id.button_tl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.play(tl_sound, 1, 1, 1, 0, 1f);
                ButtonOpacity newopacity = new ButtonOpacity(handler, v);
                newopacity.makeOpaque(handler, v);
            }
        });


//        final int twoId = soundPool.load(this, R.raw.two, 1);
        findViewById(R.id.button_tr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.play(tr_sound, 1, 1, 1, 0, 1f);
                ButtonOpacity newopacity = new ButtonOpacity(handler, v);
                newopacity.makeOpaque(handler, v);
            }
        });
        //  final int threeId = soundPool.load(this, R.raw.three, 1);
        findViewById(R.id.button_bl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //playSound(threeId);
                sp.play(bl_sound, 1, 1, 1, 0, 1f);
                ButtonOpacity newopacity = new ButtonOpacity(handler, v);
                newopacity.makeOpaque(handler, v);
            }
        });
        //final int fourId = soundPool.load(this, R.raw.four, 1);
        findViewById(R.id.button_br).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //playSound(fourId);
                sp.play(br_sound, 1, 1, 1, 0, 1f);
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