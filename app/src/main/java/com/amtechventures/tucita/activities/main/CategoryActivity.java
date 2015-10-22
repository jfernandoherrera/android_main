package com.amtechventures.tucita.activities.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.adapters.CategoryGridAdapter;
import com.amtechventures.tucita.model.category.Category;
import com.amtechventures.tucita.model.tucita.context.category.CategoryContext;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import java.util.ArrayList;
import java.util.List;


public class CategoryActivity extends AppCompatActivity {


    RecyclerView.Adapter mAdapter;

    RecyclerView mRecyclerView;

    RecyclerView.LayoutManager mLayoutManager;

    CategoryContext categoryContext;

    public static final int COLUMNS_IN_CATEGORIES = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_category);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mRecyclerView.setHasFixedSize(true);

        ParseObject.registerSubclass(Category.class);

        mLayoutManager = new GridLayoutManager(getApplicationContext(), COLUMNS_IN_CATEGORIES);

        mRecyclerView.setLayoutManager(mLayoutManager);

        categoryContext=CategoryContext.context(this,null);

        mAdapter = new CategoryGridAdapter(categoryContext.getCategories());

        mRecyclerView.setAdapter(mAdapter);
  }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }



}