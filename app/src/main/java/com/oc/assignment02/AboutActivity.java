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
import android.os.Bundle;
import android.view.View;

public class AboutActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    public void back(View view)
    {
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
    }
}

