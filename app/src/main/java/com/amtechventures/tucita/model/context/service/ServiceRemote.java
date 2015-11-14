package com.amtechventures.tucita.model.context.service;


import com.amtechventures.tucita.model.context.subcategory.SubCategoryCompletion;
import com.amtechventures.tucita.model.domain.service.Service;
import com.amtechventures.tucita.model.domain.service.ServiceAttributes;
import com.amtechventures.tucita.model.domain.subcategory.SubCategory;
import com.amtechventures.tucita.model.error.AppError;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class ServiceRemote {

    public void loadServices(ParseQuery<Service> servicesRemoteQuery, final ServiceCompletion.ErrorCompletion completion){

        servicesRemoteQuery.include(ServiceAttributes.subCategory);

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

    public void loadSubCategorizedServices(SubCategory subCategory, final ServiceCompletion.ErrorCompletion completion){

        ParseQuery query = Service.getQuery();

        query.whereEqualTo(ServiceAttributes.subCategory, subCategory);

             query.findInBackground(new FindCallback<Service>() {
                 @Override
                 public void done(List objects, ParseException e) {


                     if (objects != null) {
                         try {

                             ParseObject.pinAll(objects);

                         } catch (ParseException pe) {
                         }
                     }
                         completion.completion(objects, null);

                     }
                 }

                 );

             }

    }
