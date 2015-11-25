package com.amtechventures.tucita.model.context.subcategory;

import com.amtechventures.tucita.model.domain.category.CategoryAttributes;
import com.amtechventures.tucita.model.domain.subcategory.SubCategory;
import com.amtechventures.tucita.model.error.AppError;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.List;

public class SubCategoryRemote {

    ParseQuery<SubCategory> query;

    private void setQuery(){

        query = SubCategory.getQuery();
    }

    public void cancelQuery(){

        if(query != null){

            query.cancel();
        }

    }

    public void loadSubCategories(ParseQuery<SubCategory> subCategoryRemoteQuery, final SubCategoryCompletion.ErrorCompletion completion) {

        query = subCategoryRemoteQuery;

        query.orderByAscending(CategoryAttributes.name);

        query.findInBackground(new FindCallback<SubCategory>() {

            @Override
            public void done(List<SubCategory> objects, com.parse.ParseException e) {

                if (objects != null) {

                    try {

                        ParseObject.pinAll(objects);

                    } catch (ParseException pe) {
                    }

                }

                AppError appError = e != null ? new AppError(SubCategory.class.toString(), 0, null) : null;

                completion.completion(objects, appError);
            }

        });

    }

    public void loadLikeSubCategories(String like, final SubCategoryCompletion.ErrorCompletion completion) {

        setQuery();

        query.whereContains(CategoryAttributes.name, like).findInBackground(new FindCallback<SubCategory>() {
            @Override
            public void done(List<SubCategory> objects, com.parse.ParseException e) {

                if (objects != null) {
                    try {

                        ParseObject.pinAll(objects);

                    } catch (ParseException pe) {
                    }

                }

                AppError appError = e != null ? new AppError(SubCategory.class.toString(), 0, null) : null;

                completion.completion(objects, appError);
            }

        });

    }

}