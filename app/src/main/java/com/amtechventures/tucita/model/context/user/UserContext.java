package com.amtechventures.tucita.model.context.user;

import com.amtechventures.tucita.model.domain.user.User;

public class UserContext {

    private UserLocal local;
    private UserRemote remote;

    public static UserContext context(UserContext userContext) {

        if (userContext == null) {

            userContext = new UserContext();

        }

        return userContext;

    }

    public UserContext() {

        local = new UserLocal();

        remote = new UserRemote();

    }

    public void login(String email, String password, UserCompletion.UserErrorCompletion completion) {

        remote.login(email, password, completion);

    }

    public void signup(String email, String password, UserCompletion.UserErrorCompletion completion) {

        remote.signup(email, password, completion);

    }

    public User currentUser() {

        return local.currentUser();

    }

    public void logout() {

        local.logout();

    }

}