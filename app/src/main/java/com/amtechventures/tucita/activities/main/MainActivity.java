package com.amtechventures.tucita.activities.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.account.AccountActivity;
import com.amtechventures.tucita.activities.main.fragments.category.CategoryFragment;
import com.amtechventures.tucita.activities.login.LoginActivity;
import com.amtechventures.tucita.activities.main.fragments.search.SearchFragment;
import com.amtechventures.tucita.activities.main.fragments.subcategory.SubCategoryFragment;
import com.amtechventures.tucita.activities.signup.SignUpActivity;
import com.amtechventures.tucita.model.domain.user.UserAttributes;
import com.amtechventures.tucita.utils.common.AppFont;
import com.amtechventures.tucita.utils.views.AlertDialogError;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity implements CategoryFragment.OnItemClicked, SubCategoryFragment.OnOthersClicked {

    private SearchFragment searchFragment;
    private Toolbar toolbar;
    private final int minimumToSearch = 3;
    private CategoryFragment fragment;
    private Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        fragment = new CategoryFragment();

        searchFragment = new SearchFragment();

        AppFont appFont = new AppFont();

        typeface = appFont.getLightAppFont(getApplicationContext());

        fragment.setTypeface(typeface);

        searchFragment.setTypeface(typeface);

        setCategoryFragment();

        setFragmentToSearch();

        categoryShow();

        searchHide();

        setToolbar();

    }

    private void setCategoryFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.layout_main, fragment);

        transaction.commit();

    }

    private void showSubCategoryFragment(String name) {

        FragmentManager fragmentManager = getSupportFragmentManager();

        SubCategoryFragment prev = (SubCategoryFragment) fragmentManager.findFragmentByTag(SubCategoryFragment.class.getName());

        if (prev == null) {

            prev = SubCategoryFragment.newInstance(name);

            prev.setTypeface(typeface);
        }

        prev.show(fragmentManager, SubCategoryFragment.class.getName());

    }

    private void setFragmentToSearch() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.layout_main, searchFragment);

        transaction.commit();

    }

    private void categoryHide() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.hide(fragment);

        transaction.commit();

    }

    private void categoryShow() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.show(fragment);

        transaction.commit();

    }

    private void searchHide() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.hide(searchFragment);

        transaction.commit();

    }

    private void searchShow() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.show(searchFragment);

        transaction.commit();

    }

    private void setToolbar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {

            setSupportActionBar(toolbar);

        }

    }

    private TextView getActionBarTextView() {

        TextView titleTextView = null;

        String defaultNameTitleMenu = "mTitleTextView";

        try {

            Field field = toolbar.getClass().getDeclaredField(defaultNameTitleMenu);

            field.setAccessible(true);

            titleTextView = (TextView) field.get(toolbar);

        } catch (NoSuchFieldException e) {

        } catch (IllegalAccessException e) {

        }

        return titleTextView;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        toolbar.inflateMenu((R.menu.menu_main));

        TextView toolbarTextView = getActionBarTextView();

        toolbarTextView.setTypeface(typeface);

        toolbarTextView.setText(getString(R.string.categories));

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextFocusChangeListener(new SearchView.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {

                    searchHide();

                    categoryShow();

                }

            }

        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.equals("")) {

                    searchFragment.setupRecentVenueList();

                    searchFragment.setupRecentSubCategoryList();

                } else if (newText.length() < minimumToSearch) {

                    AlertDialogError alertDialogError = new AlertDialogError();

                    alertDialogError.noTypedEnough(getApplicationContext());

                } else {

                    searchFragment.setupSubCategoryList(newText);

                    searchFragment.setupVenueList(newText);

                }

                return true;

            }

        });

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        goToSearch();

        return super.onOptionsItemSelected(item);

    }

    public void goToSearch() {

        categoryHide();

        searchShow();

        searchFragment.setupRecentVenueList();

        searchFragment.setupRecentSubCategoryList();

    }

    public void goToLogin(View v) {

        LoginActivity.goToLogin(this);

        finish();

    }

    public void goToAccount(View v) {

        AccountActivity.goToAccount(this);

        finish();

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

        if (!fragment.isHidden()) {

            finish();

        }

    }

    @Override
    public void onItemClicked(String name) {

        showSubCategoryFragment(name);

    }

    @Override
    public void onOthersClicked() {

        goToSearch();

    }

    public static void goToCategoriesFromLogout(Context context){

        Intent intent = new Intent(context, MainActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.putExtra(UserAttributes.connected, false);

        context.startActivity(intent);

    }

    public static void goToCategories(Context context) {

        Intent intent = new Intent(context, MainActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.putExtra(UserAttributes.connected, true);

        context.startActivity(intent);

    }

    public void signUp(View view) {

        Intent intent = new Intent(this, SignUpActivity.class);

        startActivity(intent);

        finish();

    }

}