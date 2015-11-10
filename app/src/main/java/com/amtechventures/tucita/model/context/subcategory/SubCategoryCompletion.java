package com.amtechventures.tucita.model.context.subcategory;


import com.amtechventures.tucita.model.domain.subcategory.SubCategory;
import com.amtechventures.tucita.model.error.AppError;

import java.util.List;

public class SubCategoryCompletion {

    public interface ErrorCompletion {

        void completion(List<SubCategory> subCategoriesList, AppError error);

    }
}
