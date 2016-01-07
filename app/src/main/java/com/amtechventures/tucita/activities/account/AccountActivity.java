package com.amtechventures.tucita.activities.account;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import com.amtechventures.tucita.R;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.amtechventures.tucita.activities.account.adapters.PagerAccountAdapter;
import com.amtechventures.tucita.activities.main.MainActivity;
import com.amtechventures.tucita.model.context.user.UserContext;
import com.amtechventures.tucita.model.domain.user.User;
import com.amtechventures.tucita.model.domain.user.UserAttributes;
import com.mikhaellopez.circularimageview.CircularImageView;

public class AccountActivity extends AppCompatActivity {

    private UserContext userContext;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private CircularImageView circularImageView;
    private TextView textName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        userContext = UserContext.context(userContext);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_account);

        setToolbar();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        circularImageView = (CircularImageView) findViewById(R.id.imageUser);

        textName = (TextView) findViewById(R.id.textName);

        TabLayout.Tab tab = tabLayout.newTab();

        tab.setCustomView(R.layout.item_tab);

        TextView tabText = (TextView) tab.getCustomView();

        tabText.setText(R.string.bookings);

        TabLayout.Tab tab1 = tabLayout.newTab();

        tab1.setCustomView(R.layout.item_tab);

        TextView tabText1 = (TextView) tab1.getCustomView();

        tabText1.setText(R.string.venues);

        tabLayout.addTab(tab);

        tabLayout.addTab(tab1);

        viewPager = (ViewPager) findViewById(R.id.container);

        PagerAccountAdapter pagerAccountAdapter = new PagerAccountAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(pagerAccountAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    });

        setImageUser();
    }

    private void setToolbar(){

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {

            setSupportActionBar(toolbar);
        }
    }

    private void setNameUser(User user){

        textName.setText(user.getName());
    }

    private void setImageUser(){

        User user = userContext.currentUser();

        if(userContext.IsFacebook(user.getParseUser())) {

            circularImageView.setImageBitmap(userContext.getPicture());

        }else{

            circularImageView.setVisibility(View.GONE);
        }

        setNameUser(user);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getMenuInflater().inflate(R.menu.menu_logout, menu);

        MenuItem logoutItem = menu.findItem(R.id.action_logout);

        logoutItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                userContext.logout();

                goToCategoriesFromLogout();

                return true;

            }

        });

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        goToCategories();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        goToCategories();

        super.onBackPressed();
    }

    public static void goToAccount(Context context) {

        Intent intent = new Intent(context, AccountActivity.class);

        context.startActivity(intent);

    }

    public void goToCategoriesFromLogout(){

        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra(UserAttributes.connected, false);

        startActivity(intent);

        finish();
    }

    public void goToCategories(){

        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra(UserAttributes.connected, true);

        startActivity(intent);

        finish();
    }
}
