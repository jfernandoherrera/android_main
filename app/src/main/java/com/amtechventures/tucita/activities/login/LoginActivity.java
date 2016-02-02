package com.amtechventures.tucita.activities.login;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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
import com.amtechventures.tucita.utils.common.AppFont;
import com.amtechventures.tucita.utils.views.CustomSpanTypeface;
import com.parse.ParseFacebookUtils;

public class LoginActivity extends AppCompatActivity {

    private TextView emailView;
    private EditText passwordView;
    private UserContext userContext;
    private Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        userContext = UserContext.context(userContext);

        overridePendingTransition(R.anim.login_animation_pull_in, R.anim.animation_hold);

        setContentView(R.layout.activity_login);

        AppFont appFont = new AppFont();

        typeface = appFont.getAppFont(getApplicationContext());

        emailView = (AutoCompleteTextView) findViewById(R.id.email);

        emailView.setTypeface(typeface);

        Button close = (Button) findViewById(R.id.close);

        TextView loginTitle = (TextView) findViewById(R.id.loginTitle);

        loginTitle.setText(getStringLoginModified());

        close.setBackgroundResource(R.mipmap.ic_close);

        close.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    v.setBackgroundResource(R.mipmap.ic_close_pressed);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {

                    v.setBackgroundResource(R.mipmap.ic_close);

                }

                return false;

            }
        });

        passwordView = (EditText) findViewById(R.id.password);

        passwordView.setTypeface(typeface);

        setupTitlesTypeface();

    }

    public void close(View v){

        processUnLoggedUser();

    }

    private void setupTitlesTypeface(){

        TextInputLayout textInputLayoutEmail = (TextInputLayout) findViewById(R.id.inputEmail);

        TextInputLayout textInputLayoutPass = (TextInputLayout) findViewById(R.id.inputPassword);

        TextView forgiven = (TextView) findViewById(R.id.forgiven);

        TextView newAccount = (TextView) findViewById(R.id.newAccount);

        final Button log = (Button) findViewById(R.id.email_sign_in_button);

        Button face = (Button) findViewById(R.id.facebook_button);

        textInputLayoutEmail.setTypeface(typeface);

        textInputLayoutPass.setTypeface(typeface);

        forgiven.setTypeface(typeface, Typeface.BOLD);

        newAccount.setTypeface(typeface, Typeface.BOLD);

        log.setTypeface(typeface, Typeface.BOLD);

        face.setTypeface(typeface, Typeface.BOLD);

        face.setBackgroundResource(R.drawable.cling_button_normal);

        face.setOnTouchListener(new View.OnTouchListener() {
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

        final Drawable coloredNormal = colorDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cling_button_normal));

        log.setBackgroundDrawable(coloredNormal);

        log.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    v.setBackgroundResource(R.drawable.cling_button_pressed);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {

                   v.setBackgroundDrawable(coloredNormal);

                }

                return false;

            }
        });

    }

    private Drawable colorDrawable(Drawable drawable){

        final int yellowColor = Color.argb(255, 251, 197, 70);

        drawable.setColorFilter(yellowColor, PorterDuff.Mode.SRC_ATOP);

        DrawableCompat.setTint(drawable, yellowColor);

        return drawable;
    }
    private SpannableStringBuilder getStringLoginModified() {

        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);

        DisplayMetrics metrics = new DisplayMetrics();

        wm.getDefaultDisplay().getMetrics(metrics);

        final int initialSize = 18;

        int size = (int) (initialSize * metrics.scaledDensity);

        String firstString = getResources().getString(R.string.action_sign_in_short).toUpperCase();

        String secondString = getResources().getString(R.string.or).toLowerCase();

        String thirdString = getResources().getString(R.string.action_sign_up).toUpperCase();

        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(firstString + " " + secondString + " " + thirdString);

        stringBuilder.setSpan(new CustomSpanTypeface(null, Typeface.BOLD, size, null, null, typeface), 0, firstString.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        stringBuilder.setSpan(new CustomSpanTypeface(null, Typeface.BOLD, size, ColorStateList.valueOf(Color.rgb(200, 200, 200)), null, typeface), firstString.length() + 1, firstString.length() + secondString.length() + 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        stringBuilder.setSpan(new CustomSpanTypeface(null, Typeface.BOLD, size, null, null, typeface), firstString.length() + secondString.length() + 2, firstString.length() + secondString.length() + thirdString.length() + 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        return stringBuilder;

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

            overridePendingTransition(R.anim.login_animation_pull_in, R.anim.animation_hold);

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

            finish();

            startActivity(intent);



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
