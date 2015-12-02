package com.amtechventures.tucita.activities.search;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.book.BookActivity;
import com.amtechventures.tucita.activities.search.adapters.SearchAdapter;
import com.amtechventures.tucita.activities.search.advanced.AdvancedSearchActivity;
import com.amtechventures.tucita.model.context.subcategory.SubCategoryContext;
import com.amtechventures.tucita.model.context.subcategory.SubCategoryCompletion;
import com.amtechventures.tucita.model.context.venue.VenueCompletion;
import com.amtechventures.tucita.model.context.venue.VenueContext;
import com.amtechventures.tucita.model.domain.category.CategoryAttributes;
import com.amtechventures.tucita.model.domain.subcategory.SubCategory;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.domain.venue.VenueAttributes;
import com.amtechventures.tucita.model.error.AppError;
import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private SubCategoryContext subCategoryContext;
    private VenueContext venueContext;
    private SearchAdapter searchAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<SubCategory> subCategories = new ArrayList<>();
    private List <Venue> venues = new ArrayList<>();
    private List<SubCategory> recentSubCategories = new ArrayList<>();
    private List <Venue> recentVenues = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        subCategoryContext = SubCategoryContext.context(subCategoryContext);

        venueContext = VenueContext.context(venueContext);

        searchAdapter = new SearchAdapter(venues,subCategories, getContext());

        searchAdapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

        boolean isSubcategoryHeader = position == 0 && ! subCategories.isEmpty() ? true : false;

        boolean isVenueHeader = position == subCategories.size() + 1 && ! venues.isEmpty() ? true : false;

                if(! isSubcategoryHeader && ! isVenueHeader && position <= subCategories.size()) {

                    advancedSearch(position);
                }else {

                    position = position - subCategories.size();

                    venue(position);
                }

            }
        });
        layoutManager = new GridLayoutManager(getContext(), 1);

        recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(searchAdapter);

        return rootView;
    }

    private void advancedSearch(int position){

        String name = subCategories.get(position - 1).getName();

        Class activity = AdvancedSearchActivity.class;

        Intent intent = new Intent(getContext(), activity);

        intent.putExtra(CategoryAttributes.name, name);

        subCategoryContext.cancelQuery();

        venueContext.cancelQuery();

        startActivity(intent);
    }

    private void venue(int position){

        String name = venues.get(position - SearchAdapter.typeSection).getName();

        Class activity = BookActivity.class;

        String address = venues.get(position - SearchAdapter.typeSection).getAddress();

        Intent intent = new Intent(getContext(), activity);

        intent.putExtra(Venue.class.getName(), name);

        intent.putExtra(VenueAttributes.address, address);

        subCategoryContext.cancelQuery();

        venueContext.cancelQuery();

        startActivity(intent);
    }


    public void setupSubCategoryList(String newText){

        List<SubCategory> subCategoriesList = subCategoryContext.loadLikeSubCategories(newText, new SubCategoryCompletion.ErrorCompletion() {
            @Override
            public void completion(List<SubCategory> subCategoriesList, AppError error) {

                if (subCategoriesList != null && !subCategoriesList.isEmpty()) {

                    subCategories.clear();

                    subCategories.addAll(subCategoriesList);

                    searchAdapter.notifyDataSetChanged();
                }

            }
        });

        if (subCategoriesList != null && !subCategoriesList.isEmpty()) {

            subCategories.clear();

            subCategories.addAll(subCategoriesList);

            searchAdapter.notifyDataSetChanged();
        }
    }

    public void setupRecentVenueList(){

        if(recentVenues.isEmpty()) {

            final List<Venue> venuesList = venueContext.loadRecentVenues();

            if (venuesList != null && !venuesList.isEmpty()) {

                recentVenues.addAll(venuesList);

                venues.clear();

                venues.addAll(recentVenues);

                searchAdapter.notifyDataSetChanged();
            }
        }else{
            venues.clear();

            venues.addAll(recentVenues);

            searchAdapter.notifyDataSetChanged();
        }

    }

    public void setupRecentSubCategoryList(){

        if(recentSubCategories.isEmpty()) {

            List<SubCategory> subCategoriesList = subCategoryContext.loadRecentSubcategories();

            if (subCategoriesList != null && !subCategoriesList.isEmpty()) {

                recentSubCategories.addAll(subCategoriesList);

                subCategories.clear();

                subCategories.addAll(recentSubCategories);

                searchAdapter.notifyDataSetChanged();
            }
        }else{
            subCategories.clear();

            subCategories.addAll(recentSubCategories);

            searchAdapter.notifyDataSetChanged();
        }
    }

    public void setupVenueList(String newText){

        final List<Venue> venuesList = venueContext.loadLikeVenues(newText, new VenueCompletion.ErrorCompletion() {
                    @Override
                    public void completion(List<Venue> venuesList, AppError error) {

                        if (venuesList != null && !venuesList.isEmpty()) {

                            venues.clear();

                            venues.addAll(venuesList);

                            searchAdapter.notifyDataSetChanged();
                        }
                    }
                }
            );

            if (venuesList != null && !venuesList.isEmpty()) {

                venues.clear();

                venues.addAll(venuesList);

                searchAdapter.notifyDataSetChanged();
            }

    }

}
