package com.amtechventures.tucita.utils.views;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.utils.common.AppFont;

public class AppToolbar extends Toolbar {

    private TextView textView;

    public AppToolbar(Context context, AttributeSet attrs) {

        super(context, attrs);

        setTitle("");

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.toolbar_view, this);

        textView = (TextView) findViewById(R.id.toolbarTextView);

    }

    @Override
    public void setTitle(int resId) {

        textView.setText(resId);

    }

}
