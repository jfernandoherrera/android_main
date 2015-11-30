package com.amtechventures.tucita.model.context.subcategory;


import com.amtechventures.tucita.model.domain.category.CategoryAttributes;
import com.amtechventures.tucita.model.domain.service.ServiceAttributes;
import com.amtechventures.tucita.model.domain.subcategory.SubCategory;
import com.amtechventures.tucita.model.domain.subcategory.SubCategoryAttributes;
import com.parse.ParseException;
import com.parse.ParseQuery;
import java.util.ArrayList;
import java.util.List;

public class SubCategoryLocal {


    public List<SubCategory> loadRecentSubCategories(){

        ParseQuery subCategoryLocalQuery = SubCategory.getQuery();

        subCategoryLocalQuery.fromLocalDatastore();

        subCategoryLocalQuery.setLimit(10);

        subCategoryLocalQuery.orderByAscending(SubCategoryAttributes.updatedAt);

        List<SubCategory> subCategoryList = new ArrayList<>();

        try {
            List subCategories = subCategoryLocalQuery.find();

            if(subCategories != null) {

                subCategoryList = subCategories;
            }
        } catch (com.parse.ParseException e) {

            e.printStackTrace();
        }

        return subCategoryList;
    }


    public List<SubCategory> loadSubCategories(ParseQuery<SubCategory> subCategoryLocalQuery){

        subCategoryLocalQuery.fromLocalDatastore();

        subCategoryLocalQuery.orderByAscending(CategoryAttributes.name);

        List<SubCategory> subCategoryList = new ArrayList<>();

        try {
            List subCategories = subCategoryLocalQuery.find();

            if(subCategories != null) {

                subCategoryList = subCategories;
            }
        } catch (com.parse.ParseException e) {

            e.printStackTrace();
        }

        return subCategoryList;
    }

    public List<SubCategory> loadLikeSubCategories(String likeWord){

        ParseQuery subCategoriesLocalQuery = SubCategory.getQuery();

        subCategoriesLocalQuery.fromLocalDatastore();

        subCategoriesLocalQuery.orderByAscending(CategoryAttributes.name);

        List<SubCategory> subCategoryList = new ArrayList<>();

        try {
            List subCategories = subCategoriesLocalQuery.whereContains(SubCategoryAttributes.nameToSearch,likeWord).find();

            if(subCategories != null) {

                subCategoryList = subCategories;
            }
        } catch (com.parse.ParseException e) {

            e.printStackTrace();
        }

        return subCategoryList;
    }

    public SubCategory findSubCategory(String name){

        SubCategory subCategory = null;

        ParseQuery query = SubCategory.getQuery();

        query.fromLocalDatastore();

        query.whereEqualTo(CategoryAttributes.name, name);

        List subCategories = null;

        try {

            subCategories = query.find();

        } catch (ParseException e) {

            e.printStackTrace();
        }
        if(subCategories != null){

            subCategory = (SubCategory) subCategories.get(0);
        }
        return subCategory;
    }

}
