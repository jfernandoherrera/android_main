package com.amtechventures.tucita.activities.account;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.account.adapters.AppointmentsAdapter;
import com.amtechventures.tucita.activities.account.adapters.PagerAccountAdapter;
import com.amtechventures.tucita.activities.account.adapters.VenuesAdapter;
import com.amtechventures.tucita.activities.account.fragments.bookings.BookingsFragment;
import com.amtechventures.tucita.activities.account.fragments.review.ReviewFragment;
import com.amtechventures.tucita.activities.account.fragments.venues.VenuesFragment;
import com.amtechventures.tucita.activities.main.MainActivity;
import com.amtechventures.tucita.model.context.appointment.AppointmentCompletion;
import com.amtechventures.tucita.model.context.appointment.AppointmentContext;
import com.amtechventures.tucita.model.context.user.UserContext;
import com.amtechventures.tucita.model.domain.appointment.Appointment;
import com.amtechventures.tucita.model.domain.user.User;
import com.amtechventures.tucita.model.domain.user.UserAttributes;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.error.AppError;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AccountActivity extends AppCompatActivity implements VenuesAdapter.OnReview, ReviewFragment.OnSend{

    private UserContext userContext;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private CircularImageView circularImageView;
    private TextView textName;
    Typeface roboto;
    private AppointmentContext appointmentContext;
    PagerAccountAdapter pagerAccountAdapter;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        userContext = UserContext.context(userContext);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_account);

        setToolbar();

        setupTabs();

        circularImageView = (CircularImageView) findViewById(R.id.imageUser);

        textName = (TextView) findViewById(R.id.textName);

        roboto = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");

        textName.setTypeface(roboto);

        appointmentContext = AppointmentContext.context(appointmentContext);

        userContext = UserContext.context(userContext);

        setImageUser();

        setupAppointments();

    }

    private void setToolbar(){

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {

            setSupportActionBar(toolbar);

        }

    }

    private void setNameUser(User user) {

        textName.setText(user.getName());

    }

    private void setupTabs() {

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        TabLayout.Tab tab = tabLayout.newTab();

        tab.setCustomView(R.layout.item_tab);

        TextView tabText = (TextView) tab.getCustomView();

        tabText.setText(R.string.bookings);

        tabText.setTypeface(roboto, Typeface.BOLD);

        TabLayout.Tab tab1 = tabLayout.newTab();

        tab1.setCustomView(R.layout.item_tab);

        TextView tabText1 = (TextView) tab1.getCustomView();

        tabText1.setTypeface(roboto);

        tabText1.setText(R.string.venues);

        tabLayout.addTab(tab);

        tabLayout.addTab(tab1);

        viewPager = (ViewPager) findViewById(R.id.container);

        pagerAccountAdapter = new PagerAccountAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(pagerAccountAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                TextView tabText = (TextView) tab.getCustomView();

                viewPager.setCurrentItem(tab.getPosition());

                tabText.setTypeface(roboto, Typeface.BOLD);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                TextView tabText = (TextView) tab.getCustomView();

                tabText.setTypeface(roboto, Typeface.NORMAL);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }

        });

    }

    private void showReviewFragment(Venue venue, float rating) {

        FragmentManager fragmentManager = getSupportFragmentManager();

        ReviewFragment prev = (ReviewFragment) fragmentManager.findFragmentByTag(ReviewFragment.class.getName());

        if (prev == null) {

            prev = new ReviewFragment();
        }

        prev.show(fragmentManager, ReviewFragment.class.getName());

        prev.setUser(user);

        prev.setVenue(venue);

        prev.setRating(rating);

        prev.setStyle(DialogFragment.STYLE_NO_TITLE, 0);

    }

    public void setupAppointments() {

        final User user = userContext.currentUser();

        appointmentContext.loadUserAppointments(user, new AppointmentCompletion.AppointmentErrorCompletion() {

            @Override
            public void completion(List<Appointment> appointmentList, AppError error) {

                if (appointmentList != null && !appointmentList.isEmpty()) {

                    ((BookingsFragment) pagerAccountAdapter.getItem(0)).setAppointmentList(appointmentList);

                    List<Venue> venues = new ArrayList<>();

                    Calendar calendar = Calendar.getInstance();

                    for (Appointment appointment : appointmentList) {

                        boolean isThere = false;

                        Venue venueToCheck;

                        if (appointment.getDate().before(calendar.getTime())) {

                            venueToCheck = appointment.getVenue();

                            for (Venue venue : venues) {

                                boolean equalName = venueToCheck.getName().equals(venue.getName());

                                boolean equalAddress = venueToCheck.getAddress().equals(venue.getAddress());

                                if (equalAddress && equalName) {

                                    isThere = true;

                                }

                            }

                            if (!isThere) {

                                venues.add(venueToCheck);

                            }

                        }
                    }

                    ((VenuesFragment) pagerAccountAdapter.getItem(1)).setVenuesAndUser(venues, user);

                }

            }

        });

    }

    private void setImageUser(){

        user = userContext.currentUser();

        if(userContext.isFacebook(user)) {

            Bitmap image = userContext.getPicture();

            if(image != null) {

                circularImageView.setImageBitmap(image);

            }else{

                circularImageView.setVisibility(View.GONE);

            }

        } else {

            circularImageView.setVisibility(View.GONE);

        }

        setNameUser(user);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getMenuInflater().inflate(R.menu.menu_logout, menu);

        MenuItem logoutItem = menu.findItem(R.id.action_logout);

        logoutItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                userContext.logout();

                goToCategoriesFromLogout();

                return true;

            }

        });

        MenuItem contactUs = menu.findItem(R.id.action_contact_us);

        contactUs.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                String email = "jose@amtechventures.com";

                String typeEmail = "message/rfc822";

                Intent intent = new Intent(Intent.ACTION_SEND);

                intent.setType(typeEmail);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { email });

                Intent mailer = Intent.createChooser(intent, null);

                startActivity(mailer);

                return true;

            }
        });

        return true;

    }

    @Override
    public void onBackPressed() {

        goToCategories();

        super.onBackPressed();

    }

    public static void goToAccount(Context context) {

        Intent intent = new Intent(context, AccountActivity.class);

        context.startActivity(intent);

    }

    public void goToCategoriesFromLogout(){

        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra(UserAttributes.connected, false);

        startActivity(intent);

        finish();

    }

    public void goToCategories() {

        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra(UserAttributes.connected, true);

        startActivity(intent);

        finish();

    }

    @Override
    public void onReview(Venue venue, float ratingSelected) {

        showReviewFragment(venue, ratingSelected);

    }

    @Override
    public void onSend() {

        setupAppointments();

    }

}
