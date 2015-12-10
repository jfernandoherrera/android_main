package com.amtechventures.tucita.activities.book.fragments;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.book.adapters.ServicesToBookAdapter;
import com.amtechventures.tucita.model.domain.service.Service;
import java.util.ArrayList;

public class ShoppingCarFragment extends DialogFragment implements ServicesToBookAdapter.OnItemClosed{

    ArrayList<Service> servicesToBook;
    private RecyclerView recyclerView;
    private ServicesToBookAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private OnItemClosed listener;
    private OnAddMoreServices onAddMoreServicesListener;

    public interface OnItemClosed{

        void onItemClosed();
    }

    public interface OnAddMoreServices{

        void onAddMoreServices();
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

        listener = (OnItemClosed) context;

        onAddMoreServicesListener  = (OnAddMoreServices) context;
    }

    @Override
    public void onDetach() {

        super.onDetach();

        listener = null;

        onAddMoreServicesListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_shopping_car, container, false);

        adapter = new ServicesToBookAdapter(servicesToBook, this);

        recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);

        layoutManager = new LinearLayoutManager(rootView.getContext());

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        final Button button = (Button) rootView.findViewById(R.id.moreServices);

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    button.setBackgroundResource(R.drawable.pressed_application_background_static);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {

                    button.setBackgroundColor(Color.TRANSPARENT);
                }


                return false;
            }
        });

        button.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dismiss();

                        onAddMoreServicesListener.onAddMoreServices();
                    }
                }
        );

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

        setStyle(STYLE_NO_TITLE, 0);
    }

    @Override
    public void onStart() {
        super.onStart();

        Window window = getDialog().getWindow();

        WindowManager.LayoutParams windowParams = window.getAttributes();

        windowParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;

        window.setAttributes(windowParams);

    }

    @Override
    public void onItemClosed(Service service) {
        
        servicesToBook.remove(service);

        adapter.notifyDataSetChanged();

        listener.onItemClosed();

        if(servicesToBook.isEmpty()){

            dismiss();
        }

    }
}
