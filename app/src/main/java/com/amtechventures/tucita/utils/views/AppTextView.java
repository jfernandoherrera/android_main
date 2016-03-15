package com.amtechventures.tucita.utils.views;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.utils.common.AppFont;

public class AppTextView extends TextView{


    public AppTextView(Context context, AttributeSet attrs) {

        this(context, attrs, 0);

    }

    public AppTextView(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AppTextView, defStyle, 0);

        boolean isBold = a.getBoolean(R.styleable.AppTextView_bold, false);

        //a.recycle();

        AppFont appFont = new AppFont();

        if(isBold) {

            setTypeface(appFont.getAppFontLight(context), Typeface.BOLD);

        } else {

            setTypeface(appFont.getAppFontLight(context));

        }

    }

}
