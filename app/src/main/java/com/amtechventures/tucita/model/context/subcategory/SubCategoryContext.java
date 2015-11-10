package com.amtechventures.tucita.model.context.subcategory;


import com.amtechventures.tucita.model.domain.category.Category;
import com.amtechventures.tucita.model.domain.category.CategoryAttributes;
import com.amtechventures.tucita.model.domain.subcategory.SubCategory;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import java.util.List;

public class SubCategoryContext {

    private SubCategoryLocal subCategoryLocal;
    private SubCategoryRemote subCategoryRemote;

    public static SubCategoryContext context(SubCategoryContext subCategoryContext) {

        if (subCategoryContext == null) {

            subCategoryContext = new SubCategoryContext();

        }

        return subCategoryContext;

    }

    public SubCategoryContext(){

        subCategoryRemote = new SubCategoryRemote();

        subCategoryLocal = new SubCategoryLocal();
    }

    public List<SubCategory> loadServices(Category category, SubCategoryCompletion.ErrorCompletion completion){

      List services;

        ParseRelation object = (ParseRelation) category.get(CategoryAttributes.subCategories);

        ParseQuery<SubCategory> queryLocal = object.getQuery();

        services = subCategoryLocal.loadServices(queryLocal);

        ParseQuery<SubCategory> queryRemote = object.getQuery();

        subCategoryRemote.loadServices(queryRemote,completion);

        return services;
    }

    public List<SubCategory> loadLikeServices(String likeWord, SubCategoryCompletion.ErrorCompletion completion){

        List services;

        services = subCategoryLocal.loadLikeServices(likeWord);

        subCategoryRemote.loadLikeServices(likeWord, completion);

        return services;
    }
}
