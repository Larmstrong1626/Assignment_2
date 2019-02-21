/*************
 Developer: Robert Armstrong/Justin Duff
 App: Assignment_1
 MainActivity.java
 Tested on Samsung Galaxy 5
 5.1"x2.85"
 Marshmallow 6.0.1
 API 23

 **************/
package com.oc.assignment_1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener{




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button more_btn = findViewById(R.id.more_btn);
        more_btn.setOnClickListener(this);

        Button play_wild = findViewById(R.id.play_btn);
        play_wild.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                playWild();
            }
        });

        Button play_not = findViewById(R.id.play_btn2);
        play_not.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                playNot();
            }
        });


    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.more_btn) {
            Uri my_link = Uri.parse("https://en.wikipedia.org/wiki/Tic-tac-toe_variants");
            Intent intent = new Intent(Intent.ACTION_VIEW, my_link);
            startActivity(intent);
        }

    }
    public void playWild(){
        Intent intent = new Intent(getApplicationContext(),
                PlayWildTicTacToe.class);
        startActivity(intent);
    }

    public void playNot(){
        Intent intent = new Intent(getApplicationContext(),
                NotaktoActivity.class);
        startActivity(intent);
    }

    public void about(View view)
    {
        Intent i = new Intent(getApplicationContext(),AboutActivity.class);
        startActivity(i);
    }



}
