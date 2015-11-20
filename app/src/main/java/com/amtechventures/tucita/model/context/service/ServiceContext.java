package com.amtechventures.tucita.model.context.service;

import com.amtechventures.tucita.model.domain.service.Service;
import com.amtechventures.tucita.model.domain.subcategory.SubCategory;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.domain.venue.VenueAttributes;
import com.amtechventures.tucita.utils.blocks.Completion;
import com.parse.ParseQuery;
import com.parse.ParseRelation;

import java.util.List;

public class ServiceContext {

    ServiceLocal serviceLocal;
    ServiceRemote serviceRemote;

    public void cancelQuery(){

        serviceRemote.cancelQuery();
    }

    public int getPricesFrom(SubCategory subCategory, Venue venue){

        ParseRelation object = (ParseRelation) venue.get(VenueAttributes.services);

        ParseQuery<Service> queryRemote = object.getQuery();

        return serviceRemote.getPricesFrom(subCategory,queryRemote);
    }

    public static ServiceContext context(ServiceContext serviceContext) {

        if (serviceContext == null) {

            serviceContext = new ServiceContext();

        }

        return serviceContext;

    }

    ServiceContext(){

        serviceLocal = new ServiceLocal();

        serviceRemote = new ServiceRemote();
    }


    public List<Service> loadServices(Venue venue, ServiceCompletion.ErrorCompletion completion){

        List services;

        ParseRelation object = (ParseRelation) venue.get(VenueAttributes.services);

        ParseQuery<Service> queryLocal = object.getQuery();

        services = serviceLocal.loadServices(queryLocal);

        ParseQuery<Service> queryRemote = object.getQuery();

        serviceRemote.loadServices(queryRemote, completion);

        return services;
    }

    public List<Service> loadSubCategorizedServices(SubCategory subCategory, ServiceCompletion.ErrorCompletion errorCompletion){

        List<Service> services;

        services = serviceLocal.loadSubCategorizedServices(subCategory);

        serviceRemote.loadSubCategorizedServices(subCategory,errorCompletion);

        return services;
    }
}

