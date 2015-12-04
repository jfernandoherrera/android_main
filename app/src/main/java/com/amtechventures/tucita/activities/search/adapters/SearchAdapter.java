package com.amtechventures.tucita.activities.search.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.model.domain.subcategory.SubCategory;
import com.amtechventures.tucita.model.domain.venue.Venue;
import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SubCategory> subCategories = new ArrayList<>();
    private List <Venue> venues = new ArrayList<>();
    OnItemClickListener mItemClickListener;
    private final int typeSubCategory = 0;
    private final int typeVenue = 1;
    public static final int typeSection = 2;
    private Context context;

    public interface OnItemClickListener {

       void onItemClick(View view , int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {

        this.mItemClickListener = mItemClickListener;
    }

    public SearchAdapter(List <Venue> venues,List<SubCategory> subCategories, Context context){

        this.venues = venues;

        this.subCategories = subCategories;

        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {

        int type;

        boolean subCategoriesCheck = !subCategories.isEmpty() && position == 0;

        boolean venuesCheck = !venues.isEmpty() && position == 0;

        boolean venuesWithSubCategoriesCheck = position == subCategories.size() + 1;

        if(subCategoriesCheck || venuesWithSubCategoriesCheck || venuesCheck){

            type = typeSection;

        } else{

            type = position <= subCategories.size() ? typeSubCategory : typeVenue;
        }

        return type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v;

        RecyclerView.ViewHolder viewHolder;

        if(viewType == typeSection){

            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.section, viewGroup, false);

            viewHolder = new ViewHolderSections(v);

        }else if(viewType == typeSubCategory){

            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);

            viewHolder = new ViewHolderSubCategories(v);
        }else {

            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.simplified_item_venue, viewGroup, false);

            viewHolder = new ViewHolderVenues(v);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        boolean venuesEmpty = ! venues.isEmpty();

        boolean subCategoriesEmpty = ! subCategories.isEmpty();

        boolean venueCheck = (subCategoriesEmpty && position == subCategories.size() + 1) || position == 0;

        if(position == 0 && viewHolder instanceof ViewHolderSections && ! subCategories.isEmpty()){

                ViewHolderSections sections = (ViewHolderSections) viewHolder;

                sections.name.setText(context.getString(R.string.services));

            }else if(venuesEmpty && venueCheck){

                ViewHolderSections sections = (ViewHolderSections) viewHolder;

                sections.name.setText(context.getString(R.string.venues));

                sections.name.setGravity(0x50);

            }   else if (subCategoriesEmpty && viewHolder instanceof ViewHolderSubCategories) {

            SubCategory subCategory = subCategories.get(position - 1);

            ViewHolderSubCategories subCategories = (ViewHolderSubCategories) viewHolder;

            subCategories.name.setText(subCategory.getName());

        }else if(venuesEmpty){

            Venue venue = venues.get(position - subCategories.size() - typeSection);

            ViewHolderVenues venues = (ViewHolderVenues) viewHolder;

            venues.name.setText(venue.getName());

            venues.location.setText(venue.getCity().formatedLocation());

        }
    }

    @Override
    public int getItemCount() {

        int  size = subCategories.size() + venues.size() + typeSection;

        return size;
    }

    class ViewHolderSections extends RecyclerView.ViewHolder{

        protected TextView name;

        public ViewHolderSections(View itemView) {

            super(itemView);

            name = (TextView) itemView.findViewById(R.id.text);
        }
    }

    class ViewHolderVenues extends RecyclerView.ViewHolder implements View.OnClickListener{

        protected TextView name;

        protected TextView location;

        public ViewHolderVenues(final View itemView) {

            super(itemView);

            name = (TextView) itemView.findViewById(R.id.textName);

            location = (TextView) itemView.findViewById(R.id.textLocation);


            itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {

                        itemView.setBackgroundResource(R.drawable.pressed_application_background_static);

                    } else if(event.getAction() != MotionEvent.ACTION_MOVE){

                        itemView.setBackgroundColor(Color.TRANSPARENT);

                    }

                    return false;
                }
            });

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        mItemClickListener.onItemClick(v, getAdapterPosition());
        }

    }

    class ViewHolderSubCategories extends RecyclerView.ViewHolder implements View.OnClickListener{

        protected TextView name;

        public ViewHolderSubCategories(final View itemView) {

            super(itemView);

            name = (TextView) itemView.findViewById(R.id.textlist);

            itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {

                        itemView.setBackgroundResource(R.drawable.pressed_application_background_static);

                    } else if(event.getAction() != MotionEvent.ACTION_MOVE){

                        itemView.setBackgroundColor(Color.TRANSPARENT);

                    }

                        return false;
                }
            });

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            mItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }
}