package com.amtechventures.tucita.activities.account.fragments.bookings;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.account.adapters.AppointmentsAdapter;
import com.amtechventures.tucita.model.domain.appointment.Appointment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BookingsFragment extends Fragment {

    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private TextView noResults;
    private List<Appointment> completedAppointmentsList;
    private List<Appointment> pendingAppointmentsList;
    private AppointmentsAdapter.OnReview adapterListener;

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

        adapterListener = (AppointmentsAdapter.OnReview) context;

    }

    @Override
    public void onDetach() {

        super.onDetach();

        adapterListener = null;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        completedAppointmentsList = new ArrayList<>();

        pendingAppointmentsList = new ArrayList<>();

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_bookings, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        noResults = (TextView) rootView.findViewById(R.id.noResults);

        layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                int pastVisibleItems, visibleItemCount, totalItemCount;

                if (dy > 0) {

                    visibleItemCount = layoutManager.getChildCount();

                    totalItemCount = layoutManager.getItemCount();

                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {

                        Log.v("...", "Last Item Wow !");

                    }
                }

                super.onScrolled(recyclerView, dx, dy);

            }

        });

        return rootView;

    }

    public void setAppointmentList(List<Appointment> appointmentList) {

        Calendar calendar = Calendar.getInstance();

        completedAppointmentsList = new ArrayList<>();

        pendingAppointmentsList = new ArrayList<>();

        for (Appointment appointment : appointmentList) {

        boolean isBefore = appointment.getDate().before(calendar.getTime());

            if (isBefore) {

                completedAppointmentsList.add(appointment);

            } else {

            pendingAppointmentsList.add(appointment);

            }

        }

        setupList();
    }

    public void setupList() {

        boolean noEmpty = !completedAppointmentsList.isEmpty() || !pendingAppointmentsList.isEmpty();

        if (noEmpty) {

            adapter = new AppointmentsAdapter(completedAppointmentsList, pendingAppointmentsList, adapterListener);

            recyclerView.setAdapter(adapter);

            noResults.setVisibility(View.GONE);

        }

    }

}
