package com.amtechventures.tucita.model.context.venue;


import com.amtechventures.tucita.model.domain.venue.Venue;

import java.util.List;

public class VenueContext {

    private VenueRemote venueRemote;
    private VenueLocal venueLocal;

    public static VenueContext context(VenueContext venueContext) {

        if (venueContext == null) {

            venueContext = new VenueContext();

        }

        return  venueContext;

    }

    VenueContext(){
        venueLocal = new VenueLocal();
        venueRemote = new VenueRemote();
    }

    public List<Venue> loadLikeVenues(String likeWord, VenueCompletion.ErrorCompletion completion){

        List venues = venueLocal.loadLikeVenues(likeWord);

        venueRemote.loadLikeVenues(likeWord, completion);

        return venues;
    }


    public Venue findVenue(String nombre){

        Venue venue = venueLocal.findVenue(nombre);

        return venue;
    }
}
