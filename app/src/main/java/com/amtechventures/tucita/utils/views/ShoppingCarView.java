package com.amtechventures.tucita.utils.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.amtechventures.tucita.R;

public class ShoppingCarView extends FrameLayout {

    private ImageView car;

    private Button bookNow;

    private CircleTextView circleTextView;

    private int count = 0;

    public ShoppingCarView(Context context, AttributeSet attrs) {

        super(context, attrs);

        init(context);
    }

    private void init(Context context){

        LayoutInflater inflater = (LayoutInflater) context

                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.shopping_car, this);

        setBackgroundResource(R.drawable.log_in_or_signup_click_out);

        car = (ImageView) findViewById(R.id.car);

        bookNow = (Button) findViewById(R.id.bookNow);

        circleTextView = (CircleTextView) findViewById(R.id.count);

        car.setImageResource(R.mipmap.ic_launcher);


        circleTextView.setText(" 10");

        circleTextView.setTranslationX(32);

        circleTextView.bringToFront();
    }

    public void increment(){

        count++;

        circleTextView.setText(" " + count);
    }

    public void decrement(){
        count--;

        circleTextView.setText(" " + count);
    }

    public void setOnClick(OnClickListener onClickListener){

        bookNow.setOnClickListener(onClickListener);
    }
}
