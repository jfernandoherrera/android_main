package com.amtechventures.tucita.activities.account.fragments.review;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.model.context.review.ReviewCompletion;
import com.amtechventures.tucita.model.context.review.ReviewContext;
import com.amtechventures.tucita.model.context.user.UserContext;
import com.amtechventures.tucita.model.domain.review.Review;
import com.amtechventures.tucita.model.domain.user.User;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.error.AppError;
import com.amtechventures.tucita.utils.views.AlertDialogError;
import com.amtechventures.tucita.utils.views.RatingView;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

public class AddReviewFragment extends DialogFragment {

    CircularImageView circularImageView;
    RelativeLayout layout;
    RatingView ratingView;
    ReviewContext reviewContext;
    TextView textTitle;
    TextView textDescription;
    TextView textName;
    TextView send;
    UserContext userContext;
    User user;
    Venue venue;
    OnSend listener;
    float rating;
    boolean doReview;

    public interface OnSend{

        void onSend(Venue venue);
    }

    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);

        listener = (OnSend) activity;

    }


    @Override
    public void onDetach() {

        super.onDetach();

        listener = null;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        reviewContext = ReviewContext.context(reviewContext);

    }

    public void setUser(User user) {

        this.user = user;

    }

    public void setVenue(Venue venue) {

        this.venue = venue;

    }

    private Bitmap setImageUser(User user){

        Bitmap image = null;

        if(userContext.isFacebook(user)) {

            image = userContext.getPicture();

        }

        return image;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        userContext = UserContext.context(userContext);

        View rootView = inflater.inflate(R.layout.fragment_add_review, container, false);

        textName = (TextView) rootView.findViewById(R.id.textName);

        textDescription = (TextView) rootView.findViewById(R.id.description);

        textTitle = (TextView) rootView.findViewById(R.id.title);

        circularImageView = (CircularImageView) rootView.findViewById(R.id.imageUser);

        ratingView = (RatingView) rootView.findViewById(R.id.ratingView);

        send = (TextView) rootView.findViewById(R.id.send);

        ratingView.setRating(rating);

        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                sendReview();

            }
        });

        layout = (RelativeLayout) rootView.findViewById(R.id.frames);

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

            layout.bringToFront();

        }

        Window window = getDialog().getWindow();

        window.setBackgroundDrawableResource(android.R.color.transparent);

        String reviewBy = getString(R.string.review_by);

        String text = reviewBy + " " + user.getName();

        textName.setText(text);

        Bitmap image = setImageUser(user);

        if(image != null) {

            circularImageView.setBorderColor(getResources().getColor(R.color.colorAccent));

            circularImageView.setImageBitmap(image);

        }


        return rootView;
    }

    public void setRating(float rating){

    this.rating = rating;

    }

    private void sendReview(){

        if(! doReview) {

            Review review = new Review();

            review.setUser(user);

            review.setVenue(venue);

            doReview = true;

            review.setRating(ratingView.getRating());

            review.setDescription(textDescription.getText().toString());

            review.setTitle(textTitle.getText().toString());

            reviewContext.saveReview(review, new ReviewCompletion.ReviewErrorCompletion() {
                @Override
                public void completion(List<Review> reviewList, AppError error) {

                    if (error != null) {

                        doReview = false;

                        AlertDialogError alertDialogError = new AlertDialogError();

                        alertDialogError.noInternetConnectionAlert(getContext());

                    } else {

                        listener.onSend(venue);

                        listener = null;

                        dismiss();

                    }

                }
            });

        }

    }

}
