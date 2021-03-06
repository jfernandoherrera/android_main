package com.amtechventures.tucita.activities.book.fragments.select.adapters;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.Log;

import com.amtechventures.tucita.activities.book.fragments.select.SelectHourFragment;
import com.amtechventures.tucita.model.domain.blockade.Blockade;
import com.amtechventures.tucita.model.domain.blockade.BlockadeAttributes;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.utils.views.CustomSpanTypeface;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.TimeZone;

public class PagerSelectHourAdapter extends FragmentStatePagerAdapter {

    public final Calendar calendar = Calendar.getInstance();
    public Calendar calendarOneMonthMore = Calendar.getInstance();
    public Calendar calendarTwoMonthMore = Calendar.getInstance();
    private int currentDay;
    private int daysToEndMonth;
    private Venue venue;
    private int durationHours;
    private int durationMinutes;
    private SelectHourAdapter.OnSlotSelected listener;
    private Typeface typeface;
    private List<Blockade> blockades;

    public PagerSelectHourAdapter(FragmentManager fm, SelectHourAdapter.OnSlotSelected listener, Typeface typeface, List<Blockade> blockades) {

        super(fm);

        this.listener = listener;

        this.typeface = typeface;

        this.blockades = blockades;

        calendarOneMonthMore.add(Calendar.MONTH, 1);

        calendarTwoMonthMore.add(Calendar.MONTH, 2);

        currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        daysToEndMonth = (getLastDayOfMonth(0) - currentDay);

    }

    public void setVenue(Venue venue) {

        this.venue = venue;

    }

    public int getCurrentDay() {

        return currentDay;

    }

    public int getLastDayOfMonth(int month) {

        int lastDay = 0;

        switch (month) {

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

        final int dateBug = 1900;

        Calendar fragmentDate = getFragmentDay(position);

        selectHourFragment.setDate(fragmentDate);

        selectHourFragment.setVenue(venue);

        selectHourFragment.setTypeface(typeface);

        for(Blockade blockade : blockades) {

            if(blockade.getType().equals(BlockadeAttributes.typeDays)) {

                List<Date> datesArray = blockade.getDates();

                for(Date date : datesArray) {

                    boolean isSame = fragmentDate.get(Calendar.MONTH) == date.getMonth() && fragmentDate.get(Calendar.DAY_OF_MONTH) == date.getDate() && calendar.get(Calendar.YEAR) == (date.getYear() + dateBug);

                    if(isSame) {

                        selectHourFragment.setIsBlocked(true);

                        break;

                    }

                }

            } else {

                Date date = blockade.getDate();

                boolean isSame = fragmentDate.get(Calendar.MONTH) == date.getMonth() && fragmentDate.get(Calendar.DAY_OF_MONTH) == date.getDate() && calendar.get(Calendar.YEAR) == (date.getYear() + dateBug);

                if ( isSame ){

                    selectHourFragment.setBlocked(blockade.getDataArray());

                }
            }

        }

        selectHourFragment.setDuration(durationHours, durationMinutes);

        if (position == 0) {

            selectHourFragment.setIsFirst(true);

        }

        return selectHourFragment;

    }

    public void setDuration(int durationHours, int durationMinutes) {

        this.durationHours = durationHours;

        this.durationMinutes = durationMinutes;

    }

    public Calendar getFragmentDay(int position) {

        TimeZone timezone = TimeZone.getDefault();

        Calendar calendar = new GregorianCalendar(timezone);

        position += currentDay;

        if (position < getLastDayOfMonth(0)) {

            calendar.set(Calendar.YEAR, this.calendar.get(Calendar.YEAR));

            calendar.set(Calendar.DAY_OF_MONTH, position);

        } else if (position <= (getLastDayOfMonth(0) + getLastDayOfMonth(1))) {

            calendar.set(Calendar.YEAR, calendarOneMonthMore.get(Calendar.YEAR));

            calendar.set(Calendar.MONTH, calendarOneMonthMore.get(Calendar.MONTH));

            calendar.set(Calendar.DAY_OF_MONTH, position - getLastDayOfMonth(0));

        } else {

            calendar.set(Calendar.YEAR, calendarTwoMonthMore.get(Calendar.YEAR));

            calendar.set(Calendar.MONTH, calendarTwoMonthMore.get(Calendar.MONTH));

            calendar.set(Calendar.DAY_OF_MONTH, position - (getLastDayOfMonth(0) + getLastDayOfMonth(1)));

        }

        calendar.set(Calendar.HOUR_OF_DAY, 1);

        calendar.set(Calendar.MINUTE, 1);

        return calendar;

    }

    @Override
    public CharSequence getPageTitle(int position) {

        String title;

        Calendar date = getFragmentDay(position);

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE", Locale.getDefault());

        title = dateFormat.format(date.getTime()) + "\n" + String.valueOf(date.get(Calendar.DAY_OF_MONTH));

        return title;

    }

    @Override
    public int getCount() {

        return getLastDayOfMonth(1) + getLastDayOfMonth(2) + daysToEndMonth;

    }

}
