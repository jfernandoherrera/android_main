package com.amtechventures.tucita.utils.common;


import android.content.Context;
import android.graphics.Typeface;

public class AppFont {


    public Typeface getAppFontLight(Context context){

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Avenir-Light.ttf");

        return typeface;

    }

    public Typeface getAppFontMedium(Context context){

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Avenir-Medium.ttf");

        return typeface;

    }


}
