package com.amtechventures.tucita.utils.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

public class CircleTextView extends TextView {

    private int padding;
    private Paint circlePaint;

    public CircleTextView(Context context, AttributeSet attrs) {

        super(context, attrs);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        DisplayMetrics metrics = new DisplayMetrics();

        wm.getDefaultDisplay().getMetrics(metrics);

        double size = metrics.scaledDensity;

        padding = (int) ((Integer.parseInt(attrs.getAttributeValue(2).substring(0, 2)) ) * size) / 2;

        setPadding(padding / 3, padding / 3, 0, 0);

        circlePaint = new Paint();

        circlePaint.setStyle(Style.FILL);

        circlePaint.setAntiAlias(true);

        circlePaint.setColor(Color.rgb(16, 215, 218));

    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawCircle((getMeasuredWidth() / 2) , (getMeasuredHeight() / 2) , padding, circlePaint);

        super.onDraw(canvas);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(resolveSize(getMeasuredWidth() + (padding * 2), widthMeasureSpec), resolveSize(getMeasuredHeight() + (padding * 2), heightMeasureSpec));

    }

}