package com.amtechventures.tucita.activities.account;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.category.CategoryActivity;
import com.amtechventures.tucita.model.context.user.UserContext;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_account);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_logout, menu);

        MenuItem logoutItem = menu.findItem(R.id.action_logout);

        logoutItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                UserContext.context().logOut();

                goToCategories();

                return true;
            }
        });

        return true;

    }

    public void goToCategories(){

        Intent intent = new Intent(this, CategoryActivity.class);

        startActivity(intent);

        finish();
    }
}
