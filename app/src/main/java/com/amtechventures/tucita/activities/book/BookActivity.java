package com.amtechventures.tucita.activities.book;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.service.ServiceFragment;
import com.amtechventures.tucita.activities.venue.VenueFragment;

public class BookActivity extends AppCompatActivity implements VenueFragment.OnServiceSelected {

    private VenueFragment venueFragment;
    private ServiceFragment serviceFragment;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_book);

        venueFragment = new VenueFragment();

        serviceFragment = new ServiceFragment();

        setVenueFragment();

        setServiceFragment();

        serviceHide();

        setToolbar();
    }

    private void setVenueFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.layout_main, venueFragment);

        transaction.commit();
    }

    private void setServiceFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.layout_main, serviceFragment);

        transaction.commit();
    }


    private void venueHide(){

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.hide(venueFragment);

        transaction.commit();
    }

    private void venueShow(){

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.show(venueFragment);

        transaction.commit();
    }


    private void serviceHide(){

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.hide(serviceFragment);

        transaction.commit();
    }

    private void serviceShow(){

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.show(serviceFragment);

        transaction.commit();
    }

    private void setToolbar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {

            setSupportActionBar(toolbar);
        }
    }

    public void bookNow(View v) {

        ImageSpan is = new ImageSpan(this, R.mipmap.ic_launcher);

        final Spannable text = new SpannableString("    Had a snack" + " ");

        text.setSpan(is, 0, 4, 0);

        Snackbar snackbar = Snackbar

                .make(findViewById(R.id.layout_main), text, Snackbar.LENGTH_LONG)

                .setAction("Undo", new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                    }
                });

        snackbar.setActionTextColor(Color.RED);

        View snackbarView = snackbar.getView();

        snackbarView.setBackgroundColor(Color.YELLOW);

        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);

        snackbar.setDuration(Snackbar.LENGTH_INDEFINITE);

        textView.setTextColor(Color.BLUE);

        snackbar.show();
    }

    @Override
    public void onServiceSelected(String serviceName) {

        venueHide();

        serviceShow();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        toolbar.inflateMenu((R.menu.menu_back_and_title));

    return true;
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

        if (! venueFragment.isHidden()){

            finish();
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (! venueFragment.isHidden()){

            finish();
        }else {

            serviceHide();

            venueShow();
        }
        return super.onOptionsItemSelected(item);
    }
}