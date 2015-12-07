package com.amtechventures.tucita.utils.views;


import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.amtechventures.tucita.R;

public class ServiceAddView extends RelativeLayout{

    ImageView imageView;

    RelativeLayout relativeLayout;


    public ServiceAddView(Context context, AttributeSet attrs) {

        super(context, attrs);

        init(context);
    }


    private void init(Context context) {

        LayoutInflater inflater = (LayoutInflater) context

                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.item_add_service, this);

        imageView = (ImageView) findViewById(R.id.image);

        relativeLayout = (RelativeLayout) findViewById(R.id.container);
    }

    public void backgroundClicked(){

        relativeLayout.setBackgroundResource(R.drawable.pressed_application_background_static);
    }

    public void backgroundNormal(){

        relativeLayout.setBackgroundColor(Color.TRANSPARENT);

    }

    public void plusImage(){

        imageView.setImageResource(R.mipmap.ic_plus);
    }

    public void checkImage(){

        imageView.setImageResource(R.mipmap.ic_check);
    }



    }
