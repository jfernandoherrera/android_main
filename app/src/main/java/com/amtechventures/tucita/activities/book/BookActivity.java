package com.amtechventures.tucita.activities.book;


import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.service.ServiceFragment;
import com.amtechventures.tucita.activities.venue.VenueFragment;
import com.amtechventures.tucita.model.domain.service.Service;
import com.amtechventures.tucita.utils.views.ShoppingCarView;

public class BookActivity extends AppCompatActivity implements VenueFragment.OnServiceSelected, ShoppingCarView.OnCarClicked {

    private VenueFragment venueFragment;
    private ServiceFragment serviceFragment;
    private Toolbar toolbar;
    private ShoppingCarView shoppingCarView;
    private boolean plus = false;


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

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {

        book();

        super.onPostCreate(savedInstanceState);
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

        if(!plus) {

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

                shoppingCarView.animate().translationY(shoppingCarView.getHeight()).withEndAction(new Runnable() {
                    @Override
                    public void run() {

                        shoppingCarView.setVisibility(View.VISIBLE);

                        shoppingCarView.animate().translationY(0);
                    }
                });

            } else {

                shoppingCarView.setVisibility(View.VISIBLE);

            }

            serviceFragment.checkImage();

            venueFragment.setMarginBottomToShoppingCarVisible(shoppingCarView.getHeight());

            serviceFragment.setMarginBottomToShoppingCarVisible(shoppingCarView.getHeight());

            plus = true;

        }else{

            book();
        }

    }

    public void book() {

        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

            shoppingCarView.animate().translationY(shoppingCarView.getHeight()).withEndAction(new Runnable() {

                @Override
                public void run() {

                    shoppingCarView.setVisibility(View.GONE);
                }
            });
        }else{

            shoppingCarView.setVisibility(View.GONE);
        }

        serviceFragment.plusImage();

        venueFragment.setMarginBottomToShoppingCarGone();

        serviceFragment.setMarginBottomToShoppingCarGone();

        plus = false;
    }

    @Override
    public void onServiceSelected(Service service, View view) {

        serviceFragment.setService(service);

        venueHide();

        serviceShow();

        view.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        return true;
    }

    private void back(){

        if (! venueFragment.isHidden()){

            venueFragment.cancelQuery();

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

    @Override
    public void onCarClicked() {

    }
}