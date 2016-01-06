package com.amtechventures.tucita.model.context.appointment;

import com.amtechventures.tucita.model.context.venue.VenueCompletion;
import com.amtechventures.tucita.model.domain.appointment.Appointment;
import com.amtechventures.tucita.model.domain.service.Service;
import com.amtechventures.tucita.model.domain.user.User;
import com.amtechventures.tucita.model.domain.venue.Venue;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AppointmentContext {

    private AppointmentRemote appointmentRemote;
    private AppointmentLocal appointmentLocal;

    public static AppointmentContext context(AppointmentContext appointmentContext){

        if(appointmentContext == null){

            appointmentContext = new AppointmentContext();
        }

        return appointmentContext;
    }

    public AppointmentContext(){

        appointmentLocal = new AppointmentLocal();

        appointmentRemote = new AppointmentRemote();
    }

    public void loadAppointmentsDateVenue(Venue venue, Calendar date, AppointmentCompletion.AppointmentErrorCompletion completion){

        appointmentRemote.loadAppointmentsDateVenue(venue, date, completion);
    }

    public void placeOrder(Appointment appointment, AppointmentCompletion.AppointmentErrorCompletion completion){

        appointmentRemote.placeOrder(appointment, completion);
    }

    public List loadPendingAppointments(User user, AppointmentCompletion.AppointmentErrorCompletion completion){

        List appointments = appointmentLocal.loadPendingAppointments(user);

        appointmentRemote.loadPendingAppointments(user, completion);

        return  appointments;
    }
}
