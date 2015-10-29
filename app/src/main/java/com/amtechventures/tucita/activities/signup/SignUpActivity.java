package com.amtechventures.tucita.activities.signup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.model.context.user.UserContext;
import com.amtechventures.tucita.utils.blocks.Completion;
import com.parse.ParseUser;

public class SignUpActivity extends AppCompatActivity {


    private UserContext userContext;

    private TextView emailView;

    private EditText passwordView;

    private EditText nameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);

        emailView = (AutoCompleteTextView) findViewById(R.id.email);

        passwordView = (EditText) findViewById(R.id.password);

        nameView = (EditText) findViewById(R.id.name);

        userContext = UserContext.context();

        Button singUpButton = (Button) findViewById(R.id.buttonSignUp);

        singUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            signUp();
            }
        });
    }

    private void signUp(){
        String email = emailView.getText().toString();

        String password = passwordView.getText().toString();

        String name = nameView.getText().toString();

        userContext.signUp(email,password, name,new Completion.BoolErrorUserCompletion() {
            @Override
            public void completion(ParseUser user, boolean ok, Error error) {
                if(ok){

                    userContext.me().setParseUser(user);

                    //goTo deberìa ir a una activity guardada
                  }else{
                  Toast errorSignUp=  Toast.makeText(SignUpActivity.this, error.getMessage(),Toast.LENGTH_SHORT);

                    errorSignUp.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);

                    errorSignUp.show();
                }
            }
        });
    }

}
