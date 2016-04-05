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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.book.BookActivity;
import com.amtechventures.tucita.model.domain.appointment.Appointment;
import com.amtechventures.tucita.model.domain.appointmentVenue.AppointmentVenue;
import com.amtechventures.tucita.model.domain.review.Review;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.domain.venue.VenueAttributes;

import java.util.List;

public class VenuesAdapter extends RecyclerView.Adapter<VenuesAdapter.ViewHolder> {

    List<AppointmentVenue> items;
    OnReview listener;
    String reviewBy;
    String users;

    public interface OnReview {

        void onReview(Venue venue, float ratingSelected);

    }

    public VenuesAdapter(List<AppointmentVenue> venues, OnReview listener, String reviewBy, String users) {

        super();

        this.listener = listener;

        this.reviewBy = reviewBy;

        this.users = users;

        items = venues;

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

        final AppointmentVenue appointmentVenue = items.get(position);

        final Venue venue = appointmentVenue.getVenue();

        final String venueName = venue.getName();

        holder.name.setText(venueName);

        holder.setAddress(venue.getAddress());

        holder.location.setText(venue.getCity().formattedLocation());

        holder.textViewReviews.setText(reviews(venue.getReviews()));

        holder.venueImage.setImageBitmap(venue.getPicture());

        final float rating = (float)venue.getRating();

        if(rating != 0) {

            holder.ratingBar.setRating(rating);

        } else{

            holder.ratingBar.setVisibility(View.INVISIBLE);

            holder.textViewReviews.setVisibility(View.INVISIBLE);

        }

        holder.rating.setVisibility(View.VISIBLE);

        holder.rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.onReview(venue, rating);

            }
        });

        if(appointmentVenue.getRanked()){

            holder.withReview.setBackgroundDrawable(null);

            holder.withoutReview.setBackgroundResource(R.drawable.bg_bottom_results);

            holder.rating.setVisibility(View.GONE);

        }else {

            holder.withReview.setBackgroundResource(R.drawable.bg_bottom_results);

            holder.withoutReview.setBackgroundColor(Color.TRANSPARENT);

            holder.rating.setVisibility(View.VISIBLE);

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

        protected TextView textViewReviews;

        protected RatingBar ratingBar;

        protected ImageView venueImage;

        private CardView venueIcon;

        private TextView rating;

        protected String from;

        protected RelativeLayout withReview;

        protected RelativeLayout withoutReview;

        public void setAddress(String address) {

            this.address = address;
        }

        public ViewHolder(final View itemView) {

            super(itemView);

            location = (TextView) itemView.findViewById(R.id.textLocation);

            textViewReviews = (TextView) itemView.findViewById(R.id.textViewReviews);

            name = (TextView) itemView.findViewById(R.id.textName);

            venueIcon = (CardView) itemView.findViewById(R.id.card_view);

            venueImage = (ImageView) itemView.findViewById(R.id.imageSearchVenue);

            ratingBar = (RatingBar) itemView.findViewById(R.id.searchRatingBar);

            rating = (TextView) itemView.findViewById(R.id.ratingButton);

            withoutReview = (RelativeLayout) itemView.findViewById(R.id.withoutReview);

            withReview = (RelativeLayout) itemView.findViewById(R.id.withReview);

            setupStars();

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


