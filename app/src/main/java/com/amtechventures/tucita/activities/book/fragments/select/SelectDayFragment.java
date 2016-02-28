package com.amtechventures.tucita.activities.book.fragments.select;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.book.fragments.select.adapters.PagerSelectHourAdapter;
import com.amtechventures.tucita.activities.book.fragments.select.adapters.SelectHourAdapter;
import com.amtechventures.tucita.model.domain.blockade.Blockade;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.utils.common.AppFont;
import com.amtechventures.tucita.utils.common.AppTabLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SelectDayFragment extends Fragment {


    private AppTabLayout tabLayout;
    private ViewPager viewPager;
    private PagerSelectHourAdapter adapter;
    private SelectHourAdapter.OnSlotSelected listener;
    private List<Blockade> blockades;

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

        listener = (SelectHourAdapter.OnSlotSelected) context;

    }

    @Override
    public void onDetach() {

        super.onDetach();

        listener = null;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_select_date, container, false);

        tabLayout = (AppTabLayout) rootView.findViewById(R.id.tabs);

        viewPager = (ViewPager) rootView.findViewById(R.id.container);

        AppFont appFont = new AppFont();

        Typeface typeface = appFont.getAppFont(rootView.getContext());

        blockades = new ArrayList<>();

        adapter = new PagerSelectHourAdapter(getChildFragmentManager(), listener, typeface, blockades);

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                position = position + adapter.getCurrentDay();

                if (position <= adapter.getLastDayOfMonth(0)) {

                    setupDateViews(adapter.calendar);

                } else if (position <= (adapter.getLastDayOfMonth(0) + adapter.getLastDayOfMonth(1))) {

                    setupDateViews(adapter.calendarOneMonthMore);

                } else {

                    setupDateViews(adapter.calendarTwoMonthMore);

                }

            }

            @Override
            public void onPageSelected(int position) {}

            @Override
            public void onPageScrollStateChanged(int state) {}

        });

        return rootView;

    }

    public void setBlockades(List<Blockade> blockades) {

        this.blockades.addAll(blockades);

    }

    public void setVenue(Venue venue) {

        adapter.setVenue(venue);

    }

    public void setupDateViews(Calendar calendar) {

        String month = setCurrentMonth(calendar.getTime());

        String year = setCurrentYear(calendar.get(Calendar.YEAR));

        String title = month + " " + year;

        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();

        appCompatActivity.getSupportActionBar().setTitle(title);

    }

    public void backSetupDateViews() {

        setupDateViews(adapter.calendar);

    }

    private String setCurrentMonth(Date date) {

        String month = new java.text.SimpleDateFormat("MMMM").format(date);

        return month;

    }

    private String setCurrentYear(int currentYear) {

        return String.valueOf(currentYear);

    }

    public void reload() {

        adapter.notifyDataSetChanged();

    }

    public void setDuration(int[] duration) {

        adapter.setDuration(duration[0], duration[1]);

    }

}