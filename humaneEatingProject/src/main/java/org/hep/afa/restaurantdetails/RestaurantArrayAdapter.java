package org.hep.afa.restaurantdetails;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.geofire.GeoLocation;

import org.hep.afa.R;
import org.hep.afa.model.HEPRestaurant;
import org.hep.afa.utils.ColorUtils;
import org.hep.afa.utils.GeneralUtils;
import org.hep.afa.utils.LocationUtils;

import java.util.List;

import static android.R.attr.description;

/**
 * RestaurantArrayAdapter class.
 */
public class RestaurantArrayAdapter extends ArrayAdapter<HEPRestaurant>
{
    List<HEPRestaurant> restaurantContainer;
    int resource;

    public RestaurantArrayAdapter(Context context, int resource, List<HEPRestaurant> objects) {
        super(context, resource, objects);
        this.restaurantContainer = objects;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        Context context = getContext();
        HEPRestaurant restaurant = restaurantContainer.get(position);

        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(resource, null);
            holder = new ViewHolder();
            holder.imageHumaneStatus = (ImageView) convertView.findViewById(R.id.humane_status);
            holder.imageHumaneStatus2 = (ImageView) convertView.findViewById(R.id.humane_status2);
            holder.imageHumaneStatusWatchlist = convertView.findViewById(R.id.humane_status_watchlist);
            holder.restaurantName = (TextView) convertView.findViewById(R.id.restaurant_name);
            holder.restaurantDescription = (TextView) convertView.findViewById(R.id.restaurant_description);
            holder.cusine = (TextView) convertView.findViewById(R.id.cuisine);
            holder.numReviews = (TextView) convertView.findViewById(R.id.ratings_count);
            holder.priceRange = (TextView) convertView.findViewById(R.id.price_range);
            holder.expense = (TextView) convertView.findViewById(R.id.expense);
            holder.ratingBar = (ImageView) convertView.findViewById(R.id.rating_bar);

            holder.distance = (TextView) convertView.findViewById(R.id.distance);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String humaneStatus = restaurant.humaneStatus();
        boolean isWatchlist = GeneralUtils.isWatchList(context, humaneStatus);

        holder.restaurantName.setText(restaurant.name());

        // Limit what we show for watchlists
        if (isWatchlist) {

            holder.restaurantDescription.setVisibility(View.GONE);

            holder.ratingBar.setVisibility(View.GONE);


            holder.priceRange.setVisibility(View.GONE);
            holder.expense.setVisibility(View.GONE);
            holder.numReviews.setVisibility(View.GONE);

            holder.imageHumaneStatusWatchlist.setVisibility(View.VISIBLE);
            holder.imageHumaneStatus.setVisibility(View.GONE);
            holder.imageHumaneStatus2.setImageResource(0);
            holder.cusine.setVisibility(View.GONE);
        }
        else {

            String description = restaurant.description();
            if (description == null || description.isEmpty()) {
                holder.restaurantDescription.setVisibility(View.GONE);
            }
            else {
                holder.restaurantDescription.setText(description);
                holder.restaurantDescription.setVisibility(View.VISIBLE);
            }

            String cuisine = restaurant.cuisine1();
            if (cuisine == null || cuisine.isEmpty() ||
                cuisine.equalsIgnoreCase(context.getString(R.string.invalid_cuisine))) {
                holder.cusine.setVisibility(View.GONE);
            }
            else {
                holder.cusine.setText(cuisine);
                holder.cusine.setVisibility(View.VISIBLE);
            }

            // Set Rating bar
            float rating = restaurant.yelpRating().floatValue();
            if (rating != 0) {
                if(rating == 5)
                {
                    holder.ratingBar.setBackgroundResource(R.drawable.stars_small_5);

                }
                else if (rating < 5 && rating > 4)
                {
                    holder.ratingBar.setBackgroundResource(R.drawable.stars_small_4_half);
                }
                else if (rating == 4)
                {
                    holder.ratingBar.setBackgroundResource(R.drawable.stars_small_4);

                }
                else if (rating < 4 && rating > 3)
                {
                    holder.ratingBar.setBackgroundResource(R.drawable.stars_small_3_half);
                }
                else if (rating == 3)
                {
                    holder.ratingBar.setBackgroundResource(R.drawable.stars_small_3);
                }
                else if (rating < 3 && rating > 2)
                {
                    holder.ratingBar.setBackgroundResource(R.drawable.stars_small_2_half);
                }
                else if (rating == 2)
                {
                    holder.ratingBar.setBackgroundResource(R.drawable.stars_small_2);
                }
                else if (rating < 2 && rating > 1)
                {
                    holder.ratingBar.setBackgroundResource(R.drawable.stars_small_1_half);
                }
                else if (rating == 1)
                {
                    holder.ratingBar.setBackgroundResource(R.drawable.stars_small_1);
                }

                holder.ratingBar.setVisibility(View.VISIBLE);
            } else {
                holder.ratingBar.setVisibility(View.GONE);
                            }

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.cusine.getLayoutParams();
            if(holder.ratingBar.getVisibility() == View.VISIBLE)
                params.addRule(RelativeLayout.BELOW, R.id.rating_bar);


            // Set number of reviews
            if(restaurant.yelpReviewCount() != 0) {
                holder.numReviews.setText(restaurant.yelpReviewCount() + " Yelp reviews");
            } else {
                holder.numReviews.setText("");
            }
            holder.numReviews.setVisibility(View.VISIBLE);

            //Set price and expenses
            if(restaurant.priceRangeNumber()==null) {
                holder.priceRange.setText("");
                holder.expense.setText("");
            } else if(restaurant.priceRangeNumber().equals("1")) {
                holder.priceRange.setText("$");
                holder.expense.setText("Inexpensive");
            } else if(restaurant.priceRangeNumber().equals("2")) {
                holder.priceRange.setText("$$");
                holder.expense.setText("Average");
            } else if(restaurant.priceRangeNumber().equals("3")) {
                holder.priceRange.setText("$$$");
                holder.expense.setText("Expensive");
            } else {
                holder.priceRange.setText("");
                holder.expense.setText("");
            }
            holder.priceRange.setVisibility(View.VISIBLE);
            holder.expense.setVisibility(View.VISIBLE);

            holder.imageHumaneStatus.setImageResource(getHumaneStatusMarker(humaneStatus));
            holder.imageHumaneStatusWatchlist.setVisibility(View.GONE);
            holder.imageHumaneStatus.setVisibility(View.VISIBLE);

            //Set different markers based on human status2
            String humaneStatus2 = restaurant.humaneStatus2();
            Boolean isSponsored = restaurant.getIsSponsored();

            if (humaneStatus2 == null) {
                holder.imageHumaneStatus2.setImageResource(0);
            }
            else {
                if (isSponsored) {
                    holder.imageHumaneStatus2.setImageResource(getSponsoredHumaneStatusMarker(humaneStatus2));
                }
                else {
                    holder.imageHumaneStatus2.setImageResource(getHumaneStatusMarker(humaneStatus2));
                }
            }
        }

        // Set the distance from current location to the restaurant.
        GeoLocation restaurantGeoLocation = restaurant.geoLocation();
        holder.distance.setText(LocationUtils.getInstance().distanceInMilesString(restaurantGeoLocation));

        convertView.setBackgroundColor(ColorUtils.alternateColor(position));
        return convertView;
    }

