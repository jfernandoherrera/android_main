package com.amtechventures.tucita.activities.account.fragments.bookings;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.account.adapters.AppointmentsAdapter;
import com.amtechventures.tucita.activities.account.adapters.VenuesAdapter;
import com.amtechventures.tucita.model.context.appointment.AppointmentCompletion;
import com.amtechventures.tucita.model.context.appointment.AppointmentContext;
import com.amtechventures.tucita.model.domain.appointment.Appointment;
import com.amtechventures.tucita.model.domain.user.User;
import com.amtechventures.tucita.model.error.AppError;

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
    private RelativeLayout relativeLayout;
    private Typeface typeface;
    private AppointmentContext appointmentContext;
    private User user;

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

    }

    @Override
    public void onDetach() {

        super.onDetach();

    }

    public void setTypeface(Typeface typeface) {

        this.typeface = typeface;

    }

    public void setUser(User user) {

        this.user = user;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        completedAppointmentsList = new ArrayList<>();

        pendingAppointmentsList = new ArrayList<>();

        appointmentContext = AppointmentContext.context(appointmentContext);

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

        noResults.setTypeface(typeface);

        relativeLayout = (RelativeLayout) rootView.findViewById(R.id.concealer);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            int skip = 4;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                int pastVisibleItems, visibleItemCount, totalItemCount;

                if (dy > 0) {

                    visibleItemCount = layoutManager.getChildCount();

                    totalItemCount = layoutManager.getItemCount();

                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {

                        List<Appointment> appointments = appointmentContext.loadUserAppointments(user, new AppointmentCompletion.AppointmentErrorCompletion() {
                            @Override
                            public void completion(List<Appointment> appointmentList, AppError error) {

                                if (appointmentList != null && !appointmentList.isEmpty()) {

                                    setAppointmentList(appointmentList);

                                }

                            }

                            @Override
                            public void completion(Appointment appointment, AppError error) {

                            }
                        }, skip);

                        if (appointments != null && !appointments.isEmpty()) {

                            setAppointmentList(appointments);

                        }

                        Log.v("...", "Last Item Wow !" + skip);

                        skip += 4;

                    }
                }

                super.onScrolled(recyclerView, dx, dy);

            }

        });

        return rootView;

    }

    public void setAppointmentList(List<Appointment> appointmentList) {

        Calendar calendar = Calendar.getInstance();

        if(completedAppointmentsList == null) {

            completedAppointmentsList = new ArrayList<>();

            pendingAppointmentsList = new ArrayList<>();

        }

        for (Appointment appointment : appointmentList) {

        boolean isBefore = appointment.getDate().before(calendar.getTime());

            if (isBefore) {

                completedAppointmentsList.add(appointment);

            } else {

            pendingAppointmentsList.add(appointment);

            }

        }

        removeCompletedRepeated();

        removePendingRepeated();

        setupList();

    }

    private void removePendingRepeated(){

        List<Appointment> toRemove = new ArrayList();

        int index = 0;

        for(Appointment appointment : pendingAppointmentsList){

            List<Appointment> temp = new ArrayList<>();

            temp.addAll(pendingAppointmentsList);

            temp.remove(index);

            for(Appointment compare : temp){

                String compareId = compare.getObjectId();

                String appointmentId = appointment.getObjectId();

                if(compareId.equals(appointmentId)){

                    toRemove.add(appointment);

                    break;
                }

            }

            index ++;

        }

        pendingAppointmentsList.removeAll(toRemove);
    }

    private void removeCompletedRepeated(){

        List<Appointment> toRemove = new ArrayList();

        int index = 0;

        for(Appointment appointment : completedAppointmentsList){

            List<Appointment> temp = new ArrayList<>();

            temp.addAll(completedAppointmentsList);

            temp.remove(index);

            for(Appointment compare : temp){

                String compareId = compare.getObjectId();

                String appointmentId = appointment.getObjectId();

                if(compareId.equals(appointmentId)){

                    toRemove.add(appointment);

                    break;
                }

            }

            index ++;

        }

        completedAppointmentsList.removeAll(toRemove);
    }

    public void setupList() {

        if (recyclerView != null) {

            relativeLayout.setVisibility(View.GONE);

            boolean noEmpty = !completedAppointmentsList.isEmpty() || !pendingAppointmentsList.isEmpty();

            if (noEmpty) {

                adapter = new AppointmentsAdapter(completedAppointmentsList, pendingAppointmentsList, typeface);

                recyclerView.setAdapter(adapter);

                noResults.setVisibility(View.GONE);

            }

        }else {

        }

    }

}
