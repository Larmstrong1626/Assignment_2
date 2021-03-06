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

import android.app.Activity;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
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
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class RewindSimonActivity extends Activity {


    //*****Buttons*****//
    private Button rewindTL_Btn;
    private Button rewindTR_Btn;
    private Button rewindBR_Btn;
    private Button rewindBL_Btn;
    private Button rewindNG_Btn;
    /*****TextViews*****/
    private TextView rewind_turn;
    private TextView rewind_current_score;
    private TextView rewind_highScore;
    /*****Initializing Values*****/
    int RhighScore = 0;
    int rewind_humanMove;
    int rewind_moves = 1;
    int rewind_roundNumber = 1;
    int rewind_score = 0;
    Random rewindRand = new Random();
    boolean Rewind_AI_Turn = true;

    private Rewind_Computer_player Simon;
    FileOperations rewind_file;


    private List<Integer> rewind_AI_Choices;
    private List<Integer> rewind_User_Choices;
    private List<Integer> revList;
    /*****High Score File*****/
    String filename = "rewindHigh_score";


    final Handler handler = new Handler();
    private SoundPool rewindSoundPool;
    private Set<Integer> rewindSoundsLoaded;
    public SoundPool rewindSP = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
    public int rewindTL_sound, rewindTR_sound, rewindBL_sound, rewindBR_sound;

    int newGameID;
    int highScoreID;
    int gameOverID;
    int redID;
    int blueID;
    int greenID;
    int yellowID;

//*****
//
//onCreate calls Fileoperations to store the highScore, Initializes arrays & buttons, Creates a soundpool and initializes sounds & sets up Listeners for buttons.
//
// *****//
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewind);

        rewind_file=new FileOperations(this,filename);
        rewind_file.setFilename(filename);
        RhighScore=rewind_file.getHighscore();

        rewind_AI_Choices = new ArrayList<Integer>();
        rewind_User_Choices = new ArrayList<Integer>();
        rewind_turn = findViewById(R.id.rewindTurn_tv);
        rewind_current_score = (TextView) findViewById(R.id.currentScore_tv2);
        rewind_highScore = (TextView) findViewById(R.id.highScore_tv2);
        rewind_highScore.setText(Integer.toString(RhighScore));




        rewindTL_Btn = findViewById(R.id.button_rewind_tl);
        rewindTR_Btn = findViewById(R.id.button_rewind_tr);
        rewindBR_Btn = findViewById(R.id.button_rewind_br);
        rewindBL_Btn = findViewById(R.id.button_rewind_bl);
        rewindTL_Btn.setClickable(false);
        rewindTR_Btn.setClickable(false);
        rewindBL_Btn.setClickable(false);
        rewindBR_Btn.setClickable(false);
        rewindTL_Btn.setEnabled(false);
        rewindTR_Btn.setEnabled(false);
        rewindBL_Btn.setEnabled(false);
        rewindBR_Btn.setEnabled(false);
        rewindNG_Btn = findViewById(R.id.button_rewind_ng);


        AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
        attrBuilder.setUsage(AudioAttributes.USAGE_GAME);

        SoundPool.Builder rewindSPBuilder = new SoundPool.Builder();
        rewindSPBuilder.setAudioAttributes(attrBuilder.build());
        rewindSPBuilder.setMaxStreams(1);
        rewindSoundPool = rewindSPBuilder.build();

        rewindSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener(){
            public void onLoadComplete(SoundPool soundpool, int sampleID, int status){
                if(status == 0){
                    rewindSoundsLoaded.add(sampleID);
                }else{

                }
            }
        });

       newGameID = rewindSoundPool.load(this, R.raw.new_game2, 1);
       highScoreID = rewindSoundPool.load(this, R.raw.high_score1, 1);
       gameOverID = rewindSoundPool.load(this, R.raw.game_over, 1);
       greenID = rewindSoundPool.load(this, R.raw.green, 1);
       redID = rewindSoundPool.load(this, R.raw.red, 1);
       yellowID = rewindSoundPool.load(this, R.raw.red, 1);
       blueID = rewindSoundPool.load(this, R.raw.blue, 1);


        rewindTL_sound = rewindSP.load(this, R.raw.one, 1);
        rewindTR_sound = rewindSP.load(this, R.raw.two, 1);
        rewindBL_sound = rewindSP.load(this, R.raw.three, 1);
        rewindBR_sound = rewindSP.load(this, R.raw.four, 1);

        rewindSoundsLoaded = new HashSet<Integer>();

        rewindNG_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rewindSoundsLoaded.contains(newGameID)) {
                    rewindSoundPool.play(newGameID, 1.0f, 1.0f, 0, 0, 1.0f);
                }
                startRewindGame();
            }
        });


        rewindTL_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rewindSoundsLoaded.contains(blueID)) {
                    rewindSoundPool.play(blueID, 1.0f, 1.0f, 0, 0, 1.0f);
                }
                ButtonOpacity newopacity = new ButtonOpacity(handler, v);
                newopacity.makeOpaque(handler, v);
                rewind_humanMove = 1;
                rewind_moves++;
                Log.i("-------", "----- AI_choice -----" + rewind_AI_Choices.get(rewind_moves - 1) + "");
                rewindCheckChoice();
                handler.removeCallbacks(rewind_endGame);
            }
        });

        rewindTR_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rewindSoundsLoaded.contains(greenID)) {
                    rewindSoundPool.play(greenID, 1.0f, 1.0f, 0, 0, 1.0f);
                }
                ButtonOpacity newopacity = new ButtonOpacity(handler, v);
                newopacity.makeOpaque(handler, v);
                rewind_humanMove = 2;
                rewind_moves++;
                Log.i("-------", "----- AI_choice -----" + rewind_AI_Choices.get(rewind_moves - 1) + "");
                rewindCheckChoice();
                handler.removeCallbacks(rewind_endGame);
            }
        });
        rewindBL_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rewindSoundsLoaded.contains(redID)) {
                    rewindSoundPool.play(redID, 1.0f, 1.0f, 0, 0, 1.0f);
                }
                ButtonOpacity rewind_newopacity = new ButtonOpacity(handler, v);
                rewind_newopacity.makeOpaque(handler, v);
                rewind_humanMove = 3;
                rewind_moves++;
                Log.i("-------", "----- AI_choice -----" + rewind_AI_Choices.get(rewind_moves - 1) + "");
                rewindCheckChoice();
                handler.removeCallbacks(rewind_endGame);
            }

        });

        rewindBR_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rewindSoundsLoaded.contains(yellowID)) {
                    rewindSoundPool.play(yellowID, 1.0f, 1.0f, 0, 0, 1.0f);
                }
                ButtonOpacity rewind_newopacity = new ButtonOpacity(handler, v);
                rewind_newopacity.makeOpaque(handler, v);
                rewind_humanMove = 4;
                rewind_moves++;
                Log.i("-------", "----- AI_choice -----" + rewind_AI_Choices + "");
                rewindCheckChoice();
                handler.removeCallbacks(rewind_endGame);
            }
        });
    }
