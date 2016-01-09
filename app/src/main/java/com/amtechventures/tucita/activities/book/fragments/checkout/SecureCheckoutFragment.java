package com.amtechventures.tucita.activities.book.fragments.checkout;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.book.adapters.ExpandableWithoutParentAdapter;
import com.amtechventures.tucita.model.context.appointment.AppointmentCompletion;
import com.amtechventures.tucita.model.context.appointment.AppointmentContext;
import com.amtechventures.tucita.model.domain.appointment.Appointment;
import com.amtechventures.tucita.model.domain.service.Service;
import com.amtechventures.tucita.model.domain.user.User;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.error.AppError;
import com.amtechventures.tucita.utils.views.AlertDialogError;
import com.amtechventures.tucita.utils.views.AppointmentView;
import com.amtechventures.tucita.utils.views.ViewUtils;
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
    private TextView textEmail;
    private TextView textTotal;
    private AppointmentContext appointmentContext;
    private OnPlaceOrder listener;
    private LayoutInflater inflater;
    private ExpandableWithoutParentAdapter adapter;
    private ExpandableListView listViewServices;

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

        View rootView = inflater.inflate(R.layout.fragment_checkout, container, false);

        appointmentView = (AppointmentView) rootView.findViewById(R.id.appointment);

        textClientName = (TextView) rootView.findViewById(R.id.clientName);

        textEmail = (TextView) rootView.findViewById(R.id.clientEmail);

        textTotal = (TextView) rootView.findViewById(R.id.textPrice);

        listViewServices = (ExpandableListView) rootView.findViewById(R.id.listViewServices);

        user = new User();

        if (ParseUser.getCurrentUser() != null) {

            user.setParseUser(ParseUser.getCurrentUser());

            textClientName.setText(user.getName());

            textEmail.setText(user.getEmail());

        }

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

        setupTitle();

        setupAppointmentView();

        setupServices();

        setupTotal();

    }

    public void setupAppointmentView() {

        appointmentView.setTextName(venue.getName());

        appointmentView.setTextDate(date.getTime().toLocaleString());

    }

    private void setupTitle() {

        String title = getString(R.string.secure_checkout);

        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();

        appCompatActivity.getSupportActionBar().setTitle(title);

    }

    private void setupTotal() {

        int total = 0;

        for (Service service : services) {

            total = total + service.getPrice();

        }

        String money = "$" + total;

        textTotal.setText(money);

    }

    public void placeOrder() {

        Appointment appointment = new Appointment();

        appointment.setDate(date);

        appointment.setDuration(duration);

        appointment.setUser(user);

        appointment.setVenue(venue);

        appointmentContext.placeOrder(appointment, new AppointmentCompletion.AppointmentErrorCompletion() {

            @Override
            public void completion(List<Appointment> appointmentList, AppError error) {

                if (error != null) {

                    AlertDialogError alertDialogError = new AlertDialogError();

                    alertDialogError.noAvailableSlot(getContext());

                } else {

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