package com.amtechventures.tucita.model.context.service;

import com.amtechventures.tucita.model.domain.category.CategoryAttributes;
import com.amtechventures.tucita.model.domain.service.Service;
import com.amtechventures.tucita.model.error.AppError;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.List;

public class ServiceRemote {

    public static void loadServices(ParseQuery<Service> servicesRemoteQuery, final ServicesCompletion.ErrorCompletion completion){

        servicesRemoteQuery.findInBackground(new FindCallback<Service>() {
            @Override
            public void done(List<Service> objects, com.parse.ParseException e) {

                if(objects != null){
                    try {

                        ParseObject.pinAll(objects);

                    } catch (ParseException pe) {}

                }

                AppError appError = e != null ? new AppError(Service.class.toString(), 0, null) : null;

                completion.completion(objects,appError);
            }

        });

    }


    public static void loadLikeServices(String like, final ServicesCompletion.ErrorCompletion completion){

        ParseQuery servicesRemoteQuery = Service.getQuery();

        servicesRemoteQuery.whereContains(CategoryAttributes.name,like).findInBackground(new FindCallback<Service>() {
            @Override
            public void done(List<Service> objects, com.parse.ParseException e) {

                if (objects != null) {
                    try {

                        ParseObject.pinAll(objects);

                    } catch (ParseException pe) {
                    }

                }

                AppError appError = e != null ? new AppError(Service.class.toString(), 0, null) : null;

                completion.completion(objects, appError);
            }

        });

    }

}
