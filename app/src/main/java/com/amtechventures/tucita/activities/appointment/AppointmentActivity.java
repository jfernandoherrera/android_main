package com.amtechventures.tucita.activities.appointment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.appointment.fragments.details.AppointmentDetailsFragment;
import com.amtechventures.tucita.activities.book.fragments.select.SelectDayFragment;
import com.amtechventures.tucita.activities.book.fragments.select.adapters.SelectHourAdapter;
import com.amtechventures.tucita.activities.main.MainActivity;
import com.amtechventures.tucita.model.context.appointment.AppointmentCompletion;
import com.amtechventures.tucita.model.context.appointment.AppointmentContext;
import com.amtechventures.tucita.model.context.service.ServiceCompletion;
import com.amtechventures.tucita.model.context.service.ServiceContext;
import com.amtechventures.tucita.model.domain.appointment.Appointment;
import com.amtechventures.tucita.model.domain.service.Service;
import com.amtechventures.tucita.model.domain.slot.Slot;
import com.amtechventures.tucita.model.error.AppError;
import com.amtechventures.tucita.utils.common.AppFont;
import com.amtechventures.tucita.utils.views.AlertDialogError;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

public class AppointmentActivity extends AppCompatActivity implements AppointmentDetailsFragment.OnChangeDate, SelectHourAdapter.OnSlotSelected{

    private Toolbar toolbar;
    private AppointmentDetailsFragment appointmentDetailsFragment;
    private AppointmentContext appointmentContext;
    private ServiceContext serviceContext;
    private SelectDayFragment selectDateFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        appointmentDetailsFragment = new AppointmentDetailsFragment();

        appointmentContext = AppointmentContext.context(appointmentContext);

        serviceContext = ServiceContext.context(serviceContext);

        selectDateFragment = new SelectDayFragment();

        setContentView(R.layout.activity_appointment);

        setToolbar();

        setAppointmentDetailsFragment();

        setSelectDateFragment();

        selectDateHide();

        setupAppointment();

        setTitle();

    }

    private void setSelectDateFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.layout_main, selectDateFragment);

        transaction.commit();

    }

    private void selectDateHide() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.hide(selectDateFragment);

        transaction.commit();

    }

    private void selectDateShow() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.show(selectDateFragment);

        transaction.commit();

    }

    private void setAppointmentDetailsFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.layout_main, appointmentDetailsFragment);

        transaction.commit();

    }

    private void appointmentDetailsShow() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.show(appointmentDetailsFragment);

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

        back();

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {

        back();

    }

    private void back() {

        if (!selectDateFragment.isHidden()) {

            selectDateHide();

            appointmentDetailsShow();

        }else {

            finish();
        }

    }

    private void setupAppointment(){

        String objectId = getIntent().getStringExtra(Appointment.class.getName());

        final Appointment appointment = appointmentContext.getAppointment(objectId, new AppointmentCompletion.AppointmentErrorCompletion() {

            @Override
            public void completion(List<Appointment> appointmentList, AppError error) {

            }

            @Override
            public void completion(Appointment object, AppError error) {

                if(object != null){

                    appointmentDetailsFragment.setAppointment(object);

                    setupServices(object);

                }

            }

        });

        if(appointment != null){

            appointmentDetailsFragment.setAppointment(appointment);

            setupServices(appointment);

        }

    }

    private void setupServices(Appointment appointment){

        serviceContext.loadAppointmentServices(appointment, new ServiceCompletion.ErrorCompletion() {

            @Override
            public void completion(List<Service> servicesList, AppError error) {

                if (servicesList != null) {

                    appointmentDetailsFragment.setServices(servicesList);


                    if (!servicesList.isEmpty()) {

                        appointmentDetailsFragment.setup();

                    }

                }

            }
        });

    }

    private void detailsHide() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.hide(appointmentDetailsFragment);

        transaction.commit();

    }

    public void onChangeDate(){

        selectDateFragment.setVenue(appointmentDetailsFragment.getVenue());

        selectDateFragment.setDuration(appointmentDetailsFragment.getDuration());

        selectDateFragment.reload();

        selectDateShow();

        detailsHide();

    }

    @Override
    public void onChangeDate(Date date) {

        onChangeDate();

    }

    @Override
    public void onSlotSelected(Slot slot) {

        selectDateHide();

        Appointment appointment = appointmentDetailsFragment.getAppointment();

        appointment.putDate(slot.getDate());

        appointmentContext.placeOrder(appointment, new AppointmentCompletion.AppointmentErrorCompletion() {


            @Override
            public void completion(List<Appointment> appointmentList, AppError error) {

            }

            @Override
            public void completion(Appointment appointment, AppError error) {

                if (error != null) {

                    AlertDialogError alertDialogError = new AlertDialogError();

                    alertDialogError.noAvailableSlot(getApplicationContext());

                }else{

                    MainActivity.goToCategories(getApplicationContext());

                }

            }
        });

    }
}
