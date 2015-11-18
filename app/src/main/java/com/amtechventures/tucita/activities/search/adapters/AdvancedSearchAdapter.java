package com.amtechventures.tucita.activities.search.adapters;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.venue.VenueActivity;
import com.amtechventures.tucita.model.context.service.ServiceContext;
import com.amtechventures.tucita.model.domain.subcategory.SubCategory;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.domain.venue.VenueAttributes;
import com.amtechventures.tucita.model.error.AppError;
import com.amtechventures.tucita.utils.blocks.Completion;

import java.util.List;

public class AdvancedSearchAdapter extends RecyclerView.Adapter<AdvancedSearchAdapter.ViewHolder> {

    List<Venue> items;

    List<String> priceStrings;

    public AdvancedSearchAdapter(List<Venue> offer, List<String> priceStrings) {

        super();

        items = offer;

        this.priceStrings = priceStrings;

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

        viewHolder.location.setText(venue.getCity().formatedLocation());

        viewHolder.venueImage.setImageBitmap(venue.getPicture());

            float rating = (float)venue.getRating();

            if(rating != 0) {

                viewHolder.ratingBar.setRating(rating);

            }
            else{

                viewHolder.ratingBar.setVisibility(View.INVISIBLE);
            }

        viewHolder.pricesFrom.setText(priceStrings.get(position));
    }

    @Override
    public int getItemCount() {

        return items.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder{

        protected TextView name;

        protected String address;

        protected TextView location;

        protected RatingBar ratingBar;

        protected ImageView venueImage;

        private CardView categoryIcon;

        protected TextView pricesFrom;

        public void setAddress(String address){

            this.address = address;
        }

        public ViewHolder(final View itemView) {

            super(itemView);

            location = (TextView)itemView.findViewById(R.id.textLocation);

            name = (TextView)itemView.findViewById(R.id.textName);

            categoryIcon = (CardView)itemView.findViewById(R.id.card_view);

            venueImage = (ImageView) itemView.findViewById(R.id.imageSearchVenue);

            ratingBar = (RatingBar) itemView.findViewById(R.id.searchRatingBar);

            pricesFrom = (TextView) itemView.findViewById(R.id.textPricesFrom);

            categoryIcon.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(view.getContext() , VenueActivity.class);

                    intent.putExtra(Venue.class.getName(),name.getText());

                    intent.putExtra(VenueAttributes.address, address);

                    view.getContext().startActivity(intent);

                }

            });

        }

    }

}