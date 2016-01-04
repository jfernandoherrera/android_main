package com.amtechventures.tucita.model.context.user;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import com.amtechventures.tucita.model.domain.user.UserAttributes;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
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
import org.json.JSONException;
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

    public void signup(String email, String password, String name, final UserCompletion.UserErrorCompletion completion) {

        final ParseUser parseUser = new ParseUser();

        parseUser.setEmail(email);

        parseUser.setUsername(email);

        parseUser.setPassword(password);
        
        parseUser.put(UserAttributes.name, name);

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



                if (parseUser != null && parseUser.isNew()) {

                        final User user = new User();

                        user.setParseUser(parseUser);

                        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {

                                String name = null;

                                String email = null;

                                try {
                                    name = object.getString(UserAttributes.name);

                                    email = object.getString(UserAttributes.email);

                                } catch (JSONException e) {

                                    e.printStackTrace();
                                }
                                user.setEmail(email);

                                user.setName(name);

                                saveUser(user);
                            }
                        });
                        Bundle parameters = new Bundle();

                        String fields = "fields";

                        parameters.putString(fields, UserAttributes.name + "," + UserAttributes.email);

                        request.setParameters(parameters);

                        request.executeAsync();
                }

                processLogin(parseUser, e, completion);
            }

        });

    }

    public Bitmap getPicture(ParseUser parseUser){

        Bitmap icon = null;
        URL img_value = null;

        try {
            HashMap authData = (HashMap) parseUser.get("authData");

            HashMap facebook = (HashMap) authData.get("facebook");

            img_value = new URL("https://graph.facebook.com/" + facebook.get("id") + "/picture?type=square");

        } catch (MalformedURLException exception) {

            exception.printStackTrace();
        }
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);

            icon = BitmapFactory.decodeStream((InputStream) img_value.getContent());

        } catch (IOException exce) {

            exce.printStackTrace();
        }
    return icon;
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

    public void saveUser(User user){

        List users = new ArrayList();

        users.add(user.getParseUser());

        try {

            ParseObject.saveAll(users);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}