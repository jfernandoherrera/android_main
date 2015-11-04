package com.amtechventures.tucita.model.context.service;


import com.amtechventures.tucita.model.domain.category.Category;
import com.amtechventures.tucita.model.domain.category.CategoryAttributes;
import com.amtechventures.tucita.model.domain.service.Service;
import com.amtechventures.tucita.utils.blocks.Completion;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;

import java.util.ArrayList;
import java.util.List;

public class ServiceContext {

    public static ServiceContext context(ServiceContext serviceContext) {

        if (serviceContext == null) {

            serviceContext = new ServiceContext();

        }

        return  serviceContext;

    }

    public ServiceContext(){

    }
    public List<Service> loadServices(Category category, ServicesCompletion.ErrorCompletion completion){

      List services;

        ParseRelation object = (ParseRelation) category.get(CategoryAttributes.services);

        ParseQuery<Service> queryLocal = object.getQuery();

        services = ServiceLocal.loadServices(queryLocal);

        ParseQuery<Service> queryRemote = object.getQuery();

        ServiceRemote.loadServices(queryRemote,completion);

        return services;
    }


    public List<Service> loadLikeServices(String likeWord, ServicesCompletion.ErrorCompletion completion){

        List services;

        services = ServiceLocal.loadLikeServices(likeWord);

        ServiceRemote.loadLikeServices(likeWord, completion);

        return services;
    }
}
