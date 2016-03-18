package com.amtechventures.tucita.activities.advanced;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.advanced.adapters.CityAdapter;
import com.amtechventures.tucita.activities.advanced.fragments.LocationOptionsFragment;
import com.amtechventures.tucita.activities.advanced.fragments.VenuesResultFragment;
import com.amtechventures.tucita.model.context.city.CityCompletion;
import com.amtechventures.tucita.model.context.city.CityContext;
import com.amtechventures.tucita.model.domain.category.CategoryAttributes;
import com.amtechventures.tucita.model.domain.city.City;
import com.amtechventures.tucita.model.error.AppError;
import com.amtechventures.tucita.utils.common.AppFont;
import com.amtechventures.tucita.utils.strings.Strings;
import com.amtechventures.tucita.utils.views.AlertDialogError;
import com.amtechventures.tucita.utils.views.AppEditText;
import com.amtechventures.tucita.utils.views.AppTextView;
import com.amtechventures.tucita.utils.views.AppToolbar;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class AdvancedSearchActivity extends AppCompatActivity  {

    private AppToolbar toolbar;
    private String name;
    private VenuesResultFragment venuesResultFragment;
    private LocationOptionsFragment locationOptionsFragment;
    private Typeface typeface;
    private static final int REQUEST_FINE_LOCATION = 0;
    private ListView listViewCities;
    private CityAdapter citiesAdapter;
    private ArrayList<City> currentCities;
    private CityContext cityContext;
    private AppEditText searchView;
    private RelativeLayout alert;
    private TextView textViewCities;
    private final int minimumToSearch = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        loadPermissions(Manifest.permission.ACCESS_FINE_LOCATION, REQUEST_FINE_LOCATION);

        setContentView(R.layout.activity_advanced_search);

        name = getIntent().getStringExtra(CategoryAttributes.name);

        venuesResultFragment = new VenuesResultFragment();

        locationOptionsFragment = new LocationOptionsFragment();

        searchView = (AppEditText) findViewById(R.id.searchCities);

        searchView.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String text = String.valueOf(searchView.getText());

                if (text.length() < minimumToSearch) {

                  noTypedEnough();

                }

                setupCities(text);

            }

        });

        alert = (RelativeLayout) findViewById(R.id.alert);

        textViewCities = (TextView) findViewById(R.id.textViewCities);

        listViewCities = (ListView) findViewById(R.id.listViewCities);

        cityContext = CityContext.context(cityContext);

        currentCities = new ArrayList<>();

        citiesAdapter = new CityAdapter(getApplicationContext(), R.layout.city_list_item);

        listViewCities.setAdapter(citiesAdapter);

        listViewCities.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

              onCitySelected(currentCities.get(position));

            }

        });

        setLocationOptionsFragment();

        setToolbar();

    }

    public void noTypedEnough() {

     alert.setVisibility(View.VISIBLE);

    }

    public void continueTypying(View view) {

        alert.setVisibility(View.GONE);

    }
    public void setupCities(String like){

        cityContext.loadLikeCities(like, new CityCompletion.ErrorCompletion() {

            @Override
            public void completion(List<City> cities, AppError error) {

                currentCities.clear();

                citiesAdapter.clear();

                if(cities != null && ! cities.isEmpty()){

                    currentCities.addAll(cities);

                    citiesAdapter.addAll(setCitiesToStringsArray());

                    textViewCities.setVisibility(View.VISIBLE);

                    searchView.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.pin_line), null, getResources().getDrawable(R.mipmap.ic_close), null);

                    citiesAdapter.notifyDataSetChanged();

                } else{

                    searchView.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.pin_line), null, null, null);

                    textViewCities.setVisibility(View.GONE);

                }

            }

        });

    }

    private ArrayList<String> setCitiesToStringsArray(){

        ArrayList<String> stringsCities = new ArrayList<>();

        for(City city: currentCities){

            stringsCities.add(city.formattedLocation());

        }

        return stringsCities;

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

    private void showVenuesResultFragment(City city) {

        if(city != null) {

            venuesResultFragment.setCity(city);

        }

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


    private void setToolbar() {

        toolbar = (AppToolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {

            setSupportActionBar(toolbar);

        }

    }

    private void back() {

        if (!venuesResultFragment.isHidden()) {

            finish();

        } else {

            hideLocationOptionsFragment();

            showVenuesResultFragment(null);

        }

    }

    @Override
    public void onBackPressed() {

        back();

    }

    private void setCity(String city){

       searchView.setText(city);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setTitle(name);

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

    public void onCitySelected(City city) {

        setCity(city.formattedLocation());

        setVenuesResultFragment();

        hideLocationOptionsFragment();

        showVenuesResultFragment(city);

    }

}