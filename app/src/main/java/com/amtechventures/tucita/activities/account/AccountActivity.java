package com.amtechventures.tucita.activities.account;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.amtechventures.tucita.R;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_logout, menu);

        MenuItem searchItem = menu.findItem(R.id.action_logout);


    ;

        return true;

    }
}
