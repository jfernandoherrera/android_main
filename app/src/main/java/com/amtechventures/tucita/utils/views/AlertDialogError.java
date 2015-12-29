package com.amtechventures.tucita.utils.views;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.widget.Toast;

import com.amtechventures.tucita.R;

public class AlertDialogError {

    public void noInternetConnectionAlert(Context context) {

        Toast typeMore = Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_SHORT);

        typeMore.setGravity(Gravity.CENTER | Gravity.LEFT, 0, 0);

        typeMore.show();
    }

    public void noLocationAlert(Context context) {

        Toast typeMore = Toast.makeText(context, R.string.no_Location, Toast.LENGTH_SHORT);

        typeMore.setGravity(Gravity.CENTER | Gravity.LEFT, 0, 0);

        typeMore.show();
    }

    public void noTypedEnough(Context context) {

        Toast typeMore = Toast.makeText(context, R.string.typing_advertisement, Toast.LENGTH_SHORT);

        typeMore.setGravity(Gravity.CENTER | Gravity.LEFT, 0, 0);

        typeMore.show();
    }

    public void noAvailableSlot(Context context) {

        Toast typeMore = Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_SHORT);

        typeMore.setGravity(Gravity.CENTER | Gravity.LEFT, 0, 0);

        typeMore.show();
    }

}
