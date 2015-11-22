package com.amtechventures.tucita.activities.account;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import com.amtechventures.tucita.R;
import android.support.v7.app.AppCompatActivity;

import com.amtechventures.tucita.activities.main.MainActivity;
import com.amtechventures.tucita.model.context.user.UserContext;
import com.amtechventures.tucita.activities.category.CategoryFragment;
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
    }


    private void setToolbar(){

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {

            setSupportActionBar(toolbar);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_logout, menu);

        MenuItem logoutItem = menu.findItem(R.id.action_logout);

        logoutItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                userContext.logout();

                goToCategories();

                return true;

            }

        });

        return true;

    }

    public void goToCategories(){

        Intent intent = new Intent(this, MainActivity.class);
        
        intent.putExtra(UserAttributes.connected, false);

        startActivity(intent);

        finish();
    }
}
