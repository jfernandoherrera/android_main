package com.amtechventures.tucita.utils.views;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;

import com.amtechventures.tucita.utils.common.AppFont;

public class AppButton extends Button{

    public AppButton(Context context, AttributeSet attrs) {

        super(context, attrs);

        AppFont font = new AppFont();

        setTypeface(font.getAppFontMedium(context), Typeface.BOLD);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            setElevation(8);

        }

    }

}
