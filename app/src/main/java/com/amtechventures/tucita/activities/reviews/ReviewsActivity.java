package com.amtechventures.tucita.activities.reviews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.amtechventures.tucita.R;

public class ReviewsActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reviews);

        setToolbar();

    }


    public static void goToReviews(Context context) {

        Intent intent = new Intent(context, ReviewsActivity.class);

        context.startActivity(intent);

    }

    private void setToolbar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {

            setSupportActionBar(toolbar);

        }

    }

}
