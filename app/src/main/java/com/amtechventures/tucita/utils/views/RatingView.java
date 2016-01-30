package com.amtechventures.tucita.utils.views;


import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amtechventures.tucita.R;

public class RatingView extends RelativeLayout{

    TextView means;
    RatingBar ratingBar;

    public RatingView(Context context, AttributeSet attrs) {

        super(context, attrs);

        init(context);
    }

    private void init(Context context) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.rating_view, this);

        means = (TextView) findViewById(R.id.means);

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        ratingBar.setStepSize(1);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                int ratingInt = (int) rating;

                if (rating < 1) {

                    ratingBar.setRating(1);

                    ratingInt = 1;

                }

                switch (ratingInt) {

                    case 1:

                        means.setText(R.string.i_hate_it);

                        break;

                    case 2:

                        means.setText(R.string.i_dont_like_it);

                        break;

                    case 3:

                        means.setText(R.string.ok);

                        break;

                    case 4:

                        means.setText(R.string.i_like_it);

                        break;

                    case 5:

                        means.setText(R.string.i_loved_it);

                        break;

                }
            }
        });

    }

    public void setTypeface(Typeface typeface) {

        means.setTypeface(typeface);

    }

    public void setRating(float rating){

        ratingBar.setRating(rating);

    }

    public int getRating(){

        return (int) ratingBar.getRating();

    }

    }


