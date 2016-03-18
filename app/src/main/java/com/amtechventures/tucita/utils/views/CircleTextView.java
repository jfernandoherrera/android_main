package com.amtechventures.tucita.utils.views;

import android.content.Context;
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

    private int padding;
    private Drawable circlePaint;

    public CircleTextView(Context context, AttributeSet attrs) {

        super(context, attrs);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        DisplayMetrics metrics = new DisplayMetrics();

        wm.getDefaultDisplay().getMetrics(metrics);

        double size = metrics.scaledDensity;

        padding = (int) ((Integer.parseInt(attrs.getAttributeValue(2).substring(0, 2)) ) * size) / 2;

        setPadding(padding / 2, padding / 3, 0, 0);

        circlePaint = context.getResources().getDrawable(R.drawable.rate_05);

        setBackgroundDrawable(circlePaint);

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