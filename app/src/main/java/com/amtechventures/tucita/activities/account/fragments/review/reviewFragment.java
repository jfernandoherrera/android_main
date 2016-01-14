package com.amtechventures.tucita.activities.account.fragments.review;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.model.context.review.ReviewCompletion;
import com.amtechventures.tucita.model.context.review.ReviewContext;
import com.amtechventures.tucita.model.domain.review.Review;
import com.amtechventures.tucita.model.domain.user.User;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.error.AppError;
import com.amtechventures.tucita.utils.views.RatingView;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

public class ReviewFragment extends DialogFragment {

    CircularImageView circularImageView;
    RelativeLayout layout;
    RatingView ratingView;
    ReviewContext reviewContext;
    TextView textTitle;
    TextView textDescription;
    TextView textName;
    Button send;
    User user;
    Venue venue;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        reviewContext = ReviewContext.context(reviewContext);

    }

    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);

    }

    public void setUser(User user) {

        this.user = user;

    }

    public void setVenue(Venue venue) {

        this.venue = venue;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_review, container, false);

        textName = (TextView) rootView.findViewById(R.id.textName);

        textDescription = (TextView) rootView.findViewById(R.id.description);

        textTitle = (TextView) rootView.findViewById(R.id.title);

        circularImageView = (CircularImageView) rootView.findViewById(R.id.imageUser);

        ratingView = (RatingView) rootView.findViewById(R.id.ratingView);

        send = (Button) rootView.findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

        sendReview();

            }
        });

        layout = (RelativeLayout) rootView.findViewById(R.id.frames);

        layout.bringToFront();

        Window window = getDialog().getWindow();

        window.setBackgroundDrawableResource(android.R.color.transparent);

        String reviewBy = getString(R.string.review_by);

        String text = reviewBy +" " + user.getName();

        textName.setText(text);

        return rootView;
    }


    private void sendReview(){

        Review review = new Review();

        review.setUser(user);

        review.setVenue(venue);

        review.setRating(ratingView.getRating());

        review.setDescription(textDescription.getText().toString());

        review.setTitle(textTitle.getText().toString());

        reviewContext.createReview(review, new ReviewCompletion.ReviewErrorCompletion() {
            @Override
            public void completion(List<Review> reviewList, AppError error) {

                if(error == null){

                }
            }
        });
    }
}
