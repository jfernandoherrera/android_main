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
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.venue.adapters.ExpandableListAdapter;
import com.amtechventures.tucita.model.context.openingHour.OpeningHourCompletion;
import com.amtechventures.tucita.model.context.openingHour.OpeningHourContext;
import com.amtechventures.tucita.model.context.service.ServiceCompletion;
import com.amtechventures.tucita.model.context.service.ServiceContext;
import com.amtechventures.tucita.model.context.subcategory.SubCategoryCompletion;
import com.amtechventures.tucita.model.context.venue.VenueContext;
import com.amtechventures.tucita.model.domain.category.CategoryAttributes;
import com.amtechventures.tucita.model.domain.openingHour.OpeningHour;
import com.amtechventures.tucita.model.domain.service.Service;
import com.amtechventures.tucita.model.domain.service.ServiceAttributes;
import com.amtechventures.tucita.model.domain.subcategory.SubCategory;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.domain.venue.VenueAttributes;
import com.amtechventures.tucita.model.error.AppError;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class VenueActivity extends AppCompatActivity {

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
    private List<OpeningHour> openingHours = new ArrayList<>();
    private List<ArrayList> services = new ArrayList<>();
    private List<SubCategory> subCategories = new ArrayList<>();
    private ExpandableListAdapter fullMenuAdapter;
    private ExpandableListView listViewFullMenu;

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

        setup();
    }

    @Override
    protected void onPause() {

        super.onPause();

        finish();
    }

    private void setup(){

        thisVenue();

        setupPicture();

        setupName();

        setupDescription();

        setupRating();

        setupAddressAndlocation();

        setupDay();

        setupOpeningHours();

        setupFullmenu();
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

                if(servicesList != null){

                    services.clear();

                    subCategories.clear();

                    setStringsArray(servicesList);

                    fullMenuAdapter.notifyDataSetChanged();
                }
            }
        });
        setStringsArray(servicesList);

        fullMenuAdapter = new ExpandableListAdapter(subCategories, services,listViewFullMenu);

        fullMenuAdapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);

        listViewFullMenu.setAdapter(fullMenuAdapter);
    }

    private ArrayList<String> setStringsArray(List<Service> servicesList){

        ArrayList<String> stringsServices = new ArrayList<>();

        for(ParseObject service : servicesList){

            SubCategory subCategory = (SubCategory) service.get(ServiceAttributes.subCategory);

            if(subCategories.contains(subCategory)){

                int indexOfsubCategory = subCategories.indexOf(subCategory);

                services.get(indexOfsubCategory).add(setServiceString(service));
            }else {
                subCategories.add(subCategory);
                ArrayList temp = new ArrayList();
                temp.add(setServiceString(service));
                services.add(temp);
            }

        }
        return stringsServices;
    }

    private String setServiceString(ParseObject service){

        String serviceName = service.getString(ServiceAttributes.name);

        int durationHours = service.getInt(ServiceAttributes.durationHours);

        int durationMinutes = service.getInt(ServiceAttributes.durationMinutes);

        String serviceDurationHours = durationHours == 0 ? "" : String.valueOf(durationHours) + shortHour;

        String serviceDurationMinutes = durationMinutes == 0 ? "" : String.valueOf(durationMinutes) + shortMinutes;

        String servicePrice = "$" + String.valueOf(service.getInt(ServiceAttributes.price));

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

        List<OpeningHour> openingHoursList = openingHourContext.loadOpeningHours(venue, new OpeningHourCompletion.OpeningHourErrorCompletion(){

            @Override
            public void completion(List<OpeningHour> openingHoursList, AppError error) {

                if (openingHoursList != null) {

                    openingHours.clear();

                    populateOpeningHours(openingHoursList);
                } else {

                    noInternetConnectionAlert();

                }

            }

        });

        populateOpeningHours(openingHoursList);
    }

    private RadioButton getViewDay(int day){

        RadioButton radioButton;

        switch (day){

            case Calendar.MONDAY:

                radioButton = (RadioButton) findViewById(R.id.radioButton1);

                break;
            case Calendar.TUESDAY:

                radioButton = (RadioButton) findViewById(R.id.radioButton2);

                break;
            case Calendar.WEDNESDAY:

                radioButton = (RadioButton) findViewById(R.id.radioButton3);

                break;
            case Calendar.THURSDAY:

                radioButton = (RadioButton) findViewById(R.id.radioButton4);

                break;
            case Calendar.FRIDAY:

                radioButton = (RadioButton) findViewById(R.id.radioButton5);

                break;
            case Calendar.SATURDAY:

                radioButton = (RadioButton) findViewById(R.id.radioButton6);

                break;
            case Calendar.SUNDAY:

                radioButton = (RadioButton) findViewById(R.id.radioButton7);

                break;
            default:

                radioButton = (RadioButton) findViewById(R.id.radioButton1);

                break;
        }

        return radioButton;
    }

    private void populateOpeningHours(List<OpeningHour> openingHours){

        for(OpeningHour openingHour : openingHours ){

            RadioButton radioButton;

            radioButton = getViewDay(openingHour.getDay());

            String textOpeningStartHour =  hourFormat(openingHour.getStartHour(), openingHour.getStartMinute());

            String textOpeningEndHour = hourFormat(openingHour.getEndHour(), openingHour.getEndMinute());

            String textDay = radioButton.getText().toString();

            String textTime = textDay + textOpeningStartHour + "-" + textOpeningEndHour;

            radioButton.setText(textTime);
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

        String city = venue.getCity().getName();

        String department = venue.getCity().getDepartment();

        String address = city + " " + department + " " + venue.getAddress();

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

        venue = venueContext.findVenue(name,address);
    }

    private void setupDay(){

        Calendar c = Calendar.getInstance();

        int day = c.get(Calendar.DAY_OF_WEEK);

        RadioButton radioButton;

        radioButton = getViewDay(day);

        radioButton.toggle();
    }

    public void openWebURL( String inURL ) {

        Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse(inURL) );

        startActivity(browse);
    }

    private void noInternetConnectionAlert() {

        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.alert))
                .setMessage(getResources().getString(R.string.no_internet_connection))
                .setRecycleOnMeasureEnabled(true)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
