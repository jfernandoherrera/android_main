package com.amtechventures.tucita.model.domain.appointment;

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
    public static ParseQuery<Appointment> getQuery(){

        return ParseQuery.getQuery(Appointment.class);
    }
}
