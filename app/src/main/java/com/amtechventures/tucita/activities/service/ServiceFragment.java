package com.amtechventures.tucita.activities.service;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.amtechventures.tucita.R;

public class ServiceFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

      View rootView = inflater.inflate(R.layout.fragment_services, container, false);

        return rootView;
    }



}
