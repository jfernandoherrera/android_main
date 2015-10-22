package com.amtechventures.tucita.model.tucita.context.category;

import android.content.Context;
import android.util.Log;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.model.category.Category;
import com.amtechventures.tucita.model.remote.RemoteCategory;
import com.parse.ParseException;
import java.util.ArrayList;

public class  CategoryContext {

    private ArrayList<Category> categories;

    private RemoteCategory remoteCategory;

    public static CategoryContext context(Context context, CategoryContext categoryContext) {

        if(categoryContext==null){

         categoryContext=new CategoryContext(context);
        }

        return  categoryContext;
    }

    CategoryContext(Context context){

    remoteCategory=new RemoteCategory(context);

        try {
            categories= remoteCategory.loadFromParse();

        } catch (ParseException e) {

            Log.i(this.getClass().getName(),

                    context.getResources().getString(R.string.load_from_parse)+

                            context.getResources().getString(R.string.error_pinning_categories)

                                + e.getMessage());
        }
    }
    public ArrayList<Category> getCategories(){

        return categories;
    }



}