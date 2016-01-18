package com.amtechventures.tucita.activities.reviews.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.reviews.adapters.ReviewsAdapter;
import com.amtechventures.tucita.model.context.user.UserContext;
import com.amtechventures.tucita.model.domain.review.Review;
import com.amtechventures.tucita.model.domain.user.User;

import java.util.List;

public class ReviewsFragment extends Fragment implements ReviewsAdapter.OnReviewClicked{

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    TextView textRating;
    TextView textReviews;
    RatingBar ratingBar;
    List<Review> reviewList;
    float rating;
    UserContext user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_reviews, container, false);

        textRating = (TextView) rootView.findViewById(R.id.textRating);

        textReviews = (TextView) rootView.findViewById(R.id.textReviews);

        ratingBar = (RatingBar) rootView.findViewById(R.id.ratingBar);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);

        ratingBar.setRating(rating);

        textRating.setText(String.valueOf(rating));

        String reviewBy = getString(R.string.review_by);

        String users = getString(R.string.users);

        String textReviewsDone = reviewBy + " " + reviewList.size() + " " + users;

        textReviews.setText(textReviewsDone);

        adapter = new ReviewsAdapter(reviewList, this, user);

        recyclerView.setAdapter(adapter);

        return rootView;
    }

    public void setRating(float rating) {

        this.rating = rating;

    }

    public void setUser(UserContext user) {

        this.user = user;

    }

    public void setReviewList(List<Review> reviewList) {

        if(this.reviewList == null){

            this.reviewList = reviewList;

        }else {

            this.reviewList.clear();

            this.reviewList.addAll(reviewList);

        }

    }

    @Override
    public void onReviewClicked(Review review) {

    }
}