//*****
//
//startRewindGame is activated in onCreate, when the ngButton is pressed. The pc play "Simon" is created, buttons are disabled, and the game is started.
//
// *****//
    public void startRewindGame() {

        rewindTL_Btn.setEnabled(true);
        rewindTR_Btn.setEnabled(true);
        rewindBL_Btn.setEnabled(true);
        rewindBR_Btn.setEnabled(true);
        rewindNG_Btn.setEnabled(false);


                if (Simon == null) {
                    Simon = new Rewind_Computer_player();
                    Simon.execute();
                }

                rewindNG_Btn.setEnabled(false);
                rewind_roundNumber = 1;
                rewind_score = 0;
                rewind_current_score.setText(Integer.toString(rewind_score));
                rewind_moves = 1;
            }

//******
//
// rewindCheckChoice creates a new ArrayList that holds the reverse moves of Simon, then check the users moves against the reverse array list.
//
// *****//
    public void rewindCheckChoice() {
        Log.i("-------", "----- Moves -----" + rewind_moves + "");
        Log.i("-------", "----- Round -----" + rewind_roundNumber + "");
        Log.i("-------", "----- AI_choice -----" + rewind_AI_Choices.get(rewind_moves - 1) + "");
        Log.i("-------", "----- Human_choice -----" + rewind_humanMove + "");
        Log.i("-------", "----- #of choices -----" + rewind_AI_Choices + "");

        revList = new ArrayList<>(rewind_AI_Choices);
        Collections.reverse(revList);


        final Runnable r = new Runnable() {
            public void run() {
                if (rewind_humanMove == revList.get(rewind_moves - 1)) {
                    if (rewind_moves == revList.size()) {
                        rewind_roundNumber++;
                        rewind_score++;
                        if (rewind_score > RhighScore) {
                            if (rewindSoundsLoaded.contains(highScoreID)) {
                                Toast toast = Toast.makeText(getApplicationContext(),
                                        "NEW HIGH SCORE!!", Toast.LENGTH_SHORT);
                                toast.show();
                                rewindSoundPool.play(highScoreID, 1.0f, 1.0f, 0, 0, 2.5f);

                            }

                            RhighScore = rewind_score;
                            rewind_file.setHighscore(rewind_score);
                            rewind_file.writeHighScore(rewind_score, RewindSimonActivity.this);
                            rewind_highScore.setText(Integer.toString(RhighScore));
                        }
                        Simon = new Rewind_Computer_player();
                        Simon.execute();

                    }
                } else {
                    rewind_turn.setText("GAME OVER!");
                    if (rewindSoundsLoaded.contains(gameOverID)) {
                        rewindSoundPool.play(gameOverID, 1.0f, 1.0f, 0, 0, 1.0f);

                    }
                    Rewind_AI_Turn = false;
                    rewind_AI_Choices.clear();
                    revList.clear();
                    rewind_User_Choices.clear();
                    rewindTR_Btn.setClickable(false);
                    rewindTL_Btn.setClickable(false);
                    rewindBR_Btn.setClickable(false);
                    rewindBL_Btn.setClickable(false);
                    rewindNG_Btn.setEnabled(true);
                }
            }};
        handler.postDelayed(r, 300);
        handler.postDelayed(rewind_endGame, 5000);
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (Simon != null) {
            Simon.cancel(true);
            Simon = null;
        }
        if (rewindSP != null) {
            rewindSP.release();
            rewindSP = null;

            rewindSoundsLoaded.clear();
        }


    }

    //*****
    //
    // Rewind_Computer_player is an Async Task that is called when it is Simon's turn.
    //
    // *****//
    public class Rewind_Computer_player extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            rewind_turn.setText("Simon is up");
            handler.removeCallbacks(rewind_endGame);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            rewindTL_Btn.setClickable(false);
            rewindTR_Btn.setClickable(false);
            rewindBR_Btn.setClickable(false);
            rewindBL_Btn.setClickable(false);

            Rewind_AI_Turn = true;
            rewind_current_score.setText(Integer.toString(rewind_score));
        }

        @Override
        protected Void doInBackground(Void... params) {
            int delay = 2000;
            if (rewind_roundNumber < 5) {
                delay = 1500;
            } else if (rewind_roundNumber >= 5 && rewind_roundNumber < 9) {
                delay = 750;
            } else if (rewind_roundNumber >= 9) {
                delay = 300;
            }
            try {
                Rewind_AI_Turn = true;
                int Max = 4;
                int Min = 1;
                int ai_choice = rewindRand.nextInt(Max - Min + 1) + Min;
                rewind_AI_Choices.add(ai_choice);
                Log.i("Button", " = " + ai_choice);
                for (int i = 0; i < rewind_AI_Choices.size(); i++) {
                    Thread.sleep(delay);
                    publishProgress(rewind_AI_Choices.get(i));
                }
            } catch (InterruptedException e) {
                Log.i("-------", "----- INTERRUPTED -----");
            }
            Simon = null;
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.i("-------", "inside onProgress Update");


            play_sound(values[0]);


        }

        @Override
        protected void onPostExecute(Void result) {
            rewind_turn.setText("Your turn");
            rewindTL_Btn.setClickable(true);
            rewindTR_Btn.setClickable(true);
            rewindBL_Btn.setClickable(true);
            rewindBR_Btn.setClickable(true);
            Rewind_AI_Turn = false;
            rewind_moves = 0;
            handler.postDelayed(rewind_endGame, 5000);
        }


        public void play_sound(int soundId) {
            if (soundId == 1) {
                if (rewindSoundsLoaded.contains(blueID)) {
                    rewindSoundPool.play(blueID, 1.0f, 1.0f, 0, 0, 1.0f);
                }
                rewindTL_Btn.getBackground().setAlpha(51);
                final Runnable r = new Runnable() {
                    public void run() {
                        rewindTL_Btn.getBackground().setAlpha(255);

                    }
                };
                handler.postDelayed(r, 100);


            }
            if (soundId == 2) {
                if (rewindSoundsLoaded.contains(greenID)) {
                    rewindSoundPool.play(greenID, 1.0f, 1.0f, 0, 0, 1.0f);
                }
                rewindTR_Btn.getBackground().setAlpha(51);
                final Runnable r = new Runnable() {
                    public void run() {
                        rewindTR_Btn.getBackground().setAlpha(255);

                    }
                };
                handler.postDelayed(r, 100);


            }
            if (soundId == 3) {
                if (rewindSoundsLoaded.contains(redID)) {
                    rewindSoundPool.play(redID, 1.0f, 1.0f, 0, 0, 1.0f);
                }
                rewindBL_Btn.getBackground().setAlpha(51);
                final Runnable r = new Runnable() {
                    public void run() {
                        rewindBL_Btn.getBackground().setAlpha(255);

                    }
                };
                handler.postDelayed(r, 100);

            }
            if (soundId == 4) {
                if (rewindSoundsLoaded.contains(yellowID)) {
                    rewindSoundPool.play(yellowID, 1.0f, 1.0f, 0, 0, 1.0f);
                }
                rewindBR_Btn.getBackground().setAlpha(51);
                final Runnable r = new Runnable() {
                    public void run() {
                        rewindBR_Btn.getBackground().setAlpha(255);

                    }
                };
                handler.postDelayed(r, 100);


            }


        }


        }
    final Runnable rewind_endGame = new Runnable() {
        public void run() {Rewind_AI_Turn = false;
            rewind_AI_Choices.clear();
            rewind_User_Choices.clear();
            rewindTL_Btn.setEnabled(false);
            rewindTR_Btn.setEnabled(false);
            rewindBL_Btn.setEnabled(false);
            rewindBR_Btn.setEnabled(false);
            rewindTL_Btn.setClickable(false);
            rewindTR_Btn.setClickable(false);
            rewindBL_Btn.setClickable(false);
            rewindBR_Btn.setClickable(false);
            rewind_turn.setText("Time Expired");
            rewindNG_Btn.setEnabled(true);


        }
    };



}

