package com.amtechventures.tucita.activities.book.fragments.select;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.book.fragments.select.adapters.SelectHourAdapter;
import com.amtechventures.tucita.model.context.appointment.AppointmentCompletion;
import com.amtechventures.tucita.model.context.appointment.AppointmentContext;
import com.amtechventures.tucita.model.context.slot.SlotCompletion;
import com.amtechventures.tucita.model.context.slot.SlotContext;
import com.amtechventures.tucita.model.domain.appointment.Appointment;
import com.amtechventures.tucita.model.domain.slot.Slot;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.error.AppError;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SelectHourFragment extends Fragment {

    private Calendar date;
    private Venue venue;
    private SlotContext slotContext;
    private SelectHourAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Slot> slots;
    private int durationHours;
    private int durationMinutes;
    private int price = 0;
    private boolean isFirst = false;
    private AppointmentContext appointmentContext;
    private SelectHourAdapter.OnSlotSelected listener;
    private Typeface typeface;
    private RelativeLayout relativeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_select_hour, container, false);

        slotContext = SlotContext.context(slotContext);

        appointmentContext = AppointmentContext.context(appointmentContext);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);

        slots = new ArrayList<>();

        TextView textView = (TextView) rootView.findViewById(R.id.closed);

        textView.setTypeface(typeface);

        relativeLayout = (RelativeLayout) rootView.findViewById(R.id.concealer);

        adapter = new SelectHourAdapter(price, slots, listener, date, typeface);

        recyclerView.setAdapter(adapter);

        loadDay(rootView);

        return rootView;

    }

    public void setTypeface(Typeface typeface) {

        this.typeface = typeface;

    }

    public void setPrice(int price) {

        this.price = price;

    }

    public void setListener(SelectHourAdapter.OnSlotSelected listener) {

        this.listener = listener;

    }

    public void loadDay(final View rootView) {

        try {

            final int day = date.get(Calendar.DAY_OF_WEEK);

            slotContext.loadDaySlots(venue, day, new SlotCompletion.SlotErrorCompletion() {

                        @Override
                        public void completion(List<Slot> slotList, AppError error) {

                            setup(slotList, rootView);

                        }

                    }

            );
        }catch (NullPointerException e){

        }

    }

    public void setDuration(int durationHours, int durationMinutes) {

        this.durationHours = durationHours;

        this.durationMinutes = durationMinutes;

    }

    public void setIsFirst(boolean first) {

        isFirst = first;

    }

    public void setVenue(Venue another) {

        venue = another;

    }

    public void setDate(Calendar date) {

        this.date = date;

        if (adapter != null) {

            adapter.notifyDataSetChanged();

        }

    }

    private void setupNoSlots(View view) {

        TextView textView = (TextView) view.findViewById(R.id.closed);

        textView.setVisibility(View.VISIBLE);

        String sorry = view.getResources().getString(R.string.sorry_there_are_no);

        String please = view.getResources().getString(R.string.pls_select_another_day);

        String test = sorry + " " + date.get(Calendar.DAY_OF_MONTH) + " " + date.get(Calendar.MONTH) + " " + date.get(Calendar.YEAR) + " " + please;

        textView.setText(test);

        recyclerView.setVisibility(View.GONE);

        relativeLayout.setVisibility(View.GONE);

    }

    public void setup(List<Slot> slotsDay, View view) {

        if (slotsDay == null || slotsDay.isEmpty()) {

            setupNoSlots(view);

        } else if (isFirst) {

            setupNoSlots(view);

        } else {

            slots.clear();

            slots.addAll(slotsDay);

            adapter.notifyDataSetChanged();

            slots();

            setupSlots();

        }

    }

    private void slots() {

        for (Slot slot : slots) {

            slot.setAmount();

        }

    }

    private void setupSlots() {

        removeSlotsForAppointments();

    }

    private void removeSlotsForAppointments() {

        appointmentContext.loadAppointmentsDateVenue(venue, date, new AppointmentCompletion.AppointmentErrorCompletion() {
            @Override
            public void completion(List<Appointment> appointmentList, AppError error) {

                if (appointmentList == null || appointmentList.isEmpty()) {

                    int slotEnd = slots.size() - 1;

                    removeSlotsForDuration(slotEnd);

                } else {

                    List<Slot> toRemove = new ArrayList<>();

                    ArrayList<Integer> indexFirst = new ArrayList<>();

                    for (Appointment appointment : appointmentList) {

                        Date appointmentDate = appointment.getDate();

                        int startHour = appointmentDate.getHours();

                        int startMinute = appointmentDate.getMinutes();

                        int[] duration = appointment.getDuration();

                        int[] endTime = sixtyMinutes(startHour + duration[0], startMinute + duration[1]);

                        for (Slot slot : slots) {

                            boolean isFirst = slot.isSmallerOrEqual(startHour, startMinute) && slot.endIsGreater(startHour, startMinute);

                            boolean contained = slot.isGreater(startHour, startMinute) && slot.endIsSmaller(endTime[0], endTime[1]);

                            boolean isLast = slot.isSmaller(endTime[0], endTime[1]) && slot.endIsGreaterOrEqual(endTime[0], endTime[1]);

                            if (isFirst || contained || isLast) {

                                if (isFirst) {

                                    indexFirst.add(slots.indexOf(slot));

                                }

                                slot.decrementAmount();

                                if (slot.getAmount() <= 0) {

                                    toRemove.add(slot);

                                }

                            }

                        }

                    }

                    removeSlotsForDurationAfterAppointments(indexFirst, toRemove);

                    if (slots.isEmpty()) {

                        setupNoSlots(getView());

                    }

                }

            }

            @Override
            public void completion(Appointment appointment, AppError error) {

            }
        });

    }

    private void removeSlotsForDuration(int slotsEnd){

        int durationHoursToRemove = durationHours;

        int durationMinutesToRemove = durationMinutes;

        int indexInt = slotsEnd;

        List<Slot> toRemove = new ArrayList<>();

        while ( !(durationHoursToRemove <= 0 && durationMinutesToRemove <= 0)) {

            durationHoursToRemove -= slots.get(indexInt).getDuration()[0];

            durationMinutesToRemove -= slots.get(indexInt).getDuration()[1];

            slots.get(indexInt).decrementAmount();

            if (slots.get(indexInt).getAmount() <= 0) {

                toRemove.add(slots.get(indexInt));

            }

            indexInt--;

        }

        slots.removeAll(toRemove);

        adapter.notifyDataSetChanged();

        relativeLayout.setVisibility(View.GONE);

    }

    private void removeSlotsForDurationAfterAppointments(List<Integer> indexList, List<Slot> toRemove) {

        for (Integer index : indexList) {

            index--;

            int durationHoursToRemove = durationHours;

            int durationMinutesToRemove = durationMinutes;

            while (index >= 0 && !(durationHoursToRemove <= 0 && durationMinutesToRemove <= 0)) {

                int indexInt = index;

                durationHoursToRemove -= slots.get(indexInt).getDuration()[0];

                durationMinutesToRemove -= slots.get(indexInt).getDuration()[1];

                slots.get(indexInt).decrementAmount();

                if (slots.get(indexInt).getAmount() <= 0) {

                    toRemove.add(slots.get(indexInt));

                }

                index--;

            }

        }

        slots.removeAll(toRemove);

        adapter.notifyDataSetChanged();

        relativeLayout.setVisibility(View.GONE);

    }

    private int[] sixtyMinutes(int hour, int minutes) {

        int[] time = new int[2];

        if (minutes < 60) {

            time[0] = hour;

            time[1] = minutes;

        } else {

            time[0] = hour + 1;

            minutes = minutes - 60;

            time[1] = minutes;

        }

        return time;

    }

}
