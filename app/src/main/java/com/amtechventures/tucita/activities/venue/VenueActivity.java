package com.amtechventures.tucita.activities.venue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import com.amtechventures.tucita.R;
import java.util.Calendar;


public class VenueActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_venue);

        setup();
    }

    private void setup(){

    setupDay();
    }

    private void setupDay(){

        Calendar c = Calendar.getInstance();

        int day = c.get(Calendar.DAY_OF_WEEK);

        RadioButton radioButton;

        switch (day){

            case Calendar.MONDAY:

                radioButton = (RadioButton) findViewById(R.id.radioButton1);

                break;
            case Calendar.TUESDAY:

                radioButton = (RadioButton) findViewById(R.id.radioButton2);

                break;
            case Calendar.WEDNESDAY:

                radioButton = (RadioButton) findViewById(R.id.radioButton3);

                break;
            case Calendar.THURSDAY:

                radioButton = (RadioButton) findViewById(R.id.radioButton4);

                break;
            case Calendar.FRIDAY:

                radioButton = (RadioButton) findViewById(R.id.radioButton5);

                break;
            case Calendar.SATURDAY:

                radioButton = (RadioButton) findViewById(R.id.radioButton6);

                break;
            case Calendar.SUNDAY:

                radioButton = (RadioButton) findViewById(R.id.radioButton7);

                break;
            default:

                radioButton = (RadioButton) findViewById(R.id.radioButton1);

                break;
        }
        radioButton.toggle();
    }
}
