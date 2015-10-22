package com.amtechventures.tucita.model.user;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@ParseClassName("User")
public class User extends ParseObject {
    public void setUserName(String name){

        put("username",name );

    }
    public String getUserName(){

        return getString("username");

    }
    public static ParseQuery<User> getQuery() {
        return ParseQuery.getQuery(User.class);
    }
}
