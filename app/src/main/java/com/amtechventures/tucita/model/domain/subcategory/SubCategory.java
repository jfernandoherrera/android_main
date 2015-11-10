package com.amtechventures.tucita.model.domain.subcategory;

import com.amtechventures.tucita.model.domain.category.CategoryAttributes;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
@ParseClassName("SubCategory")
public class SubCategory extends ParseObject {

        public String getName(){

            return getString(CategoryAttributes.name);

        }

        public static ParseQuery<SubCategory> getQuery() {

            return ParseQuery.getQuery(SubCategory.class);
        }
}
