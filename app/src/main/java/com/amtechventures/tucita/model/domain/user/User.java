package com.amtechventures.tucita.model.domain.user;

import com.amtechventures.tucita.utils.strings.Strings;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

@ParseClassName("User")
public class User {
    private ParseUser parseUser;
    private String pictureURL;
    private String authType= Strings.ANONYMOUS;

    public void setUserName(String name){

        parseUser.put("username",name );

    }
    public String getUserName(){

        return parseUser.getString("username");

    }
    public String getAuthType(){
        return authType;
    }
    public void setEmail(String email){

        parseUser.put("email", email);

    }
    public String getEmail(){

      return  parseUser.getString("email");

    }



}
