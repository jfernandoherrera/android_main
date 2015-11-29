package com.amtechventures.tucita.activities.main;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.TextAppearanceSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.account.AccountActivity;
import com.amtechventures.tucita.activities.category.CategoryFragment;
import com.amtechventures.tucita.activities.login.LoginActivity;
import com.amtechventures.tucita.activities.search.SearchFragment;
import com.amtechventures.tucita.model.context.user.UserContext;
import com.amtechventures.tucita.model.domain.user.UserAttributes;
import com.amtechventures.tucita.utils.views.AlertDialogError;

public class MainActivity extends AppCompatActivity {

    private SearchFragment searchFragment;
    private Toolbar toolbar;
    private UserContext userContext;
    private final int minimumToSearch = 3;
    private CategoryFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        fragment = new CategoryFragment();

        searchFragment = new SearchFragment();

        userContext = UserContext.context(userContext);

        setCategoryFragment();

        setFragmentToSearch();

        searchFragment.setUserVisibleHint(false);

        fragment.setUserVisibleHint(true);

        setToolbar();

        setupLogged();
    }

    private void setupLogged(){

        boolean connected = getIntent().getExtras().getBoolean(UserAttributes.connected);


        final Button buttonText = (Button) findViewById(R.id.go_to_login);

        if (connected){

            buttonText.setVisibility(View.GONE);

        }else {

            final ImageButton button = (ImageButton) findViewById(R.id.account);

            button.setVisibility(View.GONE);

            buttonText.setText(getStringLoginModified());

            buttonText.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {

                       buttonText.setBackgroundResource(R.drawable.log_in_or_signup_click_in);

                    } else if (event.getAction() == MotionEvent.ACTION_UP) {

                        buttonText.setBackgroundResource(R.drawable.log_in_or_signup_click_out);

                        buttonText.callOnClick();
                    }
                return true;
                }
            });

        }
    }

    private SpannableStringBuilder getStringLoginModified(){

        String firstString = getResources().getString(R.string.action_sign_in_short).toUpperCase();

        String secondString = getResources().getString(R.string.or).toLowerCase();

        String thirdString = getResources().getString(R.string.action_sign_up).toUpperCase();

        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(firstString +" "+ secondString +" "+ thirdString);

        stringBuilder.setSpan(new TextAppearanceSpan(Typeface.SANS_SERIF.toString(), Typeface.NORMAL, 45, null, null), 0, firstString.length(),
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        stringBuilder.setSpan(new TextAppearanceSpan( Typeface.SANS_SERIF.toString(), Typeface.NORMAL, 45, ColorStateList.valueOf(Color.rgb(238,238,238)), null), firstString.length() + 1,

                firstString.length() + secondString.length()+1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        stringBuilder.setSpan(new TextAppearanceSpan(Typeface.SANS_SERIF.toString(), Typeface.NORMAL, 45, null, null), firstString.length() + secondString.length() + 2,

                firstString.length() + secondString.length() + thirdString.length() + 2,

                Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        return stringBuilder;
    }

    private void setCategoryFragment(){

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.layout_main, fragment);

        transaction.commit();
    }

    private void setFragmentToSearch(){

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.layout_main, searchFragment);

        transaction.commit();
    }

    private void categoryHide(){

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.hide(fragment);

        transaction.commit();
    }

    private void categoryShow(){

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.show(fragment);

        transaction.commit();
    }

    private void searchHide(){

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.hide(searchFragment);

        transaction.commit();
    }

    private void searchShow(){

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.show(searchFragment);

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

                if (newText.length() < minimumToSearch) {

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

      searchShow();

      categoryHide();

        return super.onOptionsItemSelected(item);
    }

    public void goToLogin(View v) {

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);

        startActivity(intent);

    }

    public void goToAccount(View v) {

        Intent intent = new Intent(MainActivity.this, AccountActivity.class);

        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

        if (! fragment.isHidden()){

        finish();
        }

    }
}