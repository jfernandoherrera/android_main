package com.amtechventures.tucita.activities.service;

import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.amtechventures.tucita.R;

public class ServiceFragment extends Fragment {

    private RelativeLayout relativeLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_services, container, false);

        relativeLayout = (RelativeLayout) rootView.findViewById(R.id.serviceContainer);

        return rootView;
    }

    public void setMarginBottomToShoppingCarVisible(){

        RelativeLayout.LayoutParams params =new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );


            params.setMargins(0, 90, 0, 60);

        relativeLayout.setLayoutParams(params);
    }

    public void setMarginBottomToShoppingCarGone(){

        RelativeLayout.LayoutParams params =new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        params.setMargins(0, 90, 0, 0);

        relativeLayout.setLayoutParams(params);
    }

}
