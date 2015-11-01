package com.amtechventures.tucita.app;

import com.parse.Parse;

import com.parse.ParseObject;
import com.facebook.FacebookSdk;
import com.parse.ParseFacebookUtils;
import com.amtechventures.tucita.utils.strings.Strings;
import com.amtechventures.tucita.model.domain.category.Category;

public class Application extends android.app.Application {

    public void onCreate() {

        super.onCreate();

        String app_parse_id = Strings.APP_PARSE_ID;

        String app_client_parse_id = Strings.CLIENT_PARSE_ID;

        Parse.enableLocalDatastore(this);

        Parse.initialize(this, app_parse_id, app_client_parse_id);

        ParseFacebookUtils.initialize(this);

        FacebookSdk.sdkInitialize(this);

        ParseObject.registerSubclass(Category.class);

    }

}
