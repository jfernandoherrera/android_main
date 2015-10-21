package com.amtechventures.tucita.model.category;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@ParseClassName("Category")
public class Category extends ParseObject {


    public void setName(String name){

        put("name",name );

    }
    public String getName(){

        return getString("name");

    }

    public static ParseQuery<Category> getQuery() {
        return ParseQuery.getQuery(Category.class);
    }
}
