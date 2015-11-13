package com.amtechventures.tucita.activities.search;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.category.CategoryActivity;
import com.amtechventures.tucita.activities.search.advanced.AdvancedSearchActivity;
import com.amtechventures.tucita.activities.venue.VenueActivity;
import com.amtechventures.tucita.model.context.subcategory.SubCategoryContext;
import com.amtechventures.tucita.model.context.subcategory.SubCategoryCompletion;
import com.amtechventures.tucita.model.context.venue.VenueCompletion;
import com.amtechventures.tucita.model.context.venue.VenueContext;
import com.amtechventures.tucita.model.domain.category.Category;
import com.amtechventures.tucita.model.domain.category.CategoryAttributes;
import com.amtechventures.tucita.model.domain.subcategory.SubCategory;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.domain.venue.VenueAttributes;
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
    private SubCategoryContext subCategoryContext;
    private VenueContext venueContext;
    private ArrayAdapter<String> subCategoriesAdapter;
    private ArrayAdapter<String> venuesAdapter;
    private List <SubCategory> subCategories = new ArrayList<>();
    private List <Venue> venues = new ArrayList<>();
    private final int minimunToSearch = 3;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);

        setToolbar();

        subCategoryContext = SubCategoryContext.context(subCategoryContext);

        venueContext = VenueContext.context(venueContext);

        listViewTreatments = (ListView) findViewById(R.id.listViewTreatments);

        listViewVenues = (ListView) findViewById(R.id.listViewVenues);

        textViewTreatments = (TextView) findViewById(R.id.textViewTreatments);

        textViewVenues = (TextView) findViewById(R.id.textViewVenues);

        separator = findViewById(R.id.separator5);

        textViewTreatments.setVisibility(View.INVISIBLE);

        textViewVenues.setVisibility(View.INVISIBLE);

        separator.setVisibility(View.INVISIBLE);

        subCategoriesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        venuesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        listViewTreatments.setAdapter(subCategoriesAdapter);

        listViewTreatments.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                advancedSearch(position);
            }
        });

        listViewVenues.setAdapter(venuesAdapter);

        listViewVenues.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

            venue(position);
            }

        });
    }

    private void setToolbar(){

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {

            setSupportActionBar(toolbar);
        }
    }

    private void advancedSearch(int position){

        String name = subCategories.get(position).getName();

        Class activity = AdvancedSearchActivity.class;

        Intent intent = new Intent(SearchActivity.this, activity);

        intent.putExtra(CategoryAttributes.name, name);

        startActivity(intent);

        finish();

    }
    private void venue(int position){

        String name = venues.get(position).getName();

        Class activity = VenueActivity.class;

        String address = venues.get(position).getAddress();

        Intent intent = new Intent(SearchActivity.this, activity);

        intent.putExtra(Venue.class.getName(), name);

        intent.putExtra(VenueAttributes.address, address);

        startActivity(intent);

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        toolbar.inflateMenu(R.menu.menu_main);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchItem.expandActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.length() < minimunToSearch) {

                    Toast typeMore = Toast.makeText(SearchActivity.this, R.string.typing_advertisement, Toast.LENGTH_SHORT);

                    typeMore.setGravity(Gravity.CENTER | Gravity.LEFT, 0, 0);

                    typeMore.show();

                } else {

                    setupSubCategoryList(newText);

                    setupVenueList(newText);
                }
                return false;

            }

        });

        return true;

    }

    private ArrayList<String> setSubCategoriesToStringsArray(){

        ArrayList<String> stringsSubCategories = new ArrayList<>();

        for(SubCategory subCategory : subCategories){

            stringsSubCategories.add(subCategory.getName());
        }

        return stringsSubCategories;
    }

    private ArrayList<String> setVenuesToStringsArray(){

        ArrayList<String> stringsVenues = new ArrayList<>();

        for(Venue venue : venues){

            stringsVenues.add(venue.getName());
        }

        return stringsVenues;
    }

    private void setupSubCategoryList(String newText){

        textViewTreatments.setVisibility(View.INVISIBLE);

        String capitalized = Strings.capitalize(newText);

        List<SubCategory> subCategoriesList = subCategoryContext.loadLikeSubCategories(capitalized, new SubCategoryCompletion.ErrorCompletion() {
            @Override
            public void completion(List<SubCategory> subCategoriesList, AppError error) {

                subCategoriesAdapter.clear();

                subCategories.clear();

                if (subCategoriesList != null && !subCategoriesList.isEmpty()) {

                    textSubCategories();

                    subCategories.addAll(subCategoriesList);

                    subCategoriesAdapter.addAll(setSubCategoriesToStringsArray());

                    subCategoriesAdapter.notifyDataSetChanged();
                }

            }
        });

        if (subCategoriesList != null && !subCategoriesList.isEmpty()) {

            textSubCategories();

            subCategories.addAll(subCategoriesList);

            subCategoriesAdapter.addAll(setSubCategoriesToStringsArray());

            subCategoriesAdapter.notifyDataSetChanged();

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

    private void textSubCategories(){

        textViewTreatments.setVisibility(View.VISIBLE);

        textViewTreatments.setText(getResources().getString(R.string.services));
    }

    private void textVenues(){

        textViewVenues.setVisibility(View.VISIBLE);

        textViewVenues.setText(getResources().getString(R.string.venues));
    }

    public static void launch(CategoryActivity activity, View transitionView, String shared) {

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(

                        activity, transitionView, shared);

        Intent intent = new Intent(activity, SearchActivity.class);

        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }
}
