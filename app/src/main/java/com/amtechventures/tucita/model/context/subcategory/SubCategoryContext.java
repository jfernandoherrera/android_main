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

    public List<SubCategory> loadSubCategories(Category category, SubCategoryCompletion.ErrorCompletion completion){

      List subCategories;

        ParseRelation object = (ParseRelation) category.get(CategoryAttributes.subCategories);

        ParseQuery<SubCategory> queryLocal = object.getQuery();

        subCategories = subCategoryLocal.loadSubCategories(queryLocal);

        ParseQuery<SubCategory> queryRemote = object.getQuery();

        subCategoryRemote.loadSubCategories(queryRemote,completion);

        return subCategories;
    }

    public List<SubCategory> loadLikeSubCategories(String likeWord, SubCategoryCompletion.ErrorCompletion completion){

        List subCategories;

        subCategories = subCategoryLocal.loadLikeSubCategories(likeWord);

        subCategoryRemote.loadLikeSubCategories(likeWord, completion);

        return subCategories;
    }

    public SubCategory findSubCategory(String name){
        return subCategoryLocal.findSubCategory(name);
    }
}
