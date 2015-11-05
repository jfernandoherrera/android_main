package com.amtechventures.tucita.model.context.category;

import android.util.Log;

import java.util.List;
import java.util.ArrayList;

import com.amtechventures.tucita.model.context.service.ServiceContext;
import com.amtechventures.tucita.model.context.service.ServiceLocal;
import com.amtechventures.tucita.model.error.AppError;
import com.amtechventures.tucita.utils.blocks.Completion;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.amtechventures.tucita.model.domain.category.Category;
import com.parse.ParseRelation;

public class CategoryLocal {

    public Category findCategory(String nombre){

        Category find = null;

        List<Category> categoryList = loadCategories();

        for(Category category : categoryList){
            if(category.getName().equals(nombre)){
                find = category;
            }
        }
        return find;
    }

    public List<Category> loadCategories() {

        List<Category> categoryList = new ArrayList<>();

        ParseQuery<Category> query = Category.getQuery().fromLocalDatastore();

        try {

            List<Category> categories = query.find();

            if (categories != null) {

                categoryList = categories;

            }

        }catch (Exception exception) {

            exception.printStackTrace();

        }

        return categoryList;

    }

}
