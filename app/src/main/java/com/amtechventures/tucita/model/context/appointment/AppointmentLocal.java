package com.amtechventures.tucita.model.context.appointment;

import com.amtechventures.tucita.model.domain.appointment.Appointment;
import com.amtechventures.tucita.model.domain.appointment.AppointmentAttributes;
import com.amtechventures.tucita.model.domain.user.User;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.Calendar;
import java.util.List;

public class AppointmentLocal {

    public List loadPendingAppointments(User user){

        List appointments = null;

        ParseQuery<Appointment> query = Appointment.getQuery();

        query.fromLocalDatastore();

        query.whereEqualTo(AppointmentAttributes.user, user.getParseUser());

        Calendar calendar = Calendar.getInstance();

        query.whereGreaterThan(AppointmentAttributes.date, calendar.getTime());

        query.include(AppointmentAttributes.venue);

        try {
            appointments = query.find();

        } catch (ParseException e) {

            e.printStackTrace();
        }
        return appointments;
    }

    public List loadAppointmentsWithVenue(User user){

        List venues = null;

        ParseQuery<Appointment> query = Appointment.getQuery();

        query.fromLocalDatastore();

        query.whereEqualTo(AppointmentAttributes.user, user.getParseUser());

        query.include(AppointmentAttributes.venue);

        try {
            venues = query.find();

        } catch (ParseException e) {

            e.printStackTrace();
        }
        return venues;
    }
}
