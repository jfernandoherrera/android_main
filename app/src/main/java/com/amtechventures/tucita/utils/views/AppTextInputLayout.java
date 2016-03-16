package com.amtechventures.tucita.utils.views;


import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;

import com.amtechventures.tucita.utils.common.AppFont;

public class AppTextInputLayout extends TextInputLayout {

    public AppTextInputLayout(Context context, AttributeSet attrs) {

        super(context, attrs);

        AppFont appFont = new AppFont();

        setTypeface(appFont.getAppFontLight(context));
    }

}
