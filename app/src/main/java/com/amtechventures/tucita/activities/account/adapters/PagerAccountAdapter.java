package com.amtechventures.tucita.activities.account.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.amtechventures.tucita.activities.account.fragments.bookings.BookingsFragment;

public class PagerAccountAdapter extends FragmentStatePagerAdapter {
    int numOfTabs;

    public PagerAccountAdapter(FragmentManager fm, int NumOfTabs) {

        super(fm);

        this.numOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment current;

        switch (position) {

            case 0:
                current= new BookingsFragment();

                break;

            default:
                current= new BookingsFragment();

                break;
        }
        return current;
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}