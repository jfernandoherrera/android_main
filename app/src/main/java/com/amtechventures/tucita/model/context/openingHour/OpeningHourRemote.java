package com.amtechventures.tucita.model.context.openingHour;

import com.amtechventures.tucita.model.domain.openingHour.OpeningHour;
import com.amtechventures.tucita.model.domain.openingHour.OpeningHourAttributes;
import com.amtechventures.tucita.model.error.AppError;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class OpeningHourRemote {

    private ParseQuery<OpeningHour> query;

    public void cancelQuery() {

        if (query != null) {

            query.cancel();

        }

    }

    public void loadOpeningHours(ParseQuery<OpeningHour> openingHourRemoteQuery, final OpeningHourCompletion.OpeningHourErrorCompletion completion) {

        query = openingHourRemoteQuery;

        query.findInBackground(new FindCallback<OpeningHour>() {

            @Override
            public void done(List<OpeningHour> objects, com.parse.ParseException e) {

                if (objects != null) {

                    try {

                        ParseObject.pinAll(objects);

                    } catch (ParseException pe) {}

                }

                AppError appError = e != null ? new AppError(OpeningHour.class.toString(), 0, null) : null;

                completion.completion(objects, appError);

            }

        });

    }

    public void loadDayOpeningHours(ParseQuery<OpeningHour> openingHourRemoteQuery, int day, final OpeningHourCompletion.OpeningHourErrorCompletion completion) {

        query = openingHourRemoteQuery;

        query.whereEqualTo(OpeningHourAttributes.day, day);

        query.findInBackground(new FindCallback<OpeningHour>() {

            @Override
            public void done(List<OpeningHour> objects, com.parse.ParseException e) {

                if (objects != null) {

                    try {

                        ParseObject.pinAll(objects);

                    } catch (ParseException pe) {}

                }

                AppError appError = e != null ? new AppError(OpeningHour.class.toString(), 0, null) : null;

                completion.completion(objects, appError);

            }

        });

    }

}
