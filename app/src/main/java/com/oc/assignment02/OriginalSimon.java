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

import android.os.Bundle;
import android.app.Activity;
import android.widget.Button;

public class OriginalSimon extends Activity {
    Button topLeft,topRight,bottomRight,bottomLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_original_simon);

        topLeft.findViewById(R.id.buttontl);
        topRight.findViewById(R.id.buttontr);
        bottomLeft.findViewById(R.id.buttonbl);
        bottomRight.findViewById(R.id.buttonbr);

    }

}
