package com.amtechventures.tucita.model.context.user;

import java.util.List;
import java.util.Arrays;

import android.app.Activity;

import com.parse.ParseUser;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.SignUpCallback;
import com.parse.ParseFacebookUtils;

import com.amtechventures.tucita.model.error.AppError;
import com.amtechventures.tucita.model.domain.user.User;
import com.amtechventures.tucita.model.domain.category.Category;

import com.amtechventures.tucita.model.domain.facebook.FacebookPermissions;

public class UserRemote {

    public void login(String email, String password, final UserCompletion.UserErrorCompletion completion) {

        ParseUser.logInInBackground(email, password, new LogInCallback() {

            @Override
            public void done(ParseUser parseUser, ParseException e) {

                processLogin(parseUser, e, completion);

            }

        });

    }

    public void signup(String email, String password, final UserCompletion.UserErrorCompletion completion) {

        final ParseUser parseUser = new ParseUser();

        parseUser.setEmail(email);

        parseUser.setUsername(email);

        parseUser.setPassword(password);

        parseUser.signUpInBackground(new SignUpCallback() {

            @Override
            public void done(ParseException e) {

                processLogin(parseUser, e, completion);

            }

        });

    }

    public void loginWithFacebook(Activity activity, final UserCompletion.UserErrorCompletion completion) {

        List<String> permissions = Arrays.asList(FacebookPermissions.public_profile, FacebookPermissions.email, FacebookPermissions.user_friends);

        ParseFacebookUtils.logInWithReadPermissionsInBackground(activity, permissions, new LogInCallback() {

            @Override
            public void done(ParseUser parseUser, ParseException e) {

                processLogin(parseUser, e, completion);

            }

        });

    }

    private void processLogin(ParseUser parseUser, ParseException e, UserCompletion.UserErrorCompletion completion) {

        User user = null;

        AppError appError = new AppError(Category.class.toString(), 0, null);

        if (e == null && parseUser != null) {

            user = new User();

            user.setParseUser(parseUser);

            appError = null;

        }

        completion.completion(user, appError);

    }

}