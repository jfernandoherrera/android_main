package com.amtechventures.tucita.utils.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.model.domain.review.Review;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.Date;

public class UserReviewView extends RelativeLayout{


    protected TextView textName;

    protected TextView textTitle;

    protected TextView textDescription;

    protected TextView textDate;

    protected CircularImageView circularImageView;

    protected RatingBar ratingBar;

    protected LinearLayout relativeLayout;

    private Button edit;

    private Review review;

    private OnEdit onEdit;

    public interface OnEdit{

        void onEdit(Review review);

    }

    public UserReviewView(Context context, AttributeSet attrs) {

        super(context, attrs);

        init(context);

        onEdit = (OnEdit) context;

    }

    private void init(Context context) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.item_review_user, this);

        textName = (TextView) findViewById(R.id.textNameUser);

        textDescription = (TextView) findViewById(R.id.textDescription);

        textTitle = (TextView) findViewById(R.id.textTitle);

        textDate = (TextView) findViewById(R.id.textDate);

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        relativeLayout = (LinearLayout) findViewById(R.id.container);

        circularImageView = (CircularImageView) findViewById(R.id.imageUser);

        edit = (Button) findViewById(R.id.edit);

        edit.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    v.setBackgroundResource(R.drawable.pressed_application_background_static);

                } else if (event.getAction() != MotionEvent.ACTION_MOVE) {

                    v.setBackgroundColor(Color.TRANSPARENT);

                }

                return false;

            }
        });

        edit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

            onEdit.onEdit(review);

            }

        });

        relativeLayout.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    v.setBackgroundResource(R.drawable.pressed_application_background_static);

                } else if (event.getAction() != MotionEvent.ACTION_MOVE) {

                    v.setBackgroundColor(Color.TRANSPARENT);

                }

                return false;

            }
        });

        relativeLayout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {



            }

        });

    }

    public void setReview(Review review) {

        this.review = review;

        setTextTitle(review.getTitle());

        setRating(review.getRating());

        setTextName(review.getUser().getName());

        setTextDate(date(review.getUpdatedAt()));

        setTextDescription(review.getDescription());

    }

    private String date(Date date){

        String slash = "/";

        String formattedDate;

        int bugDate = 1900;

        formattedDate = date.getDate() + slash + (date.getMonth() + 1) + slash + (date.getYear() + bugDate);

        return formattedDate;

    }

    public void setTextDescription(String textDescription) {

        if(! textDescription.equals("")){

            this.textDescription.setText(textDescription);

            this.textDescription.setVisibility(View.VISIBLE);

        }else {

            this.textDescription.setVisibility(View.GONE);

        }

    }

    public void setTextTitle(String textTitle) {

        if(! textTitle.equals("")){

            this.textTitle.setText(textTitle);

            this.textTitle.setVisibility(View.VISIBLE);

        }else {

            this.textTitle.setVisibility(View.GONE);

        }

    }

    public void setRating(float rating) {

        this.ratingBar.setRating(rating);

    }

    public void setTextDate(String textDate) {

        this.textDate.setText(textDate);

    }

    public void setTextName(String textName) {

        this.textName.setText(textName);

    }

    public void setImageUser(Bitmap imageUser){

        circularImageView.setImageBitmap(imageUser);

    }
}
