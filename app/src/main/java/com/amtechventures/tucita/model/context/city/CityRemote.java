package com.amtechventures.tucita.model.context.city;


import com.amtechventures.tucita.model.context.subcategory.SubCategoryCompletion;
import com.amtechventures.tucita.model.domain.category.CategoryAttributes;
import com.amtechventures.tucita.model.domain.city.City;
import com.amtechventures.tucita.model.domain.city.CityAttributes;
import com.amtechventures.tucita.model.domain.subcategory.SubCategory;
import com.amtechventures.tucita.model.domain.venue.VenueAttributes;
import com.amtechventures.tucita.model.error.AppError;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class CityRemote {

    ParseQuery<City> query;

    private void setQuery(){

        query = City.getQuery();
    }

    public void cancelQuery(){

        if(query != null){

            query.cancel();
        }

    }

    public void loadLikeCities(String like, final CityCompletion.ErrorCompletion completion) {

        setQuery();

        query.whereContains(CityAttributes.nameToSearch, like).findInBackground(new FindCallback<City>() {
            @Override
            public void done(List<City> objects, com.parse.ParseException e) {

                if (objects != null) {
                    try {

                        ParseObject.pinAll(objects);

                    } catch (ParseException pe) {
                    }

                }

                AppError appError = e != null ? new AppError(City.class.toString(), 0, null) : null;

                completion.completion(objects, appError);
            }

        });

    }
}
