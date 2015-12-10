package com.amtechventures.tucita.utils.views;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.amtechventures.tucita.R;

public class ShoppingCarView extends FrameLayout {

    private ImageView car;

    private Button bookNow;

    private CircleTextView circleTextView;

    private int count = 1;

    private final String init = " 1";

    private final double positionRatio = 0.095;

    OnCarClicked listener;

    public interface OnCarClicked{

       void onCarClicked();

    }

    public ShoppingCarView(Context context, AttributeSet attrs) {

        super(context, attrs);

        init(context);
    }

    private void init(Context context){

        LayoutInflater inflater = (LayoutInflater) context

                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.shopping_car, this);

        listener = (OnCarClicked) context;

        car = (ImageView) findViewById(R.id.car);

        bookNow = (Button) findViewById(R.id.bookNow);

        circleTextView = (CircleTextView) findViewById(R.id.count);

        car.setImageResource(R.mipmap.ic_launcher);

        circleTextView.setText(init);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        Display display = wm.getDefaultDisplay();

        Point size = new Point();

        display.getSize(size);

        int translation = (int)(size.x * positionRatio) / 2;

        circleTextView.setTranslationX(translation);

        circleTextView.bringToFront();

        car.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.onCarClicked();
            }
        });
    }

    public void showView(){

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

            animate().translationY(getHeight()).withEndAction(new Runnable() {
                @Override
                public void run() {

                    setVisibility(View.VISIBLE);

                    animate().translationY(0);
                }
            });

        } else {

        setVisibility(View.VISIBLE);

        }
    }

    public void hideView() {

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

            animate().translationY(getHeight()).withEndAction(new Runnable() {

                @Override
                public void run() {

                    setVisibility(View.GONE);
                }
            });
        } else {

            setVisibility(View.GONE);
        }
    }

    public void increment(){

        count++;

        circleTextView.setText(" " + count);

        if(count == 1){

            showView();
        }
    }

    public boolean isEmpty(){

        boolean isEmpty = count == 0;

        return isEmpty;
    }
    public void decrement(){

        count--;

        circleTextView.setText(" " + count);

        if(count == 0){

            hideView();
        }
    }

    public void setOnClick(OnClickListener onClickListener){

        bookNow.setOnClickListener(onClickListener);
    }
}
