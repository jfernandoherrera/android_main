package com.amtechventures.tucita.activities.date.select;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.date.select.adapters.SelectHourAdapter;
import com.amtechventures.tucita.model.context.openingHour.OpeningHourCompletion;
import com.amtechventures.tucita.model.context.openingHour.OpeningHourContext;
import com.amtechventures.tucita.model.domain.openingHour.OpeningHour;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.error.AppError;
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
    Venue venue;
    private SelectHourAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    List<String> slots;
    int price = 0;
    boolean isFirst = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_select_hour, container, false);

        context = OpeningHourContext.context(context);

        recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);

        layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);

        slots = new ArrayList<>();

        adapter = new SelectHourAdapter(price, slots);

        recyclerView.setAdapter(adapter);

        TimeZone timezone = TimeZone.getDefault();

        Calendar calendar = new GregorianCalendar(timezone);

        calendar.set(date.getYear(), date. getMonth(), date.getDate());

        int day = calendar.get(Calendar.DAY_OF_WEEK);

        context.loadDayOpeningHours(venue, day, new OpeningHourCompletion.OpeningHourErrorCompletion() {
            @Override
            public void completion(List<OpeningHour> openingHourList, AppError error) {

                setup(openingHourList, rootView);
            }
        });
        return rootView;
    }

    public void setPrice(int price){

        this.price = price;
    }

    public void setIsFirst(boolean first){

        isFirst = first;
    }

    public void setVenue(Venue another){

        venue = another;
    }

    public void setDate(Date date){

        this.date = date;
    }

    public void setup(List<OpeningHour> openingHoursDay, View view){

        if(openingHoursDay == null || openingHoursDay.isEmpty()){

            TextView textView = (TextView) view.findViewById(R.id.closed);

            textView.setVisibility(View.VISIBLE);

            String test = textView.getText() + " " + date.getDate()  + " " + date.getMonth()+ " " + date.getYear() + " " ;

            textView.setText(test);

            recyclerView.setVisibility(View.GONE);

        }else if(isFirst) {

            setupSlotsFirst(openingHoursDay);
        }else{

            setupSlots(openingHoursDay);
        }
    }

    private void setupSlots(List<OpeningHour> openingHours){

        for (OpeningHour openingHour : openingHours){

          int startPoint =  openingHour.getStartHour();

          int endPoint = openingHour.getEndHour();

          int minutes = openingHour.getStartMinute();

          while (startPoint < endPoint){

                ViewUtils viewUtils = new ViewUtils(getContext());

                String slot = viewUtils.hourFormat(startPoint, minutes) ;

                slots.add(slot);

                minutes += 30;

                int[] time = sixtyMinutes(startPoint, minutes);

                startPoint = time[0];

                minutes = time[1];
          }
        }
        adapter.notifyDataSetChanged();
    }

    private void setupSlotsFirst(List<OpeningHour> openingHours){

        for (OpeningHour openingHour : openingHours){

            Calendar calendar = Calendar.getInstance();

            int startPoint = calendar.get(Calendar.HOUR_OF_DAY) ;

            int endPoint = openingHour.getEndHour();

            int minutes = openingHour.getStartMinute();

            while (startPoint < endPoint){

                ViewUtils viewUtils = new ViewUtils(getContext());

                String slot = viewUtils.hourFormat(startPoint, minutes) ;

                slots.add(slot);

                minutes += 30;

                int[] time = sixtyMinutes(startPoint, minutes);

                startPoint = time[0];

                minutes = time[1];
            }
        }
        adapter.notifyDataSetChanged();
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
}
