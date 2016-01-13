package com.amtechventures.tucita.activities.account.fragments.review;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;

import com.amtechventures.tucita.R;
import com.mikhaellopez.circularimageview.CircularImageView;

public class ReviewFragment extends DialogFragment {

    CircularImageView circularImageView;
    RelativeLayout layout;
    RatingBar ratingBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_review, container, false);

        circularImageView = (CircularImageView) rootView.findViewById(R.id.imageUser);

        layout = (RelativeLayout) rootView.findViewById(R.id.frames);

        layout.bringToFront();

        Window window = getDialog().getWindow();

        window.setBackgroundDrawableResource(android.R.color.transparent);

        return rootView;
    }

}
