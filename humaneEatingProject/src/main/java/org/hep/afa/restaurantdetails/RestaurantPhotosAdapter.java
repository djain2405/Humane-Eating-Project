package org.hep.afa.restaurantdetails;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import org.hep.afa.R;
import org.hep.afa.model.HEPRestaurant;
import org.hep.afa.model.google.places.NearbySearch;
import org.hep.afa.model.google.places.PlaceDetails;
import org.hep.afa.utils.google.places.PlaceSearchUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RestaurantPhotosAdapter extends PagerAdapter {
    private static final String TAG = "RestaurantPhotosAdapter";

    private ArrayList<String> imageUrls = new ArrayList<String>(10);

    private Context context;
    private Handler handler;
    private OkHttpClient httpClient = new OkHttpClient();
    private LayoutInflater inflater;
    private HEPRestaurant restaurant;

    public RestaurantPhotosAdapter(Context context, HEPRestaurant restaurant) {
        this.context = context;
        this.restaurant = restaurant;

        handler = new Handler(context.getMainLooper());
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageUrls.add(restaurant.yelpImageURL());

        new LoadRestaurantImagesTask().execute();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View holder = inflater.inflate(R.layout.restaurant_details_carousel_image_holder, container, false);

        String url = imageUrls.get(position);

        if(!TextUtils.isEmpty(url)) {
            ImageView image = (ImageView) holder.findViewById(R.id.restaurant_details_carousel_image);
            Glide.with(context).load(url).centerCrop().into(image);
        }

        container.addView(holder);
        return holder;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (LinearLayout) object;
    }

    private class LoadRestaurantImagesTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String placeId = findPlaceId();

                if (placeId != null) {
                    List<String> photoReferences = findPhotoReferences(placeId);
                    for (String photoReference : photoReferences) {
                        imageUrls.add(PlaceSearchUtils.buildPhotoUrl(photoReference));
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            notifyDataSetChanged();
                        }
                    });
                }
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }

            return null;
        }

        @Nullable
        private String findPlaceId() throws  IOException {
            String latitude = String.valueOf(restaurant.latitude());
            String longitude = String.valueOf(restaurant.longitude());

            Request nearbySearchRequest = PlaceSearchUtils.buildNearbySearchRequestByName(latitude, longitude, restaurant.name());
            Response nearbySearchResponse = httpClient.newCall(nearbySearchRequest).execute();
            String responseBody = nearbySearchResponse.body().string();
            NearbySearch nearbySearch = new NearbySearch(responseBody);
            List<String> nearbyPlaceIds = nearbySearch.getPlaceIds();

            if (nearbyPlaceIds.size() != 0) {
                return nearbyPlaceIds.get(0);
            } else {
                return null;
            }

        };

        private List<String> findPhotoReferences(String placeId) throws IOException {
            Request placeDetailsRequest = PlaceSearchUtils.buildPlaceDetailsRequest(placeId);
            Response placeDetailsResponse = httpClient.newCall(placeDetailsRequest).execute();
            String responseBody = placeDetailsResponse.body().string();
            PlaceDetails placeDetails = new PlaceDetails(responseBody);
            return placeDetails.getPhotoReferences();
        };
    }
}
