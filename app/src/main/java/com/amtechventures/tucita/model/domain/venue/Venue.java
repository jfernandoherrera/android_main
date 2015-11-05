package com.amtechventures.tucita.model.domain.venue;


import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
@ParseClassName("Venue")
public class Venue extends ParseObject {

    public void setName(String name){

        put("name",name );

    }

    public String getName(){

        return getString("name");

    }

    public static ParseQuery<Venue> getQuery() {
        return ParseQuery.getQuery(Venue.class);
    }
}