    private static class ViewHolder
    {
        public ImageView imageHumaneStatus;
        public ImageView imageHumaneStatus2;
        public View imageHumaneStatusWatchlist;
        public TextView restaurantName;
        public TextView restaurantDescription;
        public TextView cusine;
        public TextView numReviews;
        public TextView priceRange;
        public TextView expense;
        public ImageView ratingBar;

        public TextView distance;
    }


    /**
     * Determines the correct Resource ID for the specified Humane Status
     *
     * @param humaneStatus  String representation of humane status, this should be non-null &
     *                      non-empty
     *
     * @return Resource ID for the drawable that matches the specified Humane Status
     */
    private int getHumaneStatusMarker(String humaneStatus) {

        Context context = getContext();

        if (humaneStatus.equals(context.getString(R.string.status_vegetarian))          ||
            humaneStatus.equals(context.getString(R.string.status_vegetarian_friendly)) ||
            humaneStatus.equals(context.getString(R.string.status_vegan))               ||
            humaneStatus.equals(context.getString(R.string.status_vegan_friendly))) {
            return R.drawable.pin_green2x;
        }

        if (humaneStatus.equals(context.getString(R.string.status_humane_friendly))) {
            return R.drawable.pin_brown2x;
        }

        if (humaneStatus.equals(context.getString(R.string.status_watch_list))) {
            return R.drawable.pin_red2x;
        }

        return 0;
    }

    /**
     * Determines the correct Resource ID for the specified Humane Status with sponsor
     *
     * @param humaneStatus  String representation of humane status, this should be non-null &
     *                      non-empty
     *
     * @return Resource ID for the drawable that matches the specified Humane Status sponsor
     */
    private int getSponsoredHumaneStatusMarker(String humaneStatus) {

        Context context = getContext();

        if (humaneStatus.equals(context.getString(R.string.status_vegetarian))          ||
            humaneStatus.equals(context.getString(R.string.status_vegetarian_friendly)) ||
            humaneStatus.equals(context.getString(R.string.status_vegan))               ||
            humaneStatus.equals(context.getString(R.string.status_vegan_friendly))) {
            return R.drawable.pin_green2x_starred;
        }

        if (humaneStatus.equals(context.getString(R.string.status_humane_friendly))) {
            return R.drawable.pin_brown2x_starred;
        }

        if (humaneStatus.equals(context.getString(R.string.status_watch_list))) {
            return R.drawable.pin_red2x_starred;
        }

        return 0;
    }
}
