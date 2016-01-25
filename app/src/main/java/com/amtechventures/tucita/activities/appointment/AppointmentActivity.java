package com.amtechventures.tucita.activities.appointment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.appointment.fragments.details.AppointmentDetailsFragment;
import com.amtechventures.tucita.model.context.appointment.AppointmentCompletion;
import com.amtechventures.tucita.model.context.appointment.AppointmentContext;
import com.amtechventures.tucita.model.domain.appointment.Appointment;
import com.amtechventures.tucita.model.error.AppError;

import java.util.List;

public class AppointmentActivity extends AppCompatActivity{

    private Toolbar toolbar;
    private AppointmentDetailsFragment appointmentDetailsFragment;
    private AppointmentContext appointmentContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        appointmentDetailsFragment = new AppointmentDetailsFragment();

        appointmentContext = AppointmentContext.context(appointmentContext);

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

    public static void goToDetails(Context context, String objectId) {

        Intent intent = new Intent(context, AppointmentActivity.class);

        intent.putExtra(Appointment.class.getName(), objectId);

        context.startActivity(intent);

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

    private void setupAppointment(){

        String objectId = getIntent().getStringExtra(Appointment.class.getName());

        Appointment appointment = appointmentContext.getAppointment(objectId, new AppointmentCompletion.AppointmentErrorCompletion() {

            @Override
            public void completion(List<Appointment> appointmentList, AppError error) {

                if(appointmentList != null){

                    appointmentDetailsFragment.setAppointment(appointmentList.get(0));

                }

            }
        });

        if(appointment != null){

            appointmentDetailsFragment.setAppointment(appointment);

        }

    }

}
