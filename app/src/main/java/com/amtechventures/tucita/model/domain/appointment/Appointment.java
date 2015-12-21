package com.amtechventures.tucita.model.domain.appointment;

import com.amtechventures.tucita.model.domain.user.User;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Date;

@ParseClassName("Appointment")
public class Appointment extends ParseObject{

    public int getDuration(){

      return getInt(AppointmentAttributes.duration);
    }

    public Date getDate(){

        return getDate(AppointmentAttributes.date);
    }

    public void setDate(Date date){

        put(AppointmentAttributes.date, date);
    }

    public void setDuration(int duration){

        put(AppointmentAttributes.duration, duration);
    }

    public void setVenue(Venue venue){

        put(AppointmentAttributes.venue, venue);
    }

    public void setUser(User user){

        put(AppointmentAttributes.user, user.getParseUser());
    }

    public static ParseQuery<Appointment> getQuery(){

        return ParseQuery.getQuery(Appointment.class);
    }
}
