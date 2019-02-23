package com.oc.assignment02;

import android.os.Handler;
import android.view.View;

public class ButtonOpacity {

    Handler handler ;
    View v;

    public ButtonOpacity(Handler handler, View v){
        this.handler=handler;
        this.v=v;



    }
    public void makeOpaque(Handler handler,final View v) {
        v.getBackground().setAlpha(51);
        final Runnable r = new Runnable() {
            public void run() {
                v.getBackground().setAlpha(255);
            }
        };
        handler.postDelayed(r, 300);
    }
}
