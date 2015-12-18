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

    public PagerSelectHourAdapter(FragmentManager fm) {

        super(fm);

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

       Date date = new Date();

         position += currentDay;

        if(position <= getLastDayOfMonth(0)){

            date.setYear(calendar.get(Calendar.YEAR));

            date.setMonth(calendar.get(Calendar.MONTH));

            date.setDate(position);

        }else if(position <= (getLastDayOfMonth(0) + getLastDayOfMonth(1))){

            date.setYear(calendarOneMonthMore.get(Calendar.YEAR));

            date.setMonth(calendarOneMonthMore.get(Calendar.MONTH));

            date.setDate(position - getLastDayOfMonth(0));

        }else{
            date.setYear(calendarTwoMonthMore.get(Calendar.YEAR));

            date.setMonth(calendarTwoMonthMore.get(Calendar.MONTH));

            date.setDate(position - (getLastDayOfMonth(0) + getLastDayOfMonth(1)));

        }
        return date;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        String title;
        TimeZone timezone = TimeZone.getDefault();

        Calendar calendar = new GregorianCalendar(timezone);

        Date date = getFragmentDay(position);

        calendar.set(date.getYear(), date. getMonth(), date.getDate());

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE");

        dateFormat.setTimeZone(calendar.getTimeZone());

        title = dateFormat.format(calendar.getTime()) + "\n" + String.valueOf(date.getDate());

        return title;
    }

    @Override
    public int getCount() {

        return  getLastDayOfMonth(1) + getLastDayOfMonth(2) + daysToEndMonth ;
    }
}