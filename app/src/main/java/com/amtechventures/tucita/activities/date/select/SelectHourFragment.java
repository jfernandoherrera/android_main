package com.amtechventures.tucita.activities.date.select;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.model.domain.openingHour.OpeningHour;

import java.util.List;

public class SelectHourFragment extends Fragment{

    int day, month, year;

    List<OpeningHour> openingHoursDay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_select_hour, container, false);


        return rootView;
    }

    @Override
    public void onStart() {

        super.onStart();

        setupSlots(null);

    }

    public void setupSlots(List<OpeningHour> openingHoursDay){

        if(openingHoursDay == null){

            TextView textView = (TextView) getView().findViewById(R.id.closed);

            textView.setVisibility(View.VISIBLE);
        }
    }


    }
