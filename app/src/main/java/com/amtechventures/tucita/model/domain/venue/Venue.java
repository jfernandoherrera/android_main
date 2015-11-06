package com.amtechventures.tucita.model.domain.venue;


import android.graphics.Bitmap;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
@ParseClassName("Venue")
public class Venue extends ParseObject {

    Bitmap picture;

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

        put(VenueAttributes.rating, rating );
    }

    public Bitmap getPicture(){

        return picture;
    }

    public String getAddress(){

        return getString(VenueAttributes.address);
    }

    public void setPicture(ParseFile picture, Bitmap bm){

        this.picture = bm;

        put(VenueAttributes.picture, picture);
    }

    public static ParseQuery<Venue> getQuery() {

        return ParseQuery.getQuery(Venue.class);
    }
}
