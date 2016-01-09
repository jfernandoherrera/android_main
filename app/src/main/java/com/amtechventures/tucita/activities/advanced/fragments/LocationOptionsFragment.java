package com.amtechventures.tucita.activities.advanced.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.model.context.city.CityCompletion;
import com.amtechventures.tucita.model.context.city.CityContext;
import com.amtechventures.tucita.model.domain.city.City;
import com.amtechventures.tucita.model.error.AppError;
import com.amtechventures.tucita.utils.views.AlertDialogError;

import java.util.ArrayList;
import java.util.List;

public class LocationOptionsFragment extends Fragment {

    private CityContext cityContext;
    private TextView textViewCities;
    private SearchView searchView;
    private ListView listViewCities;
    private ArrayAdapter<String> citiesAdapter;
    private ArrayList<City> currentCities;
    private OnCitySelected listener;
    private final int minimumToSearch = 3;

    public interface OnCitySelected{

        void onCitySelected(City city);

    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

        try {

            listener = (OnCitySelected) context;

        } catch (ClassCastException e) {}

    }

    @Override
    public void onDetach() {

        super.onDetach();

        listener = null;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_location_options, container, false);

        textViewCities = (TextView) rootView.findViewById(R.id.textViewCities);

        searchView = (SearchView) rootView.findViewById(R.id.searchCities);

        listViewCities = (ListView) rootView.findViewById(R.id.listViewCities);

        cityContext = CityContext.context(cityContext);

        currentCities = new ArrayList<>();

        textViewCities.setVisibility(View.GONE);

        citiesAdapter = new ArrayAdapter<>(getContext(), R.layout.list_item);

        listViewCities.setAdapter(citiesAdapter);

        listViewCities.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                listener.onCitySelected(currentCities.get(position));

            }

        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.length() < minimumToSearch) {

                    AlertDialogError alertDialogError = new AlertDialogError();

                    alertDialogError.noTypedEnough(getContext());

                } else {

                    setupCities(newText);

                }

                return false;

            }

        });

        return rootView;

    }

    private ArrayList<String> setCitiesToStringsArray(){

        ArrayList<String> stringsCities = new ArrayList<>();

        for(City city: currentCities){

            stringsCities.add(city.formatedLocation());

        }

        return stringsCities;

    }

    public void setupCities(String like){

        cityContext.loadLikeCities(like, new CityCompletion.ErrorCompletion() {

            @Override
            public void completion(List<City> cities, AppError error) {

                currentCities.clear();

                citiesAdapter.clear();

                if(cities != null && ! cities.isEmpty()){

                    currentCities.addAll(cities);

                    citiesAdapter.addAll(setCitiesToStringsArray());

                    textViewCities.setVisibility(View.VISIBLE);

                    citiesAdapter.notifyDataSetChanged();

                } else{

                    textViewCities.setVisibility(View.GONE);

                }

            }

        });

    }

}
