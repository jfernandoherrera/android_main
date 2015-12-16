package com.amtechventures.tucita.model.context.appointment;


import com.amtechventures.tucita.model.domain.appointment.Appointment;
import com.amtechventures.tucita.model.domain.appointment.AppointmentAttributes;
import com.amtechventures.tucita.model.domain.service.Service;
import com.parse.ParseQuery;

import java.util.Date;
import java.util.List;

public class AppointmentRemote {

    public void loadAppointmentsDateVenue(List<Service> services, Date date, AppointmentCompletion.AppointmentErrorCompletion completion){

        ParseQuery query = Appointment.getQuery();

        query.whereContainedIn(AppointmentAttributes.service, services);


    }
}
