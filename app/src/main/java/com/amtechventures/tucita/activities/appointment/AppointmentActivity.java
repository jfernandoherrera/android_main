package com.amtechventures.tucita.activities.appointment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.appointment.fragments.details.AppointmentDetailsFragment;

public class AppointmentActivity extends AppCompatActivity{

    private Toolbar toolbar;
    private AppointmentDetailsFragment appointmentDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        appointmentDetailsFragment = new AppointmentDetailsFragment();

        setContentView(R.layout.activity_appointment);

        setToolbar();

        setAppointmentDetailsFragment();

        setTitle();
    }

    private void setAppointmentDetailsFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.layout_main, appointmentDetailsFragment);

        transaction.commit();

    }

    private void setTitle() {

        String venue = getString(R.string.details);

        getSupportActionBar().setTitle(venue);

    }

    private void setToolbar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {

            setSupportActionBar(toolbar);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);

    }

}
