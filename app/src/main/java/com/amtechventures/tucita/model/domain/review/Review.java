package com.amtechventures.tucita.model.domain.review;


import com.amtechventures.tucita.model.domain.venue.Venue;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@ParseClassName("Review")
public class Review extends ParseObject{


    public Venue getVenue(){

      Venue venue = (Venue) get(ReviewAttributes.venue);

        return venue;

    }

    public static ParseQuery<Review> getQuery() {

        return ParseQuery.getQuery(Review.class);

    }
}
