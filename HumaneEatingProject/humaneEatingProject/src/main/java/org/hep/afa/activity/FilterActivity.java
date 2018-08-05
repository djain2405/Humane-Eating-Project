package org.hep.afa.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.flurry.android.FlurryAgent;

import org.hep.afa.R;
import org.hep.afa.constant.CuisineType;
import org.hep.afa.constant.DistanceRange;
import org.hep.afa.constant.HumaneStatus;
import org.hep.afa.constant.PriceRange;
import org.hep.afa.constant.ReviewFilter;
import org.hep.afa.model.DataModel;

/**
 * Activity class for Restaurant Filter Screen.
 */
public class FilterActivity extends AppCompatActivity
{
    private final static String FILTER_PREFERENCE_KEY = "FILTER_PREFERENCE_KEY";
    private final static String HUMANE_STATUS = String.valueOf(R.string.humane_status);
    private final static String CUISINE_TYPE  = String.valueOf(R.string.cuisine_type);
    private final static String PRICE_RANGE = String.valueOf(R.string.price_range);
    private final static String DISTANCE_RANGE = String.valueOf(R.string.distance_range);
    private final static String RATING = String.valueOf(R.string.filter_rating);
    private final static String REVIEWS = String.valueOf(R.string.filter_reviews);

    Spinner spinnerCuisine;
    Spinner spinnerHumaneStatus;
    Spinner spinnerPrice;
    Spinner spinnerDistance;
    RatingBar ratingBar;
    Spinner spinnerReviews;
    Button updateResults;
    Button clearFilters;
    String cuisine;
    String humaneStatus;
    String price;
    String distance;
    String reviews;
    public static boolean filterOn = false;

