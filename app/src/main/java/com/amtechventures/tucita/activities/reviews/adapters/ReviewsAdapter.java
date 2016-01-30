package com.amtechventures.tucita.activities.reviews.adapters;


import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.model.context.user.UserContext;
import com.amtechventures.tucita.model.domain.review.Review;
import com.amtechventures.tucita.model.domain.user.User;
import com.mikhaellopez.circularimageview.CircularImageView;
import java.util.Date;
import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {

    private List<Review> reviewList;

    private OnReviewClicked listener;

    private UserContext userContext;

    private Typeface typeface;

    public interface OnReviewClicked{

        void onReviewClicked(Review review);
    }

    public ReviewsAdapter(List<Review> reviews, OnReviewClicked onReviewClicked, UserContext user, Typeface typeface) {

        listener = onReviewClicked;

        this.typeface = typeface;

        reviewList = reviews;

        this.userContext = user;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewHolder viewHolder;

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);

        viewHolder = new ViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Review review = reviewList.get(position);

        User user = review.getUser();

        holder.textName.setText(user.getName());

        String title = review.getTitle();

        if(! title.equals("")){

            holder.textTitle.setText(title);

            holder.textTitle.setVisibility(View.VISIBLE);

        }else {

            holder.textTitle.setVisibility(View.GONE);

        }

        String description = review.getDescription();

        if(! description.equals("")){

            holder.textDescription.setText(description);

            holder.textDescription.setVisibility(View.VISIBLE);

        }else {

            holder.textDescription.setVisibility(View.GONE);

        }

        holder.ratingBar.setRating(review.getRating());

        holder.textDate.setText(date(review.getUpdatedAt()));

        holder.circularImageView.setImageResource(R.mipmap.ic_user);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                listener.onReviewClicked(review);

            }

        });

    }

    private String date(Date date){

        String slash = "/";

        String formattedDate;

        int bugDate = 1900;

        formattedDate = date.getDate() + slash + (date.getMonth() + 1) + slash + (date.getYear() + bugDate);

        return formattedDate;

    }

    @Override
    public int getItemCount() {

        return reviewList.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView textName;

        protected TextView textTitle;

        protected TextView textDescription;

        protected TextView textDate;

        protected CircularImageView circularImageView;

        protected RatingBar ratingBar;

        protected LinearLayout relativeLayout;

        public ViewHolder(View itemView) {

            super(itemView);

            textName = (TextView) itemView.findViewById(R.id.textNameUser);

            textDescription = (TextView) itemView.findViewById(R.id.textDescription);

            textTitle = (TextView) itemView.findViewById(R.id.textTitle);

            textDate = (TextView) itemView.findViewById(R.id.textDate);

            textDate.setTypeface(typeface);

            textName.setTypeface(typeface, Typeface.BOLD);

            textDescription.setTypeface(typeface);

            textTitle.setTypeface(typeface, Typeface.BOLD);

            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);

            setupStars();

            relativeLayout = (LinearLayout) itemView.findViewById(R.id.container);

            circularImageView = (CircularImageView) itemView.findViewById(R.id.imageUser);

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

        }

        private void setupStars(){

            final int yellowColor = Color.argb(255, 251, 197, 70);

            LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();

            stars.getDrawable(2).setColorFilter(yellowColor, PorterDuff.Mode.SRC_ATOP);

            Drawable progress = ratingBar.getProgressDrawable();

            DrawableCompat.setTint(progress, yellowColor);

        }

    }

}
