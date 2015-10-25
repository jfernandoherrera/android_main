package com.amtechventures.tucita.app;

import com.parse.Parse;
import com.parse.ParseObject;
import com.facebook.FacebookSdk;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.model.domain.category.Category;

public class Application extends android.app.Application {

    public void onCreate() {

    	super.onCreate();

        FacebookSdk.sdkInitialize(getApplicationContext());

        Parse.enableLocalDatastore(this);

        String app_parse_id = getResources().getString(R.string.app_parse_id);

        Parse.initialize(getApplicationContext(), app_parse_id, getResources().getString(R.string.client_parse_id));

        ParseObject.registerSubclass(Category.class);

    }

}
