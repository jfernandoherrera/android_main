package com.amtechventures.tucita.model.domain.service;

import com.amtechventures.tucita.model.domain.subcategory.SubCategory;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@ParseClassName("Service")
public class Service extends ParseObject{

    private final String shortHour = "hr";
    private final String shortMinutes = "mins";

    public String getDurationInfo(){

        int durationHours = getDurationHour();

        int durationMinutes = getDurationMinutes();

        String serviceDurationHours = durationHours == 0 ? "" : String.valueOf(durationHours) + shortHour;

        String serviceDurationMinutes = durationMinutes == 0 ? "" : String.valueOf(durationMinutes) + shortMinutes;

        String serviceDurationInfo = serviceDurationHours + " " + serviceDurationMinutes;

    return serviceDurationInfo;
    }

    public String getName(){

        return getString(ServiceAttributes.name);
    }

    public int getPrice(){

      return getInt(ServiceAttributes.price);
    }

    public SubCategory getSubcategory(){

        return (SubCategory) getParseObject(ServiceAttributes.subCategory);
    }

    public int getDurationHour(){

      return getInt(ServiceAttributes.durationHours);
    }

    public int getDurationMinutes(){

       return getInt(ServiceAttributes.durationMinutes);
    }

    public static ParseQuery<Service> getQuery() {

        return ParseQuery.getQuery(Service.class);
    }
}
