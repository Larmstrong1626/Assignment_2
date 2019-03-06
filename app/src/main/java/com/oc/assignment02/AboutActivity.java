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
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.net.URI;

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

    public void onClick(View v){
        if(v.getId() == R.id.newGameAttr_tv){

            Uri my_link = Uri.parse("https://freesound.org/people/JustInvoke/sounds/446142/");
            Intent intent = new Intent(Intent.ACTION_VIEW, my_link);
            startActivity(intent);
        }
    }
}

