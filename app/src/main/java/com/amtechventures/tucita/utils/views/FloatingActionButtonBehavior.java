package com.amtechventures.tucita.utils.views;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class FloatingActionButtonBehavior extends FloatingActionButton.Behavior{

    public FloatingActionButtonBehavior(Context context, AttributeSet attrs){
        super();
    }

    @Override
    public void onDependentViewRemoved(CoordinatorLayout parent, FloatingActionButton child, View dependency) {

        child.setVisibility(View.INVISIBLE);

        super.onDependentViewRemoved(parent, child, dependency);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton child, View dependency) {

        child.setVisibility(View.VISIBLE);
        child.bringToFront();
        child.setTranslationX(30);
        return super.onDependentViewChanged(parent, child, dependency);
    }
}
