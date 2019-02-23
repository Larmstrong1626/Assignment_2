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
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class OriginalSimon extends Activity {

    public static final int S1 = R.raw.one;
    public static final int S2 = R.raw.two;
    public static final int S3 = R.raw.three;
    public static final int S4 = R.raw.four;
    final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_original_simon);
        Button topLeft = (Button) findViewById(R.id.button_tl);
        topLeft.setOnClickListener(new MyClickListener());
        Button topRight = (Button) findViewById(R.id.button_tr);
        topRight.setOnClickListener(new MyClickListener());
        Button bottomLeft = (Button) findViewById(R.id.button_bl);
        bottomLeft.setOnClickListener(new MyClickListener());
        Button bottomRight = (Button) findViewById(R.id.button_br);
        bottomRight.setOnClickListener(new MyClickListener());


    }

//Implement button listener
    public class MyClickListener implements View.OnClickListener{


        @Override
        public void onClick(View v) {
           playSound(v.getId());
            //xorMyColor(v);
            ButtonOpacity newopacity=new ButtonOpacity(handler,v);
            newopacity.makeOpaque(handler,v);
        }
    }

    private void playSound(int id) {

        int audioRes = 0;
        switch (id) {
            case R.id.button_tl:
                audioRes = R.raw.one;
                break;
            case R.id.button_tr:
                audioRes = R.raw.two;
                break;
            case R.id.button_bl:
                audioRes = R.raw.three;
                break;
            case R.id.button_br:
                audioRes = R.raw.four;
                break;
        }
        MediaPlayer p = MediaPlayer.create(this, audioRes);
        p.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
        p.start();
    }


}
