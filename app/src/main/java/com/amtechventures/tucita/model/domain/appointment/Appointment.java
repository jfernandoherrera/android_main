package com.amtechventures.tucita.model.domain.appointment;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@ParseClassName("Appointment")
public class Appointment extends ParseObject{

    public static ParseQuery<Appointment> getQuery(){

        return ParseQuery.getQuery(Appointment.class);
    }
}
