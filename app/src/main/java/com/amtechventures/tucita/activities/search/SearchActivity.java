package com.amtechventures.tucita.activities.search;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
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
import com.amtechventures.tucita.model.context.venue.VenueCompletion;
import com.amtechventures.tucita.model.context.venue.VenueContext;
import com.amtechventures.tucita.model.domain.category.CategoryAttributes;
import com.amtechventures.tucita.model.domain.service.Service;
import com.amtechventures.tucita.model.domain.venue.Venue;
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
    private ListView listViewTreatments;
    private ListView listViewVenues;
    private ServiceContext serviceContext;
    private VenueContext venueContext;
    private ArrayAdapter<String> servicesAdapter;
    private ArrayAdapter<String> venuesAdapter;
    private List <Service> services = new ArrayList<>();
    private List <Venue> venues = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);

        serviceContext = ServiceContext.context(serviceContext);

        venueContext = VenueContext.context(venueContext);

        listViewTreatments = (ListView) findViewById(R.id.listViewTreatments);

        listViewVenues = (ListView) findViewById(R.id.listViewVenues);

        textViewTreatments = (TextView) findViewById(R.id.textViewTreatments);

        textViewVenues = (TextView) findViewById(R.id.textViewVenues);

        separator = findViewById(R.id.separator5);

        textViewTreatments.setVisibility(View.INVISIBLE);

        textViewVenues.setVisibility(View.INVISIBLE);

        separator.setVisibility(View.INVISIBLE);

        servicesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        venuesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        listViewTreatments.setAdapter(servicesAdapter);

        listViewVenues.setAdapter(venuesAdapter);
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

                    setupServiceList(newText);

                    setupVenueList(newText);
                }
                return false;

            }

        });

        return true;

    }

    private ArrayList<String> setServicesToStringsArray(){

        ArrayList<String> stringsServices = new ArrayList<>();

        for(ParseObject service : services){

            stringsServices.add(service.getString(CategoryAttributes.name));
        }

        return stringsServices;
    }

    private ArrayList<String> setVenuesToStringsArray(){

        ArrayList<String> stringsVenues = new ArrayList<>();

        for(ParseObject venue : venues){

            stringsVenues.add(venue.getString(CategoryAttributes.name));
        }

        return stringsVenues;
    }

    private void setupServiceList(String newText){

        textViewTreatments.setVisibility(View.INVISIBLE);

        String capitalized = Strings.capitalize(newText);

        List<Service> servicesList = serviceContext.loadLikeServices(capitalized, new ServicesCompletion.ErrorCompletion() {
            @Override
            public void completion(List<Service> servicesList, AppError error) {

                servicesAdapter.clear();

                services.clear();

                if (servicesList != null && !servicesList.isEmpty()) {

                    textServices();

                    services.addAll(servicesList);

                    servicesAdapter.addAll(setServicesToStringsArray());

                    servicesAdapter.notifyDataSetChanged();
                }

            }
        });

        if (servicesList != null && !servicesList.isEmpty()) {

            textServices();

            services.addAll(servicesList);

            servicesAdapter.addAll(setServicesToStringsArray());

            servicesAdapter.notifyDataSetChanged();

        }
    }

    private void setupVenueList(String newText){

        textViewVenues.setVisibility(View.INVISIBLE);

        String capitalized = Strings.capitalize(newText);

        final List<Venue> venuesList = venueContext.loadLikeVenues(capitalized, new VenueCompletion.ErrorCompletion() {
                    @Override
                    public void completion(List<Venue> venuesList, AppError error) {

                        venuesAdapter.clear();

                        venues.clear();

                        if (venuesList != null && !venuesList.isEmpty()) {

                            textVenues();

                            venues.addAll(venuesList);

                            venuesAdapter.addAll(setVenuesToStringsArray());

                            venuesAdapter.notifyDataSetChanged();


                        }
                    }

                }
            );

            if (venuesList != null && !venuesList.isEmpty()) {

                textVenues();

                venues.addAll(venuesList);

                venuesAdapter.addAll(setVenuesToStringsArray());

                venuesAdapter.notifyDataSetChanged();

            }

    }

    private void textServices(){

        textViewTreatments.setVisibility(View.VISIBLE);

        textViewTreatments.setText(getResources().getString(R.string.services));
    }

    private void textVenues(){

        textViewVenues.setVisibility(View.VISIBLE);

        textViewVenues.setText(getResources().getString(R.string.venues));
    }
}
