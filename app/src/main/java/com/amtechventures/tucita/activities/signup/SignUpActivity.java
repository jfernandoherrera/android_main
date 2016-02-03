package com.amtechventures.tucita.activities.signup;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.main.MainActivity;
import com.amtechventures.tucita.model.context.user.UserCompletion;
import com.amtechventures.tucita.model.context.user.UserContext;
import com.amtechventures.tucita.model.domain.user.User;
import com.amtechventures.tucita.model.error.AppError;
import com.amtechventures.tucita.utils.common.AppFont;

public class SignUpActivity extends AppCompatActivity {

    private TextView emailView;
    private TextView nameView;
    private EditText passwordView;
    private UserContext userContext;
    private Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);

        userContext = UserContext.context(userContext);

        passwordView = (EditText) findViewById(R.id.password);

        emailView = (AutoCompleteTextView) findViewById(R.id.email);

        nameView = (TextView) findViewById(R.id.name);

        Button signUpButton = (Button) findViewById(R.id.buttonSignUp);

        AppFont font = new AppFont();

        typeface = font.getAppFont(getApplicationContext());

        emailView.setTypeface(typeface);

        passwordView.setTypeface(typeface);

        signUpButton.setTypeface(typeface, Typeface.BOLD);

        nameView.setTypeface(typeface);

        signUpButton.setBackgroundResource(R.drawable.cling_button_normal);

        signUpButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    v.setBackgroundResource(R.drawable.cling_button_pressed);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {

                    v.setBackgroundResource(R.drawable.cling_button_normal);

                }

                return false;

            }
        });


        setupTitlesTypeface();

        signUpButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                signup();

            }

        });

    }

    private void setupTitlesTypeface(){

        TextInputLayout textInputLayoutEmail = (TextInputLayout) findViewById(R.id.inputEmail);

        TextInputLayout textInputLayoutPassword = (TextInputLayout) findViewById(R.id.inputPassword);

        TextInputLayout textInputLayoutName = (TextInputLayout) findViewById(R.id.inputName);

        textInputLayoutEmail.setTypeface(typeface);

        textInputLayoutPassword.setTypeface(typeface);

        textInputLayoutName.setTypeface(typeface);


    }

    private void signup() {

        String email = emailView.getText().toString();

        String password = passwordView.getText().toString();

        String name = nameView.getText().toString();

        userContext.signup(email, password, name, new UserCompletion.UserErrorCompletion() {

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

        MainActivity.goToCategories(getApplicationContext());

        finish();

    }

    private void processBack() {

        MainActivity.goToCategoriesFromLogout(getApplicationContext());

        finish();
    }

    @Override
    public void onBackPressed() {

        processBack();

    }
}
