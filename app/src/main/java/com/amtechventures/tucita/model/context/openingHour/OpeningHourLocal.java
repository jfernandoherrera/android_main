package com.amtechventures.tucita.model.context.openingHour;


import com.amtechventures.tucita.model.domain.openingHour.OpeningHour;

import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class OpeningHourLocal {

    public List<OpeningHour> loadOpeningHours(ParseQuery<OpeningHour> openingHoursLocalQuery){

        openingHoursLocalQuery.fromLocalDatastore();

        List<OpeningHour> openingHourList = new ArrayList<>();

        try {
            List openingHours = openingHoursLocalQuery.find();

            if(openingHours != null) {

                openingHourList = openingHours;
            }
        } catch (com.parse.ParseException e) {

            e.printStackTrace();
        }

        return openingHourList;
    }
}
