package com.amtechventures.tucita.utils.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.amtechventures.tucita.R;

public class ServiceAddView extends RelativeLayout{

    private ImageView imageView;

    private RelativeLayout relativeLayout;

    private TextView name;

    private TextView duration;

    private TextView price;

    private boolean plus = false;

    public ServiceAddView(Context context, AttributeSet attrs) {

        super(context, attrs);

        init(context);
    }

    public void setPlus(boolean value){

        plus = value;
    }

    public boolean getPlus(){

        return plus;
    }

    private void init(Context context) {

        LayoutInflater inflater = (LayoutInflater) context

                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.item_add_service, this);

        imageView = (ImageView) findViewById(R.id.image);

        relativeLayout = (RelativeLayout) findViewById(R.id.container);

        name = (TextView) findViewById(R.id.textName);

        duration = (TextView) findViewById(R.id.textDuration);

        price = (TextView) findViewById(R.id.textPricesFrom);
    }

    public void setName(String name){

        this.name.setText(name);
    }

    public void setDuration(String duration){

        this.duration.setText(duration);
    }

    public void setPrice(int price){

        String formattedPrice = "$ " + price;

    this.price.setText(formattedPrice);
    }

    public void backgroundClicked(){

        relativeLayout.setBackgroundResource(R.drawable.pressed_application_background_static);
    }

    public void backgroundNormal(){

        relativeLayout.setBackgroundColor(Color.TRANSPARENT);

    }

    public void setImageView(){

        if(plus){

            plusImage();
        }else{

            checkImage();
        }
    }

    private void plusImage(){

        imageView.setImageResource(R.mipmap.ic_plus);
    }

    private void checkImage(){

        imageView.setImageResource(R.mipmap.ic_check);
    }

    }
