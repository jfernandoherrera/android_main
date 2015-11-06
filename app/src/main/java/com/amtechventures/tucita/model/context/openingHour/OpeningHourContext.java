package com.amtechventures.tucita.model.context.openingHour;

import android.util.Log;

import com.amtechventures.tucita.model.domain.openingHour.OpeningHour;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.domain.venue.VenueAttributes;
import com.parse.ParseQuery;
import com.parse.ParseRelation;

import java.util.List;

public class OpeningHourContext {

    private OpeningHourLocal openingHourLocal;
    private OpeningHourRemote openingHourRemote;

    public OpeningHourContext(){
        openingHourLocal = new OpeningHourLocal();
        openingHourRemote = new OpeningHourRemote();
    }

    public static OpeningHourContext context(OpeningHourContext openingHourContext) {

        if (openingHourContext == null) {

            openingHourContext = new OpeningHourContext();

        }

        return  openingHourContext;

    }
    public List<OpeningHour> loadOpeningHours(Venue venue, OpeningHourCompletion.OpeningHourErrorCompletion completion){

        List openingHours;


        ParseRelation object = (ParseRelation) venue.get(VenueAttributes.openingHours);

        Log.i("hgfd", object.toString());
        ParseQuery<OpeningHour> queryLocal = object.getQuery();

        openingHours = openingHourLocal.loadOpeningHours(queryLocal);

        ParseQuery<OpeningHour> queryRemote = object.getQuery();

        openingHourRemote.loadOpeningHours(queryRemote, completion);

        return openingHours;
    }

}
