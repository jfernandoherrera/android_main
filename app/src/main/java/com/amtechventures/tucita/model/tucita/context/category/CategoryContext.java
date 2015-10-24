package com.amtechventures.tucita.model.tucita.context.category;

import android.content.Context;
import android.util.Log;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.model.category.Category;
import com.amtechventures.tucita.model.local.CategoryLocal;
import com.amtechventures.tucita.model.remote.CategoryRemote;
import com.parse.ParseException;
import java.util.ArrayList;

public class  CategoryContext {

    private ArrayList<Category> categories;

    private CategoryRemote categoryRemote;

    private CategoryLocal categoryLocal;

    public static CategoryContext context(Context context, CategoryContext categoryContext) throws ParseException {

        if(categoryContext==null){

         categoryContext=new CategoryContext(context);
        }

        return  categoryContext;
    }

    private CategoryContext(Context context) throws ParseException {
        categoryLocal =new CategoryLocal(context);
        try {
            loadFromLocal(context);
        }catch (Exception e){
            Log.i(this.getClass().getName(),

                    context.getResources().getString(R.string.load_from_parse) +

                            context.getResources().getString(R.string.error_pinning_categories)

                            + e.getMessage());
        }
            if(categories.isEmpty()){
            categoryRemote =new CategoryRemote(context);
            loadFromRemote(context);
            CategoryLocal.pinAll(categories);
        }


    }

    private void loadFromLocal(Context context) throws ParseException {
        categories= categoryLocal.loadFromParseLocal();

    }
    private void loadFromRemote(Context context) throws ParseException {

        categories= categoryRemote.loadFromParse();
    }
    public ArrayList<Category> getCategories(){

        return categories;
    }



}