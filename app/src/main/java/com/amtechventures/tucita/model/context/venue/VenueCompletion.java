package com.amtechventures.tucita.model.context.venue;

import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.error.AppError;

import java.util.List;

public class VenueCompletion {

    public interface ErrorCompletion {

        void completion(List<Venue> venueList, AppError error);

    }

}
