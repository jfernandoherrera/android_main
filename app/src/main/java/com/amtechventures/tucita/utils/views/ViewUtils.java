package com.amtechventures.tucita.utils.views;



import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckedTextView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import static android.view.View.MeasureSpec.*;

public class ViewUtils {

    public  int childHeight = 48;
    public  int parentHeight = 72;
    private final double sizeRatio = 0.0012;
    private final int twelveHoursClock = 12;
    private final int oneDigitNumber = 9;
    private Context context;

    public ViewUtils(Context context){

        this.context = context;

        if( context != null) {

        setupHeights();

        }
    }

    private void setupHeights(){

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        Display display = wm.getDefaultDisplay();

        Point size = new Point();

        display.getSize(size);

        childHeight = (int) (childHeight * (size.y * sizeRatio)) * 3;

        parentHeight = (int) (parentHeight * (size.y * sizeRatio));
    }

    private void setupHeights(int childHeight, int parentHeight){

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        Display display = wm.getDefaultDisplay();

        Point size = new Point();

        display.getSize(size);

        this.childHeight = (int) (childHeight * (size.y * sizeRatio)) * 3;

        this.parentHeight = (int) (parentHeight * (size.y * sizeRatio));
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

    public void setListViewHeightBasedOnChildren(ListView listView, int childHeight, int parentHeight) {

        setupHeights(childHeight, parentHeight);

        setListViewHeightBasedOnChildren(listView);
    }


    public void setListViewHeightBasedOnChildrenCountLines(ListView listView, int height) {

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

                totalHeight += height;
            }

        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight;

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
