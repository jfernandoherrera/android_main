package com.amtechventures.tucita.activities.date.select;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.model.context.openingHour.OpeningHourCompletion;
import com.amtechventures.tucita.model.context.openingHour.OpeningHourContext;
import com.amtechventures.tucita.model.domain.openingHour.OpeningHour;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.error.AppError;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class SelectHourFragment extends Fragment{

    Date date;
    List<OpeningHour> openingHoursDay;
    OpeningHourContext context;
    Venue venue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_select_hour, container, false);

        context = OpeningHourContext.context(context);

        return rootView;
    }

    public void setVenue(Venue another){

        venue = another;
    }

    @Override
    public void onStart() {

        super.onStart();

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        int day = calendar.get(Calendar.DAY_OF_WEEK);

        context.loadDayOpeningHours(venue, day, new OpeningHourCompletion.OpeningHourErrorCompletion() {
            @Override
            public void completion(List<OpeningHour> openingHourList, AppError error) {

            }
        });

        setupSlots(null);

    }

    public void setDate(Date date){

        this.date = date;
    }

    public void setupSlots(List<OpeningHour> openingHoursDay){

        if(openingHoursDay == null){

            TextView textView = (TextView) getView().findViewById(R.id.closed);

            textView.setVisibility(View.VISIBLE);

            String test = textView.getText() + " " + date.getDate()  + " " + date.getMonth()+ " " + date.getYear() + " " ;

            textView.setText(test);
        }
    }
}
