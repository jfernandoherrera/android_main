package com.amtechventures.tucita.model.context.location;

import android.os.Bundle;
import android.content.Context;
import android.location.Location;

import com.amtechventures.tucita.model.error.AppError;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class LocationContext implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private Context context;
    private LocationRemote locationRemote;
    private GoogleApiClient googleApiClient;
    private LocationCompletion.LocationErrorCompletion completion;

    public static LocationContext context(LocationContext locationContext, Context context, LocationCompletion.LocationErrorCompletion completion) {

        if (locationContext == null) {

            locationContext = new LocationContext(context, completion);

        }

        return locationContext;

    }

    public LocationContext(Context context, LocationCompletion.LocationErrorCompletion completion) {

        this.context = context;

        this.completion = completion;

        locationRemote = new LocationRemote();

        googleApiClient = locationRemote.buildGoogleApiClient(context, this, this);

    }

    public boolean checkPlayServices() {

        boolean result = true;

        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);

        if (resultCode != ConnectionResult.SUCCESS) {

            result = false;

        }

        return result;

    }

    public void onStart() {

        if (googleApiClient != null) {

            googleApiClient.connect();

        }

    }

    public void onStop() {

        if (googleApiClient.isConnected()) {

            googleApiClient.disconnect();

        }

    }

    @Override
    public void onConnected(Bundle bundle) {

        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

        completion.locationCompletion(location, null);

    }

    @Override
    public void onConnectionSuspended(int i) {

        googleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        completion.locationCompletion(null, new AppError("", 0, null));

    }

}
