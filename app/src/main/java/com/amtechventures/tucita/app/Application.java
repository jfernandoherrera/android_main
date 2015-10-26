package com.amtechventures.tucita.app;

import com.parse.Parse;
import com.parse.ParseObject;
import com.facebook.FacebookSdk;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.model.domain.category.Category;
import com.parse.ParseFacebookUtils;
public class Application extends android.app.Application {

    public void onCreate() {

    	super.onCreate();



        String app_parse_id = getResources().getString(R.string.app_parse_id);

        String app_client_parse_id=getResources().getString(R.string.client_parse_id);

        Parse.enableLocalDatastore(this);

        Parse.initialize(this, app_parse_id, app_client_parse_id);
        ParseFacebookUtils.initialize(this);


        ParseObject.registerSubclass(Category.class);

    }

}
