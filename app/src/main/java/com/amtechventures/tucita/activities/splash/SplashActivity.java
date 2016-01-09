package com.amtechventures.tucita.activities.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.main.MainActivity;
import com.amtechventures.tucita.model.context.user.UserContext;
import com.amtechventures.tucita.model.domain.user.UserAttributes;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private Timer timer;
    public boolean active = true;
    private long splashLength = 1000;
    private UserContext userContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        userContext = UserContext.context(userContext);

    }

    @Override
    public void onResume() {

        super.onResume();

        createTimer();

    }

    private void timerMethod() {

        runOnUiThread(timerTick);

    }

    private Runnable timerTick = new Runnable() {

        public void run() {

            if (active) {

                active = false;

                checkForLoginStatus();

            }

        }

    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            if (timer != null) {

                timer.cancel();

                timerMethod();

            }

        }

        return true;

    }

    private void checkForLoginStatus() {

        if (userContext.currentUser() == null) {

            processAnonymousUser();

        } else {

            Log.i("fderdsd", userContext.currentUser().getUserName());

            processLoggedUser();

        }

    }

    private void processAnonymousUser() {

        Class activity = MainActivity.class;

        Intent intent = new Intent(SplashActivity.this, activity);

        intent.putExtra(UserAttributes.connected, false);

        startActivity(intent);

        finish();

    }

    private void processLoggedUser() {

        Class activity = MainActivity.class;

        Intent intent = new Intent(SplashActivity.this, activity);

        intent.putExtra(UserAttributes.connected, true);

        startActivity(intent);

        finish();

    }

    private void createTimer() {

        if (timer == null) {

            timer = new Timer();

            timer.schedule(new TimerTask() {

                @Override

                public void run() {

                    timerMethod();

                }

            }, splashLength);

        } else if (active == false) {

            finish();

        }

    }

}