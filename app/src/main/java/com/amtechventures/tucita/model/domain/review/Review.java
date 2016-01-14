package com.amtechventures.tucita.model.domain.review;


import com.amtechventures.tucita.model.domain.user.User;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@ParseClassName("Review")
public class Review extends ParseObject {


    public Venue getVenue() {

      Venue venue = (Venue) get(ReviewAttributes.venue);

        return venue;

    }

    public void setVenue(Venue venue) {

        put(ReviewAttributes.venue, venue);

    }

    public void setUser(User user) {

        put(ReviewAttributes.user, user.getParseUser());

    }

    public void setTitle(String title) {

        put(ReviewAttributes.title, title);

    }

    public void setDescription(String description){

        put(ReviewAttributes.description, description);

    }

    public void setRating(int rating){

        put(ReviewAttributes.rating, rating);

    }

    public static ParseQuery<Review> getQuery() {

        return ParseQuery.getQuery(Review.class);

    }
}
