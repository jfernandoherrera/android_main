package com.amtechventures.tucita.model.domain.service;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
@ParseClassName("User")
public class Service extends ParseObject {

    String name;
    String categoryName;


        public void setName(String name){

           // put("name",name );
        this.name = name;
        }

        public String getName(){

            return name;
                    //getString("name");

        }
        public void setCategoryName(String name){
            this.name = name;
//        put("name",name );

        }

        public String getCategoryName(){

        return getString("name");

        }

        public static ParseQuery<Service> getQuery() {
            return ParseQuery.getQuery(Service.class);
        }
}
