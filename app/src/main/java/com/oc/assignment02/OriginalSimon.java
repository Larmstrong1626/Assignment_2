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


    private Button tl_btn;
    private Button tr_btn;
    private Button bl_btn;
    private Button br_btn;
    private Button ng_btn;


    private final int TL_BUTTON = 0;
    private final int TR_BUTTON = 1;
    private final int BL_BUTTON = 2;
    private final int BR_BUTTON = 3;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_original_simon);

        tl_sound = sp.load(this, R.raw.one, 1);
        tr_sound = sp.load(this, R.raw.two, 1);
        bl_sound = sp.load(this, R.raw.three, 1);
        br_sound = sp.load(this, R.raw.four, 1);

        tl_btn = (Button) findViewById(R.id.button_tl);
        tr_btn = (Button) findViewById(R.id.button_tr);
        bl_btn = (Button) findViewById(R.id.button_bl);
        br_btn = (Button) findViewById(R.id.button_br);
        ng_btn=(Button) findViewById(R.id.new_game);


        soundsLoaded = new HashSet<Integer>();

        ng_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
                ng_btn.setEnabled(false);
            }
        });

        tl_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.play(tl_sound, 1, 1, 1, 0, 1f);
                ButtonOpacity newopacity = new ButtonOpacity(handler, v);
                newopacity.makeOpaque(handler, v);
            }
        });

        tr_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.play(tr_sound, 1, 1, 1, 0, 1f);
                ButtonOpacity newopacity = new ButtonOpacity(handler, v);
                newopacity.makeOpaque(handler, v);
            }
        });

        bl_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //playSound(threeId);
                sp.play(bl_sound, 1, 1, 1, 0, 1f);
                ButtonOpacity newopacity = new ButtonOpacity(handler, v);
                newopacity.makeOpaque(handler, v);
            }
        });

        br_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //playSound(fourId);
                sp.play(br_sound, 1, 1, 1, 0, 1f);
                ButtonOpacity newopacity = new ButtonOpacity(handler, v);
                newopacity.makeOpaque(handler, v);
            }
        });

    }




    public void startGame(){


    }






    @Override
    protected void onResume() {
        super.onResume();

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