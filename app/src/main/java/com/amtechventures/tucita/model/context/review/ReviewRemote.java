package com.amtechventures.tucita.model.context.review;


import com.amtechventures.tucita.model.domain.review.Review;
import com.amtechventures.tucita.model.domain.review.ReviewAttributes;
import com.amtechventures.tucita.model.domain.user.User;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.error.AppError;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import java.util.List;

public class ReviewRemote {

    ParseQuery<Review> query;

    private void setQuery() {

        query = Review.getQuery();

    }

    public void cancelQuery() {

        if (query != null) {

            query.cancel();

        }

    }

    public void getReviewsUser(User user, final ReviewCompletion.ReviewErrorCompletion completion){

        setQuery();

        query.whereEqualTo(ReviewAttributes.user, user.getParseUser());

        query.orderByAscending(ReviewAttributes.updatedAt);

        query.include(ReviewAttributes.venue);

        query.findInBackground(new FindCallback<Review>() {
            @Override
            public void done(List<Review> objects, ParseException e) {

                if (e != null) {

                    e.printStackTrace();

                }

                if (objects != null) {

                    try {

                        ParseObject.pinAll(objects);

                    } catch (ParseException pe) {

                        pe.printStackTrace();

                    }

                }

                AppError appError = e != null ? new AppError(Review.class.toString(), 0, null) : null;

                completion.completion(objects, appError);

            }
        });
    }

    public void getReviewsVenue(Venue venue, final ReviewCompletion.ReviewErrorCompletion completion){

        setQuery();

        query.whereEqualTo(ReviewAttributes.venue, venue);

        query.orderByAscending(ReviewAttributes.updatedAt);

        query.include(ReviewAttributes.user);

        query.findInBackground(new FindCallback<Review>() {
            @Override
            public void done(List<Review> objects, ParseException e) {

                if (e != null) {

                    e.printStackTrace();

                }

                if (objects != null) {

                    try {

                        ParseObject.pinAll(objects);

                    } catch (ParseException pe) {

                        pe.printStackTrace();

                    }

                }

                AppError appError = e != null ? new AppError(Review.class.toString(), 0, null) : null;

                completion.completion(objects, appError);

            }
        });
    }


    public void saveReview(Review review, final ReviewCompletion.ReviewErrorCompletion completion){

        review.saveInBackground(new SaveCallback() {

            @Override
            public void done(ParseException e) {

                if(e != null){

                    e.printStackTrace();

                }

                AppError appError = e != null ? new AppError(Review.class.toString(), 0, null) : null;

                completion.completion(null, appError);

            }

        });

    }

}
