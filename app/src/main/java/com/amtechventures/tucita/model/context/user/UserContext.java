package com.amtechventures.tucita.model.context.user;

import android.app.Activity;
import android.content.Context;
import com.amtechventures.tucita.model.domain.user.User;
import com.amtechventures.tucita.utils.blocks.Completion;
import com.amtechventures.tucita.model.context.facebook.FacebookContext;

public class UserContext {

    private User me;

    UserRemote userRemote;

    private UserLocal userLocal;

    private FacebookContext facebookContext ;

    private static UserContext userContext;

    public static UserContext context() {

        if (userContext == null) {

            userContext = new UserContext();

        }

        return userContext;

    }

    public UserContext() {

      facebookContext  = new FacebookContext();

        userRemote = new UserRemote();

        me=new User();
    }

    public String getAuthenticationType(){

    String authenticationType = me.getAuthType();

    return authenticationType;
    }

    public void setAuthenticationType(String authenticationType){
        me.setAuthType(authenticationType);
    }

    public void login(String email, String password, Completion.BoolBoolUserCompletion completion) {

      userRemote.login(email, password, completion);
    }

    public void signUp(String email, String password,String name, Completion.BoolErrorUserCompletion completion) {
      me.setParseUser(userRemote.signUp(email, password, name, completion));
    }

    public User me() {

        return me;

    }

    public void logOut(){
    UserLocal.logout();
    }

}