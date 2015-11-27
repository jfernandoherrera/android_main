package com.amtechventures.tucita.utils.views;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.widget.TextView;

public class CircleTextView extends TextView {

    private static final int PADDING = 15;
    private Paint mCirclePaint;

    public CircleTextView(Context context, AttributeSet attrs) {

        super(context, attrs);

        mCirclePaint = new Paint();

        mCirclePaint.setStyle(Style.FILL);

        mCirclePaint.setAntiAlias(true);

        mCirclePaint.setColor(Color.GREEN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // draw circle at center of canvas
        canvas.drawCircle((getMeasuredWidth()/4) + 2, getMeasuredHeight()/4, PADDING, mCirclePaint);
        
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // grow view dimensions to account for circle
        setMeasuredDimension(resolveSize(getMeasuredWidth()+(PADDING*2), widthMeasureSpec), resolveSize(getMeasuredHeight()+(PADDING*2), heightMeasureSpec));
    }
}