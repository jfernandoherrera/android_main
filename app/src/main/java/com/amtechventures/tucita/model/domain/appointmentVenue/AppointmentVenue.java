package com.amtechventures.tucita.model.domain.appointmentVenue;

import com.amtechventures.tucita.model.domain.appointment.Appointment;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@ParseClassName("AppointmentVenue")
public class AppointmentVenue extends ParseObject{

    public void putVenue(Venue venue) {

        put(AppointmentVenueAttributes.venue, venue);

    }

    public void putRanked(boolean ranked) {

        put(AppointmentVenueAttributes.ranked, ranked);

    }

    public Venue getVenue() {

       return (Venue) get(AppointmentVenueAttributes.venue);

    }

    public boolean getRanked() {

        return getBoolean(AppointmentVenueAttributes.ranked);

    }

    public ParseQuery getQuery() {

        return ParseQuery.getQuery(getClassName());

    }

}
