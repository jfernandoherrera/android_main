package com.amtechventures.tucita.activities.appointment.fragments.details;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.book.adapters.ExpandableWithoutParentAdapter;
import com.amtechventures.tucita.activities.main.MainActivity;
import com.amtechventures.tucita.model.domain.appointment.Appointment;
import com.amtechventures.tucita.model.domain.city.City;
import com.amtechventures.tucita.model.domain.service.Service;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.domain.venue.VenueAttributes;
import com.amtechventures.tucita.utils.views.AppTextView;
import com.amtechventures.tucita.utils.views.AppointmentView;
import com.amtechventures.tucita.utils.views.ViewUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AppointmentDetailsFragment extends Fragment{

    private LayoutInflater inflater;
    private ExpandableWithoutParentAdapter adapter;
    private ExpandableListView listViewServices;
    private TextView textTotal;
    private AppointmentView appointmentView;
    private Appointment appointment;
    private Venue venue;
    private List<Service> services;
    private AppTextView location;
    private OnChangeDate listener;
    private AppTextView button;

    public interface OnChangeDate{

        void onChangeDate(Date date);

    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

        listener = (OnChangeDate) context;

    }

    @Override
    public void onDetach() {

        super.onDetach();

        listener = null;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_appointment, container, false);

        appointmentView = (AppointmentView) rootView.findViewById(R.id.appointment);

        location = (AppTextView) rootView.findViewById(R.id.watch_location);

        textTotal = (TextView) rootView.findViewById(R.id.textPrice);

        listViewServices = (ExpandableListView) rootView.findViewById(R.id.listViewServices);

        button = (AppTextView) rootView.findViewById(R.id.changeDate);

        this.inflater = inflater;

        setupChangeDate();

        return rootView;

    }

    private void setupChangeDate() {

        Calendar calendar = Calendar.getInstance();

        int oneDay = 24;

        calendar.add(Calendar.HOUR, oneDay);

        Date tomorrow = calendar.getTime();

        try {

            if (tomorrow.before(appointment.getDate())) {

                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        listener.onChangeDate(new Date());

                    }
                });

            } else {

                button.setVisibility(View.GONE);

            }

        }catch (NullPointerException e){

            e.printStackTrace();

        }

    }

    public void setServices(List<Service> services) {

        this.services = services;

    }

    public Appointment getAppointment() {

        return appointment;

    }

    public List<Service> getServices() {

        return services;

    }

    public Venue getVenue() {

        return appointment.getVenue();

    }

    public int[] getDuration(){

        return appointment.getDuration();

    }

    public void setup(){

        setupChangeDate();

        setupAppointment();

        setupServices();

        setupTotal();

        setupAddressAndLocation();

    }

    private void setupTotal() {

        int total = 0;

        for (Service service : services) {

            total = total + service.getPrice();

        }

        String money = "$" + total;

        textTotal.setText(money);

    }

    private void setupAddressAndLocation() {

        City city = appointment.getVenue().getCity();

        try{

            String locationString = city.formattedLocation();

            String address = locationString + " " + appointment.getVenue().getAddress();

            location.setText(address);

            location.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {

                        location.setBackgroundResource(R.drawable.pressed_application_background_static);

                    } else if (event.getAction() != MotionEvent.ACTION_MOVE) {

                        location.setBackgroundResource(R.drawable.log_in_or_signup_click_out);

                    }

                    return false;

                }

            });

            location.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    String url = VenueAttributes.linkToLocation + appointment.getVenue().getLatitude() + "," + appointment.getVenue().getLongitude();

                    openWebURL(url);

                }

            });

        }catch(IllegalStateException e){

            MainActivity.goToCategories(getContext());

        }



    }


    public void openWebURL(String inURL) {

        Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse(inURL));

        startActivity(browse);

    }

    private void setupAppointment(){

        Date date = appointment.getDate();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM d/y");

              String dateString = simpleDateFormat.format(date);

        simpleDateFormat = new SimpleDateFormat("hh:mm a");

        String hour = simpleDateFormat.format(date);

        appointmentView.setTextDate(dateString, hour);

        String venueName = appointment.getVenue().getName();

        appointmentView.setTextName(venueName);

    }

    private void setupServices() {

        ViewUtils viewUtils = new ViewUtils(getContext());

        adapter = new ExpandableWithoutParentAdapter(services, listViewServices, viewUtils);

        adapter.setInflater(inflater);

        listViewServices.setAdapter(adapter);

    }

    public void setAppointment(Appointment appointment) {

        this.appointment = appointment;

    }

}
