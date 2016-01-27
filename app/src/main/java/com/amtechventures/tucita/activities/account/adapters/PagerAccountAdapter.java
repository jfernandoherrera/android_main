package com.amtechventures.tucita.activities.account.adapters;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.amtechventures.tucita.activities.account.fragments.bookings.BookingsFragment;
import com.amtechventures.tucita.activities.account.fragments.venues.VenuesFragment;

public class PagerAccountAdapter extends FragmentStatePagerAdapter {

    int numOfTabs;
    BookingsFragment bookingsFragment;
    VenuesFragment venuesFragment;


    public PagerAccountAdapter(FragmentManager fm, int NumOfTabs, Typeface typeface) {

        super(fm);

        this.numOfTabs = NumOfTabs;

        bookingsFragment = new BookingsFragment();

        venuesFragment = new VenuesFragment();

        bookingsFragment.setTypeface(typeface);

        venuesFragment.setTypeface(typeface);

    }

    @Override
    public Fragment getItem(int position) {

        Fragment current;

        switch (position) {

            case 0:

                current = bookingsFragment;

                break;

            default:
                current = venuesFragment;

                break;
        }

        return current;

    }

    @Override
    public int getCount() {

        return numOfTabs;

    }

}