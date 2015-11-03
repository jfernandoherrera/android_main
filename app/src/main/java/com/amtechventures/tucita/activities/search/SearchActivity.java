package com.amtechventures.tucita.activities.search;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.amtechventures.tucita.R;

public class SearchActivity extends AppCompatActivity {
    
    private SearchView searchView;
    private TextView textViewTreatments;
    private TextView textViewVenues;
    private View separator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);

        textViewTreatments = (TextView) findViewById(R.id.textViewTreatments);

        textViewVenues = (TextView) findViewById(R.id.textViewVenues);

        separator = findViewById(R.id.separator5);

        textViewTreatments.setVisibility(View.INVISIBLE);

        textViewVenues.setVisibility(View.INVISIBLE);

        separator.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {


                if(newText.length()<3){
                    Toast typeMore = Toast.makeText(SearchActivity.this, R.string.typing_advertisement, Toast.LENGTH_SHORT);

                    typeMore.setGravity(Gravity.CENTER | Gravity.LEFT, 0, 0);

                    typeMore.show();

                }else{

                }
                return false;

            }

        });

        return true;

    }

}
