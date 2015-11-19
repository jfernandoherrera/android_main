package com.amtechventures.tucita.model.context.category;

import java.util.List;

import com.amtechventures.tucita.model.domain.category.CategoryAttributes;
import com.parse.ParseQuery;
import com.parse.ParseObject;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.amtechventures.tucita.model.error.AppError;
import com.amtechventures.tucita.model.domain.category.Category;

public class CategoryRemote {

    public void loadCategories(final CategoryCompletion.CategoriesErrorCompletion completion) {

        ParseQuery<Category> query = Category.getQuery();
        
        query.orderByAscending(CategoryAttributes.name);

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
