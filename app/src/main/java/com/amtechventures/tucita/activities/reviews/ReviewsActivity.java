package com.amtechventures.tucita.activities.reviews;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.account.fragments.review.ReviewFragment;
import com.amtechventures.tucita.activities.reviews.adapters.ReviewsAdapter;
import com.amtechventures.tucita.activities.reviews.fragments.EditReviewFragment;
import com.amtechventures.tucita.activities.reviews.fragments.ReviewDetailsFragment;
import com.amtechventures.tucita.activities.reviews.fragments.ReviewsFragment;
import com.amtechventures.tucita.model.context.city.CityCompletion;
import com.amtechventures.tucita.model.context.review.ReviewCompletion;
import com.amtechventures.tucita.model.context.review.ReviewContext;
import com.amtechventures.tucita.model.context.user.UserContext;
import com.amtechventures.tucita.model.context.venue.VenueCompletion;
import com.amtechventures.tucita.model.context.venue.VenueContext;
import com.amtechventures.tucita.model.domain.category.CategoryAttributes;
import com.amtechventures.tucita.model.domain.review.Review;
import com.amtechventures.tucita.model.domain.user.User;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.domain.venue.VenueAttributes;
import com.amtechventures.tucita.model.error.AppError;
import com.amtechventures.tucita.utils.views.UserReviewView;

import java.util.List;

public class ReviewsActivity extends AppCompatActivity implements UserReviewView.OnEdit, EditReviewFragment.OnEdited, ReviewsFragment.OnDetails, ReviewsAdapter.OnReviewClicked {

    private Toolbar toolbar;
    private String name;
    private String address;
    private VenueContext venueContext;
    private Venue venue;
    private ReviewsFragment reviewsFragment;
    private ReviewDetailsFragment reviewDetailsFragment;
    private ReviewContext reviewContext;
    private UserContext userContext;
    private RelativeLayout concealer;
    private Typeface typeface;

    @Override
    public void onDetails(Review review) {

        reviewDetailsFragment.setReview(review);

        reviewDetailsShow();

        reviewsHide();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reviews);

        concealer = (RelativeLayout) findViewById(R.id.concealer);

        venueContext = VenueContext.context(venueContext);

        reviewContext = ReviewContext.context(reviewContext);

        userContext = UserContext.context(userContext);

        reviewsFragment = new ReviewsFragment();

        reviewDetailsFragment = new ReviewDetailsFragment();

        setup();

        setToolbar();

        setReviewsFragment();

        setReviewsDetailsFragment();

        reviewDetailsHide();

    }

    private void setup(){

        name = getIntent().getStringExtra(VenueAttributes.name);

        address = getIntent().getStringExtra(VenueAttributes.address);

        venue = venueContext.findVenue(name, address, new VenueCompletion.ErrorCompletion() {

            @Override
            public void completion(List<Venue> venueList, AppError error) {

                reviewsFragment.setRating((float) venueList.get(0).getRating());

                reviewsFragment.setReviewsCount(venueList.get(0).getReviews());

                setupReviews();

            }
        });

        if(venue != null){

            reviewsFragment.setRating((float) venue.getRating());

            reviewsFragment.setReviewsCount(venue.getReviews());

            setupReviews();

        }

        setupUser();

    }

    private void setupUser(){

        reviewsFragment.setUser(userContext);

    }


    private void findYourReview(List<Review> reviewList, String userId){

        Review toRemove = null;

        if (reviewList != null) {

            for (Review review : reviewList) {

                String reviewUserId = review.getUser().getParseUser().getObjectId();

                if (reviewUserId.equals(userId)) {

                    toRemove = review;

                    reviewList.remove(review);

                    break;

                }
            }

            if (toRemove != null) {

                reviewsFragment.setYours(toRemove);

            }

            reviewsFragment.setReviewList(reviewList);

        }

    }

    private void removeYours(  List<Review> reviews, String userId){

        Review toRemove = null;

        if (reviews != null) {

            for (Review review : reviews) {

                String reviewUserId = review.getUser().getParseUser().getObjectId();

                if (reviewUserId.equals(userId)) {

                    toRemove = review;

                    reviews.remove(review);

                    break;

                }
            }

            if (toRemove != null) {

                reviewsFragment.setYours(toRemove);

            }

            reviewsFragment.setReviewList(reviews);

        }
    }
    
    private void setupReviews(){

        List<Review> reviews;

        User user =  userContext.currentUser();

        if(user != null) {


            final String userId = user.getObjectId();

            reviews = reviewContext.getReviewsVenue(venue, new ReviewCompletion.ReviewErrorCompletion() {

                @Override
                public void completion(List<Review> reviewList, AppError error) {

                    findYourReview(reviewList, userId);

                    concealer.setVisibility(View.GONE);

                }
            });

           removeYours(reviews, userId);

        }else {

            reviews = reviewContext.getReviewsVenue(venue, new ReviewCompletion.ReviewErrorCompletion() {

                @Override
                public void completion(List<Review> reviewList, AppError error) {

                    if(reviewList != null){

                        reviewsFragment.setReviewList(reviewList);
                    }

                    concealer.setVisibility(View.GONE);

                }

            });

            if(reviews != null){

                reviewsFragment.setReviewList(reviews);

            }

        }

    }




    private void reviewsHide() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.hide(reviewsFragment);

        transaction.commit();

    }

    private void reviewDetailsHide() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.hide(reviewDetailsFragment);

        transaction.commit();

    }

    private void setReviewsDetailsFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.drawView, reviewDetailsFragment);

        transaction.commit();

    }

    private void setReviewsFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.drawView, reviewsFragment);

        transaction.commit();

    }

    public static void goToReviews(Context context, String name, String address) {

        Intent intent = new Intent(context, ReviewsActivity.class);

        intent.putExtra(VenueAttributes.name, name);

        intent.putExtra(VenueAttributes.address, address);

        context.startActivity(intent);

    }

    private void setToolbar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {

            setSupportActionBar(toolbar);

        }

    }

    private void showEditReviewFragment(Review review) {

        FragmentManager fragmentManager = getSupportFragmentManager();

        EditReviewFragment prev = (EditReviewFragment) fragmentManager.findFragmentByTag(EditReviewFragment.class.getName());

        if (prev == null) {

            prev = new EditReviewFragment();
        }

        prev.setReview(review);

        prev.setUser(userContext.currentUser());

        prev.show(fragmentManager, ReviewFragment.class.getName());

        prev.setStyle(DialogFragment.STYLE_NO_TITLE, 0);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(name);

        return true;

    }

    private void back(){

        FragmentManager fragmentManager = getSupportFragmentManager();

        EditReviewFragment prev = (EditReviewFragment) fragmentManager.findFragmentByTag(EditReviewFragment.class.getName());

        if(prev != null){

            prev.dismiss();

        }else if(reviewsFragment.isHidden()){

        reviewsShow();

        reviewDetailsHide();

            }else {

            finish();

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
    public void onEdit(Review review) {

        showEditReviewFragment(review);

    }

    private void reviewDetailsShow() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.show(reviewDetailsFragment);

        transaction.commit();

    }


    private void reviewsShow() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.show(reviewsFragment);

        transaction.commit();

    }

    @Override
    public void onEdited(Review review) {

    reviewsFragment.setYours(review);

    }

    @Override
    public void onReviewClicked(Review review) {

        reviewDetailsFragment.setReview(review);

        reviewDetailsShow();

        reviewsHide();

    }
}
