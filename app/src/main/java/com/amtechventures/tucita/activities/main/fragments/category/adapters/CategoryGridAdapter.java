package com.amtechventures.tucita.activities.main.fragments.category.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.main.fragments.category.CategoryFragment;
import com.amtechventures.tucita.model.context.category.CategoryContext;
import com.amtechventures.tucita.model.domain.category.Category;
import com.amtechventures.tucita.utils.views.AppTextView;
import java.util.List;

public class CategoryGridAdapter extends RecyclerView.Adapter<CategoryGridAdapter.ViewHolder> {

    private List<Category> items;
    private CategoryFragment.OnItemClicked onItemClickListener;
    private CategoryContext categoryContext;

    public CategoryGridAdapter(List<Category> offer, CategoryContext categoryContext, CategoryFragment.OnItemClicked onItemClickListener) {

        super();

        this.categoryContext = categoryContext;

        items = offer;

        this.onItemClickListener = onItemClickListener;

    }

    public void cancelQuery() {

        categoryContext.cancelQuery();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_category, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        Category category = items.get(i);

        viewHolder.category.setText(category.getName());

        viewHolder.categoryIcon.setImageBitmap(category.getPicture());

    }

    @Override
    public int getItemCount() {

        return items.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        protected AppTextView category;

        private ImageView categoryIcon;

        public ViewHolder(final View itemView) {

            super(itemView);

            category = (AppTextView) itemView.findViewById(R.id.textCategory);

            categoryIcon = (ImageView) itemView.findViewById(R.id.imageButtonCategory);

            categoryIcon.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    onItemClickListener.onItemClicked((String) category.getText());

                }

            });

        }

    }

}