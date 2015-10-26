package com.amtechventures.tucita.model.domain.user;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

@ParseClassName("User")
public class User extends ParseUser {
    public void setUserName(String name){

        put("username",name );

    }
    public String getUserName(){

        return getString("username");

    }

}
