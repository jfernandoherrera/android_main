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

import java.util.List;

public class BookingsFragment extends Fragment {

    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private TextView noResults;
    private List<Appointment> appointmentList;

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

    }

    @Override
    public void onDetach() {

        super.onDetach();

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

        setupList();

        return rootView;

    }

    public void setAppointmentList(List<Appointment> appointmentList) {

        this.appointmentList = appointmentList;

        setupList();

    }

    public void setupList() {

        if (appointmentList != null && !appointmentList.isEmpty()) {

            adapter = new AppointmentsAdapter(appointmentList);

            recyclerView.setAdapter(adapter);

            noResults.setVisibility(View.GONE);

        }

    }

}
