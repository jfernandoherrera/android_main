package com.amtechventures.tucita.model.context.review;



import com.amtechventures.tucita.model.domain.review.Review;
import com.amtechventures.tucita.model.error.AppError;

import java.util.List;

public class ReviewCompletion {

    public interface ReviewErrorCompletion {

        void completion(List<Review> reviewList, AppError error);

    }

}
