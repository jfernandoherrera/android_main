package com.amtechventures.tucita.model.context.appointment;

import com.amtechventures.tucita.model.domain.appointment.Appointment;
import com.amtechventures.tucita.model.domain.service.Service;
import com.amtechventures.tucita.model.domain.venue.Venue;

import java.util.Calendar;
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

    public void loadAppointmentsDateVenue(Venue venue, Calendar date, AppointmentCompletion.AppointmentErrorCompletion completion){

        appointmentRemote.loadAppointmentsDateVenue(venue, date, completion);
    }

    public void placeOrder(Appointment appointment, AppointmentCompletion.AppointmentErrorCompletion completion){

        appointmentRemote.placeOrder(appointment,completion);
    }
}
