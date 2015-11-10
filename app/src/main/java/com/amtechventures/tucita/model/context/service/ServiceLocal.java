package com.amtechventures.tucita.model.context.service;


import com.amtechventures.tucita.model.domain.service.Service;
import com.amtechventures.tucita.model.domain.subcategory.SubCategory;
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
}
