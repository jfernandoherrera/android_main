package com.amtechventures.tucita.activities.book.fragments.service;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.book.fragments.venue.adapters.ExpandableListAdapter;
import com.amtechventures.tucita.model.domain.service.Service;
import com.amtechventures.tucita.utils.views.ExpandableParentAdapter;
import com.amtechventures.tucita.utils.views.ServiceAddView;

public class ServiceFragment extends Fragment {

    private RelativeLayout relativeLayout;
    private ServiceAddView serviceAddView;
    private ExpandableParentAdapter parentAdapter;
    private ExpandableListView listViewParent;
    private Service service;
    private LayoutInflater inflater;
    private OnServiceSelected listener;

    public interface OnServiceSelected{

        void onServiceBook(Service service);
    }

    public Service getService(){

        return service;
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

        listener = (OnServiceSelected) context;
    }

    @Override
    public void onDetach() {

        super.onDetach();

        listener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_services, container, false);

        relativeLayout = (RelativeLayout) rootView.findViewById(R.id.serviceContainer);

        serviceAddView = (ServiceAddView) rootView.findViewById(R.id.first);

        listViewParent = (ExpandableListView) rootView.findViewById(R.id.listViewDescription);

        serviceAddView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.onServiceBook(service);
            }
        });

        this.inflater = inflater;

        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
    }

    public void setup() {

        String description = service.getDescription();

        if (description != null) {

            parentAdapter = new ExpandableParentAdapter(R.layout.list_item, null, description);

            parentAdapter.setInflater(inflater);

            listViewParent.setAdapter(parentAdapter);

        }else {

            listViewParent.setVisibility(View.GONE);
        }
    }
    public void setService(Service service, boolean added){

        this.service = service;

        if(added){

            serviceAddView.setPlus(false);
        }else{

            serviceAddView.setPlus(true);
        }
        serviceAddView.setImageView();

        serviceAddView.setName(service.getName());

        serviceAddView.setDuration(service.getDurationInfo());

        serviceAddView.setPrice(service.getPrice());
    }

    public void setServiceState(boolean state){

        serviceAddView.setPlus(state);
    }

    public boolean getServiceState(){

      return serviceAddView.getPlus();
    }

    public void setMarginBottomToShoppingCarVisible( int shoppingCarHeight){

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
            params.setMargins(0, 0, 0, shoppingCarHeight);

        relativeLayout.setLayoutParams(params);
    }

    public void setMarginBottomToShoppingCarGone(){

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        params.setMargins(0, 0, 0, 0);

        relativeLayout.setLayoutParams(params);
    }

    public void checkImage(){

        serviceAddView.setImageView();
    }
}
