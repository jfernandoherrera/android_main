package com.amtechventures.tucita.utils.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

public class CircleTextView extends TextView {

    private int padding;
    private Paint circlePaint;

    public CircleTextView(Context context, AttributeSet attrs) {

        super(context, attrs);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        Display display = wm.getDefaultDisplay();

        Point size = new Point();

        display.getSize(size);

        padding = (int) (size.x * 0.08) / 3;

        circlePaint = new Paint();

        circlePaint.setStyle(Style.FILL);

        circlePaint.setAntiAlias(true);

        circlePaint.setColor(Color.rgb(16, 215, 218));

    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawCircle((getMeasuredWidth() / 4) + 2, getMeasuredHeight() / 4, padding, circlePaint);

        super.onDraw(canvas);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(resolveSize(getMeasuredWidth() + (padding * 2), widthMeasureSpec), resolveSize(getMeasuredHeight() + (padding * 2), heightMeasureSpec));

    }

}