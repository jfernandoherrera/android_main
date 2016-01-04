package com.amtechventures.tucita.activities.book.fragments.select.adapters;


import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.model.domain.slot.Slot;
import java.util.Calendar;
import java.util.List;

public class SelectHourAdapter extends RecyclerView.Adapter<SelectHourAdapter.ViewHolder>{

    private List<Slot> slots;
    private int price;
    private OnSlotSelected listener;
    private Calendar date;

    public interface OnSlotSelected{

        void onSlotSelected(Slot slot);
    }

    public SelectHourAdapter(int price, List<Slot> slots, OnSlotSelected listener, Calendar date){

        this.date = date;

        this.listener = listener;

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

        holder.position = position;

        holder.textHour.setText(slots.get(position).getFormattedHour());

        holder.textPricesFrom.setText(String.valueOf(price));
    }

    @Override
    public int getItemCount() {

        return slots.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView textHour;

        protected TextView textPricesFrom;

        protected int position;

        public ViewHolder(View itemView) {

            super(itemView);

            textHour = (TextView) itemView.findViewById(R.id.textHour);

            textPricesFrom = (TextView) itemView.findViewById(R.id.textPrice);

            textPricesFrom.setVisibility(View.GONE);

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
                                        }

            );

                itemView.setOnClickListener(new View.OnClickListener()

                                            {
                                                @Override
                                                public void onClick(View v) {

                                                    date.set(Calendar.HOUR_OF_DAY, slots.get(position).getStartHour());

                                                    date.set(Calendar.MINUTE, slots.get(position).getStartMinute());

                                                    date.set(Calendar.SECOND, 0);

                                                    slots.get(position).setDate(date);

                                                    listener.onSlotSelected(slots.get(position));
                                                }
                                            }

                );
            }
        }
}
