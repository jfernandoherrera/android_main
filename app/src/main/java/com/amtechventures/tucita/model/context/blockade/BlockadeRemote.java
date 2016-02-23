package com.amtechventures.tucita.model.context.blockade;



import com.amtechventures.tucita.model.domain.blockade.Blockade;
import com.amtechventures.tucita.model.domain.blockade.BlockadeAttributes;
import com.amtechventures.tucita.model.domain.slot.Slot;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.domain.venue.VenueAttributes;
import com.amtechventures.tucita.model.error.AppError;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.SaveCallback;

import java.util.Calendar;
import java.util.List;

public class BlockadeRemote {


    public void getBlockadeFromVenue(Venue venue, final BlockadeCompletion.ErrorCompletion completion){

        ParseRelation<Blockade> object = (ParseRelation) venue.get(VenueAttributes.blockades);

        ParseQuery<Blockade> query = object.getQuery();

        Calendar current = Calendar.getInstance();

        query.whereGreaterThan(BlockadeAttributes.date, current.getTime());

        query.findInBackground(new FindCallback<Blockade>() {

            @Override
            public void done(List<Blockade> objects, ParseException e) {

                AppError appError = e != null ? new AppError(Blockade.class.toString(), 0, null) : null;

                completion.completion(objects, appError);

            }
        });
    }

    public void saveBlockade(Blockade blockade, final BlockadeCompletion.ErrorCompletion completion) {

        blockade.saveInBackground(new SaveCallback() {

            @Override
            public void done(ParseException e) {

                AppError appError = e != null ? new AppError(Blockade.class.toString(), 0, null) : null;

                completion.completion(null, appError);

            }
        });

    }

}
