package com.amtechventures.tucita.activities.date.select.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.amtechventures.tucita.activities.date.select.SelectHourFragment;
import com.amtechventures.tucita.model.domain.category.Category;

import java.util.Calendar;


public class PagerSelectHourAdapter extends FragmentPagerAdapter{

    private final int totalDays = 90;
    private final int daysInAWeek = 7;
    public final Calendar calendar = Calendar.getInstance();
    public Calendar calendarOneMonthMore = Calendar.getInstance();
    public  Calendar calendarTwoMonthMore = Calendar.getInstance();

    public PagerSelectHourAdapter(FragmentManager fm) {

        super(fm);

        calendarOneMonthMore.add(Calendar.MONTH, 1);

        calendarTwoMonthMore.add(Calendar.MONTH, 2);
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

            return selectHourFragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        String title;

        position = position + 1;

        if(position <= getLastDayOfMonth(0)){

            title = String.valueOf(position);

        }else if(position <= (getLastDayOfMonth(0) + getLastDayOfMonth(1))){

            title = String.valueOf(position - getLastDayOfMonth(0));

        }else{

            title = String.valueOf(position - (getLastDayOfMonth(0) + getLastDayOfMonth(1)));

        }

        return title;
    }

    @Override
    public int getCount() {

        return totalDays;
    }
}
