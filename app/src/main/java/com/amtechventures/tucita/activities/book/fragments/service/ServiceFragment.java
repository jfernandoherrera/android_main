package com.amtechventures.tucita.activities.book.fragments.service;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.model.domain.service.Service;
import com.amtechventures.tucita.activities.book.adapters.ExpandableParentAdapter;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.domain.venue.VenueAttributes;
import com.amtechventures.tucita.utils.views.AppTextView;
import com.amtechventures.tucita.utils.views.ServiceAddView;
import com.amtechventures.tucita.utils.views.ViewUtils;

public class ServiceFragment extends Fragment {

    private RelativeLayout relativeLayout;
    private ServiceAddView serviceAddView;
    private ExpandableParentAdapter parentAdapter;
    private ExpandableListView listViewParent;
    private Service service;
    private LayoutInflater inflater;
    private OnServiceSelected listener;
    private Typeface typeface;
    private Venue venue;
    private AppTextView location;

    public interface OnServiceSelected {

        void onServiceBook(Service service);

    }

    public Service getService() {

        return service;

    }

    public void setVenue(Venue venue) {

        this.venue = venue;

        setupAddressAndLocation();

    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

        listener = (OnServiceSelected) context;

    }

    public void setTypeface(Typeface typeface) {

        this.typeface = typeface;

    }

    @Override
    public void onDetach() {

        super.onDetach();

        listener = null;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_services, container, false);

        relativeLayout = (RelativeLayout) rootView.findViewById(R.id.serviceContainer);

        serviceAddView = (ServiceAddView) rootView.findViewById(R.id.first);

        location = (AppTextView) rootView.findViewById(R.id.textLocation);

        listViewParent = (ExpandableListView) rootView.findViewById(R.id.listViewDescription);

        serviceAddView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                listener.onServiceBook(service);

            }

        });

        this.inflater = inflater;

        serviceAddView.setTypeface(typeface);

        return rootView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

    }

    public void setup(Service service, boolean added) {

        setService(service, added);

        setDescription();

    }

    private void setDescription() {

        String description = service.getDescription();

        if (description != null) {

            listViewParent.setVisibility(View.VISIBLE);

            ViewUtils viewUtils = new ViewUtils(getContext());

            parentAdapter = new ExpandableParentAdapter(description, listViewParent, viewUtils);

            parentAdapter.setInflater(inflater);

            listViewParent.setAdapter(parentAdapter);

        } else {

            listViewParent.setVisibility(View.GONE);

        }

    }

    public void setService(Service service, boolean added) {

        this.service = service;

        if (added) {

            serviceAddView.setPlus(false);

        } else {

            serviceAddView.setPlus(true);

        }

        serviceAddView.setImageView();

        serviceAddView.setName(service.getName());

        serviceAddView.setDuration(service.getDurationInfo());

        serviceAddView.setPrice(service.getPrice());

    }



    private void setupAddressAndLocation() {

        if(venue != null ) {

            String locationString = venue.getCity().formattedLocation();

            String address = locationString + " " + venue.getAddress();

            location.setText(address);

            location.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    String url = VenueAttributes.linkToLocation + venue.getLatitude() + "," + venue.getLongitude();

                    openWebURL(url);

                }

            });

        }

    }
    public void openWebURL(String inURL) {

        Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse(inURL));

        startActivity(browse);

    }

    public void setServiceState(boolean state) {

        serviceAddView.setPlus(state);

    }

    public boolean getServiceState() {

        return serviceAddView.getPlus();

    }

    public void setMarginBottomToShoppingCarVisible(int shoppingCarHeight) {

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        params.setMargins(0, 0, 0, shoppingCarHeight);

        relativeLayout.setLayoutParams(params);

    }

    public void setMarginBottomToShoppingCarGone() {

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        params.setMargins(0, 0, 0, 0);

        relativeLayout.setLayoutParams(params);

    }

    public void checkImage() {

        serviceAddView.setImageView();

    }

}
