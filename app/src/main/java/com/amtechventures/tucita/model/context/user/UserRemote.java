package com.amtechventures.tucita.model.context.user;

import com.parse.ParseUser;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.SignUpCallback;

import com.amtechventures.tucita.model.error.AppError;
import com.amtechventures.tucita.model.domain.user.User;
import com.amtechventures.tucita.model.domain.category.Category;

public class UserRemote {

    public void login(String email, String password, final UserCompletion.UserErrorCompletion completion) {

        ParseUser.logInInBackground(email, password, new LogInCallback() {

            @Override
            public void done(ParseUser parseUser, ParseException e) {

                User user = null;

                AppError appError = null;

                if (e == null) {

                    user = new User();

                    user.setParseUser(parseUser);

                    appError = e != null ? new AppError(Category.class.toString(), 0, null) : null;

                }

                completion.completion(user, appError);

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

                User user = null;

                AppError appError = null;

                if (e == null) {

                    user = new User();

                    user.setParseUser(parseUser);

                    appError = e != null ? new AppError(Category.class.toString(), 0, null) : null;

                }

                completion.completion(user, appError);

            }

        });

    }

}
