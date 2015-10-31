package com.amtechventures.tucita.model.context.user;

import com.parse.ParseUser;
import com.amtechventures.tucita.model.domain.user.User;

public class UserLocal {

    public User currentUser() {

        User user = null;

        ParseUser parseUser = ParseUser.getCurrentUser();

        if (parseUser != null) {

            user = new User();

            user.setParseUser(parseUser);

        }

        return user;

    }

    public void logout() {

        ParseUser.logOut();

    }

}
