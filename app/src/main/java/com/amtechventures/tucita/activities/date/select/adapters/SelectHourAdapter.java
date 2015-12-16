package com.amtechventures.tucita.activities.date.select.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amtechventures.tucita.R;

import java.util.List;

public class SelectHourAdapter extends RecyclerView.Adapter<SelectHourAdapter.ViewHolder>{

    private List<String> slots;
    private int price;

    public SelectHourAdapter(int price, List<String> slots){

        this.slots = slots;

        this.price = price;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewHolder viewHolder;

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_hour, parent, false);

        viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.textHour.setText(slots.get(position));

        holder.textPricesFrom.setText(String.valueOf(price));
    }

    @Override
    public int getItemCount() {

        return slots.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView textHour;

        protected TextView textPricesFrom;

        public ViewHolder(View itemView) {

            super(itemView);

            textHour = (TextView) itemView.findViewById(R.id.textHour);

            textPricesFrom = (TextView) itemView.findViewById(R.id.textPrice);

            textPricesFrom.setVisibility(View.GONE);
        }
    }
}
