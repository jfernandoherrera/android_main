package com.amtechventures.tucita.activities.account.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.appointment.AppointmentActivity;
import com.amtechventures.tucita.model.domain.appointment.Appointment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsAdapter.ViewHolder> {

    List<Appointment> completedItems;
    List<Appointment> pendingItems;

    public AppointmentsAdapter(List<Appointment> appointments, List<Appointment> pendingAppointments) {

        super();

        completedItems = appointments;

        pendingItems = pendingAppointments;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.appointment_view, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        if(position < pendingItems.size()) {

            Appointment appointment = pendingItems.get(position);

            viewHolder.objectId = appointment.getObjectId();

            String venueName = appointment.getVenue().getName();

            viewHolder.name.setText(venueName);

            Date date = appointment.getDate();

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM d/y");

            String dateString = simpleDateFormat.format(date);

            simpleDateFormat = new SimpleDateFormat("hh:mm a");

            String hour = simpleDateFormat.format(date);

            viewHolder.date.setText(dateString);

            viewHolder.hour.setText(hour);

        } else {

            position = position - pendingItems.size();

            final Appointment appointment = completedItems.get(position);

            String venueName = appointment.getVenue().getName();

            viewHolder.name.setText(venueName);

            Date date = appointment.getDate();

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM d/y");

            String dateString = simpleDateFormat.format(date);

            simpleDateFormat = new SimpleDateFormat("hh:mm a");

            String hour = simpleDateFormat.format(date);

            viewHolder.date.setText(dateString);

            viewHolder.hour.setText(hour);

        }
    }

    @Override
    public int getItemCount() {

        return completedItems.size() + pendingItems.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder{

        protected TextView name;

        protected TextView date;

        protected TextView hour;

        protected String objectId;

        public ViewHolder(final View itemView) {

            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    AppointmentActivity.goToDetails(v.getContext(), objectId);

                }
            });

            name = (TextView) itemView.findViewById(R.id.textName);

            date = (TextView) itemView.findViewById(R.id.textDate);

            hour = (TextView) itemView.findViewById(R.id.textHour);


        }

    }




}
