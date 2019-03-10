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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class RewindWarpSimon extends Activity {



    //*****Buttons*****//
    private Button RewindWarpTL_Btn;
    private Button RewindWarpTR_Btn;
    private Button RewindWarpBR_Btn;
    private Button RewindWarpBL_Btn;
    private Button RewindWarpNG_Btn;
    /*****TextViews*****/
    private TextView rewind_turn;
    private TextView rewind_current_score;
    private TextView rewind_highScore;
    /*****Initializing Values*****/
    int RWhighScore = 0;
    int rewindWarp_humanMove;
    int rewindWarp_moves = 1;
    int rewindWarp_roundNumber = 1;
    int rewindWarp_score = 0;
    Random rewindWarpRand = new Random();
    boolean RewindWarp_AI_Turn = true;

    private RewindWarp_Computer_player RW_Simon;
    FileOperations rewindWarp_file;


    private List<Integer> rewind_AI_Choices;
    private List<Integer> rewindWarp_User_Choices;
    private List<Integer> revList;
    /*****High Score File*****/
    String filename = "rewindWarpHigh_score";


    final Handler handler = new Handler();
    private SoundPool rewindWarpSoundPool;
    private Set<Integer> rewindWarpSoundsLoaded;
    public SoundPool rewindWarpSP = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
    public int rewindWarpTL_sound, rewindWarpTR_sound, rewindWarpBL_sound, rewindWarpBR_sound;

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

        rewindWarp_file =new FileOperations(this,filename);
        rewindWarp_file.setFilename(filename);
        RWhighScore = rewindWarp_file.getHighscore();

        rewind_AI_Choices = new ArrayList<Integer>();
        rewindWarp_User_Choices = new ArrayList<Integer>();
        rewind_turn = findViewById(R.id.rewindTurn_tv);
        rewind_current_score = (TextView) findViewById(R.id.currentScore_tv2);
        rewind_highScore = (TextView) findViewById(R.id.highScore_tv2);
        rewind_highScore.setText(Integer.toString(RWhighScore));




        RewindWarpTL_Btn = findViewById(R.id.button_rewind_tl);
        RewindWarpTR_Btn = findViewById(R.id.button_rewind_tr);
        RewindWarpBR_Btn = findViewById(R.id.button_rewind_br);
        RewindWarpBL_Btn = findViewById(R.id.button_rewind_bl);
        RewindWarpTL_Btn.setClickable(false);
        RewindWarpTR_Btn.setClickable(false);
        RewindWarpBL_Btn.setClickable(false);
        RewindWarpBR_Btn.setClickable(false);
        RewindWarpTL_Btn.setEnabled(false);
        RewindWarpTR_Btn.setEnabled(false);
        RewindWarpBL_Btn.setEnabled(false);
        RewindWarpBR_Btn.setEnabled(false);
        RewindWarpNG_Btn = findViewById(R.id.button_rewind_ng);


        AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
        attrBuilder.setUsage(AudioAttributes.USAGE_GAME);

        SoundPool.Builder rewindSPBuilder = new SoundPool.Builder();
        rewindSPBuilder.setAudioAttributes(attrBuilder.build());
        rewindSPBuilder.setMaxStreams(1);
        rewindWarpSoundPool = rewindSPBuilder.build();

        rewindWarpSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener(){
            public void onLoadComplete(SoundPool soundpool, int sampleID, int status){
                if(status == 0){
                    rewindWarpSoundsLoaded.add(sampleID);
                }else{

                }
            }
        });

        newGameID = rewindWarpSoundPool.load(this, R.raw.new_game2, 1);
        highScoreID = rewindWarpSoundPool.load(this, R.raw.high_score1, 1);
        gameOverID = rewindWarpSoundPool.load(this, R.raw.game_over, 1);
        greenID = rewindWarpSoundPool.load(this, R.raw.green, 1);
        redID = rewindWarpSoundPool.load(this, R.raw.red, 1);
        yellowID = rewindWarpSoundPool.load(this, R.raw.red, 1);
        blueID = rewindWarpSoundPool.load(this, R.raw.blue, 1);


        rewindWarpTL_sound = rewindWarpSP.load(this, R.raw.one, 1);
        rewindWarpTR_sound = rewindWarpSP.load(this, R.raw.two, 1);
        rewindWarpBL_sound = rewindWarpSP.load(this, R.raw.three, 1);
        rewindWarpBR_sound = rewindWarpSP.load(this, R.raw.four, 1);

        rewindWarpSoundsLoaded = new HashSet<Integer>();

        RewindWarpNG_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rewindWarpSoundsLoaded.contains(newGameID)) {
                    rewindWarpSoundPool.play(newGameID, 1.0f, 1.0f, 0, 0, 1.0f);
                }
                startRewindWarpGame();
            }
        });


        RewindWarpTL_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rewindWarpSoundsLoaded.contains(blueID)) {
                    rewindWarpSoundPool.play(blueID, 1.0f, 1.0f, 0, 0, 1.0f);
                }
                ButtonOpacity newopacity = new ButtonOpacity(handler, v);
                newopacity.makeOpaque(handler, v);
                rewindWarp_humanMove = 1;
                rewindWarp_moves++;
                Log.i("-------", "----- AI_choice -----" + rewind_AI_Choices.get(rewindWarp_moves - 1) + "");
                rewindCheckChoice();
                handler.removeCallbacks(rewind_endGame);
            }
        });

        RewindWarpTR_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rewindWarpSoundsLoaded.contains(greenID)) {
                    rewindWarpSoundPool.play(greenID, 1.0f, 1.0f, 0, 0, 1.0f);
                }
                ButtonOpacity newopacity = new ButtonOpacity(handler, v);
                newopacity.makeOpaque(handler, v);
                rewindWarp_humanMove = 2;
                rewindWarp_moves++;
                Log.i("-------", "----- AI_choice -----" + rewind_AI_Choices.get(rewindWarp_moves - 1) + "");
                rewindCheckChoice();
                handler.removeCallbacks(rewind_endGame);
            }
        });
        RewindWarpBL_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rewindWarpSoundsLoaded.contains(redID)) {
                    rewindWarpSoundPool.play(redID, 1.0f, 1.0f, 0, 0, 1.0f);
                }
                ButtonOpacity rewind_newopacity = new ButtonOpacity(handler, v);
                rewind_newopacity.makeOpaque(handler, v);
                rewindWarp_humanMove = 3;
                rewindWarp_moves++;
                Log.i("-------", "----- AI_choice -----" + rewind_AI_Choices.get(rewindWarp_moves - 1) + "");
                rewindCheckChoice();
                handler.removeCallbacks(rewind_endGame);
            }

        });

        RewindWarpBR_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rewindWarpSoundsLoaded.contains(yellowID)) {
                    rewindWarpSoundPool.play(yellowID, 1.0f, 1.0f, 0, 0, 1.0f);
                }
                ButtonOpacity rewind_newopacity = new ButtonOpacity(handler, v);
                rewind_newopacity.makeOpaque(handler, v);
                rewindWarp_humanMove = 4;
                rewindWarp_moves++;
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
    public void startRewindWarpGame() {

        RewindWarpTR_Btn.setEnabled(true);
        RewindWarpTL_Btn.setEnabled(true);
        RewindWarpBL_Btn.setEnabled(true);
        RewindWarpBR_Btn.setEnabled(true);
        RewindWarpNG_Btn.setEnabled(false);

        if (RW_Simon == null) {
            RW_Simon = new RewindWarp_Computer_player();
            RW_Simon.execute();
        }

        RewindWarpNG_Btn.setEnabled(false);
        rewindWarp_roundNumber = 1;
        rewindWarp_score = 0;
        rewind_current_score.setText(Integer.toString(rewindWarp_score));
        rewindWarp_moves = 1;
    }

    //******
