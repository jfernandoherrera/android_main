package com.amtechventures.tucita.activities.book.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.model.domain.service.Service;
import java.util.List;

public class ServicesToBookAdapter extends RecyclerView.Adapter<ServicesToBookAdapter.ViewHolder>{

    List<Service> serviceList;

    public ServicesToBookAdapter(List<Service> services){

        serviceList = services;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewHolder viewHolder;

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_basket, parent, false);

            viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Service service = serviceList.get(position);

        holder.textName.setText(service.getName());

        holder.textDuration.setText(service.getDurationInfo());

        holder.textPricesFrom.setText(String.valueOf(service.getPrice()));
    }

    @Override
    public int getItemCount() {

        return serviceList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView textName;

        protected TextView textDuration;

        protected TextView textPricesFrom;

        public ViewHolder(View itemView) {

            super(itemView);

            textName = (TextView) itemView.findViewById(R.id.textName);

            textDuration = (TextView) itemView.findViewById(R.id.textDuration);

            textPricesFrom = (TextView) itemView.findViewById(R.id.textPrice);
        }
    }
}
