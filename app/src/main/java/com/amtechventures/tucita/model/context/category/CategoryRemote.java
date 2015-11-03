package com.amtechventures.tucita.model.context.category;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.amtechventures.tucita.model.context.service.ServiceContext;
import com.amtechventures.tucita.model.context.service.ServiceLocal;
import com.amtechventures.tucita.model.context.service.ServiceRemote;
import com.amtechventures.tucita.model.context.service.ServicesCompletion;
import com.amtechventures.tucita.model.domain.service.Service;
import com.parse.ParseQuery;
import com.parse.ParseObject;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.amtechventures.tucita.model.error.AppError;
import com.amtechventures.tucita.model.domain.category.Category;
import com.parse.ParseRelation;

public class CategoryRemote {

    public void loadCategories(final CategoryCompletion.CategoriesErrorCompletion completion) {

        ParseQuery<Category> query = Category.getQuery();

        query.findInBackground(new FindCallback<Category>() {

            @Override
            public void done(List<Category> categoryList, ParseException e) {

                if (categoryList != null) {

                    try {

                        ParseObject.pinAll(categoryList);

                    } catch (ParseException pe) {}

                }

                AppError appError = e != null ? new AppError(Category.class.toString(), 0, null) : null;

                completion.completion(categoryList, appError);

            }

        });

    }

}
