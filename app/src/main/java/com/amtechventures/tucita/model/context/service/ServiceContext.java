package com.amtechventures.tucita.model.context.service;


import com.amtechventures.tucita.model.domain.category.Category;
import com.amtechventures.tucita.model.domain.service.Service;
import com.amtechventures.tucita.utils.blocks.Completion;

import java.util.ArrayList;
import java.util.List;

public class ServiceContext {

    ServiceLocal serviceLocal;


    public ServiceContext(){

        serviceLocal = new ServiceLocal();

    }
    public List<String> loadServices(String category, Completion.ErrorCompletion completion){

        ArrayList<String> services = (ArrayList<String>) serviceLocal.loadServices(category,completion);

        return services;
    }
}
