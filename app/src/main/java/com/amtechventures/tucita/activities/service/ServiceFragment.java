package com.amtechventures.tucita.activities.service;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.model.domain.service.Service;
import com.amtechventures.tucita.utils.views.ServiceAddView;

public class ServiceFragment extends Fragment {

    private RelativeLayout relativeLayout;
    private ServiceAddView serviceAddView;
    private Service service;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_services, container, false);

        relativeLayout = (RelativeLayout) rootView.findViewById(R.id.serviceContainer);

        serviceAddView = (ServiceAddView) rootView.findViewById(R.id.first);

        serviceAddView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    backgroundClicked();

                }else if(event.getAction() == MotionEvent.ACTION_UP){

                    backgroundNormal();
                }

                return false;
            }
        });

        return rootView;
    }

    public void setService(Service service){

        serviceAddView.setName(service.getName());

        serviceAddView.setDuration(service.getDurationInfo());

        serviceAddView.setPrice(service.getPrice());
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

    public void plusImage(){

        serviceAddView.plusImage();
    }

    public void checkImage(){

        serviceAddView.checkImage();
    }

    public void backgroundNormal(){

        serviceAddView.backgroundNormal();
    }

    public void backgroundClicked(){

        serviceAddView.backgroundClicked();
    }
}