//
// rewindCheckChoice creates a new ArrayList that holds the reverse moves of Simon, then check the users moves against the reverse array list.
//
// *****//
    public void rewindCheckChoice() {
        Log.i("-------", "----- Moves -----" + rewindWarp_moves + "");
        Log.i("-------", "----- Round -----" + rewindWarp_roundNumber + "");
        Log.i("-------", "----- AI_choice -----" + rewind_AI_Choices.get(rewindWarp_moves - 1) + "");
        Log.i("-------", "----- Human_choice -----" + rewindWarp_humanMove + "");
        Log.i("-------", "----- #of choices -----" + rewind_AI_Choices + "");

        revList = new ArrayList<>(rewind_AI_Choices);
        Collections.reverse(revList);


        final Runnable r = new Runnable() {
            public void run() {
                if (rewindWarp_humanMove == revList.get(rewindWarp_moves - 1)) {
                    if (rewindWarp_moves == revList.size()) {
                        rewindWarp_roundNumber++;
                        rewindWarp_score++;
                        if (rewindWarp_score > RWhighScore) {
                            if (rewindWarpSoundsLoaded.contains(highScoreID)) {
                                Toast toast = Toast.makeText(getApplicationContext(),
                                        "NEW HIGH SCORE!!", Toast.LENGTH_SHORT);
                                toast.show();
                                rewindWarpSoundPool.play(highScoreID, 1.0f, 1.0f, 0, 0, 2.5f);

                            }

                            RWhighScore = rewindWarp_score;
                            rewindWarp_file.setHighscore(rewindWarp_score);
                            rewindWarp_file.writeHighScore(rewindWarp_score, RewindWarpSimon.this);
                            rewind_highScore.setText(Integer.toString(RWhighScore));
                        }
                        RW_Simon = new RewindWarpSimon.RewindWarp_Computer_player();
                        RW_Simon.execute();

                    }
                } else {
                    rewind_turn.setText("GAME OVER!");
                    if (rewindWarpSoundsLoaded.contains(gameOverID)) {
                        rewindWarpSoundPool.play(gameOverID, 1.0f, 1.0f, 0, 0, 1.0f);

                    }
                    RewindWarp_AI_Turn = false;
                    rewind_AI_Choices.clear();
                    revList.clear();
                    rewindWarp_User_Choices.clear();
                    RewindWarpTR_Btn.setClickable(false);
                    RewindWarpTL_Btn.setClickable(false);
                    RewindWarpBR_Btn.setClickable(false);
                    RewindWarpBL_Btn.setClickable(false);
                    RewindWarpNG_Btn.setEnabled(true);
                }
            }};
        handler.postDelayed(r, 10);
        handler.postDelayed(rewind_endGame, 5000);
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (RW_Simon != null) {
            RW_Simon.cancel(true);
            RW_Simon = null;
        }
        if (rewindWarpSP != null) {
            rewindWarpSP.release();
            rewindWarpSP = null;

            rewindWarpSoundsLoaded.clear();
        }


    }

    //*****
    //
    // Rewind_Warp Computer_player is an Async Task that is called when it is Simon's turn.
    //
    // *****//
    public class RewindWarp_Computer_player extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            rewind_turn.setText("Simon is up");
            handler.removeCallbacks(rewind_endGame);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            RewindWarpTL_Btn.setClickable(false);
            RewindWarpTR_Btn.setClickable(false);
            RewindWarpBR_Btn.setClickable(false);
            RewindWarpBL_Btn.setClickable(false);

            RewindWarp_AI_Turn = true;
            rewind_current_score.setText(Integer.toString(rewindWarp_score));
        }

        @Override
        protected Void doInBackground(Void... params) {
            int delay = 300;
            if (rewindWarp_roundNumber < 5) {
                delay = 300;
            } else if (rewindWarp_roundNumber >= 5 && rewindWarp_roundNumber < 9) {
                delay = 200;
            } else if (rewindWarp_roundNumber >= 9) {
                delay = 100;
            }
            try {
                RewindWarp_AI_Turn = true;
                int Max = 4;
                int Min = 1;
                int ai_choice = rewindWarpRand.nextInt(Max - Min + 1) + Min;
                rewind_AI_Choices.add(ai_choice);
                Log.i("Button", " = " + ai_choice);
                for (int i = 0; i < rewind_AI_Choices.size(); i++) {
                    Thread.sleep(delay);
                    publishProgress(rewind_AI_Choices.get(i));
                }
            } catch (InterruptedException e) {
                Log.i("-------", "----- INTERRUPTED -----");
            }
            RW_Simon = null;
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
            RewindWarpTL_Btn.setClickable(true);
            RewindWarpTR_Btn.setClickable(true);
            RewindWarpBL_Btn.setClickable(true);
            RewindWarpBR_Btn.setClickable(true);
            RewindWarp_AI_Turn = false;
            rewindWarp_moves = 0;
            handler.postDelayed(rewind_endGame, 5000);
        }


        public void play_sound(int soundId) {
            if (soundId == 1) {
                if (rewindWarpSoundsLoaded.contains(blueID)) {
                    rewindWarpSoundPool.play(blueID, 1.0f, 1.0f, 0, 0, 1.0f);
                }
                RewindWarpTL_Btn.getBackground().setAlpha(51);
                final Runnable r = new Runnable() {
                    public void run() {
                        RewindWarpTL_Btn.getBackground().setAlpha(255);

                    }
                };
                handler.postDelayed(r, 100);


            }
            if (soundId == 2) {
                if (rewindWarpSoundsLoaded.contains(greenID)) {
                    rewindWarpSoundPool.play(greenID, 1.0f, 1.0f, 0, 0, 1.0f);
                }
                RewindWarpTR_Btn.getBackground().setAlpha(51);
                final Runnable r = new Runnable() {
                    public void run() {
                        RewindWarpTR_Btn.getBackground().setAlpha(255);

                    }
                };
                handler.postDelayed(r, 100);


            }
            if (soundId == 3) {
                if (rewindWarpSoundsLoaded.contains(redID)) {
                    rewindWarpSoundPool.play(redID, 1.0f, 1.0f, 0, 0, 1.0f);
                }
                RewindWarpBL_Btn.getBackground().setAlpha(51);
                final Runnable r = new Runnable() {
                    public void run() {
                        RewindWarpBL_Btn.getBackground().setAlpha(255);

                    }
                };
                handler.postDelayed(r, 100);

            }
            if (soundId == 4) {
                if (rewindWarpSoundsLoaded.contains(yellowID)) {
                    rewindWarpSoundPool.play(yellowID, 1.0f, 1.0f, 0, 0, 1.0f);
                }
                RewindWarpBR_Btn.getBackground().setAlpha(51);
                final Runnable r = new Runnable() {
                    public void run() {
                        RewindWarpBR_Btn.getBackground().setAlpha(255);

                    }
                };
                handler.postDelayed(r, 100);


            }


        }


    }
    final Runnable rewind_endGame = new Runnable() {
        public void run() {
            RewindWarp_AI_Turn = false;
            rewind_AI_Choices.clear();
            rewindWarp_User_Choices.clear();
            RewindWarpTL_Btn.setEnabled(false);
            RewindWarpTR_Btn.setEnabled(false);
            RewindWarpBL_Btn.setEnabled(false);
            RewindWarpBR_Btn.setEnabled(false);
            RewindWarpTL_Btn.setClickable(false);
            RewindWarpTR_Btn.setClickable(false);
            RewindWarpBL_Btn.setClickable(false);
            RewindWarpBR_Btn.setClickable(false);
            rewind_turn.setText("Time Expired");
            RewindWarpNG_Btn.setEnabled(true);


        }
    };


}
