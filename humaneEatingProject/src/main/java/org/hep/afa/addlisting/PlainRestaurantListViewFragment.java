package org.hep.afa.addlisting;

import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.hep.afa.R;
import org.hep.afa.addlisting.AddListingActivity;
import org.hep.afa.addlisting.PlainRestaurantListViewAdapter;
import org.hep.afa.model.HEPRestaurant;
import org.hep.afa.model.google.places.PlaceDetails;
import org.hep.afa.utils.LocationUtils;
import org.hep.afa.utils.google.places.PlaceSearchUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * This fragment class displays plain restaurant names as list view items
 * to help users easily add new Humane Restaurant through auto population add listings form.
 */
public class PlainRestaurantListViewFragment extends Fragment
{
    private static final String TAG = "PlainRestaurant";

    ListView lv;
    PlainRestaurantListViewAdapter adapter;
    ArrayList<HEPRestaurant> restaurantsList;
    ArrayList<HEPRestaurant> searchList;
    TextView emptyView;
    private ProgressBar loadingSpinner;
    private Handler handler;

    public PlainRestaurantListViewFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler = new Handler(getActivity().getMainLooper());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (container == null) { return null; }

        View v = inflater.inflate(R.layout.listview_restaurant_plain, container, false);
        this.emptyView = (TextView) v.findViewById(R.id.empty_restaurants);
        loadingSpinner = (ProgressBar) v.findViewById(R.id.loadingSpinner);
        this.lv = (ListView) v.findViewById(R.id.restaurant_list);
        this.restaurantsList = new ArrayList<HEPRestaurant>();
        this.searchList = new ArrayList<HEPRestaurant>();
        populateRestaurantList();

        /**
         * @TODO: In Future, consider separating out OnItemClickListener as a class. This might make
         * both listener and fragment objects reusable with higher behavior flexibility.
          */
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                HEPRestaurant restaurant = restaurantsList.get(position);

                // Each item click will lead to AddListingActivity with prepopulable field values.
                Intent myIntent = new Intent(getActivity(), AddListingActivity.class);
                Bundle extras = new Bundle();
                extras.putBoolean(getString(R.string.POPULATE), true);
                extras.putString(getString(R.string.RESTAURANT_NAME), restaurant.name());
                extras.putString(getString(R.string.OBJECT_ID), restaurant.getObjectId());
                extras.putString(getString(R.string.ADDRESS), restaurant.address1());
                extras.putString(getString(R.string.CITY), restaurant.city());
                extras.putString(getString(R.string.STATE), restaurant.state());
                extras.putString(getString(R.string.COUNTRY), restaurant.country());
                extras.putString(getString(R.string.POSTAL), restaurant.postalCode());
                extras.putString(getString(R.string.PHONE), restaurant.phone());
                extras.putString(getString(R.string.HOMEPAGE), restaurant.link());
                extras.putString(getString(R.string.EMAIL), restaurant.email());
                extras.putString(getString(R.string.FACEBOOK), restaurant.facebookPage());
                extras.putString(getString(R.string.TWITTER), restaurant.twitterPage());

                myIntent.putExtras(extras);
                startActivity(myIntent);
            }
        });

        return v;
    }

    private void populateRestaurantList()
    {
        adapter = new PlainRestaurantListViewAdapter(getActivity(),
                R.layout.row_restaurant_plain, restaurantsList);
        lv.setAdapter(adapter);

        new FindNearbyRestaurantsTask().execute();
    }

    private class FindNearbyRestaurantsTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {

                Location location = LocationUtils.getInstance().currentLocation();

                if (location != null) {

                    String latitude = String.valueOf(location.getLatitude());
                    String longitude = String.valueOf(location.getLongitude());

                    List<PlaceDetails> nearbyPlaces = PlaceSearchUtils.nearbySearch(latitude, longitude);

                    for (PlaceDetails place : nearbyPlaces) {
                        restaurantsList.add(HEPRestaurant.create(place));
                    }
                }

            } catch (Exception e) {
                Log.e(TAG, "Couldn't find local restarants", e);
            } finally {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        lv.setEmptyView(emptyView);
                        loadingSpinner.setVisibility(View.GONE);
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            return null;
        }
    }

}
