package com.amtechventures.tucita.activities.main.fragments.category.adapters;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.main.fragments.category.CategoryFragment;
import com.amtechventures.tucita.model.context.category.CategoryContext;
import com.amtechventures.tucita.model.domain.category.Category;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

public class CategoryGridAdapter extends RecyclerView.Adapter<CategoryGridAdapter.ViewHolder> {

    private List<Category> items;
    private CategoryFragment.OnItemClicked onItemClickListener;
    private Typeface typeface;
    private CategoryContext categoryContext;

    public CategoryGridAdapter(List<Category> offer, CategoryContext categoryContext, Typeface typeface, CategoryFragment.OnItemClicked onItemClickListener) {

        super();

        this.typeface = typeface;

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

        protected TextView category;

        private CircularImageView categoryIcon;

        public ViewHolder(final View itemView) {

            super(itemView);

            category = (TextView) itemView.findViewById(R.id.textCategory);

            category.setTypeface(typeface, Typeface.BOLD);

            categoryIcon = (CircularImageView) itemView.findViewById(R.id.imageButtonCategory);

            categoryIcon.setOnTouchListener(new View.OnTouchListener() {

                                                @Override
                                                public boolean onTouch(View view, MotionEvent event) {

                                                    if (event.getAction() == MotionEvent.ACTION_DOWN) {

                                                        Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.circular_image_view);

                                                        categoryIcon.startAnimation(animation);

                                                    } else if (event.getAction() == MotionEvent.ACTION_UP) {

                                                        categoryIcon.callOnClick();

                                                    }

                                                    return true;
                                                }

                                            }

            );

            categoryIcon.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    onItemClickListener.onItemClicked((String) category.getText());

                }

            });

        }

    }

}