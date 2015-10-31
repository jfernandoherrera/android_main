package com.amtechventures.tucita.model.context.user;

import com.parse.ParseUser;
import com.amtechventures.tucita.model.domain.user.User;

public class UserLocal {

    public User currentUser() {

        User user = new User();

        ParseUser parseUser = ParseUser.getCurrentUser();

        user.setParseUser(parseUser);

        return user;

    }

    public void logout() {

        ParseUser.logOut();

    }

}
