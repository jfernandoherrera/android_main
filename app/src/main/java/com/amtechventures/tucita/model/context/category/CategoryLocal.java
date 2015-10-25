package com.amtechventures.tucita.model.context.category;

import java.util.List;
import java.util.ArrayList;
import com.parse.ParseQuery;
import com.amtechventures.tucita.model.domain.category.Category;

public class CategoryLocal {

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
