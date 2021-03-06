package com.amtechventures.tucita.activities.advanced.adapters;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
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
import com.amtechventures.tucita.model.domain.service.ServiceAttributes;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.domain.venue.VenueAttributes;

import java.util.List;

public class AdvancedSearchAdapter extends RecyclerView.Adapter<AdvancedSearchAdapter.ViewHolder> {

    List<Venue> items;

    List<String> priceStrings;

    String subCategory;

    Typeface typeface;

    public AdvancedSearchAdapter(List<Venue> venues, List<String> priceStrings, String subCategory, Typeface typeface) {

        super();

        items = venues;

        this.typeface = typeface;

        this.priceStrings = priceStrings;

        this.subCategory = subCategory;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_venue, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        Venue venue = items.get(position);

        viewHolder.name.setText(venue.getName());

        viewHolder.setAddress(venue.getAddress());

        viewHolder.location.setText(venue.getCity().formattedLocation());

        viewHolder.venueImage.setImageBitmap(venue.getPicture());

        float rating = (float) venue.getRating();

        if (rating != 0) {

            viewHolder.ratingBar.setVisibility(View.VISIBLE);

            viewHolder.ratingBar.setRating(rating);

        } else {

            viewHolder.ratingBar.setVisibility(View.GONE);

        }

        String from = viewHolder.from + " $" + priceStrings.get(position);

        viewHolder.pricesFrom.setText(from);

    }

    @Override
    public int getItemCount() {

        return items.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView name;

        protected String address;

        protected TextView location;

        protected RatingBar ratingBar;

        protected ImageView venueImage;

        private CardView categoryIcon;

        protected TextView pricesFrom;

        protected String from;

        public void setAddress(String address) {

            this.address = address;

        }

        private void setupStars(){

            final int yellowColor = Color.argb(255, 251, 197, 70);

            LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();

            stars.getDrawable(2).setColorFilter(yellowColor, PorterDuff.Mode.SRC_ATOP);

            Drawable progress = ratingBar.getProgressDrawable();

            DrawableCompat.setTint(progress, yellowColor);

        }

        public ViewHolder(final View itemView) {

            super(itemView);

            location = (TextView) itemView.findViewById(R.id.textLocation);

            name = (TextView) itemView.findViewById(R.id.textName);

            categoryIcon = (CardView) itemView.findViewById(R.id.card_view);

            venueImage = (ImageView) itemView.findViewById(R.id.imageSearchVenue);

            ratingBar = (RatingBar) itemView.findViewById(R.id.searchRatingBar);

            setupStars();

            pricesFrom = (TextView) itemView.findViewById(R.id.textPricesFrom);

            pricesFrom.setTypeface(typeface);

            name.setTypeface(typeface);

            location.setTypeface(typeface);

            from = itemView.getContext().getString(R.string.from);

            if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

                categoryIcon.setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        if (event.getAction() == MotionEvent.ACTION_DOWN) {

                            categoryIcon.setCardElevation(0);


                        } else if (event.getAction() != MotionEvent.ACTION_MOVE) {

                            categoryIcon.setCardElevation(6);

                        }

                        return false;
                    }

                });

            }

            categoryIcon.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(view.getContext(), BookActivity.class);

                    intent.putExtra(Venue.class.getName(), name.getText());

                    intent.putExtra(VenueAttributes.address, address);

                    intent.putExtra(ServiceAttributes.subCategory, subCategory);

                    view.getContext().startActivity(intent);

                }

            });

        }

    }

}