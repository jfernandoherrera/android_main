package com.amtechventures.tucita.activities.account.fragments.venues;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.account.adapters.AppointmentsAdapter;
import com.amtechventures.tucita.activities.account.adapters.VenuesAdapter;
import com.amtechventures.tucita.model.context.appointment.AppointmentContext;
import com.amtechventures.tucita.model.context.venue.VenueContext;
import com.amtechventures.tucita.model.domain.appointment.Appointment;
import com.amtechventures.tucita.model.domain.venue.Venue;

import java.util.ArrayList;
import java.util.List;

public class VenuesFragment extends Fragment{

    private AppointmentContext appointmentContext;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private TextView noResults;
    private List<Venue> venues;

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
    }

    @Override
    public void onDetach() {

        super.onDetach();
    }


    public void setVenues(List<Venue> venues){

        this.venues = venues;

        setupList();
    }


    public void setupList(){

        if (venues != null && !venues.isEmpty()) {

            adapter = new VenuesAdapter(venues);

            recyclerView.setAdapter(adapter);

            noResults.setVisibility(View.GONE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_venues, container, false);

        appointmentContext = AppointmentContext.context(appointmentContext);

        recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);

        noResults = (TextView) rootView.findViewById(R.id.noResults);

        layoutManager = new GridLayoutManager(getContext(), 1);

        recyclerView.setLayoutManager(layoutManager);

    return rootView;
    }
}