    private void initializeViews() {
        this.updateResults = (Button) findViewById(R.id.buttonUpdateResults);
        this.clearFilters = (Button) findViewById(R.id.buttonClearFilters);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        initializeViews();

        // up navigation button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setCustomView(R.layout.action_bar_label_centre);
        actionBar.setDisplayShowCustomEnabled(true);
        TextView label = (TextView) findViewById(R.id.action_bar_title);
        label.setText(R.string.filter);

        spinnerCuisine = (Spinner) findViewById(R.id.spinnerCuisine);
//		String[] items = new String[]{"All", "African", "Ethiopian/Eritrean", "Ghanaian", "Moroccan", "North African", "South African", "West African", "American", "Burmese", "Cajun", "Cambodian", "Caribbean", "Central American", "Chinese", "Cantonese", "Dim Sum", "Hakka", "Hot Pot", "Hunanese", "Northern (Beijing)", "Szechuan", "Taiwanese", "Deli", "Eclectic (Fusion)", "Eclectic (Varied)", "English", "Filipino", "French", "German", "Greek", "Hmong", "Hungarian", "Indian", "Indian (Northern)", "Indian (Southern)", "Indo-Chinese", "Indonesian", "Italian", "Italian (Northern)", "Italian (Southern)", "Pizza", "Japanese", "Jewish (Eastern European)", "Greek"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_dropdown_textview, R.id.spinnerItemStyle, CuisineType.names());
        spinnerCuisine.setAdapter(adapter);

        spinnerHumaneStatus = (Spinner) findViewById(R.id.spinnerHumaneStatus);
//		String[] itemsHumaneStatus = new String[]{"All", "Vegan-Friendly", "Vegan", "Vegetarian-Friendly", "Vegetarian", "Humane-Friendly", "Watch List"};
        ArrayAdapter<String> adapterHumaneStatus = new ArrayAdapter<String>(this, R.layout.spinner_dropdown_textview, R.id.spinnerItemStyle, HumaneStatus.names());
        spinnerHumaneStatus.setAdapter(adapterHumaneStatus);

        spinnerPrice = (Spinner) findViewById(R.id.spinnerPrice);
//		String[] itemsPrice = new String[]{"All", "$-InExpensive", "$$-Average", "$$$-Expensive"};
        ArrayAdapter<String> adapterPrice = new ArrayAdapter<String>(this, R.layout.spinner_dropdown_textview, R.id.spinnerItemStyle, PriceRange.names());
        spinnerPrice.setAdapter(adapterPrice);

        spinnerDistance = (Spinner) findViewById(R.id.spinnerDistance);
//		String[] itemsDistance = new String[]{"All", "1 mi", "3 mi", "5 mi", "10 mi", "15 mi"};
        ArrayAdapter<String> adapterDistance = new ArrayAdapter<String>(this, R.layout.spinner_dropdown_textview, R.id.spinnerItemStyle, DistanceRange.names());
        spinnerDistance.setAdapter(adapterDistance);

        spinnerReviews = (Spinner) findViewById(R.id.spinnerReviews);
        ArrayAdapter<String> adapterReviews = new ArrayAdapter<String>(this, R.layout.spinner_dropdown_textview, R.id.spinnerItemStyle, ReviewFilter.names());
        spinnerReviews.setAdapter(adapterReviews);

        ratingBar = (RatingBar) findViewById(R.id.ratings);

        //Update Results
        updateResults.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                updateFilters();
                finish();
            }
        });

        //Clear Filters
        clearFilters.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                spinnerCuisine.setSelection(0);
                spinnerDistance.setSelection(0);
                spinnerHumaneStatus.setSelection(0);
                spinnerPrice.setSelection(0);
                spinnerReviews.setSelection(0);
                DataModel.ct = CuisineType.ALL;
                DataModel.hs = HumaneStatus.ALL;
                DataModel.pr = PriceRange.ALL;
                DataModel.distance = DataModel.DEFAULT_DISTANCE;
                DataModel.reviewFilter = ReviewFilter.ALL;
                DataModel.rating = 0;

                filterOn = false;
                finish();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = getSharedPreferences(FILTER_PREFERENCE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(HUMANE_STATUS, spinnerHumaneStatus.getSelectedItemPosition());
        editor.putInt(CUISINE_TYPE, spinnerCuisine.getSelectedItemPosition());
        editor.putInt(PRICE_RANGE, spinnerPrice.getSelectedItemPosition());
        editor.putInt(DISTANCE_RANGE, spinnerDistance.getSelectedItemPosition());
        editor.putInt(REVIEWS, spinnerReviews.getSelectedItemPosition());
        editor.putInt(RATING, DataModel.rating);
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prfs = getSharedPreferences(FILTER_PREFERENCE_KEY, Context.MODE_PRIVATE);
        int HumaneStatus = prfs.getInt(HUMANE_STATUS, 0);
        spinnerHumaneStatus.setSelection(HumaneStatus);

        int CuisineType = prfs.getInt(CUISINE_TYPE, 0);
        spinnerCuisine.setSelection(CuisineType);

        int PriceRange = prfs.getInt(PRICE_RANGE, 0);
        spinnerPrice.setSelection(PriceRange);

        int DistanceRange = prfs.getInt(DISTANCE_RANGE, 0);
        spinnerDistance.setSelection(DistanceRange);

        int numReviews = prfs.getInt(REVIEWS, 0);
        spinnerReviews.setSelection(numReviews);

        int numRatings = prfs.getInt(RATING, 0);
        ratingBar.setRating(numRatings);
    }


    /**
     * @TODO: Consider removing filter enums and iterators inside, and replacing it with ArraySet or ArrayMap.
     */
    public void updateFilters() {

        filterOn = false;  // reset the filter

        // Section: Cusine Type
        if (spinnerCuisine.getSelectedItem().toString() != null) {
            cuisine = spinnerCuisine.getSelectedItem().toString();
            String s = cuisine.toUpperCase();
            CuisineType[] ctVal = CuisineType.values();
            for (CuisineType ctf : ctVal) {
                if (ctf.toString().equals(cuisine)) {
                    if(!cuisine.equals(CuisineType.ALL.toString())) { filterOn = true; }
                    DataModel.ct = CuisineType.valueOf(s.replaceAll("\\W", ""));
                }
            }

        }

        // Section: Humane Status
        if (spinnerHumaneStatus.getSelectedItem().toString() != null) {
            humaneStatus = spinnerHumaneStatus.getSelectedItem().toString();
            String s = humaneStatus.toUpperCase();
            HumaneStatus[] hsVal = HumaneStatus.values();

            for (HumaneStatus hsf : hsVal) {
                if (hsf.toString().equals(humaneStatus)) {
                    if(!humaneStatus.equals(HumaneStatus.ALL.toString())) { filterOn = true; }
                    DataModel.hs = HumaneStatus.valueOf(s.replaceAll("\\W", ""));
                }
            }


        }

        // Section: PriceRange
        if (spinnerPrice.getSelectedItem().toString() != null) {
            price = spinnerPrice.getSelectedItem().toString();
            String s = price.toUpperCase();

            if (price.equalsIgnoreCase(PriceRange.fromString(price))) {
                if(!price.equals(PriceRange.ALL.toString())) { filterOn = true; }
                DataModel.pr = PriceRange.valueOf(s.replaceAll("\\W", ""));
            }
        }

        // Section: Distance
        if (spinnerDistance.getSelectedItem().toString() != "All") {
            distance = spinnerDistance.getSelectedItem().toString();
            if (distance.equalsIgnoreCase(DistanceRange.fromString(distance))) {
                DataModel.distance = DistanceRange.intVal(distance);
                filterOn = true;
            }
        } else {
            DataModel.distance = DataModel.DEFAULT_DISTANCE;
        }

        // Section: Reviews
        reviews = spinnerReviews.getSelectedItem().toString();
        DataModel.reviewFilter = ReviewFilter.getReviewFilter(reviews);
        if (DataModel.reviewFilter != ReviewFilter.ALL) {
            filterOn = true;
        }

        // Section: Ratings
        DataModel.rating = (int)ratingBar.getRating();
        if (DataModel.rating > 0) {
            filterOn = true;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    //Flurry Setup
    @Override
    protected void onStart() {
        super.onStart();
        FlurryAgent.onStartSession(this, "PJG4THZ828X649K3VFXG");
    }

    @Override
    protected void onStop() {
        super.onStop();
        FlurryAgent.onEndSession(this);
    }

}
