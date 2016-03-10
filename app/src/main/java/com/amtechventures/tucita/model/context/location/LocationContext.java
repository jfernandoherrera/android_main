package com.amtechventures.tucita.model.context.location;

import android.location.Location;
import android.os.Bundle;
import android.content.Context;
import com.amtechventures.tucita.model.error.AppError;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class LocationContext implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, com.google.android.gms.location.LocationListener {

    private Context context;
    private LocationRemote locationRemote;
    private GoogleApiClient googleApiClient;
    private int timeMillis = 5000;


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

        LocationRequest locationRequest = LocationRequest.create();

        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        locationRequest.setInterval(timeMillis);

        timeMillis += 10000;

        LocationServices.FusedLocationApi.requestLocationUpdates(

                googleApiClient, locationRequest, this);

    }

    @Override
    public void onConnectionSuspended(int i) {

        googleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        completion.locationCompletion(null, new AppError(connectionResult.getErrorMessage(), 0, null));

    }


    @Override
    public void onLocationChanged(Location location) {

        completion.locationCompletion(location, null);

    }
}
