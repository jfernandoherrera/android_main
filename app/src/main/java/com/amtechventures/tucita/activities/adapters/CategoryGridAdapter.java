package com.amtechventures.tucita.activities.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.model.category.Category;
import java.util.ArrayList;
import java.util.List;

public class CategoryGridAdapter extends RecyclerView.Adapter<CategoryGridAdapter.ViewHolder> {

    List<Category> mItems;

    public CategoryGridAdapter(ArrayList<Category> offer) {
        super();

        mItems = offer;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_category, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        Category category = mItems.get(i);

        viewHolder.category.setText(category.getName());

    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{


        public TextView category;

        private ImageButton categoryIcon;

        public ViewHolder(final View itemView) {


            super(itemView);

            categoryIcon=(ImageButton) itemView.findViewById(R.id.imageb);

            category = (TextView )itemView.findViewById(R.id.textView2);

            categoryIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                }
            });
        }
    }

}