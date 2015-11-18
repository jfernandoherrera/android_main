package com.amtechventures.tucita.activities.search.advanced;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.search.adapters.AdvancedSearchAdapter;
import com.amtechventures.tucita.model.context.service.ServiceCompletion;
import com.amtechventures.tucita.model.context.service.ServiceContext;
import com.amtechventures.tucita.model.context.subcategory.SubCategoryContext;
import com.amtechventures.tucita.model.context.venue.VenueCompletion;
import com.amtechventures.tucita.model.context.venue.VenueContext;
import com.amtechventures.tucita.model.domain.category.CategoryAttributes;
import com.amtechventures.tucita.model.domain.service.Service;
import com.amtechventures.tucita.model.domain.subcategory.SubCategory;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.error.AppError;
import com.amtechventures.tucita.utils.blocks.Completion;

import java.util.ArrayList;
import java.util.List;

public class AdvancedSearchActivity extends AppCompatActivity {

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Venue> venues = new ArrayList<>();
    private VenueContext venueContext;
    private ServiceContext serviceContext;
    private SubCategoryContext subCategoryContext;
    private Toolbar toolbar;
    private String name;
    private SubCategory subCategory;
    private ArrayList priceStrings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_advanced_search);

        venueContext = VenueContext.context(venueContext);

        subCategoryContext = SubCategoryContext.context(subCategoryContext);

        serviceContext = ServiceContext.context(serviceContext);

        name = getIntent().getStringExtra(CategoryAttributes.name);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(getApplicationContext(), 1);

        recyclerView.setLayoutManager(layoutManager);

        setToolbar();

        setupGrid();
    }

    private void setToolbar(){

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {

            setSupportActionBar(toolbar);
        }
    }

    private void setupPriceFrom(){
        try {

            for (Venue venue : venues) {

                int price = serviceContext.getPricesFrom(subCategory, venue, new Completion.IntErrorCompletion() {

                    @Override
                    public void completion(int number, AppError error) {

                        if (number != 0) {
                            priceStrings.add(String.valueOf(number));

                            adapter = new AdvancedSearchAdapter(venues, priceStrings);

                            recyclerView.setAdapter(adapter);

                        }
                    }
                });
                if(price != 0) {

                    priceStrings.add(String.valueOf(price));
                }
            }
        }catch (Exception e){

        }
    }

    private List<Venue> setupVenues(List<Service> services){

        List<Venue> venuesList = venueContext.loadSubCategorizedVenues(services, new VenueCompletion.ErrorCompletion() {

            @Override
            public void completion(List<Venue> venueList, AppError error) {

                if (venueList != null) {

                    venues.clear();

                    venues.addAll(venueList);

                    setupPriceFrom();

                }
            }

        });
        venues.clear();

        venues.addAll(venuesList);

        return  venuesList;
    }

    private void setupGrid() {

                subCategory = subCategoryContext.findSubCategory(name);

                List services = serviceContext.loadSubCategorizedServices(subCategory, new ServiceCompletion.ErrorCompletion() {
                    @Override
                    public void completion(List<Service> servicesList, AppError error) {

                        if (servicesList != null) {

                            setupVenues(servicesList);

                        }
                    }
                });

        setupVenues(services);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(name);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        finish();

        return super.onOptionsItemSelected(item);
    }
}