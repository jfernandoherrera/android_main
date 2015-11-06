package com.amtechventures.tucita.model.domain.category;


import com.parse.ParseQuery;
import com.parse.ParseObject;
import com.parse.ParseClassName;

import java.util.ArrayList;

@ParseClassName("Category")
public class Category extends ParseObject {



    public void setName(String name) {

        put(CategoryAttributes.name, name);

    }

    public String getName() {

        return getString(CategoryAttributes.name);

    }

    public static ParseQuery<Category> getQuery() {
        ParseQuery<Category> query = ParseQuery.getQuery(Category.class);
        return query;

    }

}
