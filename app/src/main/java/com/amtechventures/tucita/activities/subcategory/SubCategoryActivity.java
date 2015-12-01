package com.amtechventures.tucita.activities.subcategory;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.main.MainActivity;
import com.amtechventures.tucita.activities.search.advanced.AdvancedSearchActivity;
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

public class SubCategoryActivity extends AppCompatActivity {

    private SubCategoryContext subCategoryContext;
    private Category category;
    private List <SubCategory> subCategories = new ArrayList<>();
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private CategoryContext categoryContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sub_categories);

        categoryContext = CategoryContext.context(categoryContext);

        listView = (ListView) findViewById(R.id.listViewservices);

        subCategoryContext = SubCategoryContext.context(subCategoryContext);

        category = categoryContext.findCategory(getIntent().getExtras().getString(Category.class.getName()));

         listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                int others = subCategories.size();

                if (position == 0) {

                    String name = category.getName();

                    goToAdvancedSearch(name, true);

                } else if (position == others + 1) {


                } else {

                    String name = subCategories.get(position-1).getName();

                    goToAdvancedSearch(name, false);
                }

            }
        });
        setupList();
    }

    private ArrayList<String> setStringsArray(){

        ArrayList<String> stringsSubCategories = new ArrayList<>();

        String all = getResources().getString(R.string.all);

        stringsSubCategories.add(all + " " + category.getName());

        for(ParseObject subCategory : subCategories){

            stringsSubCategories.add(subCategory.getString(CategoryAttributes.name));
        }

        String others = getResources().getString(R.string.others);

        stringsSubCategories.add(others);

        return stringsSubCategories;
    }

    private void setupList(){

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
         }
     });

        subCategories.addAll(subCategoriesList);

        adapter = new ArrayAdapter<String>(this, R.layout.list_item, setStringsArray());

        listView.setAdapter(adapter);
    }

    private void goToAdvancedSearch(String name, boolean category) {

        Intent intent = new Intent(SubCategoryActivity.this, AdvancedSearchActivity.class);

        intent.putExtra(CategoryAttributes.name, name);

        intent.putExtra(Category.class.getName(), category);

        startActivity(intent);

        subCategoryContext.cancelQuery();
    }

    @Override
    protected void onStop() {

        super.onStop();

        subCategoryContext.cancelQuery();
    }
}
