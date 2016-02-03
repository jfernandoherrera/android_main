package com.amtechventures.tucita.model.domain.category;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.amtechventures.tucita.model.domain.venue.VenueAttributes;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@ParseClassName("Category")
public class Category extends ParseObject {

    public void setName(String name) {

        put(CategoryAttributes.name, name);

    }

    public String getName() {

        return getString(CategoryAttributes.name);

    }

    public Bitmap getPicture() {

        ParseFile picture = getParseFile(VenueAttributes.picture);

        Bitmap bm = null;

        if (picture != null) {

            try {

                bm = BitmapFactory.decodeByteArray(picture.getData(), 0, picture.getData().length);

            } catch (ParseException e) {

                e.printStackTrace();

            }

        }

        return bm;

    }

    public static ParseQuery<Category> getQuery() {

        ParseQuery<Category> query = ParseQuery.getQuery(Category.class);

        return query;

    }

}
