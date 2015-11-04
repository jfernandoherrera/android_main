package com.amtechventures.tucita.model.domain.category;

import com.amtechventures.tucita.model.domain.service.Service;
import com.parse.ParseQuery;
import com.parse.ParseObject;
import com.parse.ParseClassName;

import java.util.ArrayList;

@ParseClassName("Category")
public class Category extends ParseObject {

    private ArrayList<String> services;

    public void setName(String name) {

        put(CategoryAttributes.name, name);

    }

    public void setServices(ArrayList services){

        this.services = services;
    }
    public ArrayList<String> getServices(){
        return services;
    }
    public String getName() {

        return getString(CategoryAttributes.name);

    }

    public static ParseQuery<Category> getQuery() {
        ParseQuery<Category> query = ParseQuery.getQuery(Category.class);
        query.include("Service");
        return query;

    }

}
