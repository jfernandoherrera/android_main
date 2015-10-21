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
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import java.util.ArrayList;
import java.util.List;


public class CategoryActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;

    RecyclerView.LayoutManager mLayoutManager;

    RecyclerView.Adapter mAdapter;

    public static final int COLUMNS_IN_CATEGORIES = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_category);

        ParseObject.registerSubclass(Category.class);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, getResources().getString(R.string.app_parse_id), getResources().getString(R.string.client_parse_id));


        // Calling the RecyclerView
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);

        mRecyclerView.setHasFixedSize(true);

        Bundle b = getIntent().getExtras();
        // The number of Columns
        mLayoutManager = new GridLayoutManager(getApplicationContext(), COLUMNS_IN_CATEGORIES);

        mRecyclerView.setLayoutManager(mLayoutManager);

        loadFromParse();

  }
    private void loadFromParse() {

        ParseQuery<Category> query = Category.getQuery();

          query.findInBackground(new FindCallback<Category>() {

              public void done(final List<Category> categoryList, ParseException e) {

                  if (e == null) {

                      ParseObject.pinAllInBackground((List<Category>) categoryList,

                              new SaveCallback() {
                                  public void done(ParseException e) {

                                      if (e == null) {

                                          if (!isFinishing()) {

                                              ArrayList<Category> categories=new ArrayList();

                                              for (ParseObject cat : categoryList) {

                                                  categories.add((Category)cat);

                                              }
                                              mAdapter = new CategoryGridAdapter(categories);

                                              mRecyclerView.setAdapter(mAdapter);

                                          }
                                      } else {
                                          Log.i(this.getClass().getName(),getResources().getString(R.string.load_from_parse)+
                                                getResources().getString(R.string.error_finding_pinned_categories)
                                                          + e.getMessage());
                                      }
                                  }
                              });
                  } else {
                      Log.i(this.getClass().getName(),
                              getResources().getString(R.string.load_from_parse)+
                                      getResources().getString(R.string.error_pinning_categories)
                                      + e.getMessage());
                  }
              }
          });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }



}