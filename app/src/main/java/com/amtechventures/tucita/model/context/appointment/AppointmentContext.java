package com.amtechventures.tucita.model.context.appointment;

import com.amtechventures.tucita.model.domain.service.Service;
import com.amtechventures.tucita.model.domain.venue.Venue;

import java.util.Date;
import java.util.List;

public class AppointmentContext {

    private AppointmentRemote appointmentRemote;

    public static AppointmentContext context(AppointmentContext appointmentContext){

        if(appointmentContext == null){

            appointmentContext = new AppointmentContext();
        }

        return appointmentContext;
    }

    public AppointmentContext(){

        appointmentRemote = new AppointmentRemote();
    }

    public void loadAppointmentsDateVenue(Venue venue, Date date, AppointmentCompletion.AppointmentErrorCompletion completion){

        appointmentRemote.loadAppointmentsDateVenue(venue, date, completion);
    }
}
