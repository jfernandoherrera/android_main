package com.amtechventures.tucita.activities.date.select;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amtechventures.tucita.R;

import java.util.Calendar;

public class SelectDayFragment extends Fragment{

    private final int daysInAWeek = 7;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_select_date, container, false);

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);

        setupDateViews(tabLayout);


        return rootView;
    }

    private void setupDateViews(TabLayout tabLayout){

        Calendar calendar = Calendar.getInstance();

        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        for(int count = 0; count < daysInAWeek; count++ ){

            tabLayout.addTab(tabLayout.newTab().setText(String.valueOf(currentDay)));

            currentDay ++;
        }

        String month = setCurrentMonth(calendar.get(Calendar.MONTH));

        String year = setCurrentYear(calendar.get(Calendar.YEAR));

        String title = month + " " + year;

        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();

        appCompatActivity.getSupportActionBar().setTitle(title);
    }

    private String setCurrentMonth(int currentMonth){

        String month =  new java.text.SimpleDateFormat("MMMM").format(currentMonth);

        return month;
    }

    private String setCurrentYear(int currentYear){

        return String.valueOf(currentYear);
    }

    }
