package com.amtechventures.tucita.activities.date.select;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.date.select.adapters.PagerSelectHourAdapter;
import com.amtechventures.tucita.model.domain.venue.Venue;
import java.util.Calendar;
import java.util.Date;


public class SelectDayFragment extends Fragment{


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PagerSelectHourAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_select_date, container, false);

        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);

        viewPager = (ViewPager) rootView.findViewById(R.id.container);

        adapter = new PagerSelectHourAdapter(getChildFragmentManager());

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                position = position + adapter.getCurrentDay();

                if(position <= adapter.getLastDayOfMonth(0)){

                    setupDateViews(adapter.calendar);

                }else if(position <= (adapter.getLastDayOfMonth(0) + adapter.getLastDayOfMonth(1))){

                    setupDateViews(adapter.calendarOneMonthMore);

                }else{

                    setupDateViews(adapter.calendarTwoMonthMore);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return rootView;
    }

    public void setVenue(Venue venue) {

       adapter.setVenue(venue);
    }

    public void setupDateViews(Calendar calendar){

        String month = setCurrentMonth(calendar.getTime());

        String year = setCurrentYear(calendar.get(Calendar.YEAR));

        String title = month + " " + year;

        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();

        appCompatActivity.getSupportActionBar().setTitle(title);
    }

    private String setCurrentMonth(Date date){

        String month =  new java.text.SimpleDateFormat("MMMM").format(date);

        return month;
    }

    private String setCurrentYear(int currentYear){

        return String.valueOf(currentYear);
    }

    public void reload(){

        adapter.notifyDataSetChanged();
        }

    public void setDuration(int[] duration) {

        adapter.setDuration(duration[0], duration[1]);

    }
}


