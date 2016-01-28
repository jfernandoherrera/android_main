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
import com.amtechventures.tucita.utils.views.AppointmentView;
import com.amtechventures.tucita.utils.views.ViewUtils;

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
    private Button location;
    private OnChangeDate listener;
    private Typeface typeface;


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

    public void setTypeface(Typeface typeface) {

        this.typeface = typeface;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_appointment, container, false);

        appointmentView = (AppointmentView) rootView.findViewById(R.id.appointment);

        location = (Button) rootView.findViewById(R.id.watch_location);

        textTotal = (TextView) rootView.findViewById(R.id.textPrice);

        TextView total = (TextView) rootView.findViewById(R.id.total);

        TextView details = (TextView) rootView.findViewById(R.id.details);

        listViewServices = (ExpandableListView) rootView.findViewById(R.id.listViewServices);

        Button button = (Button) rootView.findViewById(R.id.changeDate);

        Calendar calendar = Calendar.getInstance();

        textTotal.setTypeface(typeface);

        total.setTypeface(typeface, Typeface.BOLD);

        details.setTypeface(typeface);

        location.setTypeface(typeface);

        appointmentView.setTypeface(typeface);

        button.setTypeface(typeface);

        int oneDay = 24;

        calendar.add(Calendar.HOUR, oneDay);

        Date tomorrow = calendar.getTime();
        try {

            if (tomorrow.before(appointment.getDate())) {

                button.setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        if (event.getAction() == MotionEvent.ACTION_DOWN) {

                            v.setBackgroundResource(R.drawable.pressed_application_background_static);

                        } else if (event.getAction() != MotionEvent.ACTION_MOVE) {

                            v.setBackgroundResource(R.drawable.log_in_or_signup_click_out);

                        }

                        return false;

                    }

                });

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
        this.inflater = inflater;

        return rootView;

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

        String date = appointment.getDate().toLocaleString();

        appointmentView.setTextDate(date);

        String venueName = appointment.getVenue().getName();

        appointmentView.setTextName(venueName);

    }

    private void setupVenue(){


    }


    private void setupServices() {

        ViewUtils viewUtils = new ViewUtils(getContext());

        adapter = new ExpandableWithoutParentAdapter(services, listViewServices, viewUtils, typeface);

        adapter.setInflater(inflater);

        listViewServices.setAdapter(adapter);

    }

    public void setAppointment(Appointment appointment) {

        this.appointment = appointment;

    }
}
