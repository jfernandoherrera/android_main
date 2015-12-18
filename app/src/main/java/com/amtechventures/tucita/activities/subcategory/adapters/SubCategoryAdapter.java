package com.amtechventures.tucita.activities.subcategory.adapters;


import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class SubCategoryAdapter extends ArrayAdapter{

    private Typeface roboto;

    public SubCategoryAdapter(Context context, int resource, List objects, Typeface typeface) {

            super(context, resource, objects);

            roboto = typeface;
        }

        @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView v = (TextView) super.getView(position, convertView, parent);

            v.setTypeface(roboto, Typeface.BOLD);

            return v;
    }
}
