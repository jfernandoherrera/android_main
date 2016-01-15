package com.amtechventures.tucita.activities.book.fragments.venue;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.book.fragments.venue.adapters.ExpandableListAdapter;
import com.amtechventures.tucita.activities.reviews.ReviewsActivity;
import com.amtechventures.tucita.model.context.openingHour.OpeningHourCompletion;
import com.amtechventures.tucita.model.context.openingHour.OpeningHourContext;
import com.amtechventures.tucita.model.context.service.ServiceCompletion;
import com.amtechventures.tucita.model.context.service.ServiceContext;
import com.amtechventures.tucita.model.context.venue.VenueCompletion;
import com.amtechventures.tucita.model.context.venue.VenueContext;
import com.amtechventures.tucita.model.domain.openingHour.OpeningHour;
import com.amtechventures.tucita.model.domain.service.Service;
import com.amtechventures.tucita.model.domain.service.ServiceAttributes;
import com.amtechventures.tucita.model.domain.subcategory.SubCategory;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.domain.venue.VenueAttributes;
import com.amtechventures.tucita.model.error.AppError;
import com.amtechventures.tucita.activities.book.adapters.ExpandableParentAdapter;
import com.amtechventures.tucita.utils.views.OpeningHourView;
import com.amtechventures.tucita.utils.views.ViewUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class VenueFragment extends Fragment {

    private ArrayList<Integer> days;
    private final int daysInAWeek = 7;
    private Venue venue;
    private VenueContext venueContext;
    private ServiceContext serviceContext;
    private OpeningHourContext openingHourContext;
    private ImageView venuePicture;
    private TextView venueName;
    private ExpandableListView venueDescription;
    private ExpandableParentAdapter parentAdapter;
    private RatingBar ratingBar;
    private Button location;
    private List<ArrayList<Service>> services = new ArrayList<>();
    private List<SubCategory> subCategories = new ArrayList<>();
    private ExpandableListAdapter fullMenuAdapter;
    private ExpandableListView listViewFullMenu;
    private ExpandableListAdapter anotherMenuAdapter;
    private ExpandableListView listViewAnotherMenu;
    private OnServiceSelected listener;
    private LayoutInflater inflater;
    private ProgressDialog progress;
    private ViewUtils viewUtils;
    private ScrollView scrollView;

    public interface OnServiceSelected {

        void onServiceSelected(Service service, View view);

    }

    public Venue getVenue() {

        return venue;

    }

    public void cancelQuery() {

        openingHourContext.cancelQuery();

        serviceContext.cancelQuery();

        venueContext.cancelQuery();

    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

        listener = (OnServiceSelected) context;

    }

    @Override
    public void onDetach() {

        super.onDetach();

        listener = null;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        viewUtils = new ViewUtils(getContext());

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_venue, container, false);

        venueContext = VenueContext.context(venueContext);

        serviceContext = ServiceContext.context(serviceContext);

        openingHourContext = OpeningHourContext.context(openingHourContext);

        scrollView = (ScrollView) rootView.findViewById(R.id.scrollView1);

        venuePicture = (ImageView) rootView.findViewById(R.id.imageVenue);

        venueName = (TextView) rootView.findViewById(R.id.title_Venue);

        venueDescription = (ExpandableListView) rootView.findViewById(R.id.listViewDescription);

        ratingBar = (RatingBar) rootView.findViewById(R.id.ratingBar);

        RelativeLayout clickRating = (RelativeLayout) rootView.findViewById(R.id.clickRating);

        clickRating.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    v.setBackgroundResource(R.drawable.pressed_application_background_static);

                } else if (event.getAction() != MotionEvent.ACTION_MOVE) {

                    v.setBackgroundColor(Color.TRANSPARENT);

                }

                return false;

            }
        });

        clickRating.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            goToReviews();

            }

        });

        location = (Button) rootView.findViewById(R.id.watch_location);

        listViewFullMenu = (ExpandableListView) rootView.findViewById(R.id.listViewFull);

        listViewAnotherMenu = (ExpandableListView) rootView.findViewById(R.id.listViewTop);

        this.inflater = inflater;

        return rootView;

    }

    private void goToReviews(){

        ReviewsActivity.goToReviews(getContext());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        setup();

    }

    private void setup() {

        setupDaysInAWeek();

        thisVenue();

        setupVenue();

    }

    private void setupDaysInAWeek() {

        days = new ArrayList();

        for (int day = 1; day <= daysInAWeek; day++) {

            days.add(day);

        }

    }

    private void setupVenue() {

        if (venue != null) {

            setupPicture();

            setupName();

            setupRating();

            setupAddressAndLocation();

            setupOpeningHours();

            setupDescription();

            setupFullMenu();

        } else {

            setupProgress();

        }

    }

    public void setMarginBottomToShoppingCarVisible(int shoppingCarHeight) {

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );

        params.setMargins(0, 0, 0, shoppingCarHeight);

        scrollView.setLayoutParams(params);

    }

    public void setMarginBottomToShoppingCarGone() {

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );

        params.setMargins(0, 0, 0, 0);

        scrollView.setLayoutParams(params);

    }

    private void setupPicture() {

        venuePicture.setImageBitmap(venue.getPicture());

    }

    private void setupName() {

        venueName.setText(venue.getName());

    }

    private void setupDescription() {

        Activity activity = getActivity();

        if (activity != null) {

            String description = venue.getDescription();

            parentAdapter = new ExpandableParentAdapter(description, venueDescription, viewUtils);

            parentAdapter.setInflater(inflater);

            venueDescription.setAdapter(parentAdapter);

            venueDescription.expandGroup(0);

        }

    }

    private void setupFullMenu() {

        List<Service> servicesList = serviceContext.loadServices(venue, new ServiceCompletion.ErrorCompletion() {

            @Override
            public void completion(List<Service> servicesList, AppError error) {

                if (servicesList != null && !servicesList.isEmpty()) {

                    services.clear();

                    subCategories.clear();

                    setStringsArray(servicesList);

                    viewUtils.setListViewHeightBasedOnChildren(listViewFullMenu);

                    fullMenuAdapter.notifyDataSetChanged();

                    setupAnotherMenu();

                }

            }

        });

        if (servicesList == null && progress == null) {

            setupProgress();

        }

        setStringsArray(servicesList);

        fullMenuAdapter = new ExpandableListAdapter(subCategories, services, viewUtils, listViewFullMenu, listener);

        fullMenuAdapter.setInflater(inflater);

        listViewFullMenu.setAdapter(fullMenuAdapter);

    }

    private void setupAnotherMenu() {

        Activity activity = getActivity();

        if (activity != null) {

            String subCategory = activity.getIntent().getStringExtra(ServiceAttributes.subCategory);

            if (subCategory != null) {

                TextView specials = (TextView) getActivity().findViewById(R.id.textViewTop);

                String findService = getResources().getString(R.string.find_service);

                specials.setVisibility(View.VISIBLE);

                specials.setText(findService);

                ArrayList<SubCategory> arrayList = new ArrayList();

                int indexOf = 0;

                ArrayList<ArrayList<Service>> arrayListServices = new ArrayList<>();

                for (SubCategory subCategory1 : subCategories) {

                    if (subCategory1.getName().equals(subCategory)) {

                        arrayList.add(subCategory1);

                        indexOf = subCategories.indexOf(subCategory1);

                    }

                }

                arrayListServices.add(services.get(indexOf));

                anotherMenuAdapter = new ExpandableListAdapter(arrayList, arrayListServices, viewUtils, listViewAnotherMenu, listener);

                anotherMenuAdapter.setInflater(inflater);

                listViewAnotherMenu.setAdapter(anotherMenuAdapter);

                listViewAnotherMenu.expandGroup(0);

            }

            if (progress != null) {

                progress.dismiss();

            }

        }

    }

    private void setStringsArray(List<Service> servicesList) {

        if (servicesList != null) {

            for (Service service : servicesList) {

                SubCategory subCategory = service.getSubcategory();

                if (subCategories.contains(subCategory)) {

                    int indexOfSubCategory = subCategories.indexOf(subCategory);

                    services.get(indexOfSubCategory).add(service);

                } else {

                    subCategories.add(subCategory);

                    ArrayList subCategoriesString = new ArrayList();

                    subCategoriesString.add(service);

                    services.add(subCategoriesString);

                }

            }

        }

    }

    private void setupRating() {

        float rating = (float) venue.getRating();

        if (rating != 0) {

            ratingBar.setRating(rating);

        } else {

            ratingBar.setVisibility(View.INVISIBLE);

        }

    }

    private void setupOpeningHours() {

        List<OpeningHour> openingHoursList = openingHourContext.loadOpeningHours(venue, new OpeningHourCompletion.OpeningHourErrorCompletion() {

            @Override
            public void completion(List<OpeningHour> openingHoursList, AppError error) {

                if (openingHoursList != null && !openingHoursList.isEmpty()) {

                    populateOpeningHours(openingHoursList);

                }

            }

        });

        populateOpeningHours(openingHoursList);

    }

    private OpeningHourView getViewDay(int day) {

        OpeningHourView openingHourView = null;

        Activity activity = getActivity();

        if (activity != null) {

            switch (day) {

                case Calendar.MONDAY:

                    openingHourView = (OpeningHourView) activity.findViewById(R.id.radioButton1);

                    openingHourView.setDay(getResources().getString(R.string.monday));

                    break;

                case Calendar.TUESDAY:

                    openingHourView = (OpeningHourView) activity.findViewById(R.id.radioButton2);

                    openingHourView.setDay(getResources().getString(R.string.tuesday));

                    break;

                case Calendar.WEDNESDAY:

                    openingHourView = (OpeningHourView) activity.findViewById(R.id.radioButton3);

                    openingHourView.setDay(getResources().getString(R.string.wednesday));

                    break;

                case Calendar.THURSDAY:

                    openingHourView = (OpeningHourView) activity.findViewById(R.id.radioButton4);

                    openingHourView.setDay(getResources().getString(R.string.thursday));

                    break;

                case Calendar.FRIDAY:

                    openingHourView = (OpeningHourView) activity.findViewById(R.id.radioButton5);

                    openingHourView.setDay(getResources().getString(R.string.friday));

                    break;

                case Calendar.SATURDAY:

                    openingHourView = (OpeningHourView) activity.findViewById(R.id.radioButton6);

                    openingHourView.setDay(getResources().getString(R.string.saturday));

                    break;

                case Calendar.SUNDAY:

                    openingHourView = (OpeningHourView) activity.findViewById(R.id.radioButton7);

                    openingHourView.setDay(getResources().getString(R.string.sunday));

                    break;

                default:

                    openingHourView = (OpeningHourView) activity.findViewById(R.id.radioButton1);

                    break;
            }

            setupDay(day, openingHourView);

        }

        return openingHourView;

    }

    private void populateOpeningHours(List<OpeningHour> openingHours) {

        for (Integer day : days) {

            OpeningHourView openingHourView;

            openingHourView = getViewDay(day);

            if (openingHourView != null) {

                String closed = getResources().getString(R.string.closed);

                openingHourView.setClosed(closed);

            }

        }

        for (OpeningHour openingHour : openingHours) {

            OpeningHourView openingHourView;

            openingHourView = getViewDay(openingHour.getDay());

            if (openingHourView != null) {

                ViewUtils viewUtils = new ViewUtils(getContext());

                String textOpeningStartHour = viewUtils.hourFormat(openingHour.getStartHour(), openingHour.getStartMinute());

                String textOpeningEndHour = viewUtils.hourFormat(openingHour.getEndHour(), openingHour.getEndMinute());

                String textTime = textOpeningStartHour + "-" + textOpeningEndHour;

                openingHourView.setHours(textTime);

            }

        }

    }

    private void setupAddressAndLocation() {

        String locationString = venue.getCity().formatedLocation();

        String address = locationString + " " + venue.getAddress();

        location.setText(address);

        location.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    location.setBackgroundResource(R.drawable.pressed_application_background_static);

                } else if (event.getAction() != MotionEvent.ACTION_MOVE) {

                    location.setBackgroundResource(R.drawable.log_in_or_signup_click_out);

                }

                return false;

            }

        });

        location.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String url = VenueAttributes.linkToLocation + venue.getLatitude() + "," + venue.getLongitude();

                openWebURL(url);

            }

        });

    }

    private void thisVenue() {

        Intent intent = getActivity().getIntent();

        String name = intent.getExtras().getString(Venue.class.getName());

        String address = intent.getExtras().getString(VenueAttributes.address);

        venue = venueContext.findVenue(name, address, new VenueCompletion.ErrorCompletion() {

            @Override
            public void completion(List<Venue> venuesList, AppError error) {

                if (venuesList != null) {

                    venue = venuesList.get(0);

                    setupVenue();

                }

            }

        });

    }

    private void setupDay(int day, OpeningHourView openingHourView) {

        Calendar calendar = Calendar.getInstance();

        int currentDay = calendar.get(Calendar.DAY_OF_WEEK);

        if (day == currentDay) {

            openingHourView.setState_drawable(true);

        } else {

            openingHourView.setState_drawable(false);

        }

    }

    public void openWebURL(String inURL) {

        Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse(inURL));

        startActivity(browse);

    }

    private void setupProgress() {

        progress = ProgressDialog.show(getContext(), getResources().getString(R.string.dialog_progress_title), getResources().getString(R.string.dialog_all_progress_message), true);

    }

}
