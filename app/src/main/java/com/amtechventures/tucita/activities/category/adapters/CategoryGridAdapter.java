package com.amtechventures.tucita.activities.category.adapters;

import java.util.List;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageButton;
import android.view.LayoutInflater;
import com.amtechventures.tucita.R;
import android.support.v7.widget.RecyclerView;
import com.amtechventures.tucita.activities.services.ServicesActivity;
import com.amtechventures.tucita.model.domain.category.Category;

public class CategoryGridAdapter extends RecyclerView.Adapter<CategoryGridAdapter.ViewHolder> {

    List<Category> items;

    public CategoryGridAdapter(List<Category> offer) {

        super();

        items = offer;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_category, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        Category category = items.get(i);

        viewHolder.category.setText(category.getName());

    }

    @Override
    public int getItemCount() {

        return items.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder{

        protected TextView category;

        private ImageButton categoryIcon;

        public ViewHolder(final View itemView) {

            super(itemView);

            category = (TextView)itemView.findViewById(R.id.textCategory);

            categoryIcon = (ImageButton)itemView.findViewById(R.id.imageButtonCategory);

            categoryIcon.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(view.getContext() , ServicesActivity.class);

                    intent.putExtra(Category.class.getName(),category.getText());

                    view.getContext().startActivity(intent);

                }

            });

        }

    }

}