package com.amtechventures.tucita.model.context.subcategory;


import com.amtechventures.tucita.model.domain.category.CategoryAttributes;
import com.amtechventures.tucita.model.domain.subcategory.SubCategory;
import com.parse.ParseQuery;
import java.util.ArrayList;
import java.util.List;

public class SubCategoryLocal {

    public List<SubCategory> loadSubCategories(ParseQuery<SubCategory> subCategoryLocalQuery){

        subCategoryLocalQuery.fromLocalDatastore();

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

        List<SubCategory> subCategoryList = new ArrayList<>();

        try {
            List subCategories = subCategoriesLocalQuery.whereContains(CategoryAttributes.name,likeWord).find();

            if(subCategories != null) {

                subCategoryList = subCategories;
            }
        } catch (com.parse.ParseException e) {

            e.printStackTrace();
        }

        return subCategoryList;
    }

}
