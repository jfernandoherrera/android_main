package com.amtechventures.tucita.activities.main.fragments.category;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.main.fragments.category.adapters.CategoryGridAdapter;
import com.amtechventures.tucita.model.context.category.CategoryCompletion;
import com.amtechventures.tucita.model.context.category.CategoryContext;
import com.amtechventures.tucita.model.context.user.UserContext;
import com.amtechventures.tucita.model.domain.category.Category;
import com.amtechventures.tucita.model.domain.user.User;
import com.amtechventures.tucita.model.domain.user.UserAttributes;
import com.amtechventures.tucita.model.error.AppError;
import com.amtechventures.tucita.utils.views.AlertDialogError;
import com.amtechventures.tucita.utils.views.CustomSpanTypeface;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CategoryFragment extends Fragment {

    private int COLUMNS_IN_CATEGORIES = 3;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private CategoryContext categoryContext;
    private List<Category> categories = new ArrayList<>();
    private ProgressDialog progress;
    private OnItemClicked listener;
    private Typeface typeface;
    private Typeface typefaceMedium;
    private UserContext userContext;

    public interface OnItemClicked {

        void onItemClicked(String name);

    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

        listener = (OnItemClicked) context;

    }

    public void setTypeface(Typeface typeface, Typeface typefaceMedium) {

        this.typeface = typeface;

        this.typefaceMedium = typefaceMedium;
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

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);

        userContext = UserContext.context(userContext);

        layoutManager = new GridLayoutManager(rootView.getContext(), COLUMNS_IN_CATEGORIES);

        recyclerView.setLayoutManager(layoutManager);

        setupGrid();

        setupLogged(rootView);

        return rootView;

    }


    private Bitmap setImageUser(User user, View view){

        Bitmap image = null;

        if(userContext.isFacebook(user)) {

          image = userContext.getPicture();

        }

        return image;

    }

    private void setupLogged(View view) {

        boolean connected = getActivity().getIntent().getExtras().getBoolean(UserAttributes.connected);

        final Button buttonLogin = (Button) view.findViewById(R.id.goToLogin);

        final Button buttonSignUp = (Button) view.findViewById(R.id.goToSignUp);

        final TextView textView = (TextView) view.findViewById(R.id.accountName);

        final TextView textViewMemberFrom = (TextView) view.findViewById(R.id.accountMemberFrom);

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.backgroundContainer);

        final CircularImageView circularImageView = (CircularImageView) view.findViewById(R.id.imageUser);

        textView.setTypeface(typeface, Typeface.BOLD);

        textViewMemberFrom.setTypeface(typeface);

        linearLayout.setBackgroundResource(R.drawable.bg_servicio);

        linearLayout.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    circularImageView.setBorderColor(getResources().getColor(R.color.colorAccent2));

                } else if(event.getAction() != MotionEvent.ACTION_MOVE){

                    circularImageView.setBorderColor(getResources().getColor(R.color.colorAccent));

                }

                return false;

            }

        });

        if (connected) {

            User user = userContext.currentUser();

            Bitmap image = setImageUser(user, view);

            if(image != null) {

                circularImageView.setImageBitmap(image);

            }

            Date createdAt = user.getParseUser().getCreatedAt();

            int bugDate = 1900;

            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM", Locale.getDefault());

            String memberFrom = getString(R.string.member_from) + " " + dateFormat.format(createdAt) + " " + getString(R.string.of) + " " + (createdAt.getYear() + bugDate);

            String userName = user.getName();

            textView.setText(userName);

            textViewMemberFrom.setText(memberFrom);

            buttonLogin.setVisibility(View.GONE);

            buttonSignUp.setVisibility(View.GONE);


        } else {

            circularImageView.setVisibility(View.GONE);

            textView.setVisibility(View.GONE);

            buttonLogin.setTypeface(typefaceMedium, Typeface.BOLD);

            buttonSignUp.setTypeface(typefaceMedium, Typeface.BOLD);

        }

    }

    private void setupGrid() {

        List<Category> categoryList = categoryContext.loadCategories(new CategoryCompletion.CategoriesErrorCompletion() {

            @Override
            public void completion(List<Category> categoryList, AppError error) {

                if (progress != null) {

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

        if (categoryList != null && !categoryList.isEmpty()) {

            categories.clear();

            categories.addAll(categoryList);

        } else {

            setupProgress();

        }

        adapter = new CategoryGridAdapter(categories, categoryContext, typeface, (OnItemClicked) getActivity());

        recyclerView.setAdapter(adapter);

    }

    private void setupProgress() {

        progress = ProgressDialog.show(getContext(), getResources().getString(R.string.dialog_progress_title), getResources().getString(R.string.dialog_all_progress_message), true);

    }

}