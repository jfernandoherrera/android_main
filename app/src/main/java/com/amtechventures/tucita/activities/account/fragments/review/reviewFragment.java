package com.amtechventures.tucita.activities.account.fragments.review;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
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
    OnSend listener;

    public interface OnSend{

        void onSend();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        reviewContext = ReviewContext.context(reviewContext);

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

    public void setUser(User user) {

        this.user = user;

    }

    public void setVenue(Venue venue) {

        this.venue = venue;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_add_review, container, false);

        textName = (TextView) rootView.findViewById(R.id.textName);

        textDescription = (TextView) rootView.findViewById(R.id.description);

        textTitle = (TextView) rootView.findViewById(R.id.title);

        circularImageView = (CircularImageView) rootView.findViewById(R.id.imageUser);

        ratingView = (RatingView) rootView.findViewById(R.id.ratingView);

        send = (Button) rootView.findViewById(R.id.send);

        send.setBackgroundColor(Color.WHITE);

        send.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    send.setBackgroundResource(R.drawable.pressed_application_background_static);

                } else if (event.getAction() != MotionEvent.ACTION_MOVE) {

                    send.setBackgroundColor(Color.WHITE);

                }

                return false;

            }
        });


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

        String text = reviewBy + " " + user.getName();

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

                if(error != null){

                }else{

                    listener.onSend();

                    dismiss();

                }

            }
        });

    }

}
