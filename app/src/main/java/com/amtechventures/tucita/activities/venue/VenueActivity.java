package com.amtechventures.tucita.activities.venue;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.venue.adapters.ExpandableListAdapter;
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
import com.amtechventures.tucita.utils.views.OpeningHourView;
import com.amtechventures.tucita.utils.views.ViewUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class VenueActivity extends AppCompatActivity {

    private ArrayList<Integer> days;
    private final int twelveHoursClock = 12;
    private final int oneDigitNumber = 9;
    private final String shortHour = "hr";
    private final String shortMinutes = "mins";
    private Venue venue;
    private VenueContext venueContext;
    private ServiceContext serviceContext;
    private OpeningHourContext openingHourContext;
    private ImageView venuePicture;
    private TextView venueName;
    private TextView venueDescription;
    private RatingBar ratingBar;
    private Button location;
    private List<ArrayList> services = new ArrayList<>();
    private List<SubCategory> subCategories = new ArrayList<>();
    private ExpandableListAdapter fullMenuAdapter;
    private ExpandableListView listViewFullMenu;
    private ExpandableListAdapter anotherMenuAdapter;
    private ExpandableListView listViewAnotherMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_venue);

        venueContext = VenueContext.context(venueContext);

        serviceContext = ServiceContext.context(serviceContext);

        openingHourContext = OpeningHourContext.context(openingHourContext);

        venuePicture = (ImageView) findViewById(R.id.imageVenue);

        venueName = (TextView) findViewById(R.id.title_Venue);

        venueDescription = (TextView) findViewById(R.id.textViewDescription);

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        location = (Button) findViewById(R.id.watch_location);

        listViewFullMenu = (ExpandableListView) findViewById(R.id.listViewFull);

        listViewAnotherMenu = (ExpandableListView) findViewById(R.id.listViewTop);

        setup();
    }

    @Override
    protected void onPause() {

        super.onPause();

        finish();
    }

    private void setup(){

        days = new ArrayList();

        for(int day = 1; day <= 7; day++){

            days.add(day);
        }

        thisVenue();

        setupPicture();

        setupName();

        setupDescription();

        setupRating();

        setupAddressAndlocation();

        setupOpeningHours();

        setupFullmenu();

        setupAnotherMenu();
    }

    private void setupPicture(){

        venuePicture.setImageBitmap(venue.getPicture());
    }

    private void setupName(){

        venueName.setText(venue.getName());
    }

    private void setupDescription(){

        String description = getResources().getString(R.string.description) + venue.getDescription();

        venueDescription.setText(description);
    }

    private void setupFullmenu(){

        List<Service> servicesList = serviceContext.loadServices(venue, new ServiceCompletion.ErrorCompletion() {
            @Override
            public void completion(List<Service> servicesList, AppError error) {

                if (servicesList != null) {

                    services.clear();

                    subCategories.clear();

                    setStringsArray(servicesList);

                    ViewUtils.setListViewHeightBasedOnChildren(listViewFullMenu);

                    fullMenuAdapter.notifyDataSetChanged();
                }
            }
        });
        setStringsArray(servicesList);

        fullMenuAdapter = new ExpandableListAdapter(subCategories, services,listViewFullMenu);

        fullMenuAdapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);

        listViewFullMenu.setAdapter(fullMenuAdapter);
    }

    private void setupAnotherMenu(){

        String subCategory = getIntent().getStringExtra(ServiceAttributes.subCategory);

        if (subCategory != null){

            TextView specials = (TextView) findViewById(R.id.textViewTop);

            String findService = getResources().getString(R.string.find_service);

            specials.setText(findService);

            ArrayList<SubCategory> arrayList = new ArrayList();

            int indexOf = 0;

            ArrayList<ArrayList> arrayListServices = new ArrayList<>();

            for (SubCategory subCategory1 : subCategories) {

                if(subCategory1.getName().equals(subCategory)){

                    arrayList.add(subCategory1);

                    indexOf = subCategories.indexOf(subCategory1);
                }
            }
            arrayListServices.add(services.get(indexOf));

            anotherMenuAdapter = new ExpandableListAdapter(arrayList, arrayListServices,listViewAnotherMenu);

            anotherMenuAdapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);

            listViewAnotherMenu.setAdapter(anotherMenuAdapter);

            listViewAnotherMenu.expandGroup(0);

        }
    }

    private ArrayList<String> setStringsArray(List<Service> servicesList){

        ArrayList<String> stringsServices = new ArrayList<>();

        for( Service service : servicesList){

            SubCategory subCategory = service.getSubcategory();

            if(subCategories.contains(subCategory)){

                int indexOfsubCategory = subCategories.indexOf(subCategory);

                services.get(indexOfsubCategory).add(setServiceString(service));
            }else {

                subCategories.add(subCategory);

                ArrayList subCategoriesString = new ArrayList();

                subCategoriesString.add(setServiceString(service));

                services.add(subCategoriesString);
            }

        }
        return stringsServices;
    }

    private String setServiceString(Service service){

        String serviceName = service.getName();

        int durationHours = service.getDurationHour();

        int durationMinutes = service.getDurationMinutes();

        String serviceDurationHours = durationHours == 0 ? "" : String.valueOf(durationHours) + shortHour;

        String serviceDurationMinutes = durationMinutes == 0 ? "" : String.valueOf(durationMinutes) + shortMinutes;

        String servicePrice = "$" + String.valueOf(service.getPrice());

        String serviceInfo = serviceName + " " + serviceDurationHours + " " + serviceDurationMinutes + " " + servicePrice;

        return serviceInfo;
    }

    private void setupRating(){

        float rating = (float)venue.getRating();

        if(rating != 0) {

            ratingBar.setRating(rating);

        }else{

            ratingBar.setVisibility(View.INVISIBLE);
        }
    }

    private void setupOpeningHours(){

        List<OpeningHour> openingHoursList = openingHourContext.loadOpeningHours(venue, new OpeningHourCompletion.OpeningHourErrorCompletion() {

            @Override
            public void completion(List<OpeningHour> openingHoursList, AppError error) {

                if (openingHoursList != null) {

                    populateOpeningHours(openingHoursList);
                }

            }

        });
        populateOpeningHours(openingHoursList);
    }

    private OpeningHourView getViewDay(int day){

        OpeningHourView openingHourView;

        switch (day){

            case Calendar.MONDAY:

                openingHourView = (OpeningHourView) findViewById(R.id.radioButton1);

                openingHourView.setDay(getResources().getString(R.string.monday));

                break;
            case Calendar.TUESDAY:

                openingHourView = (OpeningHourView) findViewById(R.id.radioButton2);

                openingHourView.setDay(getResources().getString(R.string.tuesday));

                break;
            case Calendar.WEDNESDAY:

                openingHourView = (OpeningHourView) findViewById(R.id.radioButton3);

                openingHourView.setDay(getResources().getString(R.string.wednesday));

                break;
            case Calendar.THURSDAY:

                openingHourView = (OpeningHourView)findViewById(R.id.radioButton4);

                openingHourView.setDay(getResources().getString(R.string.thursday));

                break;
            case Calendar.FRIDAY:

                openingHourView = (OpeningHourView) findViewById(R.id.radioButton5);

                openingHourView.setDay(getResources().getString(R.string.friday));

                break;
            case Calendar.SATURDAY:

                openingHourView = (OpeningHourView) findViewById(R.id.radioButton6);

                openingHourView.setDay(getResources().getString(R.string.saturday));

                break;
            case Calendar.SUNDAY:

                openingHourView = (OpeningHourView) findViewById(R.id.radioButton7);

                openingHourView.setDay(getResources().getString(R.string.sunday));

                break;
            default:

                openingHourView = (OpeningHourView) findViewById(R.id.radioButton1);

                break;
        }
        setupDay(day, openingHourView);

        return  openingHourView;
    }

    private void populateOpeningHours(List<OpeningHour> openingHours){

        for(Integer day : days){

                OpeningHourView openingHourView;

                openingHourView = getViewDay(day);

                String closed = getResources().getString(R.string.closed);

                openingHourView.setClosed(closed);
         }

        for(OpeningHour openingHour : openingHours ){

            OpeningHourView openingHourView;

            openingHourView = getViewDay(openingHour.getDay());

            String textOpeningStartHour =  hourFormat(openingHour.getStartHour(), openingHour.getStartMinute());

            String textOpeningEndHour = hourFormat(openingHour.getEndHour(), openingHour.getEndMinute());

            String textTime =  textOpeningStartHour + "-" + textOpeningEndHour;

            openingHourView.setHours(textTime);
        }
    }

    private String hourFormat(int hour, int minute){

        String amPm = hour <= twelveHoursClock ? "AM" : "PM";

        String minuteString = minute <= oneDigitNumber ?  "0" + String.valueOf(minute) :  String.valueOf(minute);

        hour = hour <= twelveHoursClock ? hour : hour - twelveHoursClock;

        String hourString = " " + String.valueOf(hour) + ":" + minuteString + amPm + " ";

        return hourString;
    }

    private void setupAddressAndlocation(){

        String locationString = venue.getCity().formatedLocation();

        String address = locationString + " " + venue.getAddress();

        location.setText(address);

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = VenueAttributes.linkToLocation + venue.getLatitude() + "," + venue.getLongitude();

                openWebURL(url);
            }
        });
    }

    private void thisVenue(){

        String name = getIntent().getExtras().getString(Venue.class.getName());

        String address = getIntent().getExtras().getString(VenueAttributes.address);

        venue = venueContext.findVenue(name, address, new VenueCompletion.ErrorCompletion() {
            @Override
            public void completion(List<Venue> venuesList, AppError error) {

                if(venuesList != null){

                    venue = venuesList.get(0);
                }
            }
        });
    }

    private void setupDay(int day,  OpeningHourView openingHourView) {

        Calendar c = Calendar.getInstance();

        int dayCurrent = c.get(Calendar.DAY_OF_WEEK);

        if (day == dayCurrent) {

            openingHourView.setState_drawable(true);
        } else {

            openingHourView.setState_drawable(false);
        }
    }

    public void openWebURL( String inURL ) {

        Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse(inURL) );

        startActivity(browse);
    }
}
