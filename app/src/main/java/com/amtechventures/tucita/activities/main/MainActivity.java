package com.amtechventures.tucita.activities.main;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.account.AccountActivity;
import com.amtechventures.tucita.activities.category.CategoryFragment;
import com.amtechventures.tucita.activities.login.LoginActivity;
import com.amtechventures.tucita.activities.search.SearchFragment;
import com.amtechventures.tucita.model.context.user.UserContext;

public class MainActivity extends AppCompatActivity {

    private SearchFragment searchFragment;
    private Toolbar toolbar;
    private UserContext userContext;
    private final int minimunToSearch = 3;
    private boolean isCategory;
    private CategoryFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        fragment = new CategoryFragment();

        searchFragment = new SearchFragment();

        userContext = UserContext.context(userContext);

        setCategoryFragment();

        setToolbar();

        setupVisibility();
    }

    private void setCategoryFragment(){

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.layout_main, fragment);

        transaction.commit();
    }

    private void replaceFragmentToSearch(){

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

            transaction.replace(R.id.layout_main, searchFragment);

        transaction.addToBackStack(null);

        transaction.commit();
    }

    private void setToolbar(){

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {

            setSupportActionBar(toolbar);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        toolbar.inflateMenu((R.menu.menu_main));

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextFocusChangeListener(new SearchView.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                replaceFragmentToSearch();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.length() < minimunToSearch) {

                    Toast typeMore = Toast.makeText(getApplicationContext(), R.string.typing_advertisement, Toast.LENGTH_SHORT);

                    typeMore.setGravity(Gravity.CENTER | Gravity.LEFT, 0, 0);

                    typeMore.show();

                } else {

                    searchFragment.setupSubCategoryList(newText);

                    searchFragment.setupVenueList(newText);
                }

                return true;

            }
        });
        return true;
    }

    private void setupVisibility() {

        if (userContext.currentUser() != null) {


        }else {

        }

    }

    public void goToLogin(View v) {

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);

        startActivity(intent);

    }

    public void goToAccount(View v) {

        Intent intent = new Intent(MainActivity.this, AccountActivity.class);

        startActivity(intent);
    }
}