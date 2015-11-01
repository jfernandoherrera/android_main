package com.amtechventures.tucita.activities.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.view.Gravity;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseFacebookUtils;

import android.widget.AutoCompleteTextView;
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

        passwordView = (EditText)findViewById(R.id.password);

    }

    public void login(View view) {

        String email = emailView.getText().toString();

        String password = passwordView.getText().toString();

        userContext.login(email, password, new UserCompletion.UserErrorCompletion() {

            @Override
            public void completion(User user, AppError error) {

                if (error != null) {

                    Toast errorLogin = Toast.makeText(LoginActivity.this, R.string.error, Toast.LENGTH_SHORT);

                    errorLogin.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);

                    errorLogin.show();

                } else {

                    processLoggedUser();

                }

            }

        });

    }

    public void loginWithFacebook(View view) {

        userContext.loginWithFacebook(this, new UserCompletion.UserErrorCompletion() {

            @Override
            public void completion(User user, AppError error) {

                if (error != null) {

                    Toast errorLogin = Toast.makeText(LoginActivity.this, R.string.error, Toast.LENGTH_SHORT);

                    errorLogin.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);

                    errorLogin.show();

                } else {

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);

    }

    public void signup(View view) {

        Intent intent = new Intent(this, SignUpActivity.class);

        startActivity(intent);

        finish();

    }

}
