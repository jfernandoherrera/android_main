package com.amtechventures.tucita.activities.advanced.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class CityAdapter extends ArrayAdapter {

    Typeface typeface;

    public CityAdapter(Context context, int resource, Typeface typeface) {

        super(context, resource);

        this.typeface = typeface;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup)
    {
        TextView textView = ((TextView)view);

        textView.setTypeface(typeface);

        return textView;
    }

}
