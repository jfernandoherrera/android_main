package com.amtechventures.tucita.model.domain.appointment;

import com.amtechventures.tucita.model.domain.user.User;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

@ParseClassName("Appointment")
public class Appointment extends ParseObject{

    public int[] getDuration(){

        int[] duration = new int[2];
        int durationHours = 0;
        int durationMinutes = getInt(AppointmentAttributes.duration);

        if(durationMinutes >= 60){

            durationHours = durationMinutes / 60;

            durationMinutes = durationMinutes - (60 * durationHours);
        }

        duration[0] = durationHours;

        duration[1] = durationMinutes;

        return duration;
    }

    public Date getDateEnd(){

        TimeZone timezone = TimeZone.getDefault();

        int issueDate = 1900;

        Calendar calendar = new GregorianCalendar(timezone);

        Date date = getDate(AppointmentAttributes.date);

        calendar.set(date.getYear() + issueDate, date.getMonth(), date.getDate(), date.getHours(), date.getMinutes());

        int[] duration = getDuration();

        calendar.add(Calendar.HOUR_OF_DAY, duration[0]);

        calendar.add(Calendar.MINUTE, duration[1]);

        return calendar.getTime();
    }

    public Date getDate(){

        return getDate(AppointmentAttributes.date);
    }

    public void setDate(Calendar date){

        put(AppointmentAttributes.date, date.getTime());
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
