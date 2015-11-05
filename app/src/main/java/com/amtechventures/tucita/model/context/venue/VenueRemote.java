package com.amtechventures.tucita.model.context.venue;

import com.amtechventures.tucita.model.domain.service.Service;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.error.AppError;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class VenueRemote {


    public void loadLikeVenues(String likeWord, final VenueCompletion.ErrorCompletion completion )
    {

       ParseQuery query = Venue.getQuery();

        query.findInBackground(new FindCallback<Venue>() {
            @Override
            public void done(List objects, ParseException e) {

                if(objects != null){
                    try {

                        ParseObject.pinAll(objects);

                    } catch (ParseException pe) {}

                }

                AppError appError = e != null ? new AppError(Venue.class.toString(), 0, null) : null;

                completion.completion(objects,appError);
            }
        });


    }
}
