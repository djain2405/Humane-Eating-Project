package org.hep.afa.utils.google.places;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;

import org.hep.afa.model.google.places.NearbySearch;
import org.hep.afa.model.google.places.PlaceDetails;

import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PlaceSearchUtils {
    private static final String TAG = "PlaceSearchUtils";

    private static final String PHOTO_BASE_URL = "https://maps.googleapis.com/maps/api/place/photo";
    private static final String PLACE_DETAILS_BASE_URL = "https://maps.googleapis.com/maps/api/place/details/json";
    private static final String NEARBY_SEARCH_BASE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json";

    private static final OkHttpClient httpClient = new OkHttpClient();

    private static String googleMapsApiKey;

    public static void initialize(Context context) {
        if (googleMapsApiKey == null) {
            try {
                ApplicationInfo info = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                        PackageManager.GET_META_DATA);
                Bundle metaDataBundle = info.metaData;
                googleMapsApiKey = metaDataBundle.getString("com.google.android.maps.v2.API_KEY");
            } catch (PackageManager.NameNotFoundException nnfe) {
                // this will not happen because Android is building the package name
            }
        }
    }

    public static String buildPhotoUrl(String photoReference) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(PHOTO_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter("maxwidth", "300");
        urlBuilder.addQueryParameter("key", googleMapsApiKey);
        urlBuilder.addQueryParameter("photoreference", photoReference);

        return urlBuilder.toString();
    }

    public static Request buildPlaceDetailsRequest(String placeId) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(PLACE_DETAILS_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter("placeid", placeId);
        urlBuilder.addQueryParameter("key", googleMapsApiKey);
        String url = urlBuilder.toString();

        return new Request.Builder().url(url).build();
    }

    public static Request buildNearbySearchRequestByDistance(String latitude, String longitude) {
        HttpUrl.Builder urlBuilder = buildNearbySearchUrlUsingLatLng(latitude, longitude);
        urlBuilder.addQueryParameter("rankby", "distance");

        return new Request.Builder().url(urlBuilder.toString()).build();
    }

    public static Request buildNearbySearchRequestByName(String latitude, String longitude, String name) {
        HttpUrl.Builder urlBuilder = buildNearbySearchUrlUsingLatLng(latitude, longitude);
        urlBuilder.addQueryParameter("name", String.format("\"%s\"", name));
        urlBuilder.addQueryParameter("radius", "500");

        return new Request.Builder().url(urlBuilder.toString()).build();
    }

    @NonNull
    public static List<PlaceDetails> nearbySearch(String latitude, String longitude) throws Exception {
        List<PlaceDetails> places = new ArrayList<>(20);

        Request nearbySearchRequest = buildNearbySearchRequestByDistance(latitude, longitude);
        Response nearbySearchResponse = httpClient.newCall(nearbySearchRequest).execute();

        NearbySearch nearbySearch = new NearbySearch(nearbySearchResponse.body().string());

        for(String placeId: nearbySearch.getPlaceIds()) {
            Request placeDetailsRequest = buildPlaceDetailsRequest(placeId);
            Response placeDetailsResponse = httpClient.newCall(placeDetailsRequest).execute();

            PlaceDetails place = new PlaceDetails(placeDetailsResponse.body().string());
            places.add(place);
        }

        return places;
    }

    private static HttpUrl.Builder buildNearbySearchUrlUsingLatLng(String latitude, String longitude) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(NEARBY_SEARCH_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter("key", googleMapsApiKey);
        urlBuilder.addQueryParameter("location", String.format("%s,%s", latitude, longitude));
        urlBuilder.addQueryParameter("types", "food");

        return urlBuilder;
    }
}
