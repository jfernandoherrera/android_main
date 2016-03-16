package com.amtechventures.tucita.activities.main.fragments.subcategory.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.amtechventures.tucita.R;

import java.util.List;

public class SubCategoryAdapter extends ArrayAdapter {

    int size;
    Drawable first;
    Drawable last;
    Drawable contained;

    public SubCategoryAdapter(Context context, int resource, List objects, Drawable first, Drawable last, Drawable contained) {

        super(context, resource, objects);

        size = objects.size();

        this.last = last;

        this.first = first;

        this.contained = contained;

    }

    @Override
    public void notifyDataSetChanged() {

        size = getCount();

        super.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView v = (TextView) super.getView(position, convertView, parent);

        Drawable drawable;

        if (position == 0) {

            drawable = first;

        } else if(position == size - 1) {

            drawable = last;

        } else {

                drawable = contained;

        }

                v.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);

        return v;

    }

}
