package com.amtechventures.tucita.model.context.venue;



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.domain.venue.VenueAttributes;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class VenueLocal {

    public Venue findVenue(String lookThat){

        Venue find = new Venue();

        List<Venue> venuesList = loadLikeVenues(lookThat);

        for(ParseObject venue : venuesList){

            String name = venue.getString(VenueAttributes.name);

            if(name.equals(lookThat)){

                find.setName(name);

                String description = venue.getString(VenueAttributes.description);

                find.setDescription(description);

                int rating = venue.getInt(VenueAttributes.rating);

                find.setRating(rating);

                ParseFile picture = venue.getParseFile(VenueAttributes.picture);

                Bitmap bm = null;

                try {

                  bm = BitmapFactory.decodeByteArray(picture.getData(), 0, picture.getData().length);

                } catch (ParseException e) {

                    e.printStackTrace();

                }

                find.setPicture(picture, bm);

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
