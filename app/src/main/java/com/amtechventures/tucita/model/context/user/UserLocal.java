package com.amtechventures.tucita.model.context.user;

import com.amtechventures.tucita.model.domain.user.User;
import com.amtechventures.tucita.utils.strings.Strings;
import com.parse.ParseQuery;
import org.json.JSONObject;


public class UserLocal {




public void logout(){

}
/*

    public User findMe(boolean createIfNotFound){

        User user = null;

        ParseQuery<User> query = User.getQuery().fromLocalDatastore();

        try {

            user = query.getFirst();

            if (user == null && createIfNotFound == true) {
            user =new User();
            }

        }catch (Exception exception) {

            exception.printStackTrace();

        }
        return user;
    }

    public void populateWithDictionary(User user, JSONObject dictionary) {


        String name = dictionary.optString(Strings.NAME, null);

        if (name != null) {

            user.setUserName(name);

        }

        String email = dictionary.optString(Strings.EMAIL, null);

        if (email != null) {

            user.setEmail(email);

        }

        String photo = dictionary.optString(Strings.PHOTO_URL, null);

        if (photo != null) {

            user.setPictureURL(photo);

        }

        JSONObject picture = dictionary.optJSONObject(Strings.PICTURE);

        if (picture != null) {

            JSONObject data = picture.optJSONObject(Strings.DATA);

            if (data != null) {

                String urlString = data.optString(Strings.URL, null);

                if (urlString != null) {

                    user.setPictureURL(urlString);

                }

            }

        }

    }*/

}
