package com.amtechventures.tucita.activities.main.fragments.category;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;

import android.view.ViewGroup;

import com.amtechventures.tucita.R;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.GridLayoutManager;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.amtechventures.tucita.utils.views.UserImageView;
import com.amtechventures.tucita.model.domain.user.UserAttributes;
import com.amtechventures.tucita.model.error.AppError;
import com.amtechventures.tucita.model.domain.category.Category;
import com.amtechventures.tucita.model.context.category.CategoryContext;
import com.amtechventures.tucita.model.context.category.CategoryCompletion;
import com.amtechventures.tucita.activities.main.fragments.category.adapters.CategoryGridAdapter;
import com.amtechventures.tucita.utils.views.AlertDialogError;
import com.amtechventures.tucita.utils.views.CustomSpanTypeface;

public class CategoryFragment extends Fragment {

    private int COLUMNS_IN_CATEGORIES = 3;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private CategoryContext categoryContext;
    private List<Category> categories = new ArrayList<>();
    private ProgressDialog progress;
    OnItemClicked listener;
    Typeface roboto;
    private UserImageView picture;
    public interface OnItemClicked{
        void onItemClicked(String name);
    }

    public void setPicture(UserImageView picture) {

        this.picture = picture;
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

        listener =  (OnItemClicked) context;

        roboto = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Light.ttf");
    }

    @Override
    public void onDetach() {

        super.onDetach();

        listener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_category, container, false);

        categoryContext = CategoryContext.context(categoryContext);

        recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(rootView.getContext(), COLUMNS_IN_CATEGORIES);

        recyclerView.setLayoutManager(layoutManager);

        setupGrid();

        setupLogged(rootView);

        return rootView;
    }

    private SpannableStringBuilder getStringLoginModified(){

        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);

        DisplayMetrics metrics = new DisplayMetrics();

        wm.getDefaultDisplay().getMetrics(metrics);

        final int initialSize = 18;

        int size = (int)(initialSize * metrics.scaledDensity);

        String firstString = getResources().getString(R.string.action_sign_in_short).toUpperCase();

        String secondString = getResources().getString(R.string.or).toLowerCase();

        String thirdString = getResources().getString(R.string.action_sign_up).toUpperCase();

        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(firstString +" "+ secondString +" "+ thirdString);

        stringBuilder.setSpan(new CustomSpanTypeface(null, Typeface.BOLD, size, null, null, roboto), 0, firstString.length(),
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        stringBuilder.setSpan(new CustomSpanTypeface(null, Typeface.BOLD, size, ColorStateList.valueOf(Color.rgb(223, 223, 223)), null, roboto), firstString.length() + 1,

                firstString.length() + secondString.length() + 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        stringBuilder.setSpan(new CustomSpanTypeface(null, Typeface.BOLD, size, null, null, roboto), firstString.length() + secondString.length() + 2,

                firstString.length() + secondString.length() + thirdString.length() + 2,

                Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        return stringBuilder;
    }

    private void setupLogged(View view) {

        boolean connected = getActivity().getIntent().getExtras().getBoolean(UserAttributes.connected);

        final Button buttonText = (Button) view.findViewById(R.id.go_to_login);

        final ImageButton button = (ImageButton) view.findViewById(R.id.account);

        button.setBackgroundResource(R.drawable.log_in_or_signup_click_out);

        if (connected) {

            button.setImageDrawable(picture);

            buttonText.setVisibility(View.GONE);

            button.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {

                        button.setBackgroundResource(R.drawable.log_in_or_signup_click_in);

                    } else if (event.getAction() == MotionEvent.ACTION_UP) {

                        button.setBackgroundResource(R.drawable.log_in_or_signup_click_out);

                        button.callOnClick();
                    }
                    return true;
                }
            });

        } else {

            button.setVisibility(View.GONE);

            buttonText.setText(getStringLoginModified());

            buttonText.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {

                        buttonText.setBackgroundResource(R.drawable.log_in_or_signup_click_in);

                    } else if (event.getAction() == MotionEvent.ACTION_UP) {

                        buttonText.setBackgroundResource(R.drawable.log_in_or_signup_click_out);

                        buttonText.callOnClick();
                    }
                    return true;
                }
            });

        }
    }
    private void setupGrid() {

        List<Category> categoryList = categoryContext.loadCategories(new CategoryCompletion.CategoriesErrorCompletion() {

            @Override
            public void completion(List<Category> categoryList, AppError error) {

                if(progress != null){

                    progress.dismiss();
                }

                if (categoryList != null) {

                    categories.clear();

                    categories.addAll(categoryList);

                    adapter.notifyDataSetChanged();

                } else {

                    AlertDialogError alertDialogError = new AlertDialogError();

                    alertDialogError.noInternetConnectionAlert(getContext());
                }

            }

        });
        if (categoryList != null && ! categoryList.isEmpty()) {

            categories.clear();

            categories.addAll(categoryList);
        }else {

            setupProgress();
        }
        adapter = new CategoryGridAdapter(categories, categoryContext, roboto, (OnItemClicked) getActivity());

        recyclerView.setAdapter(adapter);
    }

    private void setupProgress(){

        progress = ProgressDialog.show(getContext(), getResources().getString(R.string.dialog_progress_title),

                getResources().getString(R.string.dialog_all_progress_message), true);
    }
}