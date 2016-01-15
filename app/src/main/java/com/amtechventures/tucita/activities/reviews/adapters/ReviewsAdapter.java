package com.amtechventures.tucita.activities.reviews.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.model.domain.review.Review;
import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {

    private List<Review> reviewList;

    private OnReviewClicked listener;

    public interface OnReviewClicked{

        void onReviewClicked(Review review);
    }

    public ReviewsAdapter(List<Review> services, OnReviewClicked onItemClosed) {

        listener = onItemClosed;

        reviewList = services;

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

        holder.textName.setText(review.getTitle());

        holder.textDescription.setText(review.getDescription());

        holder.textTitle.setText(String.valueOf(review.getTitle()));

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                listener.onReviewClicked(review);

            }

        });

    }

    @Override
    public int getItemCount() {

        return reviewList.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView textName;

        protected TextView textTitle;

        protected TextView textDescription;

        protected RatingBar ratingBar;

        protected ImageView user;

        protected RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {

            super(itemView);

            textName = (TextView) itemView.findViewById(R.id.textName);

            textDescription = (TextView) itemView.findViewById(R.id.textDescription);

            textTitle = (TextView) itemView.findViewById(R.id.textTitle);

            user = (ImageView) itemView.findViewById(R.id.image);

            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.container);

            relativeLayout.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {

                        v.setBackgroundResource(R.mipmap.ic_close_pressed);

                    } else if (event.getAction() != MotionEvent.ACTION_MOVE) {

                        v.setBackgroundResource(R.mipmap.ic_close);

                    }

                    return false;

                }
            });

        }

    }

}
