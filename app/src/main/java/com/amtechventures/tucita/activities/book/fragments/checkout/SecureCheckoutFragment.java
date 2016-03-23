package com.amtechventures.tucita.activities.book.fragments.checkout;

import android.content.Context;
import android.graphics.Bitmap;
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
import com.amtechventures.tucita.model.context.user.UserCompletion;
import com.amtechventures.tucita.model.context.user.UserContext;
import com.amtechventures.tucita.model.domain.appointment.Appointment;
import com.amtechventures.tucita.model.domain.appointment.AppointmentAttributes;
import com.amtechventures.tucita.model.domain.service.Service;
import com.amtechventures.tucita.model.domain.user.User;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.error.AppError;
import com.amtechventures.tucita.utils.views.AlertDialogError;
import com.amtechventures.tucita.utils.views.AppointmentView;
import com.amtechventures.tucita.utils.views.ViewUtils;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    private UserContext userContext;
    private LayoutInflater inflater;
    private ExpandableWithoutParentAdapter adapter;
    private ExpandableListView listViewServices;
    private String telephone;
    private CircularImageView circularImageView;
    private TextInputLayout textInputLayout;
    private TextView  textViewMemberFrom;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        appointmentContext = AppointmentContext.context(appointmentContext);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        userContext = UserContext.context(userContext);

        View rootView = inflater.inflate(R.layout.fragment_checkout, container, false);

        appointmentView = (AppointmentView) rootView.findViewById(R.id.appointment);

        textClientName = (TextView) rootView.findViewById(R.id.accountName);

        textViewMemberFrom= (TextView) rootView.findViewById(R.id.accountMemberFrom);

        textEmail = (TextView) rootView.findViewById(R.id.clientEmail);

        textClientTelephone = (TextView) rootView.findViewById(R.id.clientTelephone);

        inputClientTelephone = (EditText) rootView.findViewById(R.id.inputClientTelephone);

        textTotal = (TextView) rootView.findViewById(R.id.textPrice);

        circularImageView = (CircularImageView) rootView.findViewById(R.id.imageUser);

        textInputLayout = (TextInputLayout) rootView.findViewById(R.id.inputClient);

        listViewServices = (ExpandableListView) rootView.findViewById(R.id.listViewServices);

        Button button = (Button) rootView.findViewById(R.id.placeOrder);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                listener.onPlaceOrder();

            }

        });

        this.inflater = inflater;

        return rootView;

    }


    private void setupServices() {

        ViewUtils viewUtils = new ViewUtils(getContext());

        adapter = new ExpandableWithoutParentAdapter(services, listViewServices, viewUtils);

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

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M d/y");

        String dateString = simpleDateFormat.format(date.getTime());

        simpleDateFormat = new SimpleDateFormat("hh:mm a");

        String hour = simpleDateFormat.format(date.getTime());

        appointmentView.setTextDate(dateString, hour);

        String venueName = venue.getName();

        appointmentView.setTextName(venueName);

    }

    private void setupTitle() {

        String title = getString(R.string.secure_checkout);

        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();

        appCompatActivity.getSupportActionBar().setTitle(title);

    }

    private Bitmap setImageUser(User user){

        Bitmap image = null;

        if(userContext.isFacebook(user)) {

            image = userContext.getPicture();

        }

        return image;

    }

    private void setupUser(){

        Bitmap image = setImageUser(user);

        if(image != null) {

            circularImageView.setImageBitmap(image);

        }

        Date createdAt = user.getParseUser().getCreatedAt();

        int bugDate = 1900;

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM", Locale.getDefault());

        String memberFrom = getString(R.string.member_from) + " " + dateFormat.format(createdAt) + " " + getString(R.string.of) + " " + (createdAt.getYear() + bugDate);

        String userName = user.getName();

        textViewMemberFrom.setText(memberFrom);

            textClientName.setText(userName);

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