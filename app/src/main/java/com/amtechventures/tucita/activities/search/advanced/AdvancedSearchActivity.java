package com.amtechventures.tucita.activities.search.advanced;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.model.domain.category.CategoryAttributes;


public class AdvancedSearchActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private String name;
    private VenuesResultFragment venuesResultFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_advanced_search);

        name = getIntent().getStringExtra(CategoryAttributes.name);

        venuesResultFragment = new VenuesResultFragment();

        setVenuesResultFragment();

        showVenuesResultFragment();

        setToolbar();
    }

    private void setVenuesResultFragment(){

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.myDrawView1, venuesResultFragment);

        transaction.commit();
    }

    private void showVenuesResultFragment(){

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.show(venuesResultFragment);

        transaction.commit();
    }


    private void setToolbar(){

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {

            setSupportActionBar(toolbar);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(name);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        finish();

        return super.onOptionsItemSelected(item);
    }
}