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

    public static UserContext context(Context context) {

        if (userContext == null) {

            userContext = new UserContext(context);

        }

        return userContext;

    }

    public UserContext(Context context) {

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

    public void login(Activity activity, Completion.BoolBoolUserCompletion completion) {

      userRemote.login(activity,completion);
    }

    public void signUp(Activity activity, Completion.BoolErrorUserCompletion completion) {
      me.setParseUser(userRemote.signUp(activity, completion));
    }

    public User me() {

        return me;

    }

}