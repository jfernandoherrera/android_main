package com.amtechventures.tucita.model.context.appointment;


import android.util.Log;

import com.amtechventures.tucita.model.domain.appointment.Appointment;
import com.amtechventures.tucita.model.domain.appointment.AppointmentAttributes;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.error.AppError;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

public class AppointmentRemote {

    final int issueDate = 1900;


    ParseQuery<Appointment> query;

    private void setQuery(){

        query = Appointment.getQuery();
    }

    public void cancelQuery(){

        if(query != null){

            query.cancel();
        }
    }

    public void loadAppointmentsDateVenue(Venue venue, Date date, final AppointmentCompletion.AppointmentErrorCompletion completion){

        setQuery();

        query.whereGreaterThan(AppointmentAttributes.date, date);

        TimeZone timezone = TimeZone.getDefault();

        Calendar calendar = new GregorianCalendar(timezone);

        calendar.set(date.getYear() + issueDate, date.getMonth(), date.getDate(), 1, 1);

        calendar.add(Calendar.DAY_OF_MONTH, 1);

        Date oneMoreDay = calendar.getTime();

        Log.i("onemore", date.toString() + oneMoreDay.toString());
        query.whereLessThan(AppointmentAttributes.date, oneMoreDay);


        //query.whereEqualTo(AppointmentAttributes.venue, venue.getObjectId());

        query.findInBackground(new FindCallback<Appointment>() {
            @Override
            public void done(List<Appointment> objects, ParseException e) {

                Log.i("derss", String.valueOf(objects.size()));

                AppError appError = e != null ? new AppError(Appointment.class.toString(), 0, null) : null;

                completion.completion(objects, appError);
            }
        });
    }

    public void placeOrder(Appointment appointment, final  AppointmentCompletion.AppointmentErrorCompletion completion){

        appointment.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                AppError appError = e != null ? new AppError(Appointment.class.toString(), 0, null) : null;

                completion.completion(null, appError);
            }
        });
    }
}
