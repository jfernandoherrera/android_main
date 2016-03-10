package com.amtechventures.tucita.model.context.review;

import com.amtechventures.tucita.model.domain.review.Review;
import com.amtechventures.tucita.model.domain.user.User;
import com.amtechventures.tucita.model.domain.venue.Venue;

import java.util.List;

public class ReviewContext {

    ReviewLocal reviewLocal;
    ReviewRemote reviewRemote;

    public static ReviewContext context(ReviewContext reviewContext) {

        if (reviewContext == null) {

            reviewContext = new ReviewContext();

        }

        return reviewContext;

    }

    public ReviewContext() {

        reviewLocal = new ReviewLocal();

        reviewRemote = new ReviewRemote();

    }

    public void cancelQuery() {

        reviewRemote.cancelQuery();

    }

    public void saveReview(Review review, final ReviewCompletion.ReviewErrorCompletion completion){

        reviewRemote.saveReview(review, completion);

    }

    public List<Review> getReviewsUser(User user, ReviewCompletion.ReviewErrorCompletion completion) {

        reviewRemote.getReviewsUser(user, completion);

       List<Review> reviews = reviewLocal.getReviewsUser(user);

        return reviews;
    }

    public List<Review> getReviewsVenue(Venue venue, ReviewCompletion.ReviewErrorCompletion completion) {

       reviewRemote.getReviewsVenue(venue, completion);

        List<Review> reviews = reviewLocal.getReviewsVenue(venue);

        return reviews;
    }
}
