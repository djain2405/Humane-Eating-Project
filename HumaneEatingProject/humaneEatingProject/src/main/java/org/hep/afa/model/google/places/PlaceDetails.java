package org.hep.afa.model.google.places;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.hep.afa.constant.PlaceSearchStatusCode;

import java.util.ArrayList;
import java.util.List;


/**
 * A Google Places API <a href="https://developers.google.com/places/web-service/details#PlaceDetailsResponses">PlaceDetails</a> object.
 */
public class PlaceDetails {
    private PlaceSearchStatusCode status;
    private String name;
    private String streetNumber;
    private String streetName;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String phone;
    private List<String> photoReferences = new ArrayList<>(10);

    public PlaceDetails(String json) {
        JsonParser parser = new JsonParser();
        JsonObject root = parser.parse(json).getAsJsonObject();

        parseStatusCode(root);
        if (status == PlaceSearchStatusCode.OK) {
            JsonObject result = root.get("result").getAsJsonObject();

            parseName(result);
            parseAddress(result);
            parsePhone(result);
            parsePhotoReferences(result);
        }
    }

    public PlaceSearchStatusCode getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getStreet() {
        return streetNumber + " " + streetName;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountry() {
        return country;
    }

    public String getPhoneNumber() {
        return phone;
    }

    public List<String> getPhotoReferences() {
        return photoReferences;
    }

    private void parseStatusCode(JsonObject root) {
        String statusStr = root.get("status").getAsString();
        status = PlaceSearchStatusCode.valueOf(statusStr);
    }

    private void parseName(JsonObject result) {
        name = result.get("name").getAsString();
    }

    private void parseAddress(JsonObject result) {
        JsonArray addressComponents = result.getAsJsonArray("address_components");
        for(int i = 0; i < addressComponents.size(); i++) {
            JsonObject addressComponent = addressComponents.get(i).getAsJsonObject();
            String type = addressComponent.get("types").getAsJsonArray().get(0).getAsString();

            switch (type) {
                case "street_number":
                    streetNumber = addressComponent.get("long_name").getAsString();
                    break;
                case "route": // street name
                    streetName = addressComponent.get("long_name").getAsString();
                    break;
                case "locality": // city
                    city = addressComponent.get("long_name").getAsString();
                    break;
                case "administrative_area_level_1": // state
                    state = addressComponent.get("short_name").getAsString();
                    break;
                case "postal_code":
                    postalCode = addressComponent.get("long_name").getAsString();
                    break;
                case "country":
                    country = addressComponent.get("short_name").getAsString();
                    break;
            }
        }
    }

    private void parsePhone(JsonObject result) {
        if (result.has("formatted_phone_number")) {
            phone = result.get("formatted_phone_number").getAsString();
        }
    }

    private void parsePhotoReferences(JsonObject result) {
        if (result.has("photos")) {
            JsonArray photos = result.get("photos").getAsJsonArray();

            for(int i = 0; i < photos.size(); i++) {
                JsonObject photo = photos.get(i).getAsJsonObject();
                String photoReference = photo.get("photo_reference").getAsString();
                photoReferences.add(photoReference);
            }
        }
    }

    @Override
    public String toString() {
        return String.format("Status: %s, Name: %s, Photo references: %d", status.name(), name,
                photoReferences.size());
    }
}
