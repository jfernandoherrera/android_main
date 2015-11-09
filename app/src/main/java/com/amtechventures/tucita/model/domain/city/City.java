package com.amtechventures.tucita.model.domain.city;

import com.amtechventures.tucita.model.domain.venue.VenueAttributes;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@ParseClassName("City")
public class City extends ParseObject {


    public String getDepartment(){

        return getString(CityAttributes.department);
    }


    public String getName(){

        return getString(CityAttributes.name);

    }
    public static ParseQuery<City> getQuery() {

        return ParseQuery.getQuery(City.class);
    }


}
