package com.amtechventures.tucita.activities.account.fragments.bookings;

import android.app.ProgressDialog;
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
import com.amtechventures.tucita.activities.account.adapters.PagerAccountAdapter;
import com.amtechventures.tucita.model.context.appointment.AppointmentCompletion;
import com.amtechventures.tucita.model.context.appointment.AppointmentContext;
import com.amtechventures.tucita.model.context.user.UserContext;
import com.amtechventures.tucita.model.domain.appointment.Appointment;
import com.amtechventures.tucita.model.domain.user.User;
import com.amtechventures.tucita.model.error.AppError;
import com.parse.ParseUser;

import java.util.List;

public class BookingsFragment extends Fragment{

    private ProgressDialog progress;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private AppointmentContext appointmentContext;
    private TextView noResults;
    private UserContext userContext;
    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
    }

    @Override
    public void onDetach() {

        super.onDetach();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        appointmentContext = AppointmentContext.context(appointmentContext);

        userContext = UserContext.context(userContext);

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_bookings, container, false);

        recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);

        noResults = (TextView) rootView.findViewById(R.id.noResults);

        layoutManager = new GridLayoutManager(getContext(), 1);

        recyclerView.setLayoutManager(layoutManager);

        setupList();

    return rootView;
    }

    public void setupList(){

        User user = userContext.currentUser();

        appointmentContext.loadPendingAppointments(user, new AppointmentCompletion.AppointmentErrorCompletion() {
        @Override
        public void completion(List<Appointment> appointmentList, AppError error) {

            if (appointmentList != null && !appointmentList.isEmpty()){

                adapter = new AppointmentsAdapter(appointmentList);

                recyclerView.setAdapter(adapter);

                noResults.setVisibility(View.GONE);
            }


        }
    });
    }
}
