package com.amtechventures.tucita.model.context.service;


import com.amtechventures.tucita.model.domain.service.Service;
import com.amtechventures.tucita.model.domain.service.ServiceAttributes;
import com.amtechventures.tucita.model.domain.subcategory.SubCategory;
import com.amtechventures.tucita.model.domain.venue.VenueAttributes;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class ServiceLocal {

    public List<Service> loadSubCategorizedServices(SubCategory subCategory){

        List<Service> services = null;

        ParseQuery query = Service.getQuery();

        query.fromLocalDatastore();

        query.whereEqualTo(ServiceAttributes.subCategory,subCategory);

        try {
           services = query.find();

        } catch (ParseException e) {

            e.printStackTrace();
        }
        return services;
    }


    public List<Service> loadServices(ParseQuery<Service> servicesLocalQuery){

        servicesLocalQuery.include(ServiceAttributes.subCategory);

        servicesLocalQuery.fromLocalDatastore();

        List<Service> serviceList = new ArrayList<>();

        try {
            List services = servicesLocalQuery.find();

            if(services != null) {

                serviceList = services;
            }
        } catch (com.parse.ParseException e) {

            e.printStackTrace();
        }

        return serviceList;
    }
}
