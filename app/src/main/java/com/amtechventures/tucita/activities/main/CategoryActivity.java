package com.amtechventures.tucita.activities.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.adapters.CategoryGridAdapter;
import com.amtechventures.tucita.activities.login.LoginActivity;
import com.amtechventures.tucita.model.tucita.context.category.CategoryContext;



public class CategoryActivity extends AppCompatActivity {


    private RecyclerView.Adapter Adapter;

    private RecyclerView RecyclerView;

    private RecyclerView.LayoutManager LayoutManager;

    private CategoryContext categoryContext;

    public static final int COLUMNS_IN_CATEGORIES = 3;

    private Button signOrRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_category);

        RecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        signOrRegister=(Button) findViewById(R.id.go_to_login);

        RecyclerView.setHasFixedSize(true);

        LayoutManager = new GridLayoutManager(getApplicationContext(), COLUMNS_IN_CATEGORIES);

        RecyclerView.setLayoutManager(LayoutManager);

        try {
            categoryContext = CategoryContext.context(this, null);

            Adapter = new CategoryGridAdapter(categoryContext.getCategories());

            RecyclerView.setAdapter(Adapter);
        }catch(Exception e){
            Log.i(this.getClass().getName(),

                    getResources().getString(R.string.load_from_parse) +

                            getResources().getString(R.string.error_pinning_categories)

                            + e.getMessage());
        }



        signOrRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLogin();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                goToLogin();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    private void goToLogin() {

        Intent intent = new Intent(CategoryActivity.this, LoginActivity.class);

        startActivity(intent);

    }

}