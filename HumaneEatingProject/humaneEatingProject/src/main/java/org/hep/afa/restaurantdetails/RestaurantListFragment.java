package org.hep.afa.restaurantdetails;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.geofire.GeoLocation;

import org.hep.afa.R;
import org.hep.afa.main.SearchQuery;
import org.hep.afa.addlisting.AutoAddListingActivity;
import org.hep.afa.activity.FilterActivity;
import org.hep.afa.activity.LegendActivity;
import org.hep.afa.model.HEPRestaurant;
import org.hep.afa.utils.LocationUtils;

import java.util.Comparator;

/**
 * Restaurant ListView Fragment class.
 */
public class RestaurantListFragment extends Fragment implements View.OnClickListener
{
    private static final String TAG = "RestaurantListFragment";
    private ListView list;
    private RestaurantArrayAdapter adapter;
    private RelativeLayout emptyView;
    private TextView filterOn;
    private TextView filterOnEmpty;

    public RestaurantListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        View v = inflater.inflate(R.layout.listview_fragment, container, false);

        emptyView = (RelativeLayout) v.findViewById(R.id.empty_restaurants);

        // List of Restaurants to listView
        list = (ListView) v.findViewById(R.id.list_restaurants);
        View headerView = inflater.inflate(R.layout.list_header_restaurant_details, list, false);
        initListHeader(headerView, emptyView);
        list.addHeaderView(headerView, null, false);

        adapter = new RestaurantArrayAdapter(getActivity(), R.layout.list_row, SearchQuery.getInstance().get());
        refreshRestaurantList();
        list.setAdapter(adapter);
        list.setEmptyView(emptyView);

        list.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                HEPRestaurant restaurant = (HEPRestaurant) list.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), RestaurantDetailsActivity.class);
                intent.putExtra(RestaurantDetailsActivity.ARG_RESTAURANT, restaurant);
                intent.putExtra(RestaurantDetailsActivity.ARG_MILES,
                    LocationUtils.getInstance().distanceInMilesString(restaurant.geoLocation()));

                startActivity(intent);

            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        filterToggle();
    }

    public void refreshRestaurantList() {
        Location currentLocation = LocationUtils.getInstance().currentLocation();
        DistanceComparator comparator = new DistanceComparator(currentLocation);
        adapter.sort(comparator);
        adapter.notifyDataSetChanged();
    }

    private void filterToggle() {
        if(FilterActivity.filterOn) {
            filterOn.setVisibility(View.VISIBLE);
            filterOnEmpty.setVisibility(View.VISIBLE);
        }
        else {
            filterOn.setVisibility(View.GONE);
            filterOnEmpty.setVisibility(View.GONE);
        }
    }


    // initializes the header for both list view and empty list view
    private void initListHeader(View headerView, View emptyView) {

        filterOn = (TextView) headerView.findViewById(R.id.filter_on);
        filterOnEmpty = (TextView) emptyView.findViewById(R.id.filter_on);

        // Filter
        (headerView.findViewById(R.id.button_filter)).setOnClickListener(this);
        (emptyView.findViewById(R.id.button_filter)).setOnClickListener(this);

        // Add Listing
        (headerView.findViewById(R.id.button_add_listing)).setOnClickListener(this);
        (emptyView.findViewById(R.id.button_add_listing)).setOnClickListener(this);

        // Legend
        (headerView.findViewById(R.id.button_legend)).setOnClickListener(this);
        (emptyView.findViewById(R.id.button_legend)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.button_filter:
                Intent filterActivity = new Intent(getActivity(), FilterActivity.class);
                getActivity().startActivityForResult(filterActivity, 1);
                break;

            case R.id.button_add_listing:
                startActivity(new Intent(getActivity(),AutoAddListingActivity.class));
                break;

            case R.id.button_legend:
                startActivity(new Intent(getActivity(), LegendActivity.class));
                break;
        }
    }

    private class DistanceComparator implements Comparator<HEPRestaurant> {
        private GeoLocation currentGeoLocation = null;

        public DistanceComparator(Location location) {
            if (location != null) {
                currentGeoLocation = new GeoLocation(location.getLatitude(), location.getLongitude());
            }
        }

        @Override
        public int compare(HEPRestaurant r1, HEPRestaurant r2) {

            if (currentGeoLocation != null) {
                Double r1Distance = LocationUtils.getInstance().distanceInMilesBetween(currentGeoLocation, r1.geoLocation());
                Double r2Distance = LocationUtils.getInstance().distanceInMilesBetween(currentGeoLocation, r2.geoLocation());

                return r1Distance.compareTo(r2Distance);
            } else {
                return 0;
            }

        }
    }

}
