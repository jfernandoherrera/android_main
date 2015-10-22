package com.amtechventures.tucita.activities.splash;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.main.CategoryActivity;


import java.util.Timer;
import java.util.TimerTask;
public class SplashActivity extends AppCompatActivity {

    private Timer timer;
    public boolean  active=true;
    private long splashLength=1000;
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

    private void TimerMethod() {
        this.runOnUiThread(Timer_Tick);
    }

    private Runnable Timer_Tick = new Runnable() {
        public void run() {
            if (active) {
                active = false;



                Class activity =  CategoryActivity.class;

                Intent i = new Intent(SplashActivity.this, activity);

                startActivity(i);

                finish();

            }

        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if(timer!=null){
                timer.cancel();
                TimerMethod();
            }
        }
        return true;
    }
    private void createTimer() {
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    TimerMethod();
                }

            }, splashLength);
        } else if (!active)
            finish();
    }
}