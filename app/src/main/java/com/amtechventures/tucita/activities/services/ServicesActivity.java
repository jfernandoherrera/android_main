package com.amtechventures.tucita.activities.services;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.model.context.service.ServiceContext;
import com.amtechventures.tucita.model.domain.category.Category;
import com.amtechventures.tucita.model.error.AppError;
import com.amtechventures.tucita.utils.blocks.Completion;

import java.util.ArrayList;

public class ServicesActivity extends AppCompatActivity {

    ServiceContext serviceContext;

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_services);

        listView = (ListView) findViewById(R.id.listView);


        String category = getIntent().getExtras().getString(Category.class.getName());

        serviceContext = new ServiceContext();

        ArrayList services= (ArrayList) serviceContext.loadServices(category, new Completion.ErrorCompletion() {
            @Override
            public void completion(AppError error) {

                if(error!=null){

                }
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,

                android.R.layout.simple_list_item_1, android.R.id.text1, services);

        listView.setAdapter(adapter);

        // ListView Item Click Listener
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

    }

}
