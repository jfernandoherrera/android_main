package com.amtechventures.tucita.model.context.venue;


import com.amtechventures.tucita.model.domain.category.CategoryAttributes;
import com.amtechventures.tucita.model.domain.service.Service;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class VenueLocal {

    public List<Venue> loadLikeVenues(String likeWord){

        ParseQuery query = Venue.getQuery();

        query.fromLocalDatastore();
        query.whereContains(CategoryAttributes.name,likeWord);

        List<Venue> venueList = new ArrayList<>();

        try {

           venueList = query.find();

        } catch (ParseException e) {

            e.printStackTrace();
        }
        return venueList;
    }
}
