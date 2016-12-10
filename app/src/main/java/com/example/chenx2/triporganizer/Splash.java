package com.example.chenx2.triporganizer;

import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

public class Splash extends Activity {

    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH = 2000;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);
        TextView tv = (TextView) findViewById(R.id.splash_title);
        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/lobster.ttf");
        TextView tv_welcome = (TextView) findViewById(R.id.splash_welcome);
        Typeface face_welcome = Typeface.createFromAsset(getAssets(),
                "fonts/juliussansone.ttf");
        tv_welcome.setTypeface(face_welcome);
        tv.setTypeface(face);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Splash.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}