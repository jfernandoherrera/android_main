package com.amtechventures.tucita.model.context.review;

import com.amtechventures.tucita.model.domain.review.Review;
import com.amtechventures.tucita.model.domain.review.ReviewAttributes;
import com.amtechventures.tucita.model.domain.user.User;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class ReviewLocal {

    public List<Review> getReviewsUser(User user){

        List<Review> reviews = null;

        ParseQuery query = Review.getQuery();

        query.fromLocalDatastore();

        query.whereEqualTo(ReviewAttributes.user, user.getParseUser());

        query.orderByAscending(ReviewAttributes.updatedAt);

        query.include(ReviewAttributes.venue);

        try {

           reviews = query.find();

        } catch (ParseException e) {

            e.printStackTrace();
        }

        return reviews;

    }

    public List<Review> getReviewsVenue(Venue venue){

        List<Review> reviews = null;

        ParseQuery query = Review.getQuery();

        query.fromLocalDatastore();

        query.whereEqualTo(ReviewAttributes.venue, venue);

        query.orderByAscending(ReviewAttributes.updatedAt);

        query.include(ReviewAttributes.user);

        try {

            reviews = query.find();

        } catch (ParseException e) {

            e.printStackTrace();
        }

        return reviews;

    }

}
