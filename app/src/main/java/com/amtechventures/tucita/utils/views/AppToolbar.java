package com.amtechventures.tucita.utils.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
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

        super.setTitle("");

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.toolbar_view, this);

        textView = (TextView) findViewById(R.id.toolbarTextView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            setElevation(8);

        }

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AppToolbar, 0, 0);

        boolean isBack = a.getBoolean(R.styleable.AppToolbar_navigationBack, false);

        if(isBack) {

            setNavigationIcon(R.drawable.back_arrow);

        }

        a.recycle();
    }

    public AppToolbar(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);

    }

    @Override
    public void setTitle(int resId) {

        textView.setText(resId);

    }

    @Override
    public void setTitle(CharSequence text) {

        textView.setText(text);

    }

}
