package com.amtechventures.tucita.model.domain.openingHour;


import com.parse.ParseObject;
import com.parse.ParseQuery;

import com.parse.ParseClassName;
@ParseClassName("Opening_Hour")
public class OpeningHour extends ParseObject{

    public String getDay(){

        return getString(OpeningHourAttributes.day);
    }

    public int getStartHour(){

        return getInt(OpeningHourAttributes.startHour);
    }

    public int getEndHour(){

        return getInt(OpeningHourAttributes.endHour);
    }

    public int getStartMinute(){

        return getInt(OpeningHourAttributes.startMinute);
    }

    public int getEndMinute(){

        return getInt(OpeningHourAttributes.endMinute);
    }

    public static ParseQuery<OpeningHour> getQuery() {

        return ParseQuery.getQuery(OpeningHour.class);
    }
}
