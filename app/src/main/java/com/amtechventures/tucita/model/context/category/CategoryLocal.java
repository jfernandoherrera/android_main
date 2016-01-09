package com.amtechventures.tucita.model.context.category;

import com.amtechventures.tucita.model.domain.category.Category;
import com.amtechventures.tucita.model.domain.category.CategoryAttributes;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

public class CategoryLocal {

    public Category findCategory(String name) {

        Category find = null;

        ParseQuery<Category> query = Category.getQuery().fromLocalDatastore();

        query.whereEqualTo(CategoryAttributes.name, name);

        try {

            find = query.getFirst();

        } catch (ParseException e) {

            e.printStackTrace();

        }

        return find;

    }

    public List<Category> loadCategories() {

        List<Category> categories = null;

        ParseQuery<Category> query = Category.getQuery().fromLocalDatastore();

        query.orderByAscending(CategoryAttributes.name);

        try {

            categories = query.find();

        } catch (Exception exception) {

            exception.printStackTrace();

        }

        return categories;

    }

}
