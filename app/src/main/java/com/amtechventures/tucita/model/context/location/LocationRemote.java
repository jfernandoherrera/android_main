package com.amtechventures.tucita.model.context.location;

import android.content.Context;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class LocationRemote {

    protected synchronized GoogleApiClient buildGoogleApiClient(Context context, GoogleApiClient.ConnectionCallbacks callback, GoogleApiClient.OnConnectionFailedListener listener) {

        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(callback)
                .addOnConnectionFailedListener(listener)
                .addApi(LocationServices.API)
                .build();

        return googleApiClient;

    }

}
