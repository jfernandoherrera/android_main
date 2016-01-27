package com.amtechventures.tucita.activities.advanced.fragments;

import android.app.ProgressDialog;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.advanced.adapters.AdvancedSearchAdapter;
import com.amtechventures.tucita.model.context.category.CategoryContext;
import com.amtechventures.tucita.model.context.location.LocationCompletion;
import com.amtechventures.tucita.model.context.location.LocationContext;
import com.amtechventures.tucita.model.context.service.ServiceCompletion;
import com.amtechventures.tucita.model.context.service.ServiceContext;
import com.amtechventures.tucita.model.context.subcategory.SubCategoryCompletion;
import com.amtechventures.tucita.model.context.subcategory.SubCategoryContext;
import com.amtechventures.tucita.model.context.venue.VenueCompletion;
import com.amtechventures.tucita.model.context.venue.VenueContext;
import com.amtechventures.tucita.model.domain.category.Category;
import com.amtechventures.tucita.model.domain.category.CategoryAttributes;
import com.amtechventures.tucita.model.domain.city.City;
import com.amtechventures.tucita.model.domain.service.Service;
import com.amtechventures.tucita.model.domain.subcategory.SubCategory;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.error.AppError;
import com.amtechventures.tucita.utils.views.AlertDialogError;

import java.util.ArrayList;
import java.util.List;

public class VenuesResultFragment extends Fragment implements LocationCompletion.LocationErrorCompletion {

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
    private boolean category;
    private ArrayList priceStrings = new ArrayList<>();
    private LocationContext locationContext;
    private TextView noResults;
    private CategoryContext categoryContext;
    private LinearLayout locationClick;
    private Button button;
    private Location lastLocation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_venues_result, container, false);

        venueContext = VenueContext.context(venueContext);

        subCategoryContext = SubCategoryContext.context(subCategoryContext);

        serviceContext = ServiceContext.context(serviceContext);

        categoryContext = CategoryContext.context(categoryContext);

        locationContext = LocationContext.context(locationContext, getContext(), this);

        name = getActivity().getIntent().getStringExtra(CategoryAttributes.name);

        category = getActivity().getIntent().getBooleanExtra(Category.class.getName(), false);

        recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);

        button = (Button) rootView.findViewById(R.id.locationOptions);

        locationClick = (LinearLayout) rootView.findViewById(R.id.locationClick);

        button.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    locationClick.setBackgroundResource(R.drawable.pressed_application_background_static);

                } else if(event.getAction() != MotionEvent.ACTION_MOVE){

                    locationClick.setBackgroundResource(R.drawable.btn_default_pressed_holo_dark);
                }

                return false;

            }

        });

        recyclerView.setHasFixedSize(true);

        noResults = (TextView) rootView.findViewById(R.id.noResults);

        noResults.setVisibility(View.GONE);

        layoutManager = new GridLayoutManager(getContext(), 1);

        recyclerView.setLayoutManager(layoutManager);

        return rootView;

    }

    private void setupProgress() {

        progress = ProgressDialog.show(getContext(), getResources().getString(R.string.dialog_progress_title), getResources().getString(R.string.dialog_advanced_search_progress_message), true);

    }

    @Override
    public void onStart() {

        super.onStart();

        if (locationContext.checkPlayServices()) {

            locationContext.onStart();

        }else {

            AlertDialogError alertDialogError = new AlertDialogError();

            alertDialogError.noLocationAlert(getContext());

        }

    }

    @Override
    public void onPause() {

        super.onPause();

        subCategoryContext.cancelQuery();

        serviceContext.cancelQuery();

        venueContext.cancelQuery();

    }

    @Override
    public void onStop() {

        super.onStop();

        locationContext.onStop();

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

                    if (venueList.isEmpty()) {

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

        button.setText(city);
    }

    public void setupList(final City city) {

        setupProgress();

        setCity(city.formattedLocation());

        subCategory = subCategoryContext.findSubCategory(name);

        serviceContext.loadSubCategorizedServices(subCategory, new ServiceCompletion.ErrorCompletion() {
            @Override
            public void completion(List<Service> servicesList, AppError error) {

                progress.dismiss();

                if (servicesList != null) {

                    setupCityVenues(servicesList, city);

                } else {

                    AlertDialogError alertDialogError = new AlertDialogError();

                    alertDialogError.noInternetConnectionAlert(getContext());
                }
            }
        });
    }

    private void setupListFromCategory() {

        setupProgress();

        Category category = categoryContext.findCategory(name);

        subCategoryContext.loadSubCategories(category, new SubCategoryCompletion.ErrorCompletion() {
            @Override
            public void completion(List<SubCategory> subCategoriesList, AppError error) {

                if (subCategoriesList != null) {

                    setupCategorizedVenues(subCategoriesList);

                } else {

                    progress.dismiss();

                    AlertDialogError alertDialogError = new AlertDialogError();

                    alertDialogError.noInternetConnectionAlert(getContext());
                }
            }
        });
    }

    private void setupCategorizedVenues(List<SubCategory> subCategoriesList){

        serviceContext.loadCategorizedServices(subCategoriesList, new ServiceCompletion.ErrorCompletion() {
            @Override
            public void completion(List<Service> servicesList, AppError error) {

                progress.dismiss();

                if (servicesList != null) {

                    setupNearVenues(servicesList);

                } else {

                    AlertDialogError alertDialogError = new AlertDialogError();

                    alertDialogError.noInternetConnectionAlert(getContext());
                }
            }
        });
    }

    private void setupListFromSubCategory() {

        setupProgress();

        subCategory = subCategoryContext.findSubCategory(name);

        serviceContext.loadSubCategorizedServices(subCategory, new ServiceCompletion.ErrorCompletion() {
            @Override
            public void completion(List<Service> servicesList, AppError error) {

                progress.dismiss();

                if (servicesList != null) {

                    setupNearVenues(servicesList);

                } else {

                    AlertDialogError alertDialogError = new AlertDialogError();

                    alertDialogError.noInternetConnectionAlert(getContext());
                }
            }
        });
    }


    public void locationCompletion(Location location, AppError error) {

        if (location != null) {

            lastLocation = location;

            setupListFromSubCategory();

        }

    }

}
