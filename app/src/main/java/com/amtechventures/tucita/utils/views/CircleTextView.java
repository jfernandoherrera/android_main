package com.amtechventures.tucita.utils.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.utils.common.AppFont;

public class CircleTextView extends TextView {
    int padding;

    public CircleTextView(Context context, AttributeSet attrs) {

        super(context, attrs);

        int [] attributes = new int [] {android.R.attr.layout_height};

        TypedArray arr = context.obtainStyledAttributes(attrs, attributes);

        padding = arr.hasValue(0) ? arr.getDimensionPixelOffset(0, 1) : 1;

        arr.recycle();

        padding = ((padding) / 2);

        setPadding(padding / 2, padding / 3, 0, 0);

        AppFont appFont = new AppFont();

        setTextColor(context.getResources().getColor(R.color.white));

        setTypeface(appFont.getAppFontMedium(context), Typeface.BOLD);

    }




    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(resolveSize(getMeasuredWidth() + (padding * 2), widthMeasureSpec), resolveSize(getMeasuredHeight() + (padding * 2), heightMeasureSpec));

    }

}