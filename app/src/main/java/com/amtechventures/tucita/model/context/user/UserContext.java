package com.amtechventures.tucita.model.context.user;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;

import com.amtechventures.tucita.model.context.appointmentVenue.AppointmentVenueCompletion;
import com.amtechventures.tucita.model.domain.appointment.Appointment;
import com.amtechventures.tucita.model.domain.appointmentVenue.AppointmentVenue;
import com.amtechventures.tucita.model.domain.user.User;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.utils.blocks.Completion;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import java.util.List;

public class UserContext {

    private UserLocal local;
    private UserRemote remote;

    public static UserContext context(UserContext userContext) {

        if (userContext == null) {

            userContext = new UserContext();

        }

        return userContext;

    }
    public void putAppointmentVenue(AppointmentVenue appointmentVenue, User user, UserCompletion.UserErrorCompletion completion) {

        remote.putAppointmentVenue(appointmentVenue, user, completion);

    }

    public UserContext() {

        local = new UserLocal();

        remote = new UserRemote();

    }

    public void login(String email, String password, UserCompletion.UserErrorCompletion completion) {

        remote.login(email, password, completion);

    }

    public void signup(String email, String password, String name, UserCompletion.UserErrorCompletion completion) {

        remote.signup(email, password, name, completion);

    }

    public void loginWithFacebook(Activity activity, UserCompletion.UserErrorCompletion completion) {

        remote.loginWithFacebook(activity, completion);

    }

    public User currentUser() {

        return local.currentUser();

    }

    public boolean isFacebook(User user) {

        user = remote.fetch(user);

        boolean isFacebook =  ParseFacebookUtils.isLinked(user.getParseUser());

        return isFacebook;

    }

    public Bitmap getPicture() {

        User user = currentUser();

        Bitmap bitmap = null;

        if (user != null) {

            bitmap = remote.getPicture(user.getParseUser());

        }

        return bitmap;

    }

    public AppointmentVenue getAppointmentVenue(Venue venue, User user) {

        return remote.getAppointmentVenue(venue,user);

    }

    public void getAppointmentVenues(User user, AppointmentVenueCompletion.ErrorCompletion completion) {

        remote.getAppointmentVenues(user, completion);

    }

    public void logout() {

        local.logout();

    }

}