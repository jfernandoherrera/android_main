package com.amtechventures.tucita.model.context.venue;


import android.location.Location;

import com.amtechventures.tucita.model.domain.city.City;
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

    public List<Venue> loadRecentVenues(){

        List venues = venueLocal.loadRecentVenues();

        return venues;
    }

    public Venue findVenue(String lookThat, String address, VenueCompletion.ErrorCompletion completion) {

        Venue venue = venueLocal.findVenue(lookThat, address);

        venueRemote.findVenue(lookThat, address, completion);

        return venue;
    }

    public void loadSubCategorizedNearVenues(List<Service> services, Location location, VenueCompletion.ErrorCompletion completion) {

        venueRemote.loadSubCategorizedNearVenues(services, location, completion);
    }

    public void loadSubCategorizedCityVenues(List<Service> services, City city, VenueCompletion.ErrorCompletion completion){

        venueRemote.loadSubCategorizedCityVenues(services,city, completion);
    }

    public void cancelQuery(){

        venueRemote.cancelQuery();
    }
}