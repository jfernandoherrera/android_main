package com.amtechventures.tucita.model.context.service;


import com.amtechventures.tucita.model.error.AppError;
import java.util.ArrayList;
import java.util.List;

public class ServiceResponse {


    public ServiceResponse(){


    }

    private List<String> populateHairServices(){

        List<String> hairServices = new ArrayList<>();

        hairServices.add("Haircuts & Hairdressing");

        hairServices.add("Blow Dry");

        hairServices.add("Hair Colouring");

        hairServices.add("Hair Consulting");

        return hairServices;
    }

    public List<String> populateWithData(Object data, AppError error){

            return populateHairServices();

    }
}
