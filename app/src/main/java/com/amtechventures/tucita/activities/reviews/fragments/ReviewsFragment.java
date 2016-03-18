package com.amtechventures.tucita.activities.reviews.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.reviews.ReviewsActivity;
import com.amtechventures.tucita.activities.reviews.adapters.ReviewsAdapter;
import com.amtechventures.tucita.model.context.user.UserContext;
import com.amtechventures.tucita.model.domain.review.Review;
import com.amtechventures.tucita.model.domain.user.User;
import com.amtechventures.tucita.utils.views.UserReviewView;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

public class ReviewsFragment extends Fragment {

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    TextView textRating;
    TextView textReviews;
    RatingBar ratingBar;
    List<Review> reviewList;
    float rating;
    UserContext user;
    String reviewBy ;
    String users;
    Review yours;
    UserReviewView userReviewView;
    int reviewsCount;

    public interface OnDetails{

        void onDetails(Review review);
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

        reviewBy = getString(R.string.review_by);

        users = getString(R.string.users);

    }

    @Override
    public void onDetach() {

        super.onDetach();

    }

    private void setupStars(){

        final int yellowColor = Color.argb(255, 251, 197, 70);

        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();

        stars.getDrawable(2).setColorFilter(yellowColor, PorterDuff.Mode.SRC_ATOP);

        Drawable progress = ratingBar.getProgressDrawable();

        DrawableCompat.setTint(progress, yellowColor);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_reviews, container, false);

        textRating = (TextView) rootView.findViewById(R.id.textRating);

        textReviews = (TextView) rootView.findViewById(R.id.textReviews);

        ratingBar = (RatingBar) rootView.findViewById(R.id.ratingBarVenue);

        setupStars();

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        userReviewView = (UserReviewView) rootView.findViewById(R.id.userReview);

        layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);

        if(reviewList != null){

            adapter = new ReviewsAdapter(reviewList, (ReviewsAdapter.OnReviewClicked) getActivity(), user);

            recyclerView.setAdapter(adapter);

        }

        setupRating();

        setupYours();

        return rootView;
    }

    public void setRating(float rating) {

        this.rating = rating;

        if(textRating != null) {

            setupRating();

        }

    }

    public void setReviewsCount(int count) {

        reviewsCount = count;

        if(textRating != null) {

            setupReviews();

        }

    }

    public void setUser(UserContext user) {

        this.user = user;

    }

    private void setupRating(){
        
        DecimalFormat df = new DecimalFormat("#.#");
        
        String clippedRating =  df.format(rating);

        if(clippedRating.length() == 1){

            clippedRating = clippedRating + ",0";
        }
        
        textRating.setText(clippedRating);

        ratingBar.setRating(rating);

    }

    private void setImageUser(User user){

        if(this.user.isFacebook(user)) {

            Bitmap image = this.user.getPicture();

            if(image != null) {

                userReviewView.setImageUser(image);

            }

        }

    }

    private void setupReviews(){

        String textReviewsDone = reviewBy + " " + reviewsCount + " " + users;

        textReviews.setText(textReviewsDone);

    }

    public void setYours(Review yours) {

        this.yours = yours;

        if(userReviewView != null){

            setupYours();

        }

    }

    private void setupYours(){

        if(yours == null){

            userReviewView.setVisibility(View.GONE);

        }else {

            userReviewView.setVisibility(View.VISIBLE);

            userReviewView.setReview(yours);

            setImageUser(yours.getUser());

        }

    }

    public void setReviewList(List<Review> reviewList) {

        if(this.reviewList == null){

            this.reviewList = reviewList;

            if(recyclerView != null){

                adapter = new ReviewsAdapter(reviewList, (ReviewsAdapter.OnReviewClicked) getActivity(), user);

                recyclerView.setAdapter(adapter);

            }

        }else {

            this.reviewList.clear();

            this.reviewList.addAll(reviewList);

            if (adapter != null) {

                adapter.notifyDataSetChanged();

            }
        }

        if(textReviews != null) {

            setupReviews();

        }
    }

}
