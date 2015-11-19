package com.amtechventures.tucita.model.context.category;

import java.util.List;

import com.amtechventures.tucita.model.domain.category.Category;

public class CategoryContext {

    private CategoryLocal categoryLocal;

    private CategoryRemote categoryRemote;

    public static CategoryContext context(CategoryContext categoryContext) {

        if (categoryContext == null) {

            categoryContext = new CategoryContext();
        }

        return  categoryContext;
    }

    public CategoryContext() {

        categoryLocal = new CategoryLocal();

        categoryRemote = new CategoryRemote();

    }

    public void cancelQuery(){

        categoryRemote.cancelQuery();
    }

    public List<Category> loadCategories(final CategoryCompletion.CategoriesErrorCompletion completion)  {

        List<Category> list = categoryLocal.loadCategories();

        categoryRemote.loadCategories(completion);

        return list;
    }

    public Category findCategory(String name){

       Category category = categoryLocal.findCategory(name);

        return category;
    }

}