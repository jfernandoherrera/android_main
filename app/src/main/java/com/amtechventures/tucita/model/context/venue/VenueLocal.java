package com.amtechventures.tucita.model.context.venue;



import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.domain.venue.VenueAttributes;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class VenueLocal {

    public Venue findVenue(String nombre){

        Venue find = new Venue();

        List<Venue> venuesList = loadLikeVenues(nombre);

        for(ParseObject venue : venuesList){

            String name = venue.getString(VenueAttributes.name);

            if(name.equals(nombre)){

                find.setName(name);

                String description = venue.getString(VenueAttributes.description);

                find.setDescription(description);

                int rating = venue.getInt(VenueAttributes.rating);

                find.setRating(rating);
            }
        }
        return find;
    }

    public List<Venue> loadLikeVenues(String likeWord){

        ParseQuery queryName = Venue.getQuery();

        queryName.whereContains(VenueAttributes.name, likeWord);

        ParseQuery queryAddress = Venue.getQuery();

        queryAddress.whereContains(VenueAttributes.address, likeWord);

        List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();

        queries.add(queryAddress);

        queries.add(queryName);

        ParseQuery query = ParseQuery.or(queries) ;

        query.fromLocalDatastore();

        List<Venue> venueList = new ArrayList<>();

        try {

           venueList = query.find();

        } catch (ParseException e) {

            e.printStackTrace();
        }
        return venueList;
    }
}
