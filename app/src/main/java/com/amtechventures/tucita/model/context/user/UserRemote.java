package com.amtechventures.tucita.model.context.user;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import com.amtechventures.tucita.model.context.appointmentVenue.AppointmentVenueCompletion;
import com.amtechventures.tucita.model.context.appointmentVenue.AppointmentVenueContext;
import com.amtechventures.tucita.model.domain.appointment.Appointment;
import com.amtechventures.tucita.model.domain.appointmentVenue.AppointmentVenue;
import com.amtechventures.tucita.model.domain.appointmentVenue.AppointmentVenueAttributes;
import com.amtechventures.tucita.model.domain.category.Category;
import com.amtechventures.tucita.model.domain.facebook.FacebookPermissions;
import com.amtechventures.tucita.model.domain.user.User;
import com.amtechventures.tucita.model.domain.user.UserAttributes;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.error.AppError;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class UserRemote {

    public void login(String email, String password, final UserCompletion.UserErrorCompletion completion) {

        ParseUser.logInInBackground(email, password, new LogInCallback() {

            @Override
            public void done(ParseUser parseUser, ParseException e) {

                processLogin(parseUser, e, completion);

            }

        });

    }


    public AppointmentVenue getAppointmentVenue(Venue venue, User user) {

        AppointmentVenue appointmentVenue = null;

        try {

            ParseQuery<ParseUser> query = ParseUser.getQuery();

            query.include(UserAttributes.appointmentVenues);

            query.whereEqualTo("objectId", user.getObjectId());

            ParseUser parseUser = query.getFirst();

        List<AppointmentVenue> appointmentVenues = parseUser.getList(UserAttributes.appointmentVenues);

            if(appointmentVenues != null) {

                for (AppointmentVenue current : appointmentVenues) {

                    try {

                        current.fetch();

                        Venue currentVenue = current.getVenue();

                        if (currentVenue.getObjectId().equals(venue.getObjectId())) {

                            appointmentVenue = current;

                            break;

                        }

                    } catch (ParseException e) {

                        e.printStackTrace();

                    }

                }

            }

        } catch (ParseException e) {

            e.printStackTrace();

        }

        return appointmentVenue;

    }



    public void getAppointmentVenues(User user, final AppointmentVenueCompletion.ErrorCompletion completion) {

            ParseQuery<ParseUser> query = ParseUser.getQuery();

            query.include(UserAttributes.appointmentVenues);

            String venueOfAppointmentVenue = UserAttributes.appointmentVenues + "." + AppointmentVenueAttributes.venue;

            query.include(venueOfAppointmentVenue);

            query.whereEqualTo("objectId", user.getObjectId());

            query.getFirstInBackground(new GetCallback<ParseUser>() {

                @Override
                public void done(ParseUser object, ParseException e) {

                    List<AppointmentVenue> appointmentVenues;

                    ParseUser  parseUser = object;

                    appointmentVenues = parseUser.getList(UserAttributes.appointmentVenues);

                    AppError appError = e == null ? null : new AppError(Category.class.toString(), 0, null);

                    completion.completion(appointmentVenues, appError);

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

                if (e != null) {

                    e.printStackTrace();

                }

                if (parseUser != null && parseUser.isNew()) {

                    final User user = new User();

                    user.setParseUser(parseUser);

                    GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
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

    public void putAppointmentVenue(AppointmentVenue appointmentVenue, final User user, final UserCompletion.UserErrorCompletion completion) {

        final ParseUser parseUser = user.getParseUser();

        parseUser.addUnique(UserAttributes.appointmentVenues, appointmentVenue);

        parseUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                AppError appError = e == null ? null : new AppError(User.class.toString(), 0, null);

                if(e != null){

                    e.printStackTrace();

                }

                completion.completion(user, appError);

            }
        });

    }

    public Bitmap getPicture(ParseUser parseUser) {

        Bitmap icon = null;

        URL img_value = null;

        try {

            HashMap authData = (HashMap) parseUser.get("authData");

            HashMap facebook = (HashMap) authData.get("facebook");

            img_value = new URL("https://graph.facebook.com/" + facebook.get("id") + "/picture?type=normal");

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

    public User fetch(User user){

        try {

            user.getParseUser().fetch();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return user;
    }

    private void processLogin(ParseUser parseUser, ParseException e, UserCompletion.UserErrorCompletion completion) {

        User user = null;

        AppError appError = new AppError(Category.class.toString(), 0, null);

        if(e != null){

            e.printStackTrace();

        }

        if (e == null && parseUser != null) {

            user = new User();

            user.setParseUser(parseUser);

            appError = null;

        }

        completion.completion(user, appError);

    }

    public void saveUser(User user) {

        List users = new ArrayList();

        users.add(user.getParseUser());

        try {

            ParseObject.saveAll(users);

        } catch (ParseException e) {

            e.printStackTrace();

        }

    }

}