package org.hep.afa.model.google.places;


import android.support.annotation.NonNull;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.hep.afa.constant.PlaceSearchStatusCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

// https://developers.google.com/places/web-service/search#PlaceSearchResponses
public class NearbySearch implements Iterable<JsonElement>{
    private static final String TAG = "NearbySearch";

    private JsonObject root;
    private List<String> placeIds = new ArrayList<String>(20);
    private PlaceSearchStatusCode status;

    public NearbySearch(String json) {
        parseRoot(json);
        parseStatusCode();
        parsePlaceIds();
    }

    /**
     * Get the Google "Place ID" for each restaurant.
     * @return the Google "Place ID" for each restaurant, or an empty list if no restaurants
     */
    @NonNull
    public List<String> getPlaceIds() {
        return Collections.unmodifiableList(placeIds);
    }

    public PlaceSearchStatusCode getStatus() {
        return status;
    }

    private void parsePlaceIds() {
        if (status == PlaceSearchStatusCode.OK) {
            Iterator<JsonElement> iterator = iterator();
            while (iterator.hasNext()) {
                JsonObject result = iterator.next().getAsJsonObject();
                String placeId1 = result.get("place_id").getAsString();

                placeIds.add(placeId1);
            }
        }
    }

    private void parseStatusCode() {
        String statusStr = root.get("status").getAsString();
        status = PlaceSearchStatusCode.valueOf(statusStr);
    }

    private void parseRoot(String json) {
        JsonParser parser = new JsonParser();
        root = parser.parse(json).getAsJsonObject();
    }

    @Override
    public String toString() {
        return String.format("Status: %s, Place ID: %s", getStatus().name(), placeIds);
    }

    @Override
    public Iterator<JsonElement> iterator() {
        return root.getAsJsonArray("results").iterator();
    }
}
