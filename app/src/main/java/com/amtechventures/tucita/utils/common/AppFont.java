package com.amtechventures.tucita.utils.common;


import android.content.Context;
import android.graphics.Typeface;

public class AppFont {

    public Typeface getLightAppFont(Context context){

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Avenir-Light.ttf");

        return typeface;

    }

    public Typeface getHeavyAppFont(Context context){

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Avenir-Heavy.ttf");

        return typeface;

    }

    public Typeface getMediumAppFont(Context context){

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Avenir-Heavy.ttf");

        return typeface;

    }

}
