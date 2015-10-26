package com.amtechventures.tucita.activities.login;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.category.CategoryActivity;
import com.amtechventures.tucita.model.context.facebook.FacebookContext;
import com.amtechventures.tucita.model.context.user.UserContext;

import com.amtechventures.tucita.utils.blocks.Completion;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity  {

    private TextView EmailView;

    private EditText PasswordView;

    private UserContext userContext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        userContext = UserContext.context(this, userContext);

        setContentView(R.layout.activity_login);

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

        Button facebookButton = (Button) findViewById(R.id.facebook_button);

        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        facebookButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

    }





    public void loggin(View view) {

        Log.i("log", "nice");
        // processLoggedUser();
    }

    private void attemptLogin() {

        userContext.login(this, new Completion.BoolBoolCompletion() {

                    @Override
                    public void completion(boolean logged, boolean cancelled) {

                        if (logged) {

                            processLoggedUser();

                        } else if (cancelled) {

                            Toast.makeText(LoginActivity.this, R.string.cancel, Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(LoginActivity.this, R.string.error, Toast.LENGTH_SHORT).show();

                        }

                    }

                }

        );
    }


    private void processLoggedUser() {

        userContext.updateMe();

        Intent intent = new Intent(this, CategoryActivity.class);
        intent.putExtra("authenticated", true);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);

        finish();

    }

    private boolean isEmailValid(String email) {

        return email.contains("@");

    }

    private boolean isPasswordValid(String password) {

        return password.length() > 4;

    }
}


