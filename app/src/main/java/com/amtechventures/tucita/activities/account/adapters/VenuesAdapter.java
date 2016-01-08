package com.amtechventures.tucita.activities.account.adapters;

import android.content.Intent;
import android.os.Build;
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
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.domain.venue.VenueAttributes;

import java.util.List;

public class VenuesAdapter extends RecyclerView.Adapter<VenuesAdapter.ViewHolder> {

    List<Venue> items;

    public VenuesAdapter(List<Venue> venues) {

        super();

        items = venues;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_venue, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Venue venue = items.get(position);

        String venueName = venue.getName();

        holder.name.setText(venueName);

        holder.setAddress(venue.getAddress());

        holder.location.setText(venue.getCity().formatedLocation());

        holder.venueImage.setImageBitmap(venue.getPicture());

        float rating = (float)venue.getRating();

        if(rating != 0) {

            holder.ratingBar.setRating(rating);

        }
        else{

            holder.ratingBar.setVisibility(View.INVISIBLE);
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

            protected RatingBar ratingBar;

            protected ImageView venueImage;

            private CardView venueIcon;

            protected String from;

            public void setAddress(String address) {

                this.address = address;
            }

            public ViewHolder(final View itemView) {

                super(itemView);

                location = (TextView) itemView.findViewById(R.id.textLocation);

                name = (TextView) itemView.findViewById(R.id.textName);

                venueIcon = (CardView) itemView.findViewById(R.id.card_view);

                venueImage = (ImageView) itemView.findViewById(R.id.imageSearchVenue);

                ratingBar = (RatingBar) itemView.findViewById(R.id.searchRatingBar);


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

        }
    }


