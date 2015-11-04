package com.amtechventures.tucita.model.context.service;


import com.amtechventures.tucita.model.domain.category.Category;
import com.amtechventures.tucita.model.domain.category.CategoryAttributes;
import com.amtechventures.tucita.model.domain.service.Service;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import java.util.List;

public class ServiceContext {

    ServiceLocal serviceLocal;

    ServiceRemote serviceRemote;
    public static ServiceContext context(ServiceContext serviceContext) {

        if (serviceContext == null) {

            serviceContext = new ServiceContext();

        }

        return  serviceContext;

    }

    public ServiceContext(){

        serviceRemote = new ServiceRemote();

        serviceLocal = new ServiceLocal();
    }

    public List<Service> loadServices(Category category, ServicesCompletion.ErrorCompletion completion){

      List services;

        ParseRelation object = (ParseRelation) category.get(CategoryAttributes.services);

        ParseQuery<Service> queryLocal = object.getQuery();

        services = serviceLocal.loadServices(queryLocal);

        ParseQuery<Service> queryRemote = object.getQuery();

        serviceRemote.loadServices(queryRemote,completion);

        return services;
    }

    public List<Service> loadLikeServices(String likeWord, ServicesCompletion.ErrorCompletion completion){

        List services;

        services = serviceLocal.loadLikeServices(likeWord);

        serviceRemote.loadLikeServices(likeWord, completion);

        return services;
    }
}
