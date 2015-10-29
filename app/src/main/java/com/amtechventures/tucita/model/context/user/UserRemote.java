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

    public void login(String email, String password,Completion.BoolBoolUserCompletion completion) {


        loginCompletion = completion;

        try {
            parseUser= ParseUser.logIn(email,password);

            loginCompletion.completion(parseUser, true, false);

        } catch (ParseException e) {

            Log.i(ParseException.class.getName(), e.toString());
        }

    }

    public ParseUser signUp(String email, String password,String name, Completion.BoolErrorUserCompletion completion) {

        signUpCompletion = completion;


        parseUser = new ParseUser();

        parseUser.setEmail(email);

        parseUser.setUsername(name);

        parseUser.setPassword(password);

        parseUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){

                    signUpCompletion.completion(parseUser, true, null);

                }else {
                    Error signUpError = new Error(e.getMessage());

                    signUpCompletion.completion(parseUser, false,signUpError);
                }
            }
        });
        return parseUser;
    }
}
