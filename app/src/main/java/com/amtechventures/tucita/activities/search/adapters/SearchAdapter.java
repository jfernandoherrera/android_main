package com.amtechventures.tucita.activities.search.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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

        if(position == 0 || position == subCategories.size() + 1){

            type = typeSection;

        }else{

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

        if(position == 0 && subCategories.size() != 0  ){

                ViewHolderSections sections = (ViewHolderSections) viewHolder;

                sections.name.setText(context.getString(R.string.services));

            }else if(venues.size() != 0 && position == subCategories.size() + 1){

                ViewHolderSections sections = (ViewHolderSections) viewHolder;

                sections.name.setText(context.getString(R.string.venues));

                sections.name.setGravity(0x50);

            }   else if (position <= subCategories.size()) {

            SubCategory subCategory = subCategories.get(position - 1);

            ViewHolderSubCategories subCategories = (ViewHolderSubCategories) viewHolder;

            subCategories.name.setText(subCategory.getName());

        }else{

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

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            mItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }
}