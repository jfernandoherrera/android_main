package com.amtechventures.tucita.activities.book.fragments.checkout;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.book.adapters.ExpandableWithoutParentAdapter;
import com.amtechventures.tucita.model.context.appointment.AppointmentCompletion;
import com.amtechventures.tucita.model.context.appointment.AppointmentContext;
import com.amtechventures.tucita.model.domain.appointment.Appointment;
import com.amtechventures.tucita.model.domain.appointment.AppointmentAttributes;
import com.amtechventures.tucita.model.domain.service.Service;
import com.amtechventures.tucita.model.domain.user.User;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.error.AppError;
import com.amtechventures.tucita.utils.views.AlertDialogError;
import com.amtechventures.tucita.utils.views.AppointmentView;
import com.amtechventures.tucita.utils.views.ViewUtils;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.Calendar;
import java.util.List;

public class SecureCheckoutFragment extends Fragment {

    private AppointmentView appointmentView;
    private Venue venue;
    private List<Service> services;
    private int duration;
    private Calendar date;
    private User user;
    private TextView textClientName;
    private TextView textClientTelephone;
    private EditText inputClientTelephone;
    private TextView textEmail;
    private TextView textTotal;
    private AppointmentContext appointmentContext;
    private OnPlaceOrder listener;
    private LayoutInflater inflater;
    private ExpandableWithoutParentAdapter adapter;
    private ExpandableListView listViewServices;
    private Typeface typeface;
    private String telephone;
    private TextInputLayout textInputLayout;

    public interface OnPlaceOrder {

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

    public void setTypeface(Typeface typeface) {

        this.typeface = typeface;

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

        textClientTelephone = (TextView) rootView.findViewById(R.id.clientTelephone);

        inputClientTelephone = (EditText) rootView.findViewById(R.id.inputClientTelephone);

        textTotal = (TextView) rootView.findViewById(R.id.textPrice);

        textInputLayout = (TextInputLayout) rootView.findViewById(R.id.inputClient);

        textInputLayout.setTypeface(typeface);

        inputClientTelephone.setTypeface(typeface);

        textClientTelephone.setTypeface(typeface);

        textTotal.setTypeface(typeface);

        textEmail.setTypeface(typeface);

        textClientName.setTypeface(typeface);

        appointmentView.setTypeface(typeface);

        listViewServices = (ExpandableListView) rootView.findViewById(R.id.listViewServices);

        Button button = (Button) rootView.findViewById(R.id.placeOrder);

        button.setTypeface(typeface, Typeface.BOLD);

        button.setBackgroundResource(R.drawable.cling_button_normal);

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    v.setBackgroundResource(R.drawable.cling_button_pressed);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {

                    v.setBackgroundResource(R.drawable.cling_button_normal);

                }

                return false;

            }
        });


        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                listener.onPlaceOrder();

            }

        });

        setupTitlesTypeface(rootView);

        this.inflater = inflater;

        return rootView;

    }

    private void setupTitlesTypeface(View rootView){

        TextView textClient = (TextView) rootView.findViewById(R.id.client);

        TextView textTotal = (TextView) rootView.findViewById(R.id.total);

        textClient.setTypeface(typeface, Typeface.BOLD);

        textTotal.setTypeface(typeface, Typeface.BOLD);

    }

    private void setupServices() {

        ViewUtils viewUtils = new ViewUtils(getContext());

        adapter = new ExpandableWithoutParentAdapter(services, listViewServices, viewUtils, typeface);

        adapter.setInflater(inflater);

        listViewServices.setAdapter(adapter);

    }

    public void setup() {

        setupUser();

        setupTitle();

        setupAppointmentView();

        setupServices();

        setupTotal();

    }

    public void setupAppointmentView() {

        String dateString = date.getTime().toLocaleString();

        appointmentView.setTextDate(dateString);

        String venueName = venue.getName();

        appointmentView.setTextName(venueName);

    }

    private void setupTitle() {

        String title = getString(R.string.secure_checkout);

        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();

        appCompatActivity.getSupportActionBar().setTitle(title);

    }

    private void setupUser(){

            textClientName.setText(user.getName());

            textEmail.setText(user.getEmail());

            telephone = user.getTelephone();

            if(telephone == null ){

                textInputLayout.setVisibility(View.VISIBLE);

                textClientTelephone.setVisibility(View.GONE);

            }else {

                textClientTelephone.setVisibility(View.VISIBLE);

                textClientTelephone.setText(telephone);

                textInputLayout.setVisibility(View.GONE);

            }

    }

    private void setupTotal() {

        int total = 0;

        for (Service service : services) {

            total = total + service.getPrice();

        }

        String money = "$" + total;

        textTotal.setText(money);

    }

    private boolean validateTelephoneInput(){

    boolean is = false;

        String test = inputClientTelephone.getText().toString().trim();

        boolean validateLength = test.length() > 6;

        if(validateLength){

            is = true;

        }

        return is;

    }

    public void placeOrder(AppointmentCompletion.AppointmentErrorCompletion completion) {

        if(telephone == null){

            if(validateTelephoneInput()){

               user.setTelephone(String.valueOf(inputClientTelephone.getText()));

                setupAppointmentToPlace(completion);

            }else {

                AlertDialogError alertDialogError = new AlertDialogError();

                alertDialogError.noTelephone(getContext());

            }

        }else {

            setupAppointmentToPlace(completion);
        }


    }

    private void setupAppointmentToPlace(AppointmentCompletion.AppointmentErrorCompletion completion){

        Appointment appointment = new Appointment();

        ParseRelation<Service> serviceParseRelation = appointment.getRelation(AppointmentAttributes.services);

        for(Service service : services){

            serviceParseRelation.add(service);

        }

        appointment.setDate(date);

        appointment.setDuration(duration);

        appointment.setUser(user);

        appointment.setVenue(venue);

        appointmentContext.placeOrder(appointment, completion);

    }

    public void setVenue(Venue venue) {

        this.venue = venue;

    }

    public void setUser(User user) {

        this.user = user;

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