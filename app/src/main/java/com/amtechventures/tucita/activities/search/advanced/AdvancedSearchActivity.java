package com.amtechventures.tucita.activities.search.advanced;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_advanced_search);

        venueContext = VenueContext.context(venueContext);

        subCategoryContext = SubCategoryContext.context(subCategoryContext);

        serviceContext = ServiceContext.context(serviceContext);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(getApplicationContext(), 1);

        recyclerView.setLayoutManager(layoutManager);

        setupGrid();
    }

    private List<Venue> setupVenues(List<Service> services){

        List<Venue> venuesList = venueContext.loadSubCategorizedVenues(services, new VenueCompletion.ErrorCompletion() {

            @Override
            public void completion(List<Venue> venueList, AppError error) {

                if (venueList != null) {

                    venues.clear();

                    venues.addAll(venueList);

                    adapter.notifyDataSetChanged();
                }
            }

        });
        venues.clear();

        venues.addAll(venuesList);

        adapter = new AdvancedSearchAdapter(venues);

        recyclerView.setAdapter(adapter);

        return  venuesList;
    }

    private void setupGrid() {

                String name = getIntent().getStringExtra(CategoryAttributes.name);

                SubCategory subCategory = subCategoryContext.findSubCategory(name);

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
}