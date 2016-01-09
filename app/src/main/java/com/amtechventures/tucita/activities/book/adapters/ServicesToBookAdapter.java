package com.amtechventures.tucita.activities.book.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.model.domain.service.Service;

import java.util.List;

public class ServicesToBookAdapter extends RecyclerView.Adapter<ServicesToBookAdapter.ViewHolder> {

    private List<Service> serviceList;

    private OnItemClosed listener;

    public interface OnItemClosed {

        void onItemClosed(Service service);
    }

    public ServicesToBookAdapter(List<Service> services, OnItemClosed onItemClosed) {

        listener = onItemClosed;

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

        final Service service = serviceList.get(position);

        holder.textName.setText(service.getName());

        holder.textDuration.setText(service.getDurationInfo());

        holder.textPricesFrom.setText(String.valueOf(service.getPrice()));

        holder.close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                listener.onItemClosed(service);

            }

        });

    }

    @Override
    public int getItemCount() {

        return serviceList.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView textName;

        protected TextView textDuration;

        protected TextView textPricesFrom;

        protected ImageView close;

        public ViewHolder(View itemView) {

            super(itemView);

            textName = (TextView) itemView.findViewById(R.id.textName);

            textDuration = (TextView) itemView.findViewById(R.id.textDuration);

            textPricesFrom = (TextView) itemView.findViewById(R.id.textPrice);

            close = (ImageView) itemView.findViewById(R.id.image);

            close.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {

                        close.setImageResource(R.mipmap.ic_close_pressed);

                    } else if (event.getAction() != MotionEvent.ACTION_MOVE) {

                        close.setImageResource(R.mipmap.ic_close);

                    }

                    return false;

                }
            });

        }

    }

}
