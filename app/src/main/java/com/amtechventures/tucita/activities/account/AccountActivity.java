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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.account.adapters.PagerAccountAdapter;
import com.amtechventures.tucita.activities.account.adapters.VenuesAdapter;
import com.amtechventures.tucita.activities.account.fragments.bookings.BookingsFragment;
import com.amtechventures.tucita.activities.account.fragments.review.AddReviewFragment;
import com.amtechventures.tucita.activities.account.fragments.venues.VenuesFragment;
import com.amtechventures.tucita.activities.main.MainActivity;
import com.amtechventures.tucita.model.context.appointment.AppointmentCompletion;
import com.amtechventures.tucita.model.context.appointment.AppointmentContext;
import com.amtechventures.tucita.model.context.appointmentVenue.AppointmentVenueCompletion;
import com.amtechventures.tucita.model.context.appointmentVenue.AppointmentVenueContext;
import com.amtechventures.tucita.model.context.user.UserContext;
import com.amtechventures.tucita.model.domain.appointment.Appointment;
import com.amtechventures.tucita.model.domain.appointmentVenue.AppointmentVenue;
import com.amtechventures.tucita.model.domain.user.User;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.error.AppError;
import com.amtechventures.tucita.utils.common.AppFont;
import com.amtechventures.tucita.utils.views.AlertDialogError;
import com.mikhaellopez.circularimageview.CircularImageView;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AccountActivity extends AppCompatActivity implements VenuesAdapter.OnReview, AddReviewFragment.OnSend{

    private UserContext userContext;
    private Toolbar toolbar;
    private AppointmentVenueContext appointmentVenueContext;
    private ViewPager viewPager;
    private CircularImageView circularImageView;
    private TextView textName;
    private AppointmentContext appointmentContext;
    PagerAccountAdapter pagerAccountAdapter;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        userContext = UserContext.context(userContext);

        user = userContext.currentUser();

        appointmentVenueContext = AppointmentVenueContext.context(appointmentVenueContext);

        appointmentContext = AppointmentContext.context(appointmentContext);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_account);

        setToolbar();

        circularImageView = (CircularImageView) findViewById(R.id.imageUser);

        textName = (TextView) findViewById(R.id.textName);

        setupTabs();

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

        final TextView tabText = (TextView) tab.getCustomView();

        tabText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bookings, 0, 0, 0);

        tabText.setText(R.string.bookings);

        TabLayout.Tab tab1 = tabLayout.newTab();

        tab1.setCustomView(R.layout.item_tab);

        final TextView tabText1 = (TextView) tab1.getCustomView();

        tabText1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.venues, 0, 0, 0);

        tabText1.setText(R.string.venues);

        tabLayout.addTab(tab);

        tabLayout.addTab(tab1);

        viewPager = (ViewPager) findViewById(R.id.container);

        pagerAccountAdapter = new PagerAccountAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(pagerAccountAdapter);

        tabText.setTextColor(getResources().getColor(R.color.colorAccent3));

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {


                if(tab.getPosition() == 0) {

                    tabText.setTextColor(getResources().getColor(R.color.colorAccent3));

                    tabText1.setTextColor(getResources().getColor(R.color.white));

                } else {

                    tabText.setTextColor(getResources().getColor(R.color.white));

                    tabText1.setTextColor(getResources().getColor(R.color.colorAccent3));

                }

                viewPager.setCurrentItem(tab.getPosition());


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }

        });

    }

    private void showReviewFragment(Venue venue, float rating) {

        FragmentManager fragmentManager = getSupportFragmentManager();

        AddReviewFragment prev = (AddReviewFragment) fragmentManager.findFragmentByTag(AddReviewFragment.class.getName());

        if (prev == null) {

            prev = new AddReviewFragment();

        }

        prev.show(fragmentManager, AddReviewFragment.class.getName());

        prev.setUser(user);

        prev.setVenue(venue);

        prev.setRating(rating);

        prev.setStyle(DialogFragment.STYLE_NO_TITLE, 0);

    }

    public void setupAppointments() {

        final User user = userContext.currentUser();

        ((BookingsFragment) pagerAccountAdapter.getItem(0)).setUser(user);

        appointmentContext.loadUserAppointments(user, new AppointmentCompletion.AppointmentErrorCompletion() {

            @Override
            public void completion(List<Appointment> appointmentList, AppError error) {

                ((BookingsFragment) pagerAccountAdapter.getItem(0)).setAppointmentList(appointmentList);

                List<Venue> venues = new ArrayList<>();

                if (appointmentList != null && !appointmentList.isEmpty()) {

                    for (Appointment appointment : appointmentList) {

                        boolean isThere = false;

                        Calendar calendar = Calendar.getInstance();

                        int[] duration = appointment.getDuration();

                        calendar.add(Calendar.HOUR, duration[0]);

                        calendar.add(Calendar.MINUTE, duration[1]);

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

                }

                ((VenuesFragment) pagerAccountAdapter.getItem(1)).setUser(user);

            }

            @Override
            public void completion(Appointment appointment, AppError error) {

            }

        }, 0);

    }

    private void setImageUser(){

       if(user == null) {

           user = userContext.currentUser();

       }

        if(userContext.isFacebook(user)) {

            Bitmap image = userContext.getPicture();

            if(image != null) {

                circularImageView.setImageBitmap(image);

            }else{

                circularImageView.setImageResource(R.drawable.user_icon);

            }

        } else {

            circularImageView.setImageResource(R.drawable.user_icon);

        }

        setNameUser(user);

        Date createdAt = user.getParseUser().getCreatedAt();

        int bugDate = 1900;

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM", Locale.getDefault());

        String memberFrom = getString(R.string.member_from) + " " + dateFormat.format(createdAt) + " " + getString(R.string.of) + " " + (createdAt.getYear() + bugDate);

        TextView textView = (TextView) findViewById(R.id.accountMemberFrom);

        textView.setText(memberFrom);

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

                MainActivity.goToCategoriesFromLogout(getApplicationContext());

                finish();

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

                intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{email});

                Intent mailer = Intent.createChooser(intent, null);

                startActivity(mailer);

                return true;

            }
        });

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        MainActivity.goToCategories(getApplicationContext());

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {

        MainActivity.goToCategories(getApplicationContext());

        pagerAccountAdapter.hideLoading();

        super.onBackPressed();

    }

    public static void goToAccount(Context context) {

        Intent intent = new Intent(context, AccountActivity.class);

        context.startActivity(intent);

    }



    @Override
    public void onReview(Venue venue, float ratingSelected) {

        showReviewFragment(venue, ratingSelected);

    }

    @Override
    public void onSend(Venue venue) {

        AppointmentVenue appointmentVenue = userContext.getAppointmentVenue(venue, user);

        if(! appointmentVenue.getRanked()) {

            appointmentVenue.putRanked(true);

            appointmentVenueContext.saveAppointmentVenue(appointmentVenue, new AppointmentVenueCompletion.ErrorCompletion() {

                @Override
                public void completion(AppError error) {

                    if (error != null) {

                        AlertDialogError alertDialogError = new AlertDialogError();

                        alertDialogError.noInternetConnectionAlert(getApplicationContext());

                    }

                }

                @Override
                public void completion(List<AppointmentVenue> appointmentVenues, AppError error) {

                }

            });

        }

        setupAppointments();

    }

}
