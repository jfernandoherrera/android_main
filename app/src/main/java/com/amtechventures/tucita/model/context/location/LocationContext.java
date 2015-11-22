package com.amtechventures.tucita.model.context.location;


import android.content.Context;
import android.location.Location;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class LocationContext {

    private GoogleApiClient googleApiClient;
    private LocationRemote locationRemote;
    private Location lastLocation;

    public static LocationContext context(LocationContext locationContext, Context context, GoogleApiClient.ConnectionCallbacks callbacks, GoogleApiClient.OnConnectionFailedListener failedListener) {

        if (locationContext == null) {

                locationContext = new LocationContext(context, callbacks, failedListener);
        }

        return  locationContext;
    }

    public LocationContext(Context context, GoogleApiClient.ConnectionCallbacks callbacks, GoogleApiClient.OnConnectionFailedListener failedListener){

        locationRemote = new LocationRemote();

        googleApiClient = locationRemote.buildGoogleApiClient(context, callbacks, failedListener);

        googleApiClient.connect();
    }

    public void setLocation(){

        lastLocation = LocationServices.FusedLocationApi.getLastLocation(

                googleApiClient);

        }

    public Location getLastLocation(){

        return lastLocation;
    }
}
