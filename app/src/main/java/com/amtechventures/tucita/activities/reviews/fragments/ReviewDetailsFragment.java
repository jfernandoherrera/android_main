package com.amtechventures.tucita.activities.reviews.fragments;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.model.domain.review.Review;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.Date;

public class ReviewDetailsFragment extends Fragment{

    private TextView textName;
    private TextView textTitle;
    private TextView textDescription;
    private TextView textDate;
    private CircularImageView circularImageView;
    private RatingBar ratingBar;
    private Review review;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View itemView = inflater.inflate(R.layout.fragment_review_details, container, false);

        textName = (TextView) itemView.findViewById(R.id.textNameUser);

        textDescription = (TextView) itemView.findViewById(R.id.textDescription);

        textTitle = (TextView) itemView.findViewById(R.id.textTitle);

        textDate = (TextView) itemView.findViewById(R.id.textDate);

        ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);

        setupStars();

        circularImageView = (CircularImageView) itemView.findViewById(R.id.imageUser);

        return itemView;
    }

    public void setReview(Review review) {

        this.review = review;

        setupReview();

    }

    private void setupStars(){

        final int yellowColor = Color.argb(255, 251, 197, 70);

        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();

        stars.getDrawable(2).setColorFilter(yellowColor, PorterDuff.Mode.SRC_ATOP);

        Drawable progress = ratingBar.getProgressDrawable();

        DrawableCompat.setTint(progress, yellowColor);

    }

    private void setupReview(){

        textDescription.setText(review.getDescription());

        textTitle.setText(review.getTitle());

        textDate.setText(date(review.getUpdatedAt()));

        ratingBar.setRating(review.getRating());

        textName.setText(review.getUser().getName());

    }

    private String date(Date date){

        String slash = "/";

        String formattedDate;

        int bugDate = 1900;

        formattedDate = date.getDate() + slash + (date.getMonth() + 1) + slash + (date.getYear() + bugDate);

        return formattedDate;

    }

}
