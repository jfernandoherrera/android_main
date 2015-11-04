package com.amtechventures.tucita.model.domain.service;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
@ParseClassName("Service")
public class Service extends ParseObject {

        public void setName(String name){

           put("name",name );

        }

        public String getName(){

            return getString("name");

        }

        public static ParseQuery<Service> getQuery() {
            return ParseQuery.getQuery(Service.class);
        }
}
