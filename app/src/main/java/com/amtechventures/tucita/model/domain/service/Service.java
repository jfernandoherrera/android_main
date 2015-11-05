package com.amtechventures.tucita.model.domain.service;

import com.amtechventures.tucita.model.domain.category.CategoryAttributes;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
@ParseClassName("Service")
public class Service extends ParseObject {

        public void setName(String name){

           put(CategoryAttributes.name,name );

        }

        public String getName(){

            return getString(CategoryAttributes.name);

        }

        public static ParseQuery<Service> getQuery() {

            return ParseQuery.getQuery(Service.class);
        }
}
