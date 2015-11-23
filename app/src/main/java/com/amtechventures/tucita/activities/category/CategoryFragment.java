package com.amtechventures.tucita.activities.category;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import java.util.ArrayList;

import android.view.ViewGroup;

import com.amtechventures.tucita.R;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.GridLayoutManager;
import android.widget.ProgressBar;

import com.amtechventures.tucita.model.error.AppError;
import com.amtechventures.tucita.model.domain.category.Category;
import com.amtechventures.tucita.model.context.category.CategoryContext;
import com.amtechventures.tucita.model.context.category.CategoryCompletion;
import com.amtechventures.tucita.activities.category.adapters.CategoryGridAdapter;

public class CategoryFragment extends Fragment {

    private int COLUMNS_IN_CATEGORIES = 3;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private CategoryContext categoryContext;
    private List<Category> categories = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_category, container, false);

        categoryContext = CategoryContext.context(categoryContext);

        recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(rootView.getContext(), COLUMNS_IN_CATEGORIES);

        recyclerView.setLayoutManager(layoutManager);

        setupGrid(rootView);

        return rootView;
    }

    private void setupGrid(final View view) {

        List<Category> categoryList = categoryContext.loadCategories(new CategoryCompletion.CategoriesErrorCompletion() {

            @Override
            public void completion(List<Category> categoryList, AppError error) {

                if (categoryList != null) {

                    categories.clear();

                    categories.addAll(categoryList);

                    adapter.notifyDataSetChanged();

                    goneProgressBar(view);
                } else {

                    noInternetConnectionAlert();
                }

            }

        });
        if (categoryList != null) {

            categories.clear();

            categories.addAll(categoryList);

            goneProgressBar(view);
        }
        adapter = new CategoryGridAdapter(categories, categoryContext);

        recyclerView.setAdapter(adapter);
    }

    private void goneProgressBar(View view){

        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progress);

        progressBar.setVisibility(View.GONE);
    }

    private void noInternetConnectionAlert() {

        new AlertDialog.Builder(getContext())
                .setTitle(getResources().getString(R.string.alert))
                .setMessage(getResources().getString(R.string.no_internet_connection))
                .setRecycleOnMeasureEnabled(true)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        
    }
}