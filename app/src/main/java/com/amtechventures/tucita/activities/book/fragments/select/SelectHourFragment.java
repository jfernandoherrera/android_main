package com.amtechventures.tucita.activities.book.fragments.select;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.TypefaceSpan;
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
import com.amtechventures.tucita.utils.common.AppFont;
import com.amtechventures.tucita.utils.views.TuCitaProgressDialog;

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
    private boolean isBlocked;
    private List<String> blocked;
    private TuCitaProgressDialog progress;

    private void setupProgress() {

        if (progress == null) {

            progress = new TuCitaProgressDialog(getContext(),R.style.TuCitaDialogTheme);

            progress.setCancelable(false);

            progress.setIndeterminate(true);

        }

        progress.show();

    }

    public void hideLoading() {

        if (progress != null) {

            progress.dismiss();

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_select_hour, container, false);

        slotContext = SlotContext.context(slotContext);

        setupProgress();

        appointmentContext = AppointmentContext.context(appointmentContext);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);

        slots = new ArrayList<>();

        TextView textView = (TextView) rootView.findViewById(R.id.closed);

        adapter = new SelectHourAdapter(price, slots, listener, date);

        recyclerView.setAdapter(adapter);

        loadDay(rootView);

        return rootView;

    }

    public void setPrice(int price) {

        this.price = price;

    }

    public void setBlocked(List<String> blocked) {

        this.blocked = blocked;

    }

    public void setIsBlocked(boolean isBlocked) {

        this.isBlocked = isBlocked;

        setupNoSlots(getView());

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

        try {

            TextView textView = (TextView) view.findViewById(R.id.closed);

            textView.setVisibility(View.VISIBLE);

            String sorry = view.getResources().getString(R.string.sorry_there_are_no);

            String please = view.getResources().getString(R.string.pls_select_another_day);

            String day = date.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + date.get(Calendar.DAY_OF_MONTH) : date.get(Calendar.DAY_OF_MONTH) + "";

            String month = (date.get(Calendar.MONTH) + 1) < 10 ? "0" + (date.get(Calendar.MONTH) + 1): (date.get(Calendar.MONTH) + 1) + "";

            String dateString = day + "/" + month + "/" + date.get(Calendar.YEAR);

            String test = sorry + " " + dateString + ". " + please;

            SpannableStringBuilder builder = new SpannableStringBuilder(test);

            builder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccentText)), sorry.length(), sorry.length() + dateString.length() + 1, 0);

            builder.setSpan (new TypefaceSpan("bold"), sorry.length(), sorry.length() + dateString.length() + 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

            textView.setText(builder);

            recyclerView.setVisibility(View.GONE);

            hideLoading();

        } catch (NullPointerException e){

            e.printStackTrace();

        }

    }

    public void setup(List<Slot> slotsDay, View view) {

        slots.clear();

        slots.addAll(slotsDay);

        if (slotsDay == null || slotsDay.isEmpty() || isBlocked) {

            setupNoSlots(view);

        } else if (isFirst) {

            setupNoSlots(view);

        } else {

            if(blocked != null) {

                for(String objectId : blocked) {

                    for(Slot slot : slots) {

                        if(slot.getObjectId().equals(objectId)) {

                            slots.remove(slot);

                            break;

                        }

                    }

                }

            }

            adapter.notifyDataSetChanged();

            slotsSetAmount();

            setupSlots();

        }

    }

    private void slotsSetAmount() {

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

                    if (slots.isEmpty()) {

                        setupNoSlots(getView());

                    }

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

                                slot.decrementAmount();

                                boolean isFull = slot.getAmount() <= 0;

                                if (isFull) {

                                    indexFirst.add(slots.indexOf(slot));

                                    toRemove.add(slot);

                                }

                            }

                        }

                    }

                    if (slots.isEmpty()) {

                        setupNoSlots(getView());

                    } else {
                        removeSlotsForDurationAfterAppointments(indexFirst, toRemove);

                        int index = slots.size() - 1 >= 0 ? slots.size() - 1 : 0;

                        if (slots.isEmpty()) {

                            setupNoSlots(getView());

                        } else {

                            removeSlotsForDuration(index);

                        }

                    }

                }

            }

            @Override
            public void completion(Appointment appointment, AppError error) {

            }
        });

    }

    private void removeSlotsForDuration(int slotsEnd){

        int durationMinutesToRemove = durationMinutes + (durationHours * 60);

        int indexInt = slotsEnd;

        List<Slot> toRemove = new ArrayList<>();

        while ( durationMinutesToRemove > 0) {

            int durationSlot = slots.get(indexInt).getDurationMinutes();

            durationMinutesToRemove -= durationSlot;

            if(durationMinutesToRemove > 0){

                toRemove.add(slots.get(indexInt));

            }

            indexInt--;

            if (indexInt == -1) {

                break;

            }

        }

            slots.removeAll(toRemove);

        if (slots.isEmpty()) {

            setupNoSlots(getView());
        }

        adapter.notifyDataSetChanged();

        hideLoading();

    }

    private void removeSlotsForDurationAfterAppointments(List<Integer> indexList, List<Slot> toRemove) {

        for (Integer index : indexList) {

            index--;

            int durationMinutesToRemove = durationMinutes + (durationHours * 60);

            while (index >= 0 && durationMinutesToRemove > 0) {

                int durationSlot = slots.get(index).getDurationMinutes();

                durationMinutesToRemove -= durationSlot;

                if(durationMinutesToRemove > 0){

                    toRemove.add(slots.get(index));

                }

                index--;

            }

        }

        slots.removeAll(toRemove);

        adapter.notifyDataSetChanged();

        hideLoading();

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
