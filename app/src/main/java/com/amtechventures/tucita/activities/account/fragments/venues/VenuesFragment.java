package com.amtechventures.tucita.activities.account.fragments.venues;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.account.adapters.VenuesAdapter;
import com.amtechventures.tucita.model.context.appointment.AppointmentContext;
import com.amtechventures.tucita.model.context.appointmentVenue.AppointmentVenueCompletion;
import com.amtechventures.tucita.model.context.appointmentVenue.AppointmentVenueContext;
import com.amtechventures.tucita.model.context.appointmentVenue.AppointmentVenueRemote;
import com.amtechventures.tucita.model.context.review.ReviewCompletion;
import com.amtechventures.tucita.model.context.review.ReviewContext;
import com.amtechventures.tucita.model.context.user.UserContext;
import com.amtechventures.tucita.model.domain.appointment.Appointment;
import com.amtechventures.tucita.model.domain.appointmentVenue.AppointmentVenue;
import com.amtechventures.tucita.model.domain.review.Review;
import com.amtechventures.tucita.model.domain.user.User;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.error.AppError;
import com.amtechventures.tucita.utils.views.TuCitaProgressDialog;

import java.util.List;

public class VenuesFragment extends Fragment {

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private TextView noResults;
    private User user;
    private VenuesAdapter.OnReview adapterListener;
    private UserContext userContext;
    private TuCitaProgressDialog progress;
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

    public void setUser( User user) {

        this.user = user;

        setupList();

    }


    private void setupProgress() {

        if (progress == null) {

            progress = new TuCitaProgressDialog(getContext(),R.style.TuCitaDialogTheme);

            progress.setCancelable(false);

            progress.setIndeterminate(true);

        }

        progress.show();

    }

    public void hideLoading() {

        if (progress != null) {

            progress.dismiss();

        }

    }

    public void setupList() {

        userContext.getAppointmentVenues(user, new AppointmentVenueCompletion.ErrorCompletion() {
            @Override
            public void completion(AppError error) {

            }

            @Override
            public void completion(List<AppointmentVenue> appointmentVenues, AppError error) {

               hideLoading();

                if (appointmentVenues != null && !appointmentVenues.isEmpty()) {

                    String reviewBy = getString(R.string.review_by);

                    String users = getString(R.string.users);

                    adapter = new VenuesAdapter(appointmentVenues, adapterListener, reviewBy, users);

                    recyclerView.setAdapter(adapter);

                    noResults.setVisibility(View.GONE);

                }

            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        userContext = UserContext.context(userContext);

        View rootView = inflater.inflate(R.layout.fragment_venues, container, false);

        setupProgress();

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        noResults = (TextView) rootView.findViewById(R.id.noResults);

        layoutManager = new GridLayoutManager(getContext(), 1);

        recyclerView.setLayoutManager(layoutManager);

        return rootView;

    }

}
