package com.amtechventures.tucita.model.context.service;


import com.amtechventures.tucita.utils.blocks.Completion;
import java.util.ArrayList;
import java.util.List;

public class ServiceLocal {

    ServiceResponse serviceResponse;


    public List<String> loadServices(String category, Completion.ErrorCompletion completion){

        ArrayList<String> services;

        serviceResponse = new ServiceResponse();

        services = (ArrayList<String>) serviceResponse.populateWithData(null,null);

        completion.completion(null);

        return services;
    }


}
