package com.amtechventures.tucita.activities.account.fragments.venues;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.account.adapters.VenuesAdapter;
import com.amtechventures.tucita.model.context.appointment.AppointmentContext;
import com.amtechventures.tucita.model.context.review.ReviewCompletion;
import com.amtechventures.tucita.model.context.review.ReviewContext;
import com.amtechventures.tucita.model.domain.review.Review;
import com.amtechventures.tucita.model.domain.user.User;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.error.AppError;

import java.util.List;

public class VenuesFragment extends Fragment {

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private TextView noResults;
    private List<Venue> venues;
    private User user;
    private VenuesAdapter.OnReview adapterListener;
    private ReviewContext reviewContext;

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

        adapterListener = (VenuesAdapter.OnReview) context;

    }

    @Override
    public void onDetach() {

        super.onDetach();

        adapterListener = null;

    }


    public void setVenuesAndUser(List<Venue> venues, User user) {

        this.venues = venues;

        this.user = user;

        setupReview();

    }


    public void setupList(List<Review> reviewList) {

        if (venues != null && !venues.isEmpty()) {

            adapter = new VenuesAdapter(venues, reviewList, adapterListener);

            recyclerView.setAdapter(adapter);

            noResults.setVisibility(View.GONE);

        }

    }

    private void setupReview(){

        reviewContext.getReviewsUser(user, new ReviewCompletion.ReviewErrorCompletion() {

            @Override
            public void completion(List<Review> reviewList, AppError error) {

                setupList(reviewList);

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        reviewContext = ReviewContext.context(reviewContext);

        View rootView = inflater.inflate(R.layout.fragment_venues, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        noResults = (TextView) rootView.findViewById(R.id.noResults);

        layoutManager = new GridLayoutManager(getContext(), 1);

        recyclerView.setLayoutManager(layoutManager);

        return rootView;

    }

}
