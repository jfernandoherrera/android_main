package com.amtechventures.tucita.activities.login;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.main.CategoryActivity;
import com.amtechventures.tucita.model.tucita.context.user.FacebookContext;
import com.amtechventures.tucita.model.tucita.context.user.UserContext;

import com.amtechventures.tucita.utils.blocks.Completion;

import java.util.ArrayList;
import java.util.List;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {
    private FacebookContext facebookContext = new FacebookContext();

    private EditText PasswordView;
    private TextView EmailView;

    private UserContext userContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        userContext = UserContext.context(this, userContext);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        EmailView = (AutoCompleteTextView) findViewById(R.id.email);

        PasswordView = (EditText) findViewById(R.id.password);

        TextView t2 = (TextView) findViewById(R.id.textViewxd);

        TextView t1 = (TextView) findViewById(R.id.textView2xd);
        t1.setMovementMethod(LinkMovementMethod.getInstance());

        t2.setMovementMethod(LinkMovementMethod.getInstance());

        PasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {

              /*  if (id == R.id.|| id == EditorInfo.IME_NULL) {

                    attemptLogin();

                    return true;
                }*/
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);

        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });


    }

public void loggin(View view){
    Log.i("log","nice");

}
    private void attemptLogin() {

        facebookContext.login(this, new Completion.BoolBoolCompletion() {

                    @Override
                    public void completion(boolean logged, boolean cancelled) {

                        if (logged) {

                            processLoggedUser();

                        } else if (cancelled) {

                            Toast.makeText(LoginActivity.this, R.string.cancel, Toast.LENGTH_SHORT).show();

                        } else

                        {

                            Toast.makeText(LoginActivity.this, R.string.error, Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                        return null;
                    }

                    @Override
                    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {

                    }

                    @Override
                    public void onLoaderReset(Loader<Cursor> cursorLoader) {

                    }
                }
        );}


    private void processLoggedUser() {

        userContext.updateMe();

        Intent i = new Intent(LoginActivity.this, CategoryActivity.class);

        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(i);

        finish();

    }
    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

}

