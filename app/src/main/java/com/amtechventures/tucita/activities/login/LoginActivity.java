package com.amtechventures.tucita.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
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
import com.amtechventures.tucita.activities.signup.SignUpActivity;
import com.amtechventures.tucita.model.context.facebook.FacebookContext;
import com.amtechventures.tucita.model.context.user.UserContext;
import com.amtechventures.tucita.utils.blocks.Completion;
import com.amtechventures.tucita.utils.strings.Strings;
import com.parse.ParseUser;


public class LoginActivity extends AppCompatActivity  {

    private TextView emailView;

    private EditText passwordView;

    private UserContext userContext;

    FacebookContext facebookContext ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        userContext = UserContext.context(this);

        facebookContext = FacebookContext.context(null);

        setContentView(R.layout.activity_login);

        emailView = (AutoCompleteTextView) findViewById(R.id.email);

        passwordView = (EditText) findViewById(R.id.password);

        TextView t2 = (TextView) findViewById(R.id.newAccount);

        t2.setMovementMethod(LinkMovementMethod.getInstance());

        passwordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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

                attemptLogin();
            }
        });
        facebookButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                attemptLogin();
            }
        });

    }
    private void attemptLogin() {
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();
        userContext.login(email, password, new Completion.BoolBoolUserCompletion() {

                    @Override
                    public void completion(ParseUser user, boolean logged, boolean cancelled) {

                        if (logged) {

                            userContext.me().setParseUser(user);
                            processLoggedUser();

                        } else if (cancelled) {
                            Toast errorLogin=  Toast.makeText(LoginActivity.this, R.string.cancel,Toast.LENGTH_SHORT);

                            errorLogin.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);

                            errorLogin.show();

                        } else {
                            Toast errorLogin=  Toast.makeText(LoginActivity.this,  R.string.error,Toast.LENGTH_SHORT);

                            errorLogin.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);

                            errorLogin.show();


                        }

                    }

                }

        );
    }


    private void processLoggedUser() {

        userContext.setAuthenticationType(Strings.AUTHENTICATED);

        Intent intent = new Intent(this, CategoryActivity.class);

        String authenticated = userContext.getAuthenticationType();

        intent.putExtra(authenticated, true);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);

        finish();

    }
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        facebookContext.getCallbackManager().onActivityResult(requestCode, resultCode, data);

    }


    public void goToSignUp(View view){

        Intent intent = new Intent(this, SignUpActivity.class);

        startActivity(intent);

        finish();
    }
}


