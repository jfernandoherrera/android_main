package com.amtechventures.tucita.model.tucita.context.user;

import android.content.Context;

import com.amtechventures.tucita.model.user.User;
import com.amtechventures.tucita.utils.blocks.Completion;

import java.util.List;

public class UserContext {


    private FacebookContext facebookContext = new FacebookContext();

    public static UserContext context(Context context, UserContext userContext) {

        if (userContext == null) {

            userContext = new UserContext(context);

        }

        return userContext;

    }

    public UserContext(Context context) {


    }

    /*
        public User me() {


            return me;

        }
    */
    public String logged() {
        String logged = "";

        return logged;

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