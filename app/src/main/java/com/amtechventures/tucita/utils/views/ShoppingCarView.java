package com.amtechventures.tucita.utils.views;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

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

        car = (ImageView) findViewById(R.id.car);

        bookNow = (Button) findViewById(R.id.bookNow);

        bookNow.setBackgroundColor(Color.YELLOW);

        circleTextView = (CircleTextView) findViewById(R.id.count);

        car.setImageResource(R.mipmap.ic_launcher);

        this.setBackgroundColor(Color.YELLOW);

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
