package com.amtechventures.tucita.activities.splash;

import java.util.Timer;
import android.os.Bundle;
import java.util.TimerTask;
import android.content.Intent;
import android.view.MotionEvent;
import com.amtechventures.tucita.R;
import android.support.v7.app.AppCompatActivity;
import com.amtechventures.tucita.activities.category.CategoryActivity;

public class SplashActivity extends AppCompatActivity {

    private Timer timer;
    public boolean active = true;
    private long splashLength = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

    }

    @Override
    public void onResume(){

        super.onResume();

        createTimer();

    }

    private void timerMethod() {

        runOnUiThread(timerTick);

    }

    private Runnable timerTick = new Runnable() {

        public void run() {

            if(active) {

                active = false;

                Class activity = CategoryActivity.class;

                Intent i = new Intent(SplashActivity.this, activity);

                startActivity(i);

                finish();

            }

        }

    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            if(timer != null) {

                timer.cancel();

                timerMethod();

            }

        }

        return true;

    }
    private void createTimer() {

        if(timer == null) {

            timer = new Timer();

            timer.schedule(new TimerTask() {

                @Override

                public void run() {

                    timerMethod();

                }

            }, splashLength);

        }else if (active == false) {

            finish();

        }

    }

}