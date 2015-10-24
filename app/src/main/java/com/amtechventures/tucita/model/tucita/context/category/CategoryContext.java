package com.amtechventures.tucita.model.tucita.context.category;

import android.content.Context;
import android.util.Log;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.model.category.Category;
import com.amtechventures.tucita.model.local.LocalCategory;
import com.amtechventures.tucita.model.remote.RemoteCategory;
import com.parse.ParseException;
import java.util.ArrayList;

public class  CategoryContext {

    private ArrayList<Category> categories;

    private RemoteCategory remoteCategory;

    private LocalCategory localCategory;

    public static CategoryContext context(Context context, CategoryContext categoryContext) throws ParseException {

        if(categoryContext==null){

         categoryContext=new CategoryContext(context);
        }

        return  categoryContext;
    }

    private CategoryContext(Context context) throws ParseException {
        localCategory=new LocalCategory(context);
        try {
            loadFromLocal(context);
        }catch (Exception e){
            Log.i(this.getClass().getName(),

                    context.getResources().getString(R.string.load_from_parse) +

                            context.getResources().getString(R.string.error_pinning_categories)

                            + e.getMessage());
        }
            if(categories.isEmpty()){
            remoteCategory=new RemoteCategory(context);
            loadFromRemote(context);
            LocalCategory.pinAll(categories);
        }


    }

    private void loadFromLocal(Context context) throws ParseException {
        categories=localCategory.loadFromParseLocal();

    }
    private void loadFromRemote(Context context) throws ParseException {

        categories= remoteCategory.loadFromParse();
    }
    public ArrayList<Category> getCategories(){

        return categories;
    }



}