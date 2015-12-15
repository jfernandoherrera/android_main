package com.amtechventures.tucita.activities.book;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.date.select.SelectDayFragment;
import com.amtechventures.tucita.activities.service.ServiceFragment;
import com.amtechventures.tucita.activities.venue.VenueFragment;
import com.amtechventures.tucita.model.domain.service.Service;
import com.amtechventures.tucita.utils.views.ShoppingCarView;

public class BookActivity extends AppCompatActivity implements VenueFragment.OnServiceSelected, ServiceFragment.OnServiceSelected, ShoppingCarView.OnItemClosed, ShoppingCarView.OnMoreServices, ShoppingCarView.OnBookNow {

    private VenueFragment venueFragment;
    private ServiceFragment serviceFragment;
    private SelectDayFragment selectDateFragment;
    private Toolbar toolbar;
    private ShoppingCarView shoppingCarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_book);

        shoppingCarView = (ShoppingCarView) findViewById(R.id.shoppingCar);

        venueFragment = new VenueFragment();

        serviceFragment = new ServiceFragment();

        selectDateFragment = new SelectDayFragment();

        shoppingCarView.hideList();

        setToolbar();

        setServiceFragment();

        setSelectDateFragment();

        setVenueFragment();

        serviceHide();

        selectDateHide();

        shoppingCarView.hideView();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {

        unBook();

        super.onPostCreate(savedInstanceState);
    }


    private void setVenueFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.layout_main, venueFragment);

        transaction.commit();

        venueTitle();
    }

    private void setServiceFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.layout_main, serviceFragment);

        transaction.commit();
    }

    private void setSelectDateFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.layout_main, selectDateFragment);

        transaction.commit();
    }

    private void venueHide() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.hide(venueFragment);

        transaction.commit();
    }

    private void venueShow() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.show(venueFragment);

        transaction.commit();

        venueTitle();
    }

    private void venueTitle(){

        String venue = getString(R.string.venue);

        getSupportActionBar().setTitle(venue);
    }

    private void serviceHide() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.hide(serviceFragment);

        transaction.commit();

    }

    private void serviceShow() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.show(serviceFragment);

        transaction.commit();

        String serviceName = serviceFragment.getService().getName();

        getSupportActionBar().setTitle(serviceName);
    }

    private void selectDateHide() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.hide(selectDateFragment);

        transaction.commit();

    }

    private void selectDateShow() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.show(selectDateFragment);

        transaction.commit();
    }

    private void setToolbar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {

            setSupportActionBar(toolbar);
        }
    }


    @Override
    public void onServiceSelected(Service service, View view) {

        boolean exists = shoppingCarView.alreadyExistsService(service);

        serviceFragment.setService(service, exists);

        venueHide();

        serviceShow();

        view.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        return true;
    }

    private void back() {

        if (shoppingCarView.listIsVisible()) {

            shoppingCarView.hideList();

        } else if (! venueFragment.isHidden()) {

            venueFragment.cancelQuery();

            finish();

        } else if(! serviceFragment.isHidden()){

            serviceHide();

            venueShow();
        }else {

            shoppingCarView.showView();

            selectDateHide();

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
    public void onServiceBook(Service service) {

        boolean plus = serviceFragment.getServiceState();

        if (plus) {

            shoppingCarView.addService(service);

            serviceFragment.setServiceState(false);

            serviceFragment.checkImage();

            marginCame();
        } else {

            shoppingCarView.removeService(service);

            unBook();
        }
    }

    private void unBook() {

        serviceFragment.setServiceState(true);

        serviceFragment.checkImage();

        marginGone();
    }

    private void marginCame(){

        venueFragment.setMarginBottomToShoppingCarVisible(shoppingCarView.getHeight());

        serviceFragment.setMarginBottomToShoppingCarVisible(shoppingCarView.getHeight());
    }

    private void marginGone(){

        if (shoppingCarView.isEmpty()) {

            venueFragment.setMarginBottomToShoppingCarGone();

            serviceFragment.setMarginBottomToShoppingCarGone();
        }
    }

    @Override
    public void onItemClosed(Service service) {

        if (!serviceFragment.isHidden() && serviceFragment.getService().equals(service)) {

            serviceFragment.setServiceState(true);

            serviceFragment.checkImage();
        }
        marginGone();
    }

    @Override
    public void onMoreServices() {

        if (!serviceFragment.isHidden()) {

            serviceHide();

            venueShow();
        }
    }

    @Override
    public void onBookNow() {
        if (!serviceFragment.isHidden()) {

            serviceHide();
        }

        if (!venueFragment.isHidden()) {

            venueHide();
        }

        if (shoppingCarView.listIsVisible()) {

            shoppingCarView.hideList();
        }

        selectDateFragment.setVenue(venueFragment.getVenue());

        selectDateShow();

        shoppingCarView.hideView();
    }
}