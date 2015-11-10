package com.amtechventures.tucita.model.context.subcategory;


import com.amtechventures.tucita.model.domain.category.CategoryAttributes;
import com.amtechventures.tucita.model.domain.subcategory.SubCategory;
import com.parse.ParseQuery;
import java.util.ArrayList;
import java.util.List;

public class SubCategoryLocal {

    public List<SubCategory> loadServices(ParseQuery<SubCategory> servicesLocalQuery){

        servicesLocalQuery.fromLocalDatastore();

        List<SubCategory> subCategoryList = new ArrayList<>();

        try {
            List services = servicesLocalQuery.find();

            if(services != null) {

                subCategoryList = services;
            }
        } catch (com.parse.ParseException e) {

            e.printStackTrace();
        }

        return subCategoryList;
    }

    public List<SubCategory> loadLikeServices(String likeWord){

        ParseQuery servicesLocalQuery = SubCategory.getQuery();

        servicesLocalQuery.fromLocalDatastore();

        List<SubCategory> subCategoryList = new ArrayList<>();

        try {
            List services = servicesLocalQuery.whereContains(CategoryAttributes.name,likeWord).find();

            if(services != null) {

                subCategoryList = services;
            }
        } catch (com.parse.ParseException e) {

            e.printStackTrace();
        }

        return subCategoryList;
    }

}
