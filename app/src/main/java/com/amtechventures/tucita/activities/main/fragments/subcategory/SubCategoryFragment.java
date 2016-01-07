package com.amtechventures.tucita.activities.main.fragments.subcategory;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.advanced.AdvancedSearchActivity;
import com.amtechventures.tucita.activities.main.fragments.subcategory.adapters.SubCategoryAdapter;
import com.amtechventures.tucita.model.context.category.CategoryContext;
import com.amtechventures.tucita.model.context.subcategory.SubCategoryContext;
import com.amtechventures.tucita.model.context.subcategory.SubCategoryCompletion;
import com.amtechventures.tucita.model.domain.category.Category;
import com.amtechventures.tucita.model.domain.category.CategoryAttributes;
import com.amtechventures.tucita.model.domain.subcategory.SubCategory;
import com.amtechventures.tucita.model.error.AppError;
import com.parse.ParseObject;
import java.util.ArrayList;
import java.util.List;

public class SubCategoryFragment extends DialogFragment {

    private SubCategoryContext subCategoryContext;
    private Category category;
    private List <SubCategory> subCategories = new ArrayList<>();
    private ListView listView;
    private SubCategoryAdapter adapter;
    private CategoryContext categoryContext;
    private String name;
    private OnOthersClicked listener;
    private ProgressDialog progress;
    private Typeface roboto;

    public interface OnOthersClicked{

        void onOthersClicked();
    }

    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);

        listener = (OnOthersClicked) activity;

        roboto = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Light.ttf");
    }

    @Override
    public void onDetach() {

        super.onDetach();

        listener = null;
    }

    public SubCategoryFragment(){

        categoryContext = CategoryContext.context(categoryContext);

        subCategoryContext = SubCategoryContext.context(subCategoryContext);
    }

    public static SubCategoryFragment newInstance(String name) {

        SubCategoryFragment fragment = new SubCategoryFragment();

        Bundle args = new Bundle();

        args.putString(CategoryAttributes.name, name);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        name = getArguments().getString(CategoryAttributes.name);

        setStyle(DialogFragment.STYLE_NO_TITLE, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_sub_categories, container, false);

        listView = (ListView) rootView.findViewById(R.id.listViewServices);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                int others = subCategories.size();

                if (position == 0) {

                } else if (position == others + 1) {

                    listener.onOthersClicked();

                    dismiss();

                } else {
                    String name = subCategories.get(position - 1).getName();

                    goToAdvancedSearch(name, false);
                }

            }
        });

        setupList();

        return rootView;
    }

    private ArrayList<String> setStringsArray(){

        Activity activity = getActivity();

        ArrayList<String> stringsSubCategories = new ArrayList<>();

        if(activity != null) {

            String all = activity.getString(R.string.all);

            stringsSubCategories.add(all + " " + category.getName());

            for (ParseObject subCategory : subCategories) {

                stringsSubCategories.add(subCategory.getString(CategoryAttributes.name));
            }

            String others = getResources().getString(R.string.others);

            stringsSubCategories.add(others);

        }

        return stringsSubCategories;
    }

    public void setupList(){

        category = categoryContext.findCategory(name);

        List<SubCategory> subCategoriesList = subCategoryContext.loadSubCategories(category, new SubCategoryCompletion.ErrorCompletion() {
         @Override
         public void completion(List<SubCategory> subCategoriesList, AppError error) {

             if (subCategoriesList != null) {

                 adapter.clear();

                 subCategories.clear();

                 subCategories.addAll(subCategoriesList);

                 adapter.addAll(setStringsArray());

                 adapter.notifyDataSetChanged();
             }
             if(progress != null){

                 progress.dismiss();
             }
         }
     });

        if(subCategoriesList == null) {

            setupProgress();
        }else {

            subCategories.addAll(subCategoriesList);
        }
        adapter = new SubCategoryAdapter(getContext(), R.layout.item_sub_category, setStringsArray(), roboto);

        listView.setAdapter(adapter);
    }

    private void goToAdvancedSearch(String name, boolean category) {

        Intent intent = new Intent(getContext(), AdvancedSearchActivity.class);

        intent.putExtra(CategoryAttributes.name, name);

        intent.putExtra(Category.class.getName(), category);

        startActivity(intent);

        subCategoryContext.cancelQuery();

        dismiss();
    }

    public void cancelQuery() {

        subCategoryContext.cancelQuery();
    }

    private void setupProgress(){

        progress = ProgressDialog.show(getContext(), getResources().getString(R.string.dialog_progress_title),

                getResources().getString(R.string.dialog_all_progress_message), true);
    }
}
