package com.amtechventures.tucita.activities.account.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.model.domain.appointment.Appointment;
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

            viewHolder.button.setVisibility(View.GONE);

        } else {

            position = position - pendingItems.size();

            Appointment appointment = completedItems.get(position);

            String venueName = appointment.getVenue().getName();

            viewHolder.name.setText(venueName);

            String formattedDate = appointment.getDate().toLocaleString();

            viewHolder.date.setText(formattedDate);

            viewHolder.button.setVisibility(View.VISIBLE);

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


        public ViewHolder(final View itemView) {

            super(itemView);

            name = (TextView) itemView.findViewById(R.id.textName);

            date = (TextView) itemView.findViewById(R.id.textDate);

            button = (Button) itemView.findViewById(R.id.buttonReview);
        }

    }

}
