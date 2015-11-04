package com.amtechventures.tucita.activities.services;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.model.context.category.CategoryContext;
import com.amtechventures.tucita.model.context.service.ServiceContext;
import com.amtechventures.tucita.model.context.service.ServicesCompletion;
import com.amtechventures.tucita.model.domain.category.Category;
import com.amtechventures.tucita.model.domain.service.Service;
import com.amtechventures.tucita.model.error.AppError;
import com.amtechventures.tucita.utils.blocks.Completion;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

public class ServicesActivity extends AppCompatActivity {

    ServiceContext serviceContext;

    Category category;

    List <Service>services = new ArrayList<>();

    ListView listView;

    ArrayAdapter<String> adapter;

    CategoryContext categoryContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_services);

        categoryContext = CategoryContext.context(categoryContext);

        listView = (ListView) findViewById(R.id.listViewservices);

        serviceContext = new ServiceContext();

        category = categoryContext.findCategory(getIntent().getExtras().getString(Category.class.getName()));

         listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) listView.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                        .show();

            }

        });
        setupList();
    }


    private ArrayList<String> setStringsArray(){

        ArrayList<String> stringsServices=new ArrayList<>();

        for(ParseObject service : services){

            stringsServices.add(service.getString("name"));
        }

        return stringsServices;
    }

    private void setupList(){

     List<Service> serviceList= serviceContext.loadServices(category, new ServicesCompletion.ErrorCompletion() {
            @Override
            public void completion(List<Service> servicesList, AppError error) {

                if(servicesList!=null){

                    services.clear();

                    services.addAll(servicesList);

                    adapter.addAll(setStringsArray());

                    adapter.notifyDataSetChanged();
                }
            }
        });

        services.addAll(serviceList);

        adapter = new ArrayAdapter<String>(this,  android.R.layout.simple_list_item_1, setStringsArray());

        listView.setAdapter(adapter);

    }

}
