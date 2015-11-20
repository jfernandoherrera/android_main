package com.amtechventures.tucita.model.context.venue;


import android.location.Location;

import com.amtechventures.tucita.model.domain.service.Service;
import com.amtechventures.tucita.model.domain.venue.Venue;

import java.util.List;

public class VenueContext {

    private VenueRemote venueRemote;
    private VenueLocal venueLocal;

    public static VenueContext context(VenueContext venueContext) {

        if (venueContext == null) {

            venueContext = new VenueContext();

        }

        return venueContext;

    }

    VenueContext() {
        venueLocal = new VenueLocal();

        venueRemote = new VenueRemote();
    }

    public List<Venue> loadLikeVenues(String likeWord, VenueCompletion.ErrorCompletion completion) {

        List venues = venueLocal.loadLikeVenues(likeWord);

        venueRemote.loadLikeVenues(likeWord, completion);

        return venues;
    }


    public Venue findVenue(String lookThat, String address, VenueCompletion.ErrorCompletion completion) {

        Venue venue = venueLocal.findVenue(lookThat, address);

        venueRemote.findVenue(lookThat, address, completion);

        return venue;
    }

    public List<Venue> loadSubCategorizedNearVenues(List<Service> services, Location location, VenueCompletion.ErrorCompletion completion) {

        List<Venue> venues = venueLocal.loadSubCategorizedVenues(services);

        venueRemote.loadSubCategorizedNearVenues(services,location, completion);

        return venues;
    }
}