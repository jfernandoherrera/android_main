package com.amtechventures.tucita.activities.search.advanced.fragments;


import android.app.ProgressDialog;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.search.adapters.AdvancedSearchAdapter;
import com.amtechventures.tucita.model.context.location.LocationContext;
import com.amtechventures.tucita.model.context.service.ServiceCompletion;
import com.amtechventures.tucita.model.context.service.ServiceContext;
import com.amtechventures.tucita.model.context.subcategory.SubCategoryContext;
import com.amtechventures.tucita.model.context.venue.VenueCompletion;
import com.amtechventures.tucita.model.context.venue.VenueContext;
import com.amtechventures.tucita.model.domain.category.CategoryAttributes;
import com.amtechventures.tucita.model.domain.city.City;
import com.amtechventures.tucita.model.domain.service.Service;
import com.amtechventures.tucita.model.domain.subcategory.SubCategory;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.error.AppError;
import com.amtechventures.tucita.utils.views.AlertDialogError;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

public class VenuesResultFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private ProgressDialog progress;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Venue> venues = new ArrayList<>();
    private VenueContext venueContext;
    private ServiceContext serviceContext;
    private SubCategoryContext subCategoryContext;
    private String name;
    private SubCategory subCategory;
    private ArrayList priceStrings = new ArrayList<>();
    private LocationContext locationContext;
    private TextView noResults;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_venues_result, container, false);

        venueContext = VenueContext.context(venueContext);

        subCategoryContext = SubCategoryContext.context(subCategoryContext);

        serviceContext = ServiceContext.context(serviceContext);

        locationContext = LocationContext.context(locationContext, getContext(), this, this);

        name = getActivity().getIntent().getStringExtra(CategoryAttributes.name);

        recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);

        noResults = (TextView) rootView.findViewById(R.id.noResults);

        noResults.setVisibility(View.GONE);

        layoutManager = new GridLayoutManager(getContext(), 1);

        recyclerView.setLayoutManager(layoutManager);

        return rootView;
    }

    private void setupProgress(){

        progress = ProgressDialog.show(getContext(), getResources().getString(R.string.dialog_progress_title),

                getResources().getString(R.string.dialog_advanced_search_progress_message), true);
    }


    @Override
    public void onPause() {

        super.onPause();

        subCategoryContext.cancelQuery();

        serviceContext.cancelQuery();

        venueContext.cancelQuery();
    }


    private void setupPriceFrom(){

        for (Venue venue : venues) {

            int price = serviceContext.getPricesFrom(subCategory, venue);

            if(price != 0) {

                priceStrings.add(String.valueOf(price));
            }
        }
    }

    private void setupCityVenues(List<Service> services, City city){

        venueContext.loadSubCategorizedCityVenues(services, city, new VenueCompletion.ErrorCompletion() {
            @Override
            public void completion(List<Venue> venueList, AppError error) {

                if (venueList != null) {

                    if(venueList.isEmpty()){

                        noResults.setVisibility(View.VISIBLE);
                    }

                    venues.clear();

                    venues.addAll(venueList);

                    setupPriceFrom();

                    adapter = new AdvancedSearchAdapter(venues, priceStrings, subCategory.getName());

                    recyclerView.setAdapter(adapter);

                    progress.dismiss();
                }
            }
        });
    }

    private void setupNearVenues(List<Service> services) {

        Location lastLocation = locationContext.getLastLocation();

        if (lastLocation != null) {

            venueContext.loadSubCategorizedNearVenues(services, lastLocation, new VenueCompletion.ErrorCompletion() {

                @Override
                public void completion(List<Venue> venueList, AppError error) {

                    progress.dismiss();

                    if (venueList != null) {

                        if(venueList.isEmpty()){

                            noResults.setVisibility(View.VISIBLE);
                        }

                        venues.clear();

                        venues.addAll(venueList);

                        setupPriceFrom();

                        adapter = new AdvancedSearchAdapter(venues, priceStrings, subCategory.getName());

                        recyclerView.setAdapter(adapter);

                    }
                }

            });

        }else{
            AlertDialogError alertDialogError = new AlertDialogError();

            alertDialogError.noLocationAlert(getContext());
        }
    }

    private void setCity(String city){

        Button button = (Button) getView().findViewById(R.id.locationOptions);

        button.setText(city);
    }

    public void setupList(final City city) {

        setupProgress();

        setCity(city.formatedLocation());

        subCategory = subCategoryContext.findSubCategory(name);

        serviceContext.loadSubCategorizedServices(subCategory, new ServiceCompletion.ErrorCompletion() {
            @Override
            public void completion(List<Service> servicesList, AppError error) {

                if (servicesList != null) {

                    setupCityVenues(servicesList, city);

                } else {
                    progress.dismiss();

                    AlertDialogError alertDialogError = new AlertDialogError();

                    alertDialogError.noInternetConnectionAlert(getContext());
                }
            }
        });
    }

    private void setupList() {

        setupProgress();

        subCategory = subCategoryContext.findSubCategory(name);

        serviceContext.loadSubCategorizedServices(subCategory, new ServiceCompletion.ErrorCompletion() {
            @Override
            public void completion(List<Service> servicesList, AppError error) {

                if (servicesList != null) {

                    setupNearVenues(servicesList);

                } else {

                    progress.dismiss();

                    AlertDialogError alertDialogError = new AlertDialogError();

                    alertDialogError.noInternetConnectionAlert(getContext());
                }
            }
        });
    }



    @Override
    public void onConnected(Bundle bundle) {

        locationContext.setLocation();

        setupList();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        AlertDialogError alertDialogError = new AlertDialogError();

        alertDialogError.noLocationAlert(getContext());
    }
}
