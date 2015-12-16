package com.amtechventures.tucita.utils.views;



import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckedTextView;
import android.widget.ListAdapter;
import android.widget.ListView;

import static android.view.View.MeasureSpec.*;

public class ViewUtils {

    public  int childHeight = 48;
    public  int parentHeight = 72;
    private final double sizeRatio = 0.0012;
    private final int twelveHoursClock = 12;
    private final int oneDigitNumber = 9;

    public ViewUtils(Context context){

        if( context != null) {

            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

            Display display = wm.getDefaultDisplay();

            Point size = new Point();

            display.getSize(size);

            childHeight = (int) (childHeight * (size.y * sizeRatio)) * 3;

            parentHeight = (int) (parentHeight * (size.y * sizeRatio));
        }
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {

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

    public String hourFormat(int hour, int minute){

        String amPm = hour <= twelveHoursClock ? "AM" : "PM";

        String minuteString = minute <= oneDigitNumber ?  "0" + String.valueOf(minute) :  String.valueOf(minute);

        hour = hour <= twelveHoursClock ? hour : hour - twelveHoursClock;

        String hourString = " " + String.valueOf(hour) + ":" + minuteString + amPm + " ";

        return hourString;
    }

}
