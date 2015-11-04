package com.amtechventures.tucita.model.context.service;


import com.amtechventures.tucita.model.domain.category.CategoryAttributes;
import com.amtechventures.tucita.model.domain.service.Service;
import com.parse.ParseQuery;
import java.util.ArrayList;
import java.util.List;

public class ServiceLocal {

    public List<Service> loadServices(ParseQuery<Service> servicesLocalQuery){

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

    public List<Service> loadLikeServices(String likeWord){

        ParseQuery servicesLocalQuery = Service.getQuery();

        servicesLocalQuery.fromLocalDatastore();

        List<Service> serviceList = new ArrayList<>();

        try {
            List services = servicesLocalQuery.whereContains(CategoryAttributes.name,likeWord).find();

            if(services != null) {

                serviceList = services;
            }
        } catch (com.parse.ParseException e) {

            e.printStackTrace();
        }

        return serviceList;
    }

}
