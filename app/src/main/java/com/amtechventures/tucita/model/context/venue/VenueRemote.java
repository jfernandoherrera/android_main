package com.amtechventures.tucita.model.context.venue;

import android.location.Location;

import com.amtechventures.tucita.model.domain.category.Category;
import com.amtechventures.tucita.model.domain.city.City;
import com.amtechventures.tucita.model.domain.city.CityAttributes;
import com.amtechventures.tucita.model.domain.service.Service;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.domain.venue.VenueAttributes;
import com.amtechventures.tucita.model.error.AppError;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.ArrayList;
import java.util.List;

public class VenueRemote {

    ParseQuery<Venue> query;

    private void setQuery(){

        query = Venue.getQuery();
    }

    public void cancelQuery(){

        if(query != null){

            query.cancel();
        }
    }

    public void findVenue(String lookThat, String address, final VenueCompletion.ErrorCompletion completion){

    ParseQuery queryName = Venue.getQuery();

    queryName.whereEqualTo(VenueAttributes.name, lookThat);

    ParseQuery queryAddress = Venue.getQuery();

    queryAddress.whereEqualTo(VenueAttributes.address, address);

    List<ParseQuery<ParseObject>> queries = new ArrayList<>();

    queries.add(queryAddress);

    queries.add(queryName);

    ParseQuery queryTemp = ParseQuery.or(queries) ;

        query = queryTemp;

        query.include(VenueAttributes.city);

        query.findInBackground(new FindCallback<Venue>() {
            @Override
            public void done(List<Venue> objects, ParseException e) {

                if(objects != null){
                    try {

                        ParseObject.pinAll(objects);

                    } catch (ParseException pe) {}

                }

                AppError appError = e != null ? new AppError(Venue.class.toString(), 0, null) : null;

                completion.completion(objects, appError);
            }
        });

}

    public void loadLikeVenues(String likeWord, final VenueCompletion.ErrorCompletion completion )
    {
        ParseQuery queryName = Venue.getQuery();

        queryName.whereContains(VenueAttributes.name, likeWord);

        ParseQuery queryAddress = Venue.getQuery();

        queryAddress.whereContains(VenueAttributes.address, likeWord);

        ParseQuery queryCityName = City.getQuery();

        queryCityName.whereContains(CityAttributes.name, likeWord);

        ParseQuery queryVenueCity = Venue.getQuery();

        queryVenueCity.whereMatchesQuery(VenueAttributes.city, queryCityName);

        ParseQuery queryCityDepartment = City.getQuery();

        queryCityDepartment.whereContains(CityAttributes.department, likeWord);

        ParseQuery queryVenueDepartment = Venue.getQuery();

        queryVenueDepartment.whereMatchesQuery(VenueAttributes.city, queryCityDepartment);

        List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();

        queries.add(queryAddress);

        queries.add(queryName);

        queries.add(queryVenueCity);

        queries.add(queryVenueDepartment);

        ParseQuery queryTemp = ParseQuery.or(queries) ;

        query = queryTemp;

        query.include(VenueAttributes.city);

        query.orderByAscending(VenueAttributes.name);

        query.findInBackground(new FindCallback<Venue>() {
            @Override
            public void done(List objects, ParseException e) {

                if(objects != null){
                    try {

                        ParseObject.pinAll(objects);

                    } catch (ParseException pe) {}

                }

                AppError appError = e != null ? new AppError(Venue.class.toString(), 0, null) : null;

                completion.completion(objects,appError);
            }
        });
    }

    public void loadSubCategorizedCityVenues(List<Service> services, City city, final VenueCompletion.ErrorCompletion completion){

        setQuery();

        query.include(VenueAttributes.city);

        query.whereContainedIn(VenueAttributes.services, services);

        query.whereEqualTo(VenueAttributes.city, city);

        query.findInBackground(new FindCallback<Venue>() {
            @Override
            public void done(List<Venue> objects, ParseException e) {

                AppError appError = e != null ? new AppError(Venue.class.toString(), 0, null) : null;

                completion.completion(objects, appError);
            }
        });

    }
    public void loadSubCategorizedNearVenues(List<Service> services, Location location, final VenueCompletion.ErrorCompletion completion )
    {
        ParseGeoPoint point = new ParseGeoPoint(location.getLatitude(), location.getLongitude());

        setQuery();

        query.whereContainedIn(VenueAttributes.services, services);

        query.whereNear(VenueAttributes.location, point);

        query.include(VenueAttributes.city);

        query.findInBackground(new FindCallback<Venue>() {
            @Override
            public void done(List<Venue> objects, ParseException e) {

                AppError appError = e != null ? new AppError(Venue.class.toString(), 0, null) : null;

                completion.completion(objects, appError);
            }
        });
    }
}
