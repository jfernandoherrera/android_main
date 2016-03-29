package com.amtechventures.tucita.utils.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.advanced.AdvancedSearchActivity;
import com.amtechventures.tucita.utils.common.AppFont;

public class AppEditText extends EditText{
    private Drawable drawableRight;
    private Drawable drawableLeft;
    private Drawable drawableTop;
    private Drawable drawableBottom;

    int actionX, actionY;

    private AdvancedSearchActivity.DrawableClickListener clickListener;

    public AppEditText(Context context, AttributeSet attrs) {

        this(context, attrs, 	android.R.attr.editTextStyle);

    }

    public AppEditText(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AppTextView, defStyle, 0);

        boolean isBold = a.getBoolean(R.styleable.AppTextView_bold, false);

        a.recycle();

        AppFont appFont = new AppFont();

        if(isBold) {

            setTypeface(appFont.getAppFontLight(context), Typeface.BOLD);

        } else {

            setTypeface(appFont.getAppFontLight(context));

        }

    }

    @Override
    public void setCompoundDrawables(Drawable left, Drawable top,
                                     Drawable right, Drawable bottom) {
        if (left != null) {

            drawableLeft = left;

        }

        if (right != null) {

            drawableRight = right;

        }

        if (top != null) {

            drawableTop = top;

        }

        if (bottom != null) {

            drawableBottom = bottom;

        }

        super.setCompoundDrawables(left, top, right, bottom);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Rect bounds;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            actionX = (int) event.getX();

            actionY = (int) event.getY();

            if (drawableBottom != null

                    && drawableBottom.getBounds().contains(actionX, actionY)) {

                clickListener.onClick(AdvancedSearchActivity.DrawableClickListener.DrawablePosition.BOTTOM);


            }else if (drawableTop != null

                    && drawableTop.getBounds().contains(actionX, actionY)) {

                clickListener.onClick(AdvancedSearchActivity.DrawableClickListener.DrawablePosition.TOP);

            }

            if (drawableLeft != null) {

                bounds = drawableLeft.getBounds();

                int x, y;

                int extraTapArea = (int) (13 * getResources().getDisplayMetrics().density  + 0.5);

                x = actionX;

                y = actionY;

                if (!bounds.contains(actionX, actionY)) {

                    x = (int) (actionX - extraTapArea);

                    y = (int) (actionY - extraTapArea);

                    if (x <= 0) {

                        x = actionX;

                    }

                    if (y <= 0) {

                        y = actionY;

                    }

                    if (x < y) {

                        y = x;

                    }

                }

                if (bounds.contains(x, y) && clickListener != null) {

                    clickListener.onClick(AdvancedSearchActivity.DrawableClickListener.DrawablePosition.LEFT);

                    event.setAction(MotionEvent.ACTION_CANCEL);

                }

            }

            if (drawableRight != null) {

                bounds = drawableRight.getBounds();

                int x, y;

                int extraTapArea = 13;

                x = (int) (actionX + extraTapArea);

                y = (int) (actionY - extraTapArea);

                x = getWidth() - x;

                if(x <= 0){

                    x += extraTapArea;

                }

                if (y <= 0)

                    y = actionY;

                if (bounds.contains(x, y) && clickListener != null) {

                    clickListener.onClick(AdvancedSearchActivity.DrawableClickListener.DrawablePosition.RIGHT);

                    event.setAction(MotionEvent.ACTION_CANCEL);

                }

            }

        }

        return super.onTouchEvent(event);

    }

    @Override
    protected void finalize() throws Throwable {

        drawableRight = null;

        drawableBottom = null;

        drawableLeft = null;

        drawableTop = null;

        super.finalize();

    }

    public void setDrawableClickListener(AdvancedSearchActivity.DrawableClickListener listener) {

        this.clickListener = listener;

    }
}
