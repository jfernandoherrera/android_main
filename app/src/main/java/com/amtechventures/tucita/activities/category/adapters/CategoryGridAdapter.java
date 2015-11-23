package com.amtechventures.tucita.activities.category.adapters;

import java.util.List;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.view.LayoutInflater;
import com.amtechventures.tucita.R;
import android.support.v7.widget.RecyclerView;
import com.amtechventures.tucita.activities.subcategory.SubCategoryActivity;
import com.amtechventures.tucita.model.context.category.CategoryContext;
import com.amtechventures.tucita.model.domain.category.Category;
import com.mikhaellopez.circularimageview.CircularImageView;


public class CategoryGridAdapter extends RecyclerView.Adapter<CategoryGridAdapter.ViewHolder> {

    List<Category> items;

    CategoryContext categoryContext;

    public CategoryGridAdapter(List<Category> offer, CategoryContext categoryContext) {

        super();

        this.categoryContext = categoryContext;

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

        private CircularImageView categoryIcon;

        public ViewHolder(final View itemView) {

            super(itemView);

            category = (TextView)itemView.findViewById(R.id.textCategory);

            categoryIcon = (CircularImageView)itemView.findViewById(R.id.imageButtonCategory);

            categoryIcon.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    Animation animation = AnimationUtils.loadAnimation(view.getContext(),R.anim.circular_image_view);

                    categoryIcon.startAnimation(animation);

                    Intent intent = new Intent(view.getContext() , SubCategoryActivity.class);

                    intent.putExtra(Category.class.getName(),category.getText());

                    view.getContext().startActivity(intent);

                    categoryContext.cancelQuery();
                }

            });

        }

    }

}