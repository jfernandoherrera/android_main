package com.amtechventures.tucita.activities.search;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.model.context.service.ServiceContext;
import com.amtechventures.tucita.model.context.service.ServicesCompletion;
import com.amtechventures.tucita.model.domain.category.CategoryAttributes;
import com.amtechventures.tucita.model.domain.service.Service;
import com.amtechventures.tucita.model.error.AppError;
import com.amtechventures.tucita.utils.strings.Strings;
import com.parse.ParseObject;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    
    private SearchView searchView;
    private TextView textViewTreatments;
    private TextView textViewVenues;
    private View separator;
    private ListView listView;
    private ServiceContext serviceContext;
    private ArrayAdapter<String> adapter;
    private List <Service>services = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);

        serviceContext = ServiceContext.context(serviceContext);

        listView = (ListView) findViewById(R.id.listViewTreatments);

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

                if (newText.length() < 3) {

                    Toast typeMore = Toast.makeText(SearchActivity.this, R.string.typing_advertisement, Toast.LENGTH_SHORT);

                    typeMore.setGravity(Gravity.CENTER | Gravity.LEFT, 0, 0);

                    typeMore.show();

                } else {

                    setupList(newText);
                }
                return false;

            }

        });

        return true;

    }

    private ArrayList<String> setStringsArray(){

        ArrayList<String> stringsServices = new ArrayList<>();

        for(ParseObject service : services){

            stringsServices.add(service.getString(CategoryAttributes.name));
        }

        return stringsServices;
    }

    private void setupList(String newText){

        String capitalized = Strings.capitalize(newText);

        List<Service> servicesList = serviceContext.loadLikeServices(capitalized, new ServicesCompletion.ErrorCompletion() {
                    @Override
                    public void completion(List<Service> servicesList, AppError error) {

                          if (servicesList != null) {

                              textViewTreatments.setVisibility(View.VISIBLE);

                              textViewTreatments.setText(getResources().getString(R.string.services));

                            adapter.clear();

                            services.clear();

                            services.addAll(servicesList);

                            adapter.addAll(setStringsArray());

                            adapter.notifyDataSetChanged();
                        }

                    }
                });

        services.addAll(servicesList);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, setStringsArray());

        listView.setAdapter(adapter);

    }
}
