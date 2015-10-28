package com.amtechventures.tucita.model.context.user;


import android.app.Activity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.utils.blocks.Completion;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class UserRemote {

    ParseUser parseUser;
    private Completion.BoolBoolUserCompletion loginCompletion;
    private Completion.BoolErrorUserCompletion signUpCompletion;
    public void login(Activity activity, Completion.BoolBoolUserCompletion completion) {

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

    public void signUp(Activity activity,Completion.BoolErrorUserCompletion completion) {

        signUpCompletion = completion;

        EditText passwordView = (EditText) activity.findViewById(R.id.password);

        TextView email = (TextView) activity.findViewById(R.id.email);

        TextView name = (TextView) activity.findViewById(R.id.name);

        parseUser=new ParseUser();

        parseUser.setEmail(email.getText().toString());

        parseUser.setUsername(name.getText().toString());

        parseUser.setPassword(passwordView.getText().toString());

        parseUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){
                    signUpCompletion.completion(parseUser, false, null);
                }else {
                    signUpCompletion.completion(parseUser, true, new Error(e.getMessage()));
                }
            }
        });


    }
}
