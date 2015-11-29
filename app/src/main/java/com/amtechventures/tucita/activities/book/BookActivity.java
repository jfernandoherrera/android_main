package com.amtechventures.tucita.activities.book;

import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.service.ServiceFragment;
import com.amtechventures.tucita.activities.venue.VenueFragment;
import com.amtechventures.tucita.utils.views.ShoppingCarView;

public class BookActivity extends AppCompatActivity implements VenueFragment.OnServiceSelected {

    private VenueFragment venueFragment;
    private ServiceFragment serviceFragment;
    private Toolbar toolbar;
    private ShoppingCarView shoppingCarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_book);

        shoppingCarView = (ShoppingCarView) findViewById(R.id.shoppingCar);

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

        if(android.os.Build.VERSION.SDK_INT >= 16) {

            shoppingCarView.animate().translationY(shoppingCarView.getHeight()).withEndAction(new Runnable() {
                @Override
                public void run() {
                    shoppingCarView.setVisibility(View.VISIBLE);

                    shoppingCarView.animate().translationY(0).setDuration(1000);
                }
            }).setDuration(0);


        }else {

            shoppingCarView.setVisibility(View.VISIBLE);

        }

        venueFragment.setMarginBottomToShoppingCarVisible(shoppingCarView.getHeight());

        serviceFragment.setMarginBottomToShoppingCarVisible( shoppingCarView.getHeight());
    }

    public void book(View v) {

        if(android.os.Build.VERSION.SDK_INT >= 16) {

            shoppingCarView.animate().translationY(shoppingCarView.getHeight()).withEndAction(new Runnable() {
                @Override
                public void run() {

                    shoppingCarView.setVisibility(View.GONE);
                }
            });
        }else{

            shoppingCarView.setVisibility(View.GONE);
        }

        venueFragment.setMarginBottomToShoppingCarGone();

        serviceFragment.setMarginBottomToShoppingCarGone();
    }

    @Override
    public void onServiceSelected(String serviceName) {

        venueHide();

        serviceShow();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        return true;
    }

    private void back(){

        if (! venueFragment.isHidden()){

            finish();
        }else {

            serviceHide();

            venueShow();
        }
    }

    @Override
    public void onBackPressed() {

        back();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        back();

        return super.onOptionsItemSelected(item);
    }
}