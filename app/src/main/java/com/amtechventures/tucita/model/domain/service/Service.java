package com.amtechventures.tucita.model.domain.service;


import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@ParseClassName("Service")
public class Service extends ParseObject{

    public String getName(){

        return getString(ServiceAttributes.name);

    }

    public static ParseQuery<Service> getQuery() {

        return ParseQuery.getQuery(Service.class);
    }
}
