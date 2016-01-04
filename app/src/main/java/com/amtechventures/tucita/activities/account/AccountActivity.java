package com.amtechventures.tucita.activities.account;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import com.amtechventures.tucita.R;
import android.support.v7.app.AppCompatActivity;
import com.amtechventures.tucita.activities.main.MainActivity;
import com.amtechventures.tucita.model.context.user.UserContext;
import com.amtechventures.tucita.model.domain.user.UserAttributes;

public class AccountActivity extends AppCompatActivity {

    private UserContext userContext;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        userContext = UserContext.context(userContext);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_account);

        setToolbar();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.addTab(tabLayout.newTab().setText(R.string.bookings));

        tabLayout.addTab(tabLayout.newTab().setText(R.string.venues));

    }

    private void setToolbar(){

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {

            setSupportActionBar(toolbar);
        }
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
