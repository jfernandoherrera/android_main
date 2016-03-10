package com.amtechventures.tucita.model.context.appointmentVenue;


import com.amtechventures.tucita.model.domain.appointmentVenue.AppointmentVenue;
import com.amtechventures.tucita.model.domain.user.User;
import com.amtechventures.tucita.model.domain.user.UserAttributes;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.error.AppError;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class AppointmentVenueRemote {

public void saveAppointmentVenue(AppointmentVenue appointmentVenue, final AppointmentVenueCompletion.ErrorCompletion completion) {

    appointmentVenue.saveInBackground(new SaveCallback() {

        @Override
        public void done(ParseException e) {

            AppError appError = e != null ? new AppError(AppointmentVenue.class.toString(), 0, null) : null;

            if(e != null) {

            e.printStackTrace();

            }

            completion.completion(appError);

        }

    });

}
}
