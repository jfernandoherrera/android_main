package com.amtechventures.tucita.activities.advanced.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.model.context.category.CategoryContext;
import com.amtechventures.tucita.model.context.location.LocationCompletion;
import com.amtechventures.tucita.model.context.location.LocationContext;
import com.amtechventures.tucita.model.context.service.ServiceCompletion;
import com.amtechventures.tucita.model.context.service.ServiceContext;
import com.amtechventures.tucita.model.context.subcategory.SubCategoryContext;
import com.amtechventures.tucita.model.context.venue.VenueCompletion;
import com.amtechventures.tucita.model.context.venue.VenueContext;
import com.amtechventures.tucita.model.domain.category.CategoryAttributes;
import com.amtechventures.tucita.model.domain.service.Service;
import com.amtechventures.tucita.model.domain.subcategory.SubCategory;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.domain.venue.VenueAttributes;
import com.amtechventures.tucita.model.error.AppError;
import com.amtechventures.tucita.utils.views.AlertDialogError;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class LocationOptionsFragment extends Fragment implements LocationCompletion.LocationErrorCompletion{

    private ServiceContext serviceContext;
    private ProgressDialog progress;
    private CategoryContext categoryContext;
    private String name;
    private SubCategoryContext subCategoryContext;
    private TextView noResults;
    private Location lastLocation;
    private SubCategory subCategory;
    private VenueContext venueContext;
    private LocationContext locationContext;
    private MapView mapView;
    private GoogleMap map;

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

        subCategoryContext = SubCategoryContext.context(subCategoryContext);

        if (location != null) {

            lastLocation = location;

            setupListFromSubCategory();

        }

    }


    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

    }

    @Override
    public void onDetach() {

        super.onDetach();

    }

    private void setupProgress() {

        progress = ProgressDialog.show(getContext(), getResources().getString(R.string.dialog_progress_title), getResources().getString(R.string.dialog_advanced_search_progress_message), true);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        venueContext = VenueContext.context(venueContext);

        serviceContext = ServiceContext.context(serviceContext);

        categoryContext = CategoryContext.context(categoryContext);

        locationContext = LocationContext.context(locationContext, getContext(), this);
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

        mapView.onPause();

        super.onPause();

        if(subCategoryContext != null) {

            subCategoryContext.cancelQuery();

            serviceContext.cancelQuery();

            venueContext.cancelQuery();

        }

    }

    @Override
    public void onStop() {

        super.onStop();

        locationContext.onStop();

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

                        } else {

                            LatLngBounds.Builder builder = new LatLngBounds.Builder();

                            for(Venue venue : venueList) {

                                double latitude = venue.getLatitude();

                                double longitude = venue.getLongitude();

                                MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_icon_map));

                                LatLng location = new LatLng(latitude, longitude);

                                builder.include(location);

                                markerOptions.position(location);

                                map.addMarker(markerOptions);

                            }

                            LatLngBounds bounds = builder.build();

                            int padding = 20;

                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);

                            map.animateCamera(cameraUpdate);

                        }

                    }
                }

            });

        }else{

            AlertDialogError alertDialogError = new AlertDialogError();

            alertDialogError.noLocationAlert(getContext());

        }
    }
    @Override
    public void onResume() {

        mapView.onResume();

        super.onResume();

    }


    @Override
    public void onDestroy() {

        super.onDestroy();

        mapView.onDestroy();

    }

    @Override
    public void onLowMemory() {

        super.onLowMemory();

        mapView.onLowMemory();

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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_location_options, container, false);

        name = getActivity().getIntent().getStringExtra(CategoryAttributes.name);

        mapView = (MapView) rootView;

        mapView.onCreate(savedInstanceState);

        map = mapView.getMap();

        map.getUiSettings().setMyLocationButtonEnabled(false);

        map.setMyLocationEnabled(true);

            MapsInitializer.initialize(this.getActivity());

        return rootView;

    }

}
