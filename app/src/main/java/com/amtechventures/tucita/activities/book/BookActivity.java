package com.amtechventures.tucita.activities.book;


import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.book.fragments.checkout.SecureCheckoutFragment;
import com.amtechventures.tucita.activities.book.fragments.select.SelectDayFragment;
import com.amtechventures.tucita.activities.book.fragments.select.adapters.SelectHourAdapter;
import com.amtechventures.tucita.activities.login.LoginActivity;
import com.amtechventures.tucita.activities.book.fragments.service.ServiceFragment;
import com.amtechventures.tucita.activities.book.fragments.venue.VenueFragment;
import com.amtechventures.tucita.model.context.slot.SlotContext;
import com.amtechventures.tucita.model.domain.service.Service;
import com.amtechventures.tucita.model.domain.slot.Slot;
import com.amtechventures.tucita.utils.views.ShoppingCarView;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class BookActivity extends AppCompatActivity implements VenueFragment.OnServiceSelected,
        ServiceFragment.OnServiceSelected, ShoppingCarView.OnItemClosed,
        ShoppingCarView.OnMoreServices, ShoppingCarView.OnBookNow, SelectHourAdapter.OnSlotSelected, SecureCheckoutFragment.OnPlaceOrder{

    private VenueFragment venueFragment;
    private ServiceFragment serviceFragment;
    private SelectDayFragment selectDateFragment;
    private SecureCheckoutFragment secureCheckoutFragment;
    private Toolbar toolbar;
    private ShoppingCarView shoppingCarView;
    private Slot toLock;
    private SlotContext slotContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_book);

        shoppingCarView = (ShoppingCarView) findViewById(R.id.shoppingCar);

        venueFragment = new VenueFragment();

        serviceFragment = new ServiceFragment();

        selectDateFragment = new SelectDayFragment();

        secureCheckoutFragment = new SecureCheckoutFragment();

        slotContext = SlotContext.context(slotContext);

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

    private void setSecureCheckoutFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.layout_main, secureCheckoutFragment);

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

    private void secureCheckoutHide() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.hide(secureCheckoutFragment);

        transaction.commit();
    }

    private void secureCheckoutShow() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.show(secureCheckoutFragment);

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

        serviceFragment.setup(service, exists);

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
        }else if(! selectDateFragment.isHidden()){

            shoppingCarView.showView();

            selectDateHide();

            venueShow();
        }else  if(! secureCheckoutFragment.isHidden()){

           secureCheckoutHide();

           selectDateShow();

           selectDateFragment.backSetupDateViews();
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

        selectDateFragment.setDuration(shoppingCarView.getDuration());

        selectDateFragment.reload();

        selectDateShow();

        shoppingCarView.hideView();
    }

    public void goToLogin() {

        Intent intent = new Intent(BookActivity.this, LoginActivity.class);

        startActivity(intent);

    }

    private void setLockSlot(Slot slot){

        toLock = slot;

        slotContext.lockSlot(slot, new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if(e == null){

                    secureCheckoutShow();

                    secureCheckoutFragment.setup();
                }
            }
        });

    }

    @Override
    public void onSlotSelected(Slot slot) {

        if (ParseUser.getCurrentUser() == null) {

            goToLogin();

        } else {

            selectDateHide();

            secureCheckoutFragment.setDate(slot.getDate());

            secureCheckoutFragment.setDuration(shoppingCarView.getDurationInMinutes());

            secureCheckoutFragment.setVenue(venueFragment.getVenue());

            secureCheckoutFragment.setServices(shoppingCarView.getServicesToBook());

            if(! secureCheckoutFragment.isAdded()){

                setSecureCheckoutFragment();
            }else{

                secureCheckoutFragment.setupAppointmentView();
            }

            setLockSlot(slot);
        }
    }

    @Override
    public void onPlaceOrder() {

        if(slotContext.isLocked(toLock)){

            secureCheckoutFragment.placeOrder();
        }else{

            back();
        }
    }
}

