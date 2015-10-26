package com.amtechventures.tucita.activities.category;

import java.util.List;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import java.util.ArrayList;
import android.view.MenuItem;
import android.widget.Button;
import android.content.Intent;
import com.amtechventures.tucita.R;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import com.amtechventures.tucita.model.error.AppError;
import com.amtechventures.tucita.activities.login.LoginActivity;
import com.amtechventures.tucita.model.domain.category.Category;
import com.amtechventures.tucita.model.context.category.CategoryContext;
import com.amtechventures.tucita.model.context.category.CategoryCompletion;
import com.amtechventures.tucita.activities.category.adapters.CategoryGridAdapter;
import com.amtechventures.tucita.utils.blocks.Strings;

public class CategoryActivity extends AppCompatActivity {

    private int COLUMNS_IN_CATEGORIES = 3;

    private Button signOrRegister;

    private RecyclerView recyclerView;

    private RecyclerView.Adapter adapter;

    private RecyclerView.LayoutManager layoutManager;

    private CategoryContext categoryContext;

    private List<Category> categories = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        String authenticated= Strings.FIELDS;

        if(getIntent().getExtras().getBoolean(authenticated)){

            setContentView(R.layout.activity_category_logged);
        }else{
            setContentView(R.layout.activity_category);

            signOrRegister = (Button)findViewById(R.id.go_to_login);

            signOrRegister.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    goToLogin();

                }

            });
        }


        categoryContext = CategoryContext.context(categoryContext);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(getApplicationContext(), COLUMNS_IN_CATEGORIES);

        recyclerView.setLayoutManager(layoutManager);



        setupGrid();

    }

    private void setupGrid() {

        List<Category> categoryList = categoryContext.loadCategories(new CategoryCompletion.CategoriesErrorCompletion() {

            @Override
            public void completion(List<Category> categoryList, AppError error) {

                if (categoryList != null) {

                   categories.clear();

                   categories.addAll(categoryList);

                    adapter.notifyDataSetChanged();

                }else {
                 
                    noInternetConecctionAlert();
                    
                }

            }

        });

        categories.addAll(categoryList);

        adapter = new CategoryGridAdapter(categories);

        recyclerView.setAdapter(adapter);

    }
    private void noInternetConecctionAlert(){

        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.alert))
                .setMessage(getResources().getString(R.string.no_internet_connection))
                .setRecycleOnMeasureEnabled(true)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView)MenuItemCompat.getActionView(searchItem);

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