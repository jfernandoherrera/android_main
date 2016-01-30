package com.amtechventures.tucita.activities.account.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.appointment.AppointmentActivity;
import com.amtechventures.tucita.activities.appointment.fragments.details.AppointmentDetailsFragment;
import com.amtechventures.tucita.model.domain.appointment.Appointment;
import java.util.List;

public class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsAdapter.ViewHolder> {

    List<Appointment> completedItems;
    List<Appointment> pendingItems;
    Typeface typeface;

    public AppointmentsAdapter(List<Appointment> appointments, List<Appointment> pendingAppointments, Typeface typeface) {

        super();

        this.typeface = typeface;

        completedItems = appointments;

        pendingItems = pendingAppointments;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_appointment, viewGroup, false);

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

            String formattedDate = appointment.getDate().toLocaleString();

            viewHolder.date.setText(formattedDate);

        } else {

            position = position - pendingItems.size();

            final Appointment appointment = completedItems.get(position);

            String venueName = appointment.getVenue().getName();

            viewHolder.name.setText(venueName);

            String formattedDate = appointment.getDate().toLocaleString();

            viewHolder.date.setText(formattedDate);

        }
    }

    @Override
    public int getItemCount() {

        return completedItems.size() + pendingItems.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder{

        protected TextView name;

        protected TextView date;

        protected String objectId;

        public ViewHolder(final View itemView) {

            super(itemView);

            itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {

                        v.setBackgroundResource(R.drawable.pressed_application_background_static);

                    } else if (event.getAction() != MotionEvent.ACTION_MOVE) {

                        v.setBackgroundColor(Color.TRANSPARENT);

                    }

                    return false;
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    AppointmentActivity.goToDetails(v.getContext(), objectId);

                }
            });

            name = (TextView) itemView.findViewById(R.id.textName);

            date = (TextView) itemView.findViewById(R.id.textDate);

            name.setTypeface(typeface);

            date.setTypeface(typeface);

        }

    }




}
