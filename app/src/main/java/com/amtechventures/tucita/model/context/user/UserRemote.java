package com.amtechventures.tucita.model.context.user;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.util.Log;

import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.SignUpCallback;
import com.parse.ParseFacebookUtils;
import com.amtechventures.tucita.model.error.AppError;
import com.amtechventures.tucita.model.domain.user.User;
import com.amtechventures.tucita.model.domain.category.Category;
import com.amtechventures.tucita.model.domain.facebook.FacebookPermissions;

import org.json.JSONObject;

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


                URL img_value = null;
                try {
                   HashMap jsonObject = (HashMap) parseUser.get("authData");

                    HashMap uaae = (HashMap) jsonObject.get("facebook");

                    img_value = new URL("http://graph.facebook.com/"+uaae.get("id")+"/picture?type=large");

                } catch (MalformedURLException exception) {

                    exception.printStackTrace();
                }
                try {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                    StrictMode.setThreadPolicy(policy);

                    Bitmap mIcon1 = BitmapFactory.decodeStream(img_value.openConnection().getInputStream());

                    Log.i(mIcon1.toString(), "image retrieved from facebook");
                } catch (IOException exce) {

                    exce.printStackTrace();
                }

            }

        });

    }


                private void processLogin (ParseUser parseUser, ParseException
                e, UserCompletion.UserErrorCompletion completion){

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