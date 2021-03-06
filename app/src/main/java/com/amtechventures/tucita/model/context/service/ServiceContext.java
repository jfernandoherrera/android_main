package com.amtechventures.tucita.model.context.service;

import com.amtechventures.tucita.model.context.venue.VenueCompletion;
import com.amtechventures.tucita.model.domain.appointment.Appointment;
import com.amtechventures.tucita.model.domain.appointment.AppointmentAttributes;
import com.amtechventures.tucita.model.domain.service.Service;
import com.amtechventures.tucita.model.domain.subcategory.SubCategory;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.domain.venue.VenueAttributes;
import com.parse.ParseQuery;
import com.parse.ParseRelation;

import java.util.List;

public class ServiceContext {

    private ServiceLocal serviceLocal;
    private ServiceRemote serviceRemote;

    public void cancelQuery() {

        serviceRemote.cancelQuery();

    }

    public int getPricesFrom(SubCategory subCategory, Venue venue) {

        ParseRelation object = (ParseRelation) venue.get(VenueAttributes.services);

        ParseQuery<Service> queryRemote = object.getQuery();

        return serviceRemote.getPricesFrom(subCategory, queryRemote);

    }

    public static ServiceContext context(ServiceContext serviceContext) {

        if (serviceContext == null) {

            serviceContext = new ServiceContext();

        }

        return serviceContext;

    }

    private ServiceContext() {

        serviceLocal = new ServiceLocal();

        serviceRemote = new ServiceRemote();

    }

    public List<Service> loadServices(Venue venue, ServiceCompletion.ErrorCompletion completion) {

        List services;

        ParseRelation object = venue.getServices();

        ParseQuery<Service> queryLocal = object.getQuery();

        services = serviceLocal.loadServices(queryLocal);

        ParseQuery<Service> queryRemote = object.getQuery();

        serviceRemote.loadServices(queryRemote, completion);

        return services;

    }

    public void loadSubCategorizedServices(SubCategory subCategory, ServiceCompletion.ErrorCompletion errorCompletion) {

        serviceRemote.loadSubCategorizedServices(subCategory, errorCompletion);

    }

    public void loadCategorizedServices(List<SubCategory> subCategories, ServiceCompletion.ErrorCompletion errorCompletion) {

        serviceRemote.loadCategorizedServices(subCategories, errorCompletion);

    }

    public void loadAppointmentServices(Appointment appointment, ServiceCompletion.ErrorCompletion completion){


        ParseRelation object = appointment.getServices();

        ParseQuery<Service> queryRemote = object.getQuery();

        serviceRemote.loadAppointmentServices(queryRemote, completion);

    }

}
