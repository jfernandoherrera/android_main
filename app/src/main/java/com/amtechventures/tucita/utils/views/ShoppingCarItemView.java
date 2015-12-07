package com.amtechventures.tucita.utils.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amtechventures.tucita.R;

public class ShoppingCarItemView extends RelativeLayout{

    TextView name;

    TextView duration;

    TextView price;

    public ShoppingCarItemView(Context context, AttributeSet attrs) {

        super(context, attrs);

        init(context);

    }

    private void init(Context context) {

        LayoutInflater inflater = (LayoutInflater) context

                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.item_basket, this);

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
}
