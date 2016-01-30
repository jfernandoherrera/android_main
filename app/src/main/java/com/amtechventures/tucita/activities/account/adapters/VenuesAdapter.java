package com.amtechventures.tucita.activities.account.adapters;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.book.BookActivity;
import com.amtechventures.tucita.model.domain.review.Review;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.domain.venue.VenueAttributes;

import java.util.List;

public class VenuesAdapter extends RecyclerView.Adapter<VenuesAdapter.ViewHolder> {

    List<Venue> items;
    List<Review> reviewList;
    OnReview listener;
    String reviewBy;
    String users;
    Typeface typeface;

    public interface OnReview {

        void onReview(Venue venue, float ratingSelected);

    }

    public VenuesAdapter(List<Venue> venues, List<Review> reviewList, OnReview listener, String reviewBy, String users, Typeface typeface) {

        super();

        this.typeface = typeface;

        this.listener = listener;

        this.reviewBy = reviewBy;

        this.users = users;

        items = venues;

        this.reviewList = reviewList;

    }

    private boolean haveReview(Venue venue){

        boolean haveReview = false;

        String idVenue = venue.getObjectId();

        for(Review review : reviewList){

            String id = review.getVenue().getObjectId();

            if(id.equals(idVenue)){

                haveReview = true;

                break;

            }
        }

        return haveReview;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_venue_user, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;

    }

    private String reviews(int reviews){

        String textReviewsDone = reviewBy + " " + reviews + " " + users;

        return textReviewsDone;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Venue venue = items.get(position);

        String venueName = venue.getName();

        holder.name.setText(venueName);

        holder.setAddress(venue.getAddress());

        holder.location.setText(venue.getCity().formattedLocation());

        holder.textViewReviews.setText(reviews(venue.getReviews()));

        holder.venueImage.setImageBitmap(venue.getPicture());

        float rating = (float)venue.getRating();

        if(rating != 0) {

            holder.ratingBar.setRating(rating);

        } else{

            holder.ratingBar.setVisibility(View.INVISIBLE);

        }

        holder.ratingBarReview.setVisibility(View.VISIBLE);

        holder.ratingBarReview.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                listener.onReview(venue, rating);

            }
        });

        if(haveReview(venue)){

            holder.textReview.setVisibility(View.GONE);

            holder.ratingBarReview.setVisibility(View.GONE);

        }
    }

    @Override
    public int getItemCount() {

        return items.size();

    }


    class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView name;

        protected String address;

        protected TextView location;

        protected TextView textReview;

        protected TextView textViewReviews;

        protected RatingBar ratingBar;

        protected ImageView venueImage;

        private CardView venueIcon;

        protected RatingBar ratingBarReview;

        protected String from;

        public void setAddress(String address) {

            this.address = address;
        }

        public ViewHolder(final View itemView) {

            super(itemView);

            location = (TextView) itemView.findViewById(R.id.textLocation);

            textReview = (TextView) itemView.findViewById(R.id.textReview);

            textViewReviews = (TextView) itemView.findViewById(R.id.textViewReviews);

            name = (TextView) itemView.findViewById(R.id.textName);

            venueIcon = (CardView) itemView.findViewById(R.id.card_view);

            venueImage = (ImageView) itemView.findViewById(R.id.imageSearchVenue);

            ratingBar = (RatingBar) itemView.findViewById(R.id.searchRatingBar);

            setupStars();

            ratingBarReview = (RatingBar) itemView.findViewById(R.id.ratingBar);

            location.setTypeface(typeface);

            textReview.setTypeface(typeface);

            textViewReviews.setTypeface(typeface);

            name.setTypeface(typeface);

            if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {


                venueIcon.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        if (event.getAction() == MotionEvent.ACTION_DOWN) {

                            venueIcon.setCardElevation(0);


                        } else if (event.getAction() != MotionEvent.ACTION_MOVE) {

                            venueIcon.setCardElevation(6);

                        }

                        return false;
                    }
                });

            }

            venueIcon.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(view.getContext(), BookActivity.class);

                    intent.putExtra(Venue.class.getName(), name.getText());

                    intent.putExtra(VenueAttributes.address, address);

                    view.getContext().startActivity(intent);

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


