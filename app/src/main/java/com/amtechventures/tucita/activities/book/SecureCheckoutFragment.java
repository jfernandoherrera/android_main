package com.amtechventures.tucita.activities.book;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.model.context.appointment.AppointmentCompletion;
import com.amtechventures.tucita.model.context.appointment.AppointmentContext;
import com.amtechventures.tucita.model.context.slot.SlotContext;
import com.amtechventures.tucita.model.domain.appointment.Appointment;
import com.amtechventures.tucita.model.domain.appointment.AppointmentAttributes;
import com.amtechventures.tucita.model.domain.service.Service;
import com.amtechventures.tucita.model.domain.slot.Slot;
import com.amtechventures.tucita.model.domain.user.User;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.error.AppError;
import com.amtechventures.tucita.utils.views.AlertDialogError;
import com.amtechventures.tucita.utils.views.AppointmentView;
import com.parse.ParseUser;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SecureCheckoutFragment extends Fragment{

    AppointmentView appointmentView;
    Venue venue;
    List<Service> services;
    int duration;
    Calendar date;
    User user;
    TextView textClientName;
    TextView textEmail;
    AppointmentContext appointmentContext;
    OnPlaceOrder listener;

    public interface OnPlaceOrder{

        void onPlaceOrder();
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

        listener = (OnPlaceOrder) context;

    }

    @Override
    public void onDetach() {

        super.onDetach();

        listener = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        appointmentContext = AppointmentContext.context(appointmentContext);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_checkout, container, false);

        appointmentView = (AppointmentView) rootView.findViewById(R.id.appointment);

        textClientName = (TextView) rootView.findViewById(R.id.clientName);

        textEmail = (TextView) rootView.findViewById(R.id.clientEmail);

        user = new User();

        if(ParseUser.getCurrentUser() != null) {

            user.setParseUser(ParseUser.getCurrentUser());

            textClientName.setText(user.getUserName());

            textEmail.setText(user.getEmail());
        }

        Button button = (Button) rootView.findViewById(R.id.placeOrder);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              listener.onPlaceOrder();
            }
        });

        return rootView;
    }


    @Override
    public void onResume() {

        super.onResume();

        setupAppointmentView();
    }

    public void setupAppointmentView(){

        appointmentView.setTextName(venue.getName());

        appointmentView.setTextDate(date.getTime().toLocaleString());
    }

    public void placeOrder(){

    Appointment appointment = new Appointment();

        appointment.setDate(date);

        appointment.setDuration(duration);

        appointment.setUser(user);

        appointment.setVenue(venue);

        appointmentContext.placeOrder(appointment, new AppointmentCompletion.AppointmentErrorCompletion() {
            @Override
            public void completion(List<Appointment> appointmentList, AppError error) {

            if (error != null){

                AlertDialogError alertDialogError = new AlertDialogError();

                alertDialogError.noAvailableSlot(getContext());

            }else{

                getActivity().finish();
            }
            }
        });
    }

    public void setVenue(Venue venue) {

        this.venue = venue;
    }

    public void setDate(Calendar date) {

        this.date = date;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

}
