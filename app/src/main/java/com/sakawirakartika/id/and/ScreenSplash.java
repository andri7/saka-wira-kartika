package com.sakawirakartika.id.and;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

/**
 * Created by cvglobalsolusindo on 4/1/2016.
 */
public class ScreenSplash extends Activity {

    // Splash screen timer
    private  static int SPLASH_TIME_OUT = 3000;

    @Override
    protected  void  onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);
        getActionBar().hide();
        new Handler().postDelayed(new Runnable() {

                        /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activityx
                Intent i = new Intent(ScreenSplash.this, LoginActivity.class);
                startActivity(i);

                //close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);

    }
}
