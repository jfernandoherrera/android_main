package com.amtechventures.tucita.activities.date.select.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.amtechventures.tucita.activities.date.select.SelectHourFragment;
import com.amtechventures.tucita.model.domain.venue.Venue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;


public class PagerSelectHourAdapter extends FragmentStatePagerAdapter {

    public final Calendar calendar = Calendar.getInstance();
    public Calendar calendarOneMonthMore = Calendar.getInstance();
    public Calendar calendarTwoMonthMore = Calendar.getInstance();
    private int currentDay;
    private int daysToEndMonth;
    private Venue venue;
    int durationHours;
    int durationMinutes;
    SelectHourAdapter.OnSlotSelected listener;

    public PagerSelectHourAdapter(FragmentManager fm, SelectHourAdapter.OnSlotSelected listener) {

        super(fm);

        this.listener = listener;

        calendarOneMonthMore.add(Calendar.MONTH, 1);

        calendarTwoMonthMore.add(Calendar.MONTH, 2);

        currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        daysToEndMonth = (getLastDayOfMonth(0) - currentDay);
    }

    public void setVenue(Venue venue){

        this.venue = venue;
    }

    public int getCurrentDay(){

        return currentDay;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public int getLastDayOfMonth(int month){

        int lastDay = 0;

        switch (month){

            case 0:

                lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

                break;

            case 1:

                lastDay = calendarOneMonthMore.getActualMaximum(Calendar.DAY_OF_MONTH);

                break;

            case 2:

                lastDay = calendarTwoMonthMore.getActualMaximum(Calendar.DAY_OF_MONTH);

                break;
        }
        return lastDay;
    }

    @Override
    public Fragment getItem(int position) {

            SelectHourFragment selectHourFragment = new SelectHourFragment();

            selectHourFragment.setListener(listener);

            selectHourFragment.setDate(getFragmentDay(position));

            selectHourFragment.setVenue(venue);

            selectHourFragment.setDuration(durationHours, durationMinutes);

            if(position == 0){

                selectHourFragment.setIsFirst(true);
            }

            return selectHourFragment;
    }

    public void setDuration(int durationHours, int durationMinutes){

        this.durationHours = durationHours;

        this.durationMinutes = durationMinutes;
    }

    public Date getFragmentDay(int position){

        TimeZone timezone = TimeZone.getDefault();

        Calendar calendar = new GregorianCalendar(timezone);

         position += currentDay;

        if(position < getLastDayOfMonth(0)){

            calendar.set(Calendar.DAY_OF_MONTH, position);

        }else if(position <= (getLastDayOfMonth(0) + getLastDayOfMonth(1))){

            calendar.set(Calendar.YEAR, calendarOneMonthMore.get(Calendar.YEAR));

            calendar.set(Calendar.MONTH, calendarOneMonthMore.get(Calendar.MONTH));

            calendar.set(Calendar.DAY_OF_MONTH, position - getLastDayOfMonth(0));

        }else{
            calendar.set(Calendar.YEAR, calendarTwoMonthMore.get(Calendar.YEAR));

            calendar.set(Calendar.MONTH, calendarTwoMonthMore.get(Calendar.MONTH));

            calendar.set(Calendar.DAY_OF_MONTH, position - (getLastDayOfMonth(0) + getLastDayOfMonth(1)));
        }

        calendar.set(Calendar.HOUR_OF_DAY, 1);

        calendar.set(Calendar.MINUTE,1);

        return calendar.getTime();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        String title;

        Date date = getFragmentDay(position);

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE");

        dateFormat.setTimeZone(calendar.getTimeZone());

        title = dateFormat.format(date) + "\n" + String.valueOf(date.getDate());

        return title;
    }

    @Override
    public int getCount() {

        return  getLastDayOfMonth(1) + getLastDayOfMonth(2) + daysToEndMonth ;
    }
}
