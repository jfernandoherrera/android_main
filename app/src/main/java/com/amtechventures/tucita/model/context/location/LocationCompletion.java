package com.amtechventures.tucita.model.context.location;

import android.location.Location;

import com.amtechventures.tucita.model.error.AppError;

public class LocationCompletion {

    public interface LocationErrorCompletion {

        void locationCompletion(Location location, AppError error);

    }

}
