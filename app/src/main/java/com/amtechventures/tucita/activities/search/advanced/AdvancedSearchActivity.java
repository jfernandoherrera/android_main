package com.amtechventures.tucita.activities.search.advanced;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.search.advanced.fragments.LocationOptionsFragment;
import com.amtechventures.tucita.activities.search.advanced.fragments.VenuesResultFragment;
import com.amtechventures.tucita.model.domain.category.CategoryAttributes;
import com.amtechventures.tucita.model.domain.city.City;

public class AdvancedSearchActivity extends AppCompatActivity implements LocationOptionsFragment.OnCitySelected{

    private Toolbar toolbar;
    private String name;
    private VenuesResultFragment venuesResultFragment;
    private LocationOptionsFragment locationOptionsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_advanced_search);

        name = getIntent().getStringExtra(CategoryAttributes.name);

        venuesResultFragment = new VenuesResultFragment();

        locationOptionsFragment = new LocationOptionsFragment();

        setLocationOptionsFragment();

        setVenuesResultFragment();

        showVenuesResultFragment();

        hideLocationOptionsFragment();

        setToolbar();
    }

    private void setVenuesResultFragment(){

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.myDrawView1, venuesResultFragment);

        transaction.commit();
    }

    private void setLocationOptionsFragment(){

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.myDrawView1, locationOptionsFragment);

        transaction.commit();
    }

    private void showVenuesResultFragment(){

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.show(venuesResultFragment);

        transaction.commit();
    }

    private void hideVenuesResultFragment(){

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.hide(venuesResultFragment);

        transaction.commit();
    }

    private void showLocationOptionsFragment(){

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.show(locationOptionsFragment);

        transaction.commit();
    }

    private void hideLocationOptionsFragment(){

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.hide(locationOptionsFragment);

        transaction.commit();
    }

    private void setToolbar(){

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {

            setSupportActionBar(toolbar);
        }
    }

    private void back(){

        if (! venuesResultFragment.isHidden()){

            finish();
        }else {

            hideLocationOptionsFragment();

           showVenuesResultFragment();
        }
    }

    @Override
    public void onBackPressed() {

        back();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(name);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        back();

        return super.onOptionsItemSelected(item);
    }

    public void onLocationClicked(final View view) {

        showLocationOptionsFragment();

        hideVenuesResultFragment();
    }

    @Override
    public void onCitySelected(City city) {

        venuesResultFragment.setupList(city);

        hideLocationOptionsFragment();

        showVenuesResultFragment();
    }
}