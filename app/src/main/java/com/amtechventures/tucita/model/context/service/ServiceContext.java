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
ServiceLocal serviceLocal;
ServiceRemote serviceRemote;
    public ServiceContext(){

        serviceLocal=new ServiceLocal();

        serviceRemote=new ServiceRemote();
    }
    public List<Service> loadServices(Category category, ServicesCompletion.ErrorCompletion completion){

      List services;

        ParseRelation object = (ParseRelation) category.get(CategoryAttributes.services);

        ParseQuery<Service> query = object.getQuery();

        services= serviceLocal.loadServices(query);

        serviceRemote.loadServices(object.getQuery(),completion);

        return services;
    }
}
