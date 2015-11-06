package com.amtechventures.tucita.app;

import com.amtechventures.tucita.model.domain.openingHour.OpeningHour;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.parse.Parse;
import com.parse.ParseObject;
import com.facebook.FacebookSdk;
import com.parse.ParseFacebookUtils;
import io.fabric.sdk.android.Fabric;
import com.crashlytics.android.Crashlytics;
import com.amtechventures.tucita.utils.strings.Strings;
import com.amtechventures.tucita.model.domain.category.Category;

public class Application extends android.app.Application {

    public void onCreate() {

        super.onCreate();
        
        Fabric.with(this, new Crashlytics());

        String app_parse_id = Strings.APP_PARSE_ID;

        String app_client_parse_id = Strings.CLIENT_PARSE_ID;

        Parse.enableLocalDatastore(this);

        ParseObject.registerSubclass(Category.class);

        ParseObject.registerSubclass(Venue.class);

        ParseObject.registerSubclass(OpeningHour.class);

        Parse.initialize(this, app_parse_id, app_client_parse_id);

        ParseFacebookUtils.initialize(this);

        FacebookSdk.sdkInitialize(this);

    }

}
