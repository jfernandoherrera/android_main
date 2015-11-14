package com.amtechventures.tucita.model.domain.service;

import com.amtechventures.tucita.model.domain.subcategory.SubCategory;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@ParseClassName("Service")
public class Service extends ParseObject{

    public String getName(){

        return getString(ServiceAttributes.name);

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
