package com.amtechventures.tucita.activities.account.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.model.domain.appointment.Appointment;
import java.util.List;

public class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsAdapter.ViewHolder> {

    List<Appointment> completedItems;
    List<Appointment> pendingItems;
    OnReview listener;

    public interface OnReview {

        void onReview(Appointment appointment);

    }

    public AppointmentsAdapter(List<Appointment> appointments, List<Appointment> pendingAppointments, OnReview listener) {

        super();

        this.listener = listener;

        completedItems = appointments;

        pendingItems = pendingAppointments;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_user_appointment, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        if(position < pendingItems.size()) {

            Appointment appointment = pendingItems.get(position);

            String venueName = appointment.getVenue().getName();

            viewHolder.name.setText(venueName);

            String formattedDate = appointment.getDate().toLocaleString();

            viewHolder.date.setText(formattedDate);

            viewHolder.button.setVisibility(View.INVISIBLE);

            viewHolder.ratingBar.setVisibility(View.INVISIBLE);

        } else {

            position = position - pendingItems.size();

            final Appointment appointment = completedItems.get(position);

            String venueName = appointment.getVenue().getName();

            viewHolder.name.setText(venueName);

            String formattedDate = appointment.getDate().toLocaleString();

            viewHolder.date.setText(formattedDate);

            viewHolder.button.setVisibility(View.VISIBLE);

            viewHolder.button.setBackgroundResource(R.mipmap.ic_comment);

            viewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onReview(appointment);
                }
            });

        }
    }

    @Override
    public int getItemCount() {

        return completedItems.size() + pendingItems.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder{

        protected TextView name;

        protected TextView date;

        protected Button button;

        protected RatingBar ratingBar;


        public ViewHolder(final View itemView) {

            super(itemView);

            name = (TextView) itemView.findViewById(R.id.textName);

            date = (TextView) itemView.findViewById(R.id.textDate);

            button = (Button) itemView.findViewById(R.id.buttonReview);

            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);

        }

    }

}
