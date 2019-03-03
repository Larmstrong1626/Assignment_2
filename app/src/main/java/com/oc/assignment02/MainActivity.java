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




}
