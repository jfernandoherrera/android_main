package com.amtechventures.tucita.model.context.category;

import com.amtechventures.tucita.model.domain.category.Category;
import com.amtechventures.tucita.model.domain.category.CategoryAttributes;
import com.amtechventures.tucita.model.error.AppError;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class CategoryRemote {

    ParseQuery<Category> query;

    private void setQuery() {

        query = Category.getQuery();

    }

    public void cancelQuery() {

        if (query != null) {

            query.cancel();

        }

    }

    public void loadCategories(final CategoryCompletion.CategoriesErrorCompletion completion) {

        setQuery();

        query.orderByAscending(CategoryAttributes.name);

        query.findInBackground(new FindCallback<Category>() {

            @Override
            public void done(List<Category> categoryList, ParseException e) {

                if (e != null) {

                    e.printStackTrace();

                }

                if (categoryList != null) {

                    try {

                        ParseObject.pinAll(categoryList);

                    } catch (ParseException pe) {

                        pe.printStackTrace();

                    }

                }

                AppError appError = e != null ? new AppError(Category.class.toString(), 0, null) : null;

                completion.completion(categoryList, appError);

            }

        });

    }

}
