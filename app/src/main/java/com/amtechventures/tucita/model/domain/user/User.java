package com.amtechventures.tucita.model.domain.user;

import com.parse.ParseUser;

public class User {

    private ParseUser parseUser;

    public void setParseUser(ParseUser user) {

        parseUser = user;

    }

    public ParseUser getParseUser() {

        return parseUser;

    }

    public void setUserName(String name) {

        parseUser.put(UserAttributes.username, name);

    }

    public String getUserName(){

        return parseUser.getString(UserAttributes.username);

    }

    public void setEmail(String email){

        parseUser.put(UserAttributes.email, email);

    }

    public String getEmail(){

      return  parseUser.getString(UserAttributes.email);

    }

}
