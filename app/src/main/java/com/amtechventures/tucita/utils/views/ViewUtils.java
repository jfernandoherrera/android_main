package com.amtechventures.tucita.utils.views;



import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckedTextView;
import android.widget.ListAdapter;
import android.widget.ListView;

import static android.view.View.MeasureSpec.*;

public class ViewUtils {

    public static final int childHeight = 148;
    public static final int parentHeight = 172;

    public ViewUtils(){
       
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;

        int desiredWidth = makeMeasureSpec(listView.getWidth(), AT_MOST);

        for (int i = 0; i < listAdapter.getCount(); i++) {

            View listItem = listAdapter.getView(i, null, listView);

            listItem.measure(desiredWidth, UNSPECIFIED);

            if(listItem instanceof CheckedTextView) {

                totalHeight += parentHeight;
            }else {

                totalHeight += childHeight;
            }

        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight + (listView.getDividerHeight() *

                (listAdapter.getCount() - 1));

        listView.setLayoutParams(params);

        listView.requestLayout();
    }
}
