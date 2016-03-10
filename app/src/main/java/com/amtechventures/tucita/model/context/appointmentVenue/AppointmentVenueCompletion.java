package com.amtechventures.tucita.model.context.appointmentVenue;


import com.amtechventures.tucita.model.domain.appointmentVenue.AppointmentVenue;
import com.amtechventures.tucita.model.error.AppError;

import java.util.List;

public class AppointmentVenueCompletion {


    public interface ErrorCompletion {

        void completion(AppError error);

        void completion(List<AppointmentVenue> appointmentVenues, AppError error);

    }



}
