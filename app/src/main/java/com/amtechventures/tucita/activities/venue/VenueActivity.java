package com.amtechventures.tucita.activities.venue;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.model.context.venue.VenueContext;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.domain.venue.VenueAttributes;

import java.util.Calendar;


public class VenueActivity extends AppCompatActivity {

    private Venue venue;
    private VenueContext venueContext;
    private ImageView venuePicture;
    private TextView venueName;
    private TextView venueDescription;
    private RatingBar ratingBar;
    private Button location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_venue);

        venueContext = VenueContext.context(venueContext);

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

        setupOpeningHours();

        setupDay();

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
}
