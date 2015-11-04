package com.amtechventures.tucita.activities.category;

import java.util.List;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import java.util.ArrayList;
import android.view.MenuItem;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageButton;
import com.amtechventures.tucita.R;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;

import com.amtechventures.tucita.activities.search.SearchActivity;
import com.amtechventures.tucita.model.error.AppError;
import com.amtechventures.tucita.model.context.user.UserContext;
import com.amtechventures.tucita.activities.login.LoginActivity;
import com.amtechventures.tucita.model.domain.category.Category;
import com.amtechventures.tucita.activities.account.AccountActivity;
import com.amtechventures.tucita.model.context.category.CategoryContext;
import com.amtechventures.tucita.model.context.category.CategoryCompletion;
import com.amtechventures.tucita.activities.category.adapters.CategoryGridAdapter;

public class CategoryActivity extends AppCompatActivity {

    private int COLUMNS_IN_CATEGORIES = 3;

    private ImageButton profile;
    private Button signOrRegister;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private CategoryContext categoryContext;
    private List<Category> categories = new ArrayList<>();

    private UserContext userContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        userContext = UserContext.context(userContext);

        setup();

        categoryContext = CategoryContext.context(categoryContext);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(getApplicationContext(), COLUMNS_IN_CATEGORIES);

        recyclerView.setLayoutManager(layoutManager);

        setupGrid();

    }

    private void setup() {

        if (userContext.currentUser() != null) {

            setContentView(R.layout.activity_category_logged);

            profile = (ImageButton)findViewById(R.id.go_to_user);

            profile.setImageResource(R.drawable.im1662337);

            profile.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    goToAccount();

                }

            });

        }else {

            setContentView(R.layout.activity_category);

            signOrRegister = (Button)findViewById(R.id.go_to_login);

            signOrRegister.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    goToLogin();

                }

            });
        }

    }

    private void setupGrid() {

        List<Category> categoryList = categoryContext.loadCategories(new CategoryCompletion.CategoriesErrorCompletion() {

            @Override
            public void completion(List<Category> categoryList, AppError error) {

                if (categoryList != null) {

                    categories.clear();

                    categories.addAll(categoryList);

                    adapter.notifyDataSetChanged();

                } else {

                    noInternetConnectionAlert();

                }

            }

        });

        categories.addAll(categoryList);

        adapter = new CategoryGridAdapter(categories);

        recyclerView.setAdapter(adapter);

    }

    private void noInternetConnectionAlert() {

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

        searchView.setOnQueryTextFocusChangeListener(new SearchView.OnFocusChangeListener() {


            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            goToSearch();
            }
        });

        return true;

    }

    private void goToSearch() {

        Intent intent = new Intent(CategoryActivity.this, SearchActivity.class);

        startActivity(intent);

    }

    private void goToLogin() {

        Intent intent = new Intent(CategoryActivity.this, LoginActivity.class);

        startActivity(intent);


    }

    private void goToAccount() {

        Intent intent = new Intent(CategoryActivity.this, AccountActivity.class);

        startActivity(intent);

    }

}