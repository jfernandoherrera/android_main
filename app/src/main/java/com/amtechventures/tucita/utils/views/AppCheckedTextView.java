package com.amtechventures.tucita.utils.views;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckedTextView;

import com.amtechventures.tucita.utils.common.AppFont;

public class AppCheckedTextView extends CheckedTextView{
    public AppCheckedTextView(Context context, AttributeSet attrs) {

        super(context, attrs);

        AppFont appFont = new AppFont();

        setTypeface(appFont.getAppFontLight(context));

    }
}
