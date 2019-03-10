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

import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
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

    private TextView turn;
    private TextView current_score;
    private TextView high_score;

    int highscore=0;

    private final int TL_BUTTON = 0;
    private final int TR_BUTTON = 1;
    private final int BL_BUTTON = 2;
    private final int BR_BUTTON = 3;

    //enum Player_Choice {TL,TR,BL,BR};

    final Handler handler = new Handler();
    private SoundPool soundPool;
    private Set<Integer> soundsLoaded;
    final int MAX_LENGTH = 31;

    String filename = "high_score";
    int human_move;
    Random r = new Random();
    int score = 0;
    int moves = 1;
    int roundNumber = 1;
    public SoundPool sp = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
    public int tl_sound, tr_sound, bl_sound, br_sound, new_sound, game_over,high_sound;
    boolean AI_Turn = true;

    private List<Integer> AI_Choices;
    private List<Integer> Human_Choices;

    private Computer_player pc;
    FileOperations my_file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_original_simon);
        my_file=new FileOperations(this,filename);
        my_file.setFilename(filename);
        highscore=my_file.getHighscore();
       // highscore=0;

        AI_Choices = new ArrayList<Integer>();
        Human_Choices = new ArrayList<Integer>();
        turn = (TextView) findViewById(R.id.turn);
        current_score = (TextView) findViewById(R.id.current_score);
        high_score = (TextView) findViewById(R.id.high_score);
        high_score.setText(Integer.toString(highscore));

        tl_sound = sp.load(this, R.raw.blue, 1);
        tr_sound = sp.load(this, R.raw.green, 1);
        bl_sound = sp.load(this, R.raw.red, 1);
        br_sound = sp.load(this, R.raw.yellow, 1);
        new_sound = sp.load(this, R.raw.new_game2, 1);
        high_sound=sp.load(this, R.raw.high_score1, 1);
        game_over = sp.load(this, R.raw.game_over, 1);

        tl_btn = (Button) findViewById(R.id.button_tl);
        tr_btn = (Button) findViewById(R.id.button_tr);
        bl_btn = (Button) findViewById(R.id.button_bl);
        br_btn = (Button) findViewById(R.id.button_br);
        tl_btn.setClickable(false);
        tr_btn.setClickable(false);
        bl_btn.setClickable(false);
        br_btn.setClickable(false);
        tl_btn.setEnabled(false);
        tr_btn.setEnabled(false);
        bl_btn.setEnabled(false);
        br_btn.setEnabled(false);
        ng_btn = (Button) findViewById(R.id.new_game);


        soundsLoaded = new HashSet<Integer>();

        ng_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    sp.play(new_sound, 1.0f, 1.0f, 0, 0, 1.0f);

                startGame();

            }
        });

        tl_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.play(tl_sound, 1, 1, 1, 0, 2f);
                ButtonOpacity newopacity = new ButtonOpacity(handler, v);
                newopacity.makeOpaque(handler, v);
                human_move = 1;
                moves++;
                handler.removeCallbacks(end_game);
                checkChoice();

            }
        });

        tr_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.play(tr_sound, 1, 1, 1, 0, 2f);
                ButtonOpacity newopacity = new ButtonOpacity(handler, v);
                newopacity.makeOpaque(handler, v);
                human_move = 2;
                moves++;
                handler.removeCallbacks(end_game);
                checkChoice();
                //pc = new Computer_player();
                //pc.execute();
                //handler.removeCallbacks(end_game);
            }
        });

        bl_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //playSound(threeId);
                sp.play(bl_sound, 1, 1, 1, 0, 2f);
                ButtonOpacity newopacity = new ButtonOpacity(handler, v);
                newopacity.makeOpaque(handler, v);
                human_move = 3;
                moves++;
                handler.removeCallbacks(end_game);
                checkChoice();

                //pc = new Computer_player();
                //pc.execute();
                //handler.removeCallbacks(end_game);
            }
        });

        br_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //playSound(fourId);
                sp.play(br_sound, 1, 1, 1, 0, 2f);
                ButtonOpacity newopacity = new ButtonOpacity(handler, v);
                newopacity.makeOpaque(handler, v);
                human_move = 4;
                moves++;
                checkChoice();
                // pc = new Computer_player();
                //pc.execute();
                handler.removeCallbacks(end_game);
            }
        });

    }


    public void startGame() {


        if (pc == null) {
            pc = new Computer_player();
            pc.execute();
        }
        tl_btn.setEnabled(true);
        tr_btn.setEnabled(true);
        bl_btn.setEnabled(true);
        br_btn.setEnabled(true);
        ng_btn.setEnabled(false);
        roundNumber = 1;
        score=0;
        current_score.setText(Integer.toString(score));
        moves = 1;



    }

    public void checkChoice() {
        // moves++;

        Log.i("-------", "----- Moves -----" + moves + "");
        Log.i("-------", "----- Round -----" + roundNumber + "");
        Log.i("-------", "----- AI_choice -----" + AI_Choices.get(moves - 1) + "");
        Log.i("-------", "----- Human_choice -----" + human_move + "");
        Log.i("-------", "----- #of choices -----" + AI_Choices.size() + "");
        final Runnable r = new Runnable() {
            public void run() {
                if (human_move == AI_Choices.get(moves - 1)) {
                    // moves++;
                    //pc.execute();

                    if (moves == AI_Choices.size()) {
                        //AI_Turn=true;
                        //moves++;

                        roundNumber++;
                        score++;
                        if(score>highscore){
                            highscore=score;
                            //writeHighScore();
                           // newhighscore=true;

                                    sp.play(high_sound, 1, 1, 1, 0, 3.0f);


                            my_file.setHighscore(score);
                            my_file.writeHighScore(score,OriginalSimon.this);
                            high_score.setText(Integer.toString(highscore));
                        }
                        pc = new Computer_player();
                        pc.execute();

                    }
                } else {
                    AI_Turn = false;
                    AI_Choices.clear();
                    Human_Choices.clear();
                    tl_btn.setEnabled(false);
                    tr_btn.setEnabled(false);
                    bl_btn.setEnabled(false);
                    br_btn.setEnabled(false);
                    tl_btn.setClickable(false);
                    tr_btn.setClickable(false);
                    bl_btn.setClickable(false);
                    br_btn.setClickable(false);
                    turn.setText("You lose");
                    sp.play(game_over, 1, 1, 1, 0, 1f);
                    ng_btn.setEnabled(true);
                }

            }};
        handler.postDelayed(r, 300);
        handler.postDelayed(end_game, 5000);
    }


    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    protected void onPause() {
        super.onPause();
        if (pc != null) {
            pc.cancel(true);
            pc = null;
        }
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;

            soundsLoaded.clear();
        }


    }

    public class Computer_player extends AsyncTask<Void, Integer, Void> {



        @Override
        protected void onPreExecute() {

            turn.setText("Simon is up");
            handler.removeCallbacks(end_game);
            try {
                Thread.sleep(2000);
                //turn.setText("Simon is up");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tl_btn.setClickable(false);
            tr_btn.setClickable(false);
            bl_btn.setClickable(false);
            br_btn.setClickable(false);

            AI_Turn = true;
            current_score.setText(Integer.toString(score));


        }

        @Override
        protected Void doInBackground(Void... params) {
           int delay=1500;
           if(roundNumber<=4){
               delay=1500;
           }else if(roundNumber>4 && roundNumber<9){
               delay=750;
            }else if(roundNumber>9){
               delay=300;
           }
            try {
                AI_Turn = true;
                int Max = 4;
                int Min = 1;
                int ai_choice = r.nextInt(Max - Min + 1) + Min;
                AI_Choices.add(ai_choice);
                Log.i("Button", " = " + ai_choice);
                for (int i = 0; i < AI_Choices.size(); i++) {
                    Thread.sleep(delay);
                    publishProgress(AI_Choices.get(i));
                }
            } catch (InterruptedException e) {
                Log.i("-------", "----- INTERRUPTED -----");
            }
            pc = null;
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.i("-------", "inside onProgress Update");


            play_sound(values[0]);


        }

        @Override
        protected void onPostExecute(Void result) {

             turn.setText("Your turn");

            tl_btn.setClickable(true);
            tr_btn.setClickable(true);
            bl_btn.setClickable(true);
            br_btn.setClickable(true);
            tl_btn.setEnabled(true);
            tr_btn.setEnabled(true);
            bl_btn.setEnabled(true);
            br_btn.setEnabled(true);
            AI_Turn = false;
            moves=0;
            handler.postDelayed(end_game, 5000);

        }


    }

    public void play_sound(int soundId) {
        if (soundId == 1) {
            soundId = tl_sound;
            sp.play(soundId, 1, 1, 1, 0, 2f);
            tl_btn.getBackground().setAlpha(51);
            final Runnable r = new Runnable() {
                public void run() {
                    tl_btn.getBackground().setAlpha(255);

                }
            };
            handler.postDelayed(r, 100);


        }
        if (soundId == 2) {
            soundId = tr_sound;
            sp.play(soundId, 1, 1, 1, 0, 2f);
            tr_btn.getBackground().setAlpha(51);
            final Runnable r = new Runnable() {
                public void run() {
                    tr_btn.getBackground().setAlpha(255);

                }
            };
            handler.postDelayed(r, 100);


        }
        if (soundId == 3) {
            soundId = bl_sound;
            sp.play(soundId, 1, 1, 1, 0, 2f);
            bl_btn.getBackground().setAlpha(51);
            final Runnable r = new Runnable() {
                public void run() {
                    bl_btn.getBackground().setAlpha(255);

                }
            };
            handler.postDelayed(r, 100);

        }
        if (soundId == 4) {
            soundId = br_sound;
            sp.play(soundId, 1, 1, 1, 0, 2f);

            br_btn.getBackground().setAlpha(51);
            final Runnable r = new Runnable() {
                public void run() {
                    br_btn.getBackground().setAlpha(255);

                }
            };
            handler.postDelayed(r, 100);

        }

    }

            final Runnable end_game = new Runnable() {
            public void run() {AI_Turn = false;
                AI_Choices.clear();
                Human_Choices.clear();
                tl_btn.setEnabled(false);
                tr_btn.setEnabled(false);
                bl_btn.setEnabled(false);
                br_btn.setEnabled(false);
                tl_btn.setClickable(false);
                tr_btn.setClickable(false);
                bl_btn.setClickable(false);
                br_btn.setClickable(false);
                turn.setText("Time Expired");
                ng_btn.setEnabled(true);


            }
        };

}