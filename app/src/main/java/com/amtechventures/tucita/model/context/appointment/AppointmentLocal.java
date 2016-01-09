package com.amtechventures.tucita.model.context.appointment;

import com.amtechventures.tucita.model.domain.appointment.Appointment;
import com.amtechventures.tucita.model.domain.appointment.AppointmentAttributes;
import com.amtechventures.tucita.model.domain.user.User;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

public class AppointmentLocal {

    public List loadUserAppointments(User user) {

        List venues = null;

        ParseQuery<Appointment> query = Appointment.getQuery();

        query.fromLocalDatastore();

        query.whereEqualTo(AppointmentAttributes.user, user.getParseUser());

        query.orderByDescending(AppointmentAttributes.date);

        query.include(AppointmentAttributes.venue);

        try {

            venues = query.find();

        } catch (ParseException e) {

            e.printStackTrace();

        }

        return venues;

    }

}
