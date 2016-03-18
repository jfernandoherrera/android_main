package com.amtechventures.tucita.activities.book;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.book.fragments.checkout.SecureCheckoutFragment;
import com.amtechventures.tucita.activities.book.fragments.select.SelectDayFragment;
import com.amtechventures.tucita.activities.book.fragments.select.adapters.SelectHourAdapter;
import com.amtechventures.tucita.activities.login.LoginActivity;
import com.amtechventures.tucita.activities.book.fragments.service.ServiceFragment;
import com.amtechventures.tucita.activities.book.fragments.venue.VenueFragment;
import com.amtechventures.tucita.activities.splash.SplashActivity;
import com.amtechventures.tucita.model.context.appointment.AppointmentCompletion;
import com.amtechventures.tucita.model.context.blockade.BlockadeCompletion;
import com.amtechventures.tucita.model.context.blockade.BlockadeContext;
import com.amtechventures.tucita.model.context.user.UserCompletion;
import com.amtechventures.tucita.model.context.user.UserContext;
import com.amtechventures.tucita.model.domain.appointment.Appointment;
import com.amtechventures.tucita.model.domain.appointmentVenue.AppointmentVenue;
import com.amtechventures.tucita.model.domain.blockade.Blockade;
import com.amtechventures.tucita.model.domain.service.Service;
import com.amtechventures.tucita.model.domain.slot.Slot;
import com.amtechventures.tucita.model.domain.user.User;
import com.amtechventures.tucita.model.error.AppError;
import com.amtechventures.tucita.utils.common.AppFont;
import com.amtechventures.tucita.utils.views.AlertDialogError;
import com.amtechventures.tucita.utils.views.ShoppingCarView;

import java.lang.reflect.Field;
import java.util.List;

public class BookActivity extends AppCompatActivity implements VenueFragment.OnServiceSelected,
        ServiceFragment.OnServiceSelected, ShoppingCarView.OnItemClosed,
        ShoppingCarView.OnMoreServices, ShoppingCarView.OnBookNow, SelectHourAdapter.OnSlotSelected, SecureCheckoutFragment.OnPlaceOrder, ShoppingCarView.OnHide {

    private VenueFragment venueFragment;
    private ServiceFragment serviceFragment;
    private SelectDayFragment selectDateFragment;
    private SecureCheckoutFragment secureCheckoutFragment;
    private Toolbar toolbar;
    private ShoppingCarView shoppingCarView;
    private UserContext userContext;
    private RelativeLayout relativeLayout;
    private BlockadeContext blockadeContext;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_book);

        shoppingCarView = (ShoppingCarView) findViewById(R.id.shoppingCar);

        venueFragment = new VenueFragment();

        serviceFragment = new ServiceFragment();

        selectDateFragment = new SelectDayFragment();

        secureCheckoutFragment = new SecureCheckoutFragment();

        userContext = UserContext.context(userContext);

        blockadeContext = BlockadeContext.context(blockadeContext);

        shoppingCarView.hideList();

        setToolbar();

        setServiceFragment();

        setSelectDateFragment();

        setVenueFragment();

        setSecureCheckoutFragment();

        serviceHide();

        selectDateHide();

        secureCheckoutHide();

        shoppingCarView.hideView();

        relativeLayout = (RelativeLayout) findViewById(R.id.concealer);

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

        secureCheckoutFragment.setup();

    }

    private void venueShow() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.show(venueFragment);

        transaction.commit();

        venueTitle();

    }

    private void venueTitle() {

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

        serviceShow();

        venueHide();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        return true;

    }

    private void goToMain(){

        Intent intent = new Intent(this, SplashActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);

        finish();

    }

    private void back() {

        if (shoppingCarView.listIsVisible()) {

            shoppingCarView.hideList();

        } else if (!venueFragment.isHidden()) {

            venueFragment.cancelQuery();

            goToMain();

        } else if (!serviceFragment.isHidden()) {

            serviceHide();

            venueShow();

        } else if (!selectDateFragment.isHidden()) {

            shoppingCarView.showView();

            selectDateHide();

            venueShow();

        } else if (!secureCheckoutFragment.isHidden()) {

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

    private void marginCame() {

        venueFragment.setMarginBottomToShoppingCarVisible(shoppingCarView.getHeight());

        serviceFragment.setMarginBottomToShoppingCarVisible(shoppingCarView.getHeight());

    }

    private void marginGone() {

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

        blockadeContext.getBlockadeFromVenue(venueFragment.getVenue(), new BlockadeCompletion.ErrorCompletion() {

            @Override
            public void completion(List<Blockade> blockadeList, AppError error) {

                selectDateFragment.setBlockades(blockadeList);

                selectDateShow();

                shoppingCarView.hideView();

            }
        });

    }

    public void goToLogin() {

        Intent intent = new Intent(BookActivity.this, LoginActivity.class);

        intent.putExtra(BookActivity.class.getName(), true);

        startActivity(intent);

    }

    @Override
    public void onSlotSelected(Slot slot) {

      user = userContext.currentUser();

        if (user == null) {

            goToLogin();

        } else {

            selectDateHide();

            secureCheckoutFragment.setUser(user);

            secureCheckoutFragment.setDate(slot.getDate());

            secureCheckoutFragment.setDuration(shoppingCarView.getDurationInMinutes());

            secureCheckoutFragment.setVenue(venueFragment.getVenue());

            secureCheckoutFragment.setServices(shoppingCarView.getServicesToBook());

            secureCheckoutShow();

        }

    }

    @Override
    public void onPlaceOrder() {

            secureCheckoutFragment.placeOrder(new AppointmentCompletion.AppointmentErrorCompletion() {

                @Override
                public void completion(List<Appointment> appointmentList, AppError error) {

                }

                @Override
                public void completion(Appointment appointment, AppError error) {

                    if (error != null) {

                        AlertDialogError alertDialogError = new AlertDialogError();

                        alertDialogError.noAvailableSlot(getApplicationContext());

                    } else {

                        AppointmentVenue appointmentVenue = userContext.getAppointmentVenue(venueFragment.getVenue(), user);

                        if(appointmentVenue != null) {

                            transactionSuccessful();

                        } else {

                            appointmentVenue = new AppointmentVenue();

                            appointmentVenue.putVenue(venueFragment.getVenue());

                            userContext.putAppointmentVenue(appointmentVenue, user, new UserCompletion.UserErrorCompletion() {

                                @Override
                                public void completion(User user, AppError error) {

                                    if (error == null) {

                                        transactionSuccessful();

                                    } else {

                                        AlertDialogError alertDialogError = new AlertDialogError();

                                        alertDialogError.noAvailableSlot(getApplicationContext());

                                    }

                                }

                            });

                        }

                    }

                }

            });

    }

    public void transactionSuccessful() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(

                BookActivity.this);

        String continueString = getString(R.string.continue_option);

        String successful = getString(R.string.successful_transaction);

        String message = getString(R.string.app_name);

        alertDialogBuilder.setTitle(successful)

                .setPositiveButton(continueString, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        goToMain();

                    }
                }).setCancelable(false)

                .setMessage(message).show();

    }

    @Override
    public void onHide() {

        relativeLayout.setVisibility(View.GONE);

    }

}