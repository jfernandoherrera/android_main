package com.amtechventures.tucita.model.context.user;


import android.app.Activity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.utils.blocks.BoolBoolUserCompletion;
import com.parse.ParseException;
import com.parse.ParseUser;

public class UserRemote {

    ParseUser parseUser;
    private BoolBoolUserCompletion loginCompletion;

    public void login(Activity activity, BoolBoolUserCompletion completion) {

        EditText passwordView = (EditText) activity.findViewById(R.id.password);

        TextView email = (TextView) activity.findViewById(R.id.email);

        loginCompletion = completion;

        try {
            parseUser= ParseUser.logIn(email.getText().toString(),passwordView.getText().toString());

            loginCompletion.completion(parseUser, true, false);

        } catch (ParseException e) {
            Log.i(ParseException.class.getName(), e.toString());
        }

    }
    }
