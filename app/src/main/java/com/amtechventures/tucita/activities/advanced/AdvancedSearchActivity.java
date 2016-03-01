package com.amtechventures.tucita.activities.advanced;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.advanced.fragments.LocationOptionsFragment;
import com.amtechventures.tucita.activities.advanced.fragments.VenuesResultFragment;
import com.amtechventures.tucita.model.domain.category.CategoryAttributes;
import com.amtechventures.tucita.model.domain.city.City;
import com.amtechventures.tucita.utils.common.AppFont;

import java.lang.reflect.Field;

public class AdvancedSearchActivity extends AppCompatActivity implements LocationOptionsFragment.OnCitySelected {

    private Toolbar toolbar;
    private String name;
    private VenuesResultFragment venuesResultFragment;
    private LocationOptionsFragment locationOptionsFragment;
    private Typeface typeface;
    private static final int REQUEST_FINE_LOCATION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        loadPermissions(Manifest.permission.ACCESS_FINE_LOCATION, REQUEST_FINE_LOCATION);

        setContentView(R.layout.activity_advanced_search);

        name = getIntent().getStringExtra(CategoryAttributes.name);

        venuesResultFragment = new VenuesResultFragment();

        locationOptionsFragment = new LocationOptionsFragment();

        AppFont appFont = new AppFont();

        typeface = appFont.getAppFont(getApplicationContext());

        venuesResultFragment.setTypeface(typeface);

        locationOptionsFragment.setTypeface(typeface);

        setLocationOptionsFragment();

        setVenuesResultFragment();

        showVenuesResultFragment();

        hideLocationOptionsFragment();

        setToolbar();

    }

    private void loadPermissions(String perm,int requestCode) {

        if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {

            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, perm)) {

                ActivityCompat.requestPermissions(this, new String[]{perm}, requestCode);

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case REQUEST_FINE_LOCATION: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // granted
                }

                else{

                    venuesResultFragment.onStop();

                }

                return;

            }

        }

    }

    private void setVenuesResultFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.myDrawView1, venuesResultFragment);

        transaction.commit();

    }

    private void setLocationOptionsFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.myDrawView1, locationOptionsFragment);

        transaction.commit();

    }

    private void showVenuesResultFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.show(venuesResultFragment);

        transaction.commit();

    }

    private void hideVenuesResultFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.hide(venuesResultFragment);

        transaction.commit();

    }

    private void showLocationOptionsFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.show(locationOptionsFragment);

        transaction.commit();

    }

    private void hideLocationOptionsFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.hide(locationOptionsFragment);

        transaction.commit();

    }

    private TextView getActionBarTextView() {

        TextView titleTextView = null;

        String defaultNameTitleMenu = "mTitleTextView";

        try {

            Field field = toolbar.getClass().getDeclaredField(defaultNameTitleMenu);

            field.setAccessible(true);

            titleTextView = (TextView) field.get(toolbar);

        } catch (NoSuchFieldException e) {

        } catch (IllegalAccessException e) {

        }

        return titleTextView;

    }

    private void setToolbar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {

            setSupportActionBar(toolbar);

        }

    }

    private void back() {

        if (!venuesResultFragment.isHidden()) {

            finish();

        } else {

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

        getActionBarTextView().setTypeface(typeface);

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