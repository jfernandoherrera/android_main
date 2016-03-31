package com.amtechventures.tucita.activities.reviews.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
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
import com.amtechventures.tucita.model.domain.review.Review;
import com.amtechventures.tucita.model.domain.user.User;
import com.amtechventures.tucita.model.error.AppError;
import com.amtechventures.tucita.utils.views.AppTextView;
import com.amtechventures.tucita.utils.views.RatingView;
import com.mikhaellopez.circularimageview.CircularImageView;
import java.util.List;

public class EditReviewFragment extends DialogFragment {

    CircularImageView circularImageView;
    RelativeLayout layout;
    RatingView ratingView;
    ReviewContext reviewContext;
    TextView textTitle;
    TextView textDescription;
    TextView textName;
    AppTextView edit;
    Review review;
    float rating;
    OnEdited onEdited;
    User user;


    public interface OnEdited{

        void onEdited(Review review);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        reviewContext = ReviewContext.context(reviewContext);

    }

    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);

    onEdited = (OnEdited) activity;

    }

    @Override
    public void onDetach() {

        super.onDetach();

        onEdited = null;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_edit_review, container, false);

        textName = (TextView) rootView.findViewById(R.id.textName);

        textDescription = (TextView) rootView.findViewById(R.id.description);

        textTitle = (TextView) rootView.findViewById(R.id.title);

        circularImageView = (CircularImageView) rootView.findViewById(R.id.imageUser);

        ratingView = (RatingView) rootView.findViewById(R.id.ratingView);

        edit = (AppTextView) rootView.findViewById(R.id.send);

        edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                editReview();

            }
        });

        layout = (RelativeLayout) rootView.findViewById(R.id.frames);

        layout.bringToFront();

        Window window = getDialog().getWindow();

        window.setBackgroundDrawableResource(android.R.color.transparent);

        String reviewBy = getString(R.string.review_by);

        String text = reviewBy + " " + user.getName();

        textName.setText(text);

        textTitle.setText(review.getTitle());

        textDescription.setText(review.getDescription());

        setRating(review.getRating());

        return rootView;

    }

    public void setUser(User user) {

        this.user = user;

    }

    private void setRating(float rating){

        ratingView.setRating(rating);

    }

    public void setReview(Review review) {

        this.review = review;

    }

    private void editReview(){

        review.setRating(ratingView.getRating());

        review.setDescription(textDescription.getText().toString());

        review.setTitle(textTitle.getText().toString());

        reviewContext.saveReview(review, new ReviewCompletion.ReviewErrorCompletion() {
            @Override
            public void completion(List<Review> reviewList, AppError error) {

                if (error != null) {

                } else {

                    onEdited.onEdited(review);

                    dismiss();

                }

            }
        });

    }

}
