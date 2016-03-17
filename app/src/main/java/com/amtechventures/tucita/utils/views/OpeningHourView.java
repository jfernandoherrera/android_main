package com.amtechventures.tucita.utils.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amtechventures.tucita.R;

public class OpeningHourView extends LinearLayout {

    private TextView day;
    private TextView hours;
    private String closed;

    public OpeningHourView(Context context, AttributeSet attributes) {

        super(context, attributes);

        init(context);

    }

    public OpeningHourView(Context context) {

        super(context);

        init(context);

    }

    private void init(Context context) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.opening_hour, this);

        this.setOrientation(HORIZONTAL);

        day = (TextView) findViewById(R.id.day);

        hours = (TextView) findViewById(R.id.hours);

    }

    public void setState_drawable(boolean state_drawable) {

        if (state_drawable) {

;          day.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.current_day_icon), null, null, null);

        } else {

            day.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.day_icon), null, null, null);

        }

        day.setBackgroundResource(R.drawable.border3);
        invalidate();

        requestLayout();

    }

    public void setDay(String day) {

        this.day.setText(day);

        invalidate();

        requestLayout();

    }

    public void setTypeface(Typeface typeface){

        day.setTypeface(typeface);

        hours.setTypeface(typeface);

    }

    public void setHours(String hours) {

        String current = (String) this.hours.getText();

        current = current.replace(closed, "");

        this.hours.setText(current + hours);

        invalidate();

        requestLayout();

    }

    public void setClosed(String closed) {

        this.closed = " " + closed;

        this.hours.setText(" " + closed);

        invalidate();

        requestLayout();

    }

}
