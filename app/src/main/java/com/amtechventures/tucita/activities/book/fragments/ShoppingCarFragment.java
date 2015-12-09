package com.amtechventures.tucita.activities.book.fragments;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.book.adapters.ServicesToBookAdapter;
import com.amtechventures.tucita.model.domain.service.Service;
import java.util.ArrayList;

public class ShoppingCarFragment extends DialogFragment {

    ArrayList<Service> servicesToBook;
    private RecyclerView recyclerView;
    private ServicesToBookAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_shopping_car, container, false);

        adapter = new ServicesToBookAdapter(servicesToBook);

        recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);

        layoutManager = new LinearLayoutManager(rootView.getContext());

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        return rootView;

    }

    public boolean alreadyExistsService(Service service){

        boolean exists = false;

        if(servicesToBook != null) {

            for (Service service1 : servicesToBook) {

                if (service1.getName().equals(service.getName())) {

                    exists = true;
                }
            }
        }
    return exists;
    }

    public void addService(Service service) {

        if(servicesToBook == null){

        servicesToBook = new ArrayList<>();

            servicesToBook.add(service);

        }else if(alreadyExistsService(service)){

            removeService(service);
        }else{

            servicesToBook.add(service);
        }

        if (adapter != null) {

            adapter.notifyDataSetChanged();
        }
    }

    public void removeService(Service service){

        servicesToBook.remove(service);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_TITLE, 0);
    }
}
