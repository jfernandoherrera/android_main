package com.amtechventures.tucita.activities.date.select;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.date.select.adapters.SelectHourAdapter;
import com.amtechventures.tucita.model.context.appointment.AppointmentCompletion;
import com.amtechventures.tucita.model.context.appointment.AppointmentContext;
import com.amtechventures.tucita.model.context.openingHour.OpeningHourCompletion;
import com.amtechventures.tucita.model.context.openingHour.OpeningHourContext;
import com.amtechventures.tucita.model.domain.appointment.Appointment;
import com.amtechventures.tucita.model.domain.openingHour.OpeningHour;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.error.AppError;
import com.amtechventures.tucita.utils.strings.Slot;
import com.amtechventures.tucita.utils.views.ViewUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

public class SelectHourFragment extends Fragment{

    Date date;
    OpeningHourContext context;
    int test = 0;
    Venue venue;
    private SelectHourAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    List<Slot> slots;
    int durationHours;
    int durationMinutes;
    int price = 0;
    boolean isFirst = false;
    AppointmentContext appointmentContext;
    int increment = 30;
    boolean finishSlots = false;
    SelectHourAdapter.OnSlotSelected listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_select_hour, container, false);

        context = OpeningHourContext.context(context);

        appointmentContext = AppointmentContext.context(appointmentContext);

        recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);

        layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);

        slots = new ArrayList<>();

        adapter = new SelectHourAdapter(price, slots);
        adapter = new SelectHourAdapter(price, slots, listener, date);

        recyclerView.setAdapter(adapter);

        loadDay(rootView);

        return rootView;
    }

    public void setPrice(int price){

        this.price = price;
    }

    public void setListener(SelectHourAdapter.OnSlotSelected listener) {

        this.listener = listener;
    }

    public void loadDay(final View rootView) {

        TimeZone timezone = TimeZone.getDefault();

        Calendar calendar = new GregorianCalendar(timezone);

        int issueDate = 1900 + date.getYear();

        calendar.set(issueDate, date.getMonth(), date.getDate());

        int day = calendar.get(Calendar.DAY_OF_WEEK);

        context.loadDayOpeningHours(venue, day, new OpeningHourCompletion.OpeningHourErrorCompletion() {
            @Override
            public void completion(List<OpeningHour> openingHourList, AppError error) {

                setup(openingHourList, rootView);
            }
        });
    }

    public void setDuration(int durationHours, int durationMinutes){

        this.durationHours = durationHours;

        this.durationMinutes = durationMinutes;
    }

    public void setIsFirst(boolean first){

        isFirst = first;
    }

    public void setVenue(Venue another){

        venue = another;
    }

    public void setDate(Date date){

        this.date = date;

        if(adapter != null){

            adapter.notifyDataSetChanged();
        }
    }

    private void setupNoSlots(View view){

        TextView textView = (TextView) view.findViewById(R.id.closed);

        textView.setVisibility(View.VISIBLE);

        String sorry = view.getResources().getString(R.string.sorry_there_are_no);

        String please = view.getResources().getString(R.string.pls_select_another_day);

        String test = sorry + " " + date.getDate()  + " " + date.getMonth()+ " " + date.getYear() + " " + please;

        textView.setText(test);

        recyclerView.setVisibility(View.GONE);
    }

    public void setup(List<OpeningHour> openingHoursDay, View view){

        if(openingHoursDay == null || openingHoursDay.isEmpty()){

           setupNoSlots(view);

        }else if(isFirst) {

            setupNoSlots(view);
           // setupSlotsFirst(openingHoursDay, view);
        }else{

            setupSlots(openingHoursDay);
        }
    }

    private void setupSlots(List<OpeningHour> openingHours){

    for (OpeningHour openingHour : openingHours) {

        int startPoint = openingHour.getStartHour();

        int endPoint = openingHour.getEndHour();

        int minutes = openingHour.getStartMinute();

        final int increment = 30;

        ViewUtils viewUtils = new ViewUtils(getContext());

        if (durationMinutes == 0) {

            while (!(startPoint == (endPoint - durationHours) && minutes == increment)) {

                String slotString = viewUtils.hourFormat(startPoint, minutes);

                Slot slot = new Slot(minutes, startPoint, increment, slotString);

                slots.add(slot);

                minutes += increment;

                int[] time = sixtyMinutes(startPoint, minutes);

                startPoint = time[0];

                minutes = time[1];
            }
        } else {

            while (!(startPoint == (endPoint - durationHours - 1) && minutes == increment)) {

                String slotString = viewUtils.hourFormat(startPoint, minutes);

                Slot slot = new Slot(minutes, startPoint, increment, slotString);

                slots.add(slot);

                minutes += increment;

                int[] time = sixtyMinutes(startPoint, minutes);

                startPoint = time[0];

                minutes = time[1];
            }
            String slotString = viewUtils.hourFormat(startPoint, (60 - durationMinutes));

            Slot slot = new Slot(minutes, startPoint, increment, slotString);

            slots.add(slot);

        }
    }
        fromAppointment();
}

    private void fromAppointment(){
        Log.i("veenue", String.valueOf(test));
        test++;
        appointmentContext.loadAppointmentsDateVenue(venue, date, new AppointmentCompletion.AppointmentErrorCompletion() {
            @Override
            public void completion(List<Appointment> appointmentList, AppError error) {

                if (appointmentList == null || appointmentList.isEmpty()) {

                } else {

                    for (Appointment appointment : appointmentList) {

                        TimeZone timezone = TimeZone.getDefault();

                        Calendar calendar = new GregorianCalendar(timezone);

                        Date appointmentDate = appointment.getDate();

                        Log.i("apdate", String.valueOf(appointmentDate.getHours()));

                        int startHour = appointmentDate.getHours();

                        int startMinute = appointmentDate.getMinutes();

                        Log.i(String.valueOf(startHour), String.valueOf(startMinute));

                        int [] duration = appointment.getDuration();

                        boolean isEqualHour;

                        boolean slotMinutesSmallerOrEqualThanAppointmentMinutes;

                        boolean slotMinutesPlusIncrementGreaterThanAppointmentMinutes;

                        boolean slotMinutesSmallerThanAppointmentMinutesPlusDuration;

                        boolean slotMinutesPlusIncrementGreaterThanAppointmentMinutesPlusDuration;

                        List<Slot> toRemove = new ArrayList<>();

                        for (Slot slot : slots) {

                            isEqualHour = slot.getStartHour() == startHour;

                            slotMinutesSmallerOrEqualThanAppointmentMinutes = slot.getStartMinute() <= startMinute;

                            slotMinutesPlusIncrementGreaterThanAppointmentMinutes = slot.getStartMinute() + increment > startMinute;

                            if(duration[0] == 0) {

                                slotMinutesSmallerThanAppointmentMinutesPlusDuration = slot.getStartMinute() < startMinute + duration[1];

                                slotMinutesPlusIncrementGreaterThanAppointmentMinutesPlusDuration = slot.getStartMinute() + increment >= startMinute + duration[1];

                                if (isEqualHour && ((slotMinutesSmallerOrEqualThanAppointmentMinutes && slotMinutesPlusIncrementGreaterThanAppointmentMinutes) || (slotMinutesSmallerThanAppointmentMinutesPlusDuration && slotMinutesPlusIncrementGreaterThanAppointmentMinutesPlusDuration))) {

                                    toRemove.add(slot);
                                }
                            }else{

                                boolean isEqualHourPlusDuration = (startHour + duration[0]) == slot.getStartHour();

                                boolean isThere = slotMinutesSmallerOrEqualThanAppointmentMinutes && slotMinutesPlusIncrementGreaterThanAppointmentMinutes;

                                boolean isGreater = (slot.getStartHour() > startHour ) || (slot.getStartHour() == startHour)&& (slot.getStartMinute() > startMinute);

                                boolean isSmallerThanAppointmentPlusDuration = ((slot.getStartHour() < (appointment.getDateEnd().getHours())) && slot.getStartHour() > startHour) || (slot.getEndHour() == appointment.getDateEnd().getHours() && slot.getEndMinute() < appointment.getDateEnd().getMinutes());

                                boolean contained = isGreater && isSmallerThanAppointmentPlusDuration;

                                if((isEqualHour && isThere) || (isEqualHourPlusDuration && isThere ) || contained){

                                    toRemove.add(slot);
                                }
                            }
                        }
                        slots.removeAll(toRemove);

                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }


    private void setupSlotsFirst(List<OpeningHour> openingHours, View view){

        for (OpeningHour openingHour : openingHours){

            Calendar calendar = Calendar.getInstance();

            int startPoint = calendar.get(Calendar.HOUR_OF_DAY) + 1;

            int endPoint = openingHour.getEndHour();

            int minutes = openingHour.getStartMinute();

            int increment = 30;

            ViewUtils viewUtils = new ViewUtils(getContext());

            if(durationMinutes == 0) {

                if(! (startPoint <= endPoint)){

                while (!(startPoint == (endPoint - durationHours) && minutes ==  increment)){

                    String slotString = viewUtils.hourFormat(startPoint, minutes) ;

                    Slot slot = new Slot(minutes, startPoint, increment, slotString);

                    slots.add(slot);

                    minutes += increment;

                    int[] time = sixtyMinutes(startPoint, minutes);

                    startPoint = time[0];

                    minutes = time[1];
                    }
                }else {

                    setupNoSlots(view);
                }
            }else{
                if(! (startPoint <= endPoint)){

                while (!(startPoint == (endPoint - durationHours - 1) && minutes ==  increment) ){

                    String slotString = viewUtils.hourFormat(startPoint, minutes);

                    Slot slot = new Slot(minutes, startPoint, increment, slotString);

                    slots.add(slot);

                    minutes += increment;

                    int[] time = sixtyMinutes(startPoint, minutes);

                    startPoint = time[0];

                    minutes = time[1];
                }
                String slotString = viewUtils.hourFormat(startPoint, (60 - durationMinutes));

                    Slot slot = new Slot(minutes, startPoint, increment, slotString);

                    slots.add(slot);

                }else {

                    setupNoSlots(view);
                }
            }

        }
        if(slots.isEmpty()){

            setupNoSlots(view);
        }else {

            adapter.notifyDataSetChanged();
        }
    }

    private int[] sixtyMinutes(int hour, int minutes){

        int[] time = new int[2];

        if(minutes < 60){

            time[0] = hour;

            time[1] = minutes;
        }else {

            time[0] = hour + 1;

            minutes = minutes - 60;

            time[1] = minutes;
        }

        return time;
    }

    private void removeAppointmentSlots(){

    }

}
