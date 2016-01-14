package com.amtechventures.tucita.activities.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.book.BookActivity;
import com.amtechventures.tucita.activities.main.MainActivity;
import com.amtechventures.tucita.activities.signup.SignUpActivity;
import com.amtechventures.tucita.model.context.user.UserCompletion;
import com.amtechventures.tucita.model.context.user.UserContext;
import com.amtechventures.tucita.model.domain.user.User;
import com.amtechventures.tucita.model.domain.user.UserAttributes;
import com.amtechventures.tucita.model.error.AppError;
import com.parse.ParseFacebookUtils;

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
            public void completion(final User user, AppError error) {

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

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){

            boolean fromBook = bundle.getBoolean(BookActivity.class.getName());

            if(fromBook) {

                finish();

            }

        } else {

            Intent intent = new Intent(this, MainActivity.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            intent.putExtra(UserAttributes.connected, true);

            startActivity(intent);

            finish();

        }

    }

    private void processUnLoggedUser() {

     Bundle bundle = getIntent().getExtras();

        if(bundle != null){

            boolean fromBook = bundle.getBoolean(BookActivity.class.getName());

            if(fromBook) {

                finish();

            }

        } else {

            Intent intent = new Intent(this, MainActivity.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            intent.putExtra(UserAttributes.connected, false);

            startActivity(intent);

            finish();

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);

    }

    public static void goToLogin(Context context) {

        Intent intent = new Intent(context, LoginActivity.class);

        context.startActivity(intent);

    }

    public void signup(View view) {

        Intent intent = new Intent(this, SignUpActivity.class);

        startActivity(intent);

        finish();

    }

    @Override
    public void onBackPressed() {

        processUnLoggedUser();

        super.onBackPressed();

    }

}
