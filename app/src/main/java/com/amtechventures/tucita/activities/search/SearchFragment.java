package com.amtechventures.tucita.activities.search;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.book.BookActivity;
import com.amtechventures.tucita.activities.search.advanced.AdvancedSearchActivity;
import com.amtechventures.tucita.activities.venue.VenueFragment;
import com.amtechventures.tucita.model.context.subcategory.SubCategoryContext;
import com.amtechventures.tucita.model.context.subcategory.SubCategoryCompletion;
import com.amtechventures.tucita.model.context.venue.VenueCompletion;
import com.amtechventures.tucita.model.context.venue.VenueContext;
import com.amtechventures.tucita.model.domain.category.CategoryAttributes;
import com.amtechventures.tucita.model.domain.subcategory.SubCategory;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.domain.venue.VenueAttributes;
import com.amtechventures.tucita.model.error.AppError;
import com.amtechventures.tucita.utils.strings.Strings;
import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

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

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        subCategoryContext = SubCategoryContext.context(subCategoryContext);

        venueContext = VenueContext.context(venueContext);

        listViewTreatments = (ListView) rootView.findViewById(R.id.listViewTreatments);

        listViewVenues = (ListView) rootView.findViewById(R.id.listViewVenues);

        textViewTreatments = (TextView) rootView.findViewById(R.id.textViewTreatments);

        textViewVenues = (TextView) rootView.findViewById(R.id.textViewVenues);

        separator = rootView.findViewById(R.id.separator5);

        textViewTreatments.setVisibility(View.INVISIBLE);

        textViewVenues.setVisibility(View.INVISIBLE);

        separator.setVisibility(View.INVISIBLE);

        subCategoriesAdapter = new ArrayAdapter<>(getContext(), R.layout.list_item);

        venuesAdapter = new ArrayAdapter<>(getContext(), R.layout.list_item);

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

        return rootView;
    }

    private void advancedSearch(int position){

        String name = subCategories.get(position).getName();

        Class activity = AdvancedSearchActivity.class;

        Intent intent = new Intent(getContext(), activity);

        intent.putExtra(CategoryAttributes.name, name);

        subCategoryContext.cancelQuery();

        venueContext.cancelQuery();

        startActivity(intent);
    }

    private void venue(int position){

        String name = venues.get(position).getName();

        Class activity = BookActivity.class;

        String address = venues.get(position).getAddress();

        Intent intent = new Intent(getContext(), activity);

        intent.putExtra(Venue.class.getName(), name);

        intent.putExtra(VenueAttributes.address, address);

        subCategoryContext.cancelQuery();

        venueContext.cancelQuery();

        startActivity(intent);
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

    public void setupSubCategoryList(String newText){

        textViewTreatments.setVisibility(View.INVISIBLE);

        List<SubCategory> subCategoriesList = subCategoryContext.loadLikeSubCategories(newText, new SubCategoryCompletion.ErrorCompletion() {
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

            subCategories.clear();

            subCategories.addAll(subCategoriesList);

            subCategoriesAdapter.addAll(setSubCategoriesToStringsArray());

            subCategoriesAdapter.notifyDataSetChanged();

        }
    }

    public void setupVenueList(String newText){

        textViewVenues.setVisibility(View.INVISIBLE);


        final List<Venue> venuesList = venueContext.loadLikeVenues(newText, new VenueCompletion.ErrorCompletion() {
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

                venues.clear();

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
}
