package com.amtechventures.tucita.utils.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.amtechventures.tucita.R;

public class OpeningHourView extends LinearLayout {

    private ImageView state_drawable;

    private TextView day;

    private TextView hours;

    public OpeningHourView(Context context, AttributeSet attributes){

        super(context, attributes);

       init(context);
    }

    public OpeningHourView(Context context) {

        super(context);

        init(context);
    }

    private void init(Context context){

        LayoutInflater inflater = (LayoutInflater) context

                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.opening_hour, this);

        this.setOrientation(HORIZONTAL);

        state_drawable = (ImageView) findViewById(R.id.state_drawable);

        day = (TextView) findViewById(R.id.day);

        hours = (TextView) findViewById(R.id.hours);
    }

    public void setState_drawable(boolean state_drawable){

        if(state_drawable){

            this.state_drawable.setImageResource(R.mipmap.ic_state_drawable_activated);

        }else{

            this.state_drawable.setImageResource(R.mipmap.ic_state_drawable_desactivated);
        }
        invalidate();

        requestLayout();
    }

    public void setDay(String day){

        this.day.setText(day);

        invalidate();

        requestLayout();
    }

    public void setHours(String hours){

        this.hours.setText(hours);

        invalidate();

        requestLayout();
    }
}
