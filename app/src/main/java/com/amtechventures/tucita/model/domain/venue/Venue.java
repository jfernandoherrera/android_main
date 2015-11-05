package com.amtechventures.tucita.model.domain.venue;


import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
@ParseClassName("Venue")
public class Venue extends ParseObject {

    public void setName(String name){

        put(VenueAttributes.name,name );

    }

    public String getName(){

        return getString(VenueAttributes.name);

    }

    public String getDescription(){

        return getString(VenueAttributes.description);

    }

    public void setDescription(String description){

        put(VenueAttributes.description,description);
    }

    public double getRating(){

        return getDouble(VenueAttributes.rating);

    }

    public void setRating(double rating){

        put(VenueAttributes.rating,rating );
    }

    public static ParseQuery<Venue> getQuery() {

        return ParseQuery.getQuery(Venue.class);
    }
}
