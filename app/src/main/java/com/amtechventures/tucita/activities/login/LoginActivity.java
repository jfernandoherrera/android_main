package com.amtechventures.tucita.activities.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.text.method.LinkMovementMethod;
import android.support.v7.app.AppCompatActivity;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.model.error.AppError;
import com.amtechventures.tucita.model.domain.user.User;
import com.amtechventures.tucita.model.context.user.UserContext;
import com.amtechventures.tucita.activities.signup.SignUpActivity;
import com.amtechventures.tucita.model.context.user.UserCompletion;
import com.amtechventures.tucita.activities.category.CategoryActivity;

public class LoginActivity extends AppCompatActivity {

    private TextView emailView;
    private EditText passwordView;
    private UserContext userContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        userContext = UserContext.context(userContext);

        setContentView(R.layout.activity_login);

        emailView = (AutoCompleteTextView) findViewById(R.id.email);

        passwordView = (EditText) findViewById(R.id.password);

        TextView t2 = (TextView) findViewById(R.id.newAccount);

       //this code don't works
        t2.setMovementMethod(LinkMovementMethod.getInstance());

        passwordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {

                return false;

            }

        });

        Button emailSignInButton = (Button) findViewById(R.id.email_sign_in_button);

        Button facebookButton = (Button)findViewById(R.id.facebook_button);

        emailSignInButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {

                login();

            }

        });

        facebookButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {}

        });

    }

    private void login() {

        String email = emailView.getText().toString();

        String password = passwordView.getText().toString();

        userContext.login(email, password, new UserCompletion.UserErrorCompletion() {

            @Override
            public void completion(User user, AppError error) {

                if (error != null) {

                    Toast errorLogin = Toast.makeText(LoginActivity.this, R.string.error, Toast.LENGTH_SHORT);

                    errorLogin.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);

                    errorLogin.show();

                }else {

                    processLoggedUser();

                }

            }

        });

    }

    private void processLoggedUser() {

        Intent intent = new Intent(this, CategoryActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);

        finish();

    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

    }

    public void signup(View view) {

        Intent intent = new Intent(this, SignUpActivity.class);

        startActivity(intent);

        finish();

    }

}
