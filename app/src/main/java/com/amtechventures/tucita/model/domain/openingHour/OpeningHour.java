package com.amtechventures.tucita.model.domain.openingHour;


import com.parse.ParseObject;
import com.parse.ParseQuery;

import com.parse.ParseClassName;
@ParseClassName("Opening_Hour")
public class OpeningHour extends ParseObject{

    public String getDay(){

        return getString(OpeningHourAttributes.day);
    }

    public String getStartHour(){

        return getString(OpeningHourAttributes.startHour);
    }

    public String getEndHour(){

        return getString(OpeningHourAttributes.endHour);
    }

    public static ParseQuery<OpeningHour> getQuery() {

        return ParseQuery.getQuery(OpeningHour.class);
    }
}
