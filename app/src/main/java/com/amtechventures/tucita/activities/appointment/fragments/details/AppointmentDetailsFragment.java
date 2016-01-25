package com.amtechventures.tucita.activities.appointment.fragments.details;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.book.adapters.ExpandableWithoutParentAdapter;
import com.amtechventures.tucita.model.domain.appointment.Appointment;
import com.amtechventures.tucita.model.domain.service.Service;
import com.amtechventures.tucita.model.domain.user.User;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.utils.views.AppointmentView;

import java.util.List;

public class AppointmentDetailsFragment extends Fragment{

    private LayoutInflater inflater;
    private ExpandableWithoutParentAdapter adapter;
    private ExpandableListView listViewServices;
    private TextView textClientName;
    private TextView textEmail;
    private TextView textTotal;
    private AppointmentView appointmentView;
    private Appointment appointment;
    private Venue venue;
    private List<Service> services;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_appointment, container, false);

        appointmentView = (AppointmentView) rootView.findViewById(R.id.appointment);

        textClientName = (TextView) rootView.findViewById(R.id.clientName);

        textEmail = (TextView) rootView.findViewById(R.id.clientEmail);

        textTotal = (TextView) rootView.findViewById(R.id.textPrice);

        listViewServices = (ExpandableListView) rootView.findViewById(R.id.listViewServices);

        Button button = (Button) rootView.findViewById(R.id.changeDate);

        return rootView;

    }

    public void setServices(List<Service> services) {

        this.services = services;

    }

    public void setVenue(Venue venue) {

        this.venue = venue;

    }

    public void setAppointment(Appointment appointment) {

        this.appointment = appointment;

    }
}
