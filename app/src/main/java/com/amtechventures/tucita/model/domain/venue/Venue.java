package com.amtechventures.tucita.model.domain.venue;


import android.graphics.Bitmap;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;

@ParseClassName("Venue")

public class Venue extends ParseObject {

    Bitmap picture;

    public void setName(String name){

        put(VenueAttributes.name,name );

    }

    public double getLatitude(){

        return getLocation().getLatitude();
    }

    public double getLongitude(){

        return getLocation().getLongitude();
    }

    public ParseGeoPoint getLocation(){

        return getParseGeoPoint(VenueAttributes.location);
    }

    public String getName(){

        return getString(VenueAttributes.name);

    }

    public String getDescription(){

        return getString(VenueAttributes.description);

    }

    public double getRating(){

        return getDouble(VenueAttributes.rating);

    }

    public Bitmap getPicture(){

        return picture;
    }

    public String getAddress(){

        return getString(VenueAttributes.address);
    }

    public void setPicture( Bitmap bm){

        this.picture = bm;

    }

    public static ParseQuery<Venue> getQuery() {

        return ParseQuery.getQuery(Venue.class);
    }
}
