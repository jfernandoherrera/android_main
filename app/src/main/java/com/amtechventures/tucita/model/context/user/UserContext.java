package com.amtechventures.tucita.model.context.user;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import com.amtechventures.tucita.model.domain.user.User;
import com.amtechventures.tucita.utils.blocks.Completion;
import com.amtechventures.tucita.model.context.facebook.FacebookContext;

public class UserContext {

    private User me;
    private FacebookContext facebookContext ;

    public static UserContext context(Context context, UserContext userContext) {

        if (userContext == null) {

            userContext = new UserContext(context);

        }

        return userContext;

    }

    public UserContext(Context context) {

      facebookContext  = new FacebookContext(context);

    }

    public void login(Activity activity, Completion.BoolBoolCompletion completion) {

       facebookContext.login(activity,completion);


    }

    public void logout() {


    }

    private void reportDeclinedPermissions() {


    }

    public void updateMe() {


    }

    public List<User> loadFriends(String searchString, Long offset, Long limit) {

        List<User> users = null;

        return users;

    }

    //public long friendsCount(String name) {}

    public void loadSuperUser(final Completion.ErrorCompletion completion) {


    }

    public void loadFriends(String name, Long offset, Completion.IntErrorCompletion completion) {


    }

    public void loadFacebookFriends(Long offset, final Completion.LongErrorCompletion completion) {


    }

    public void syncContacts(List<?> contacts, Completion.ErrorCompletion completion) {


    }

    // public User findFriend(String userId) {    }

    public void updateRemote(User user, final Completion.BoolCompletion completion) {

    /*    remote.update(user, null, new Completion.BoolErrorCompletion() {

            @Override
            public void completion(boolean success, AppError error) {

                if (completion != null) {

                    completion.completion(success);

                }

            }

        });*/

    }

    public void updateRemote(User user, String password, final Completion.BoolErrorCompletion completion) {

        /*remote.update(user, password, new Completion.BoolErrorCompletion() {

            @Override
            public void completion(boolean success, AppError error) {

                completion.completion(success, error);

            }

        });*/

    }

    //public void updateWithRemoteUser(User user) { }

}