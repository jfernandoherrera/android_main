package com.amtechventures.tucita.activities.signup;

import android.os.Bundle;
import android.view.View;
import android.view.Gravity;
import android.widget.Toast;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import com.amtechventures.tucita.R;
import android.widget.AutoCompleteTextView;
import android.support.v7.app.AppCompatActivity;

import com.amtechventures.tucita.model.error.AppError;
import com.amtechventures.tucita.model.domain.user.User;
import com.amtechventures.tucita.model.context.user.UserContext;
import com.amtechventures.tucita.model.context.user.UserCompletion;
import com.amtechventures.tucita.activities.category.CategoryFragment;

public class SignUpActivity extends AppCompatActivity {

    private TextView emailView;
    private EditText passwordView;
    private UserContext userContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);

        userContext = UserContext.context(userContext);

        passwordView = (EditText)findViewById(R.id.password);

        emailView = (AutoCompleteTextView)findViewById(R.id.email);

        Button singUpButton = (Button)findViewById(R.id.buttonSignUp);

        singUpButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                signup();

            }

        });

    }

    private void signup() {

        String email = emailView.getText().toString();

        String password = passwordView.getText().toString();

        userContext.signup(email, password, new UserCompletion.UserErrorCompletion() {

            @Override
            public void completion(User user, AppError error) {

                if (error != null) {

                    Toast errorSignUp = Toast.makeText(SignUpActivity.this, R.string.error, Toast.LENGTH_SHORT);

                    errorSignUp.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);

                    errorSignUp.show();

                } else {

                    processLoggedUser();

                }

            }

        });

    }

    private void processLoggedUser() {

        Intent intent = new Intent(this, CategoryFragment.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);

        finish();

    }

}
