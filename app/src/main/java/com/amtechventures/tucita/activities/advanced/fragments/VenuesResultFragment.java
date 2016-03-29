package com.amtechventures.tucita.activities.advanced.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class VenuesResultFragment extends Fragment  {

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

    private TextView noResults;
    private CategoryContext categoryContext;

    private City city;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_venues_result, container, false);

        venueContext = VenueContext.context(venueContext);

        subCategoryContext = SubCategoryContext.context(subCategoryContext);

        serviceContext = ServiceContext.context(serviceContext);

        categoryContext = CategoryContext.context(categoryContext);

        name = getActivity().getIntent().getStringExtra(CategoryAttributes.name);

        category = getActivity().getIntent().getBooleanExtra(Category.class.getName(), false);

        recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);

        noResults = (TextView) rootView.findViewById(R.id.noResults);

        noResults.setVisibility(View.GONE);

        layoutManager = new GridLayoutManager(getContext(), 1);

        recyclerView.setLayoutManager(layoutManager);

        setupList();

        return rootView;

    }

    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);


    }

    private void setupProgress() {

        progress = ProgressDialog.show(getContext(), getResources().getString(R.string.dialog_progress_title), getResources().getString(R.string.dialog_advanced_search_progress_message), true);

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

    public void setCity(City city) {

        this.city = city;

    }


    public void setupList() {

        setupProgress();

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

}
