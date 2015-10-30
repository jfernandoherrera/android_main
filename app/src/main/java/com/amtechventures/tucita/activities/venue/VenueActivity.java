package com.amtechventures.tucita.activities.venue;

import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.amtechventures.tucita.R;

public class VenueActivity extends AppCompatActivity implements ReviewFragment.OnFragmentInteractionListener,

        TopServicesFragment.OnFragmentInteractionListener,

        AboutFragment.OnFragmentInteractionListener,

        FullMenuFragment.OnFragmentInteractionListener,

        OpeningHoursFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_venue);

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
