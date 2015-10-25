package com.amtechventures.tucita.model.context.category;

import com.amtechventures.tucita.model.error.AppError;
import com.amtechventures.tucita.model.domain.category.Category;

import java.util.List;

public class CategoryCompletion {

    public interface CategoriesErrorCompletion {

        void completion(List<Category> categoryList, AppError error);

    }

}
