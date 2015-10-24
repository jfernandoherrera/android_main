package com.amtechventures.tucita.model.local;

import android.content.Context;
import android.util.Log;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.model.category.Category;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by techventures on 22/10/15.
 */
public class CategoryLocal {


    Context con;

    public CategoryLocal(Context con) {

        this.con=con;

    }
    public ArrayList<Category> loadFromParseLocal() throws ParseException {



        ParseQuery<Category> query = Category.getQuery();
        query.fromLocalDatastore();
        List<Category> find=query.find();
        ArrayList<Category> categories = new ArrayList();
        for(ParseObject cat:find) {

            Category cate=new Category();

            cate.setName(cat.get(con.getResources().getString(R.string.name)).toString());

            categories.add(cate);
        }


        return categories;
    }
    public static void pinAll(ArrayList categories){
        ParseObject.pinAllInBackground(categories);
    }
}
