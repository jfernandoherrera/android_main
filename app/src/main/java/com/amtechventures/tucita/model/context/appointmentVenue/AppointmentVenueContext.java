package com.amtechventures.tucita.model.context.appointmentVenue;


import com.amtechventures.tucita.model.domain.appointment.Appointment;
import com.amtechventures.tucita.model.domain.appointmentVenue.AppointmentVenue;

public class AppointmentVenueContext {

    AppointmentVenueRemote appointmentVenueRemote;


    public static AppointmentVenueContext context(AppointmentVenueContext  appointmentVenueContext ) {

        if (appointmentVenueContext == null) {

            appointmentVenueContext = new AppointmentVenueContext();

        }

        return appointmentVenueContext;

    }


    public AppointmentVenueContext() {

        appointmentVenueRemote = new AppointmentVenueRemote();

    }

    public void saveAppointmentVenue(AppointmentVenue appointmentVenue, AppointmentVenueCompletion.ErrorCompletion completion) {

        appointmentVenueRemote.saveAppointmentVenue(appointmentVenue, completion);

    }

}
