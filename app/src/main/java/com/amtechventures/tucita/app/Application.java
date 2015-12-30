package com.amtechventures.tucita.app;

import com.amtechventures.tucita.model.domain.appointment.Appointment;
import com.amtechventures.tucita.model.domain.city.City;
import com.amtechventures.tucita.model.domain.openingHour.OpeningHour;
import com.amtechventures.tucita.model.domain.service.Service;
import com.amtechventures.tucita.model.domain.slot.Slot;
import com.amtechventures.tucita.model.domain.subcategory.SubCategory;
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


        Parse.enableLocalDatastore(this);

        ParseObject.registerSubclass(Category.class);

        ParseObject.registerSubclass(Venue.class);

        ParseObject.registerSubclass(OpeningHour.class);

        ParseObject.registerSubclass(SubCategory.class);

        ParseObject.registerSubclass(City.class);

        ParseObject.registerSubclass(Service.class);

        ParseObject.registerSubclass(Appointment.class);

        ParseObject.registerSubclass(Slot.class);

        FacebookSdk.sdkInitialize(this);

        Parse.initialize(this);

        ParseFacebookUtils.initialize(this);

    }



}
