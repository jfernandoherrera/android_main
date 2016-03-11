package com.amtechventures.tucita.utils.common;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.ViewGroup;

public class AppTabLayout extends TabLayout {

        public AppTabLayout(Context context) {

            super(context);

        }

        public AppTabLayout(Context context, AttributeSet attrs) {

            super(context, attrs);

        }

        @Override
        public void setTabsFromPagerAdapter(@NonNull PagerAdapter adapter) {

            Typeface typeface;

            AppFont appFont = new AppFont();

            typeface = appFont.getAppFontLight(getContext());

            this.removeAllTabs();

            ViewGroup slidingTabStrip = (ViewGroup) getChildAt(0);

            for (int i = 0, count = adapter.getCount(); i < count; i++) {

                Tab tab = this.newTab();

                this.addTab(tab.setText(adapter.getPageTitle(i)));

                AppCompatTextView view = (AppCompatTextView) ((ViewGroup)slidingTabStrip.getChildAt(i)).getChildAt(1);

                view.setTypeface(typeface, Typeface.NORMAL);

            }
        }
    }