/*************
 Developer: Robert Armstrong/Justin Duff
 App: Assignment_2
 MainActivity.java
 Tested on Samsung Galaxy 5
 5.1"x2.85"
 Marshmallow 6.0.1
 API 23

 **************/
package com.oc.assignment02;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button play_original = findViewById(R.id.play_btn);
        play_original.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                playOriginal();
            }
        });

        final Button play_rewind = findViewById(R.id.play_btn2);
        play_rewind.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                playRewind();
            }
        });

        Button about = findViewById(R.id.about_btn);
        about.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                about();
            }
        });
        final Button play_warp = findViewById(R.id.play_btn3);
        play_warp.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                playWarp();
            }
        });
        final Button play_rewindWarp = findViewById(R.id.play_btn4);
        play_rewindWarp.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                playRewindWarp();
            }
        });

        Button rules = findViewById(R.id.rules_btn);
        rules.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                Rules();
            }
        });



    }
    public void Rules(){
        Intent intent = new Intent(getApplicationContext(),
                rules_activity.class);
        startActivity(intent);
    }

    public void playOriginal(){
       Intent intent = new Intent(getApplicationContext(),
                OriginalSimon.class);
        startActivity(intent);
    }
    public void about()
    {
        Intent i = new Intent(getApplicationContext(),AboutActivity.class);
        startActivity(i);
    }

    public void playRewind(){
        Intent intent = new Intent(getApplicationContext(),
                RewindSimonActivity.class);
        startActivity(intent);
    }
    public void playWarp(){
        Intent intent = new Intent(getApplicationContext(),
                WarpSpeed.class);
        startActivity(intent);
    }

    public void playRewindWarp(){
        Intent intent = new Intent(getApplicationContext(),
                RewindWarpSimon.class);
        startActivity(intent);
    }




}
