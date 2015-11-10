package com.amtechventures.tucita.model.context.openingHour;


import com.amtechventures.tucita.model.domain.openingHour.OpeningHour;
import com.amtechventures.tucita.model.error.AppError;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class OpeningHourRemote {


    public void loadOpeningHours(ParseQuery<OpeningHour> openingHourRemoteQuery, final OpeningHourCompletion.OpeningHourErrorCompletion completion){

        openingHourRemoteQuery.findInBackground(new FindCallback<OpeningHour>() {
            @Override
            public void done(List<OpeningHour> objects, com.parse.ParseException e) {

                if(objects != null){
                    try {

                        ParseObject.pinAll(objects);

                    } catch (ParseException pe) {}

                }

                AppError appError = e != null ? new AppError(OpeningHour.class.toString(), 0, null) : null;

                completion.completion(objects,appError);
            }

        });

    }

}
