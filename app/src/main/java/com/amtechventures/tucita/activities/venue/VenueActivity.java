package com.amtechventures.tucita.activities.venue;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.model.context.openingHour.OpeningHourCompletion;
import com.amtechventures.tucita.model.context.openingHour.OpeningHourContext;
import com.amtechventures.tucita.model.context.venue.VenueContext;
import com.amtechventures.tucita.model.domain.openingHour.OpeningHour;
import com.amtechventures.tucita.model.domain.openingHour.OpeningHourAttributes;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.domain.venue.VenueAttributes;
import com.amtechventures.tucita.model.error.AppError;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class VenueActivity extends AppCompatActivity {

    private Venue venue;
    private VenueContext venueContext;
    private OpeningHourContext openingHourContext;
    private ImageView venuePicture;
    private TextView venueName;
    private TextView venueDescription;
    private RatingBar ratingBar;
    private Button location;
    private List<OpeningHour> openingHours = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_venue);

        venueContext = VenueContext.context(venueContext);

        openingHourContext = OpeningHourContext.context(openingHourContext);

        venuePicture = (ImageView) findViewById(R.id.imageVenue);

        venueName = (TextView) findViewById(R.id.title_Venue);

        venueDescription = (TextView) findViewById(R.id.textViewDescription);

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        location = (Button) findViewById(R.id.watch_location);

        setup();
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

                    populateOpeningHours();
                } else {

                    noInternetConnectionAlert();

                }

            }

        });

        openingHours.addAll(openingHoursList);

        populateOpeningHours();
    }

    private void populateOpeningHours(){

        for(OpeningHour openingHour : openingHours ){

            RadioButton radioButton;

            switch (openingHour.getDay()){

                case OpeningHourAttributes.monday :


                    radioButton = (RadioButton) findViewById(R.id.radioButton1);

                    break;
                case OpeningHourAttributes.tuesday :

                    radioButton = (RadioButton) findViewById(R.id.radioButton2);

                    break;
                case OpeningHourAttributes.wednesday :

                    radioButton = (RadioButton) findViewById(R.id.radioButton3);

                    break;
                case OpeningHourAttributes.thursday :

                    radioButton = (RadioButton) findViewById(R.id.radioButton4);

                    break;
                case OpeningHourAttributes.friday :

                    radioButton = (RadioButton) findViewById(R.id.radioButton5);

                    break;
                case OpeningHourAttributes.saturday :

                    radioButton = (RadioButton) findViewById(R.id.radioButton6);

                    break;
                case OpeningHourAttributes.sunday :

                    radioButton = (RadioButton) findViewById(R.id.radioButton7);

                    break;
                default:

                    radioButton = (RadioButton) findViewById(R.id.radioButton1);

                    break;

            }

            String textOpeningHour = radioButton.getText().toString() + openingHour.getStartHour() + " - " + openingHour.getEndHour();

            radioButton.setText(textOpeningHour);

        }
    }

    private void setupAddressAndlocation(){

        location.setText(venue.getAddress());

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
