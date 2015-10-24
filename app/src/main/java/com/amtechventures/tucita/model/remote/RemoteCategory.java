package com.amtechventures.tucita.model.remote;


import android.content.Context;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.model.category.Category;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.ArrayList;
import java.util.List;

public class RemoteCategory {

    Context con;

    public RemoteCategory(Context con) {

    this.con=con;

    }
    public ArrayList<Category>  loadFromParse() throws ParseException {

        ArrayList<Category> categories = new ArrayList();

        ParseQuery<Category> query = Category.getQuery();

        List<Category> find=query.find();

        for(ParseObject cat:find) {

            Category cate=new Category();

            cate.setName(cat.get(con.getResources().getString(R.string.name)).toString());

            categories.add(cate);
        }


        return categories;
        }



}
