package org.hep.afa.model;

import android.text.TextUtils;
import android.util.Log;

import com.firebase.geofire.GeoLocation;
import com.google.firebase.database.DataSnapshot;

import org.hep.afa.constant.HEPConstants;
import org.hep.afa.model.google.places.PlaceDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HEPRestaurant implements Serializable
{
    private String key;
    private String name;
    private transient GeoLocation geoLocation;
    private double latitude;
    private double longitude;
    private String address1;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String phone;
    private String email;
    private String homepage;
    private String link;
    private String description;
    private String longDescription;
    private String humaneStatus;
    private String humaneStatus2;
    private String humaneReason;    // humane_reason
    private String priceRange;
    private String priceRangeNumber;
    private String cuisine1;
    private String cuisine2;
    private String cuisine3;
    private String isCashOnly;
    private String issue1;
    private String allowSmoking;
    private String features; // feature1 ~ feature10
    private String submittedBy; // submitted_by
    private double googleReviewScore;  // this has been removed in Firebase
    private int  googleReviewNums;  // this has been removed in Firebase
    private String googleId;  // this has been removed in Firebase
    private int yelpReviewCount;
    private String yelpImageURL;
    private double yelpRating;
    private String yelpId;
    private String facebookPage;
    private String twitterPage;
    private Boolean isSponsored;
    private String menuUrl;

    private String objectId;
    private String locationId;

    public void key(String key) { this.key = key; }

    public String key() { return key; }

    public String facebookPage() { return facebookPage; }

    public void facebookPage(String facebookPage) { this.facebookPage = facebookPage; }

    public String twitterPage() { return twitterPage; }

    public void twitterPage(String twitterPage) { this.twitterPage = twitterPage; }

    public String getObjectId() { return objectId; }

    public void setObjectId(String objectId) { this.objectId = objectId; }

    public String locationId() { return locationId; }

    public void locationId(String locationId) { this.locationId = locationId; }

    public String googleId() { return googleId; }

    public void googleId(String googleId) { this.googleId = googleId; }

    public Double googleReviewScore() { return googleReviewScore; }

    public void googleReviewScore(Double googleReviewScore) { this.googleReviewScore = googleReviewScore; }

    public int googleReviewNums() { return googleReviewNums; }

    public void googleReviewNums(int googleReviewNums) { this.googleReviewNums = googleReviewNums; }

    public int yelpReviewCount() { return yelpReviewCount; }

    public void yelpReviewCount(int yelpReviewCount) { this.yelpReviewCount = yelpReviewCount; }

    public String yelpImageURL() { return yelpImageURL; }

    public void yelpImageURL(String yelpImageURL) { this.yelpImageURL = yelpImageURL; }

    public Double yelpRating() { return yelpRating; }

    public void yelpRating(Double yelpRating) { this.yelpRating = yelpRating; }

    public String yelpId() { return yelpId; }

    public void yelpId(String yelpId) { this.yelpId = yelpId; }

    public String cuisine1() { return cuisine1; }

    public void cuisine1(String cuisine1) { this.cuisine1 = cuisine1; }

    public String cuisine2() { return cuisine2; }

    public void cuisine2(String cuisine2) { this.cuisine2 = cuisine2; }

    public String cuisine3() { return cuisine3; }

    public void cuisine3(String cuisine3) { this.cuisine3 = cuisine3; }

    public String otherCuisines() {
        StringBuilder sb = new StringBuilder();
        if(!TextUtils.isEmpty(this.cuisine2)) {
            sb.append(this.cuisine2).append(", ");
        }
        if(!TextUtils.isEmpty(this.cuisine3)) {
            sb.append(this.cuisine3);
        }
        return sb.toString();
    }

    public String features() { return features; }

    public void features(String features) { this.features = features; }

    public String cashOnly() { return isCashOnly; }

    public void cashOnly(String isCashOnly) { this.isCashOnly = isCashOnly; }

    public String allowSmoking() { return allowSmoking; }

    public void allowSmoking(String allowSmoking) { this.allowSmoking = allowSmoking; }

    public String submittedBy() { return submittedBy; }

    public void submittedBy(String submittedBy) { this.submittedBy = submittedBy; }

    public String priceRange() { return priceRange; }

    public void priceRange(String priceRange) { this.priceRange = priceRange; }

    public String priceRangeNumber() { return priceRangeNumber; }

    public void priceRangeNumber(String priceRangeNumber) { this.priceRangeNumber = priceRangeNumber; }

    public String humaneStatus() { return humaneStatus; }

    public void humaneStatus(String humaneStatus) { this.humaneStatus = humaneStatus; }

    public String humaneStatus2() { return humaneStatus2; }

    public void humaneStatus2(String humaneStatus2) { this.humaneStatus2 = humaneStatus2; }

    public String humaneReason() { return humaneReason; }

    public void humaneReason(String humaneReason) { this.humaneReason = humaneReason; }

    public String address1() { return address1; }

    public void address1(String address1) { this.address1 = address1; }

    public String city() { return city; }

    public void city(String city) { this.city = city; }

    public String state() { return state; }

    public void state(String state) { this.state = state; }

    public String country() { return country; }

    public void country(String country) { this.country = country; }

    public String postalCode() {
        return postalCode;
    }

    public void postalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String phone() {
        return phone;
    }

    public void phone(String phone) {
        this.phone = phone;
    }

    public String email() { return email;   }

    public void email(String email) {
        this.email = email;
    }

    public String homepage() {
        return homepage;
    }

    public void homepage(String homepage) { this.homepage = homepage; }

    public String link() { return link; }

    public void link(String link) { this.link = link; }

    public String description() { return description; }

    public void description(String description) { this.description = description; }

    public String longDescription() { return longDescription; }

    public void longDescription(String longDescription) { this.longDescription = longDescription; }

    public String name() { return name; }

    public void name(String name) { this.name = name; }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public GeoLocation geoLocation() { return geoLocation; }

    public void geoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
        this.latitude = geoLocation.latitude;
        this.longitude = geoLocation.longitude;
    }

    public double latitude() { return this.latitude; }

    public double longitude() { return this.longitude; }

    public Boolean getIsSponsored() {return isSponsored;}

    public String issue1() { return issue1; }
    public void setIssue1(String issue1) { this.issue1 = issue1; }

    public void setIsSponsored(Boolean isSponsored) {this.isSponsored = isSponsored;}

    /**
     * Utility: Create HEP Restaurant object from DataSnapshot.
     * // TODO refactor to use the Firebase binding instead of manual creation
     */
    public static HEPRestaurant create(DataSnapshot data)
    {
        HEPRestaurant restaurant = new HEPRestaurant();
        restaurant.key(data.getKey());
        try {
            restaurant.locationId(data.child("locationID").getValue(String.class));
            restaurant.setObjectId(data.child("objectId").getValue(String.class));
            restaurant.name(data.child("name").getValue(String.class));

            restaurant.address1(data.child("address1").getValue(String.class));
            restaurant.city(data.child("city").getValue(String.class));
            restaurant.state(data.child("region").getValue(String.class));
            restaurant.country(data.child("country").getValue(String.class));
            restaurant.postalCode(data.child("postalcode").getValue(String.class));
            restaurant.phone(data.child("phone").getValue(String.class));
            restaurant.email(data.child("email").getValue(String.class));
            restaurant.homepage(data.child("homepage").getValue(String.class));
            restaurant.link(data.child("link").getValue(String.class));
            restaurant.description(data.child("description").getValue(String.class));
            restaurant.longDescription(data.child("longdescription").getValue(String.class));
            restaurant.humaneStatus(data.child("humane_status").getValue(String.class));
            restaurant.humaneStatus2(data.child("humane_status2").getValue(String.class));
            restaurant.humaneReason(data.child("humane_reason").getValue(String.class));
            restaurant.priceRangeNumber(data.child("pricerangenumber").getValue(String.class));
            restaurant.cuisine1(data.child("cuisine1").getValue(String.class));
            restaurant.cuisine2(data.child("cuisine2").getValue(String.class));
            restaurant.cuisine3(data.child("cuisine3").getValue(String.class));
            restaurant.setIssue1(data.child("issue1").getValue(String.class));

            // TODO build features
            restaurant.features(buildFeatures(data));
            restaurant.submittedBy(data.child("submitted_by").getValue(String.class));

            String facebookUrl = data.child("facebook_url").getValue(String.class);
            if (!TextUtils.isEmpty(facebookUrl) && facebookUrl.equals("None")) {
                facebookUrl = HEPConstants.EMPTY;
            }
            restaurant.facebookPage(facebookUrl);
            restaurant.twitterPage(data.child("twitter_url").getValue(String.class));

            restaurant.yelpId(data.child("yelp_id").getValue(String.class));
            String url = data.child("yelp_image_url").getValue(String.class);
            if (!TextUtils.isEmpty(url)) {
                url = url.replace("/ms.jpg", "/l.jpg");
            }
            restaurant.yelpImageURL(url);

            Double yr = 0D;
            if (data.child("yelp_rating").exists()) {
                Object yelpRatingObject = data.child("yelp_rating").getValue();
                String yelpRatingStr = yelpRatingObject.toString();
                yr = Double.parseDouble(yelpRatingStr);
//                if (yelpRatingObject instanceof String) {
//                    String yelpRatingStr = (String) yelpRatingObject;
//                    yr = Double.parseDouble(yelpRatingStr);
//                } else if (yelpRatingObject instanceof Double){
//                    yr = (Double) yelpRatingObject;
//                }
            }
            restaurant.yelpRating(yr);

            int yrc = 0;
            if (data.child("yelp_review_count").exists()) {
                Object yelpReviewCountObject = data.child("yelp_review_count").getValue();
                String yelpReviewCountStr = yelpReviewCountObject.toString();
                yrc = Integer.parseInt(yelpReviewCountStr);
//                if (yelpReviewCountObject instanceof String) {
//                    //String yelpReviewCountStr = (String) yelpReviewCountObject;
//                    yrc = Integer.parseInt(yelpReviewCountStr);
//                    System.out.println("Divyaaa " + yrc);
//                } else if (yelpReviewCountObject instanceof Double) {
//                    yrc = (Integer) yelpReviewCountObject;
//                }
            }
            restaurant.yelpReviewCount(yrc);

            if (data.child("hep_sponsor").exists()) {
                restaurant.setIsSponsored(data.child("hep_sponsor").getValue(Boolean.class));
            } else {
                restaurant.setIsSponsored(false);
            }

            if (data.child("menu_url").exists()) {
                restaurant.setMenuUrl(data.child("menu_url").getValue(String.class));
            }
            
        } catch (Exception e) {
            Log.e("Firebase", "Problem when trying to create restaurant: " + restaurant.key(), e);
        }


        return restaurant;
    }

    /**
     * Utility: Create HEP Restaurant object from {@link PlaceDetails}.
     */
    public static HEPRestaurant create(PlaceDetails placeDetails) {
        HEPRestaurant restaurant = new HEPRestaurant();

        restaurant.name(placeDetails.getName());
        restaurant.address1(placeDetails.getStreet());
        restaurant.city(placeDetails.getCity());
        restaurant.state(placeDetails.getState());
        restaurant.country(placeDetails.getCountry());
        restaurant.postalCode(placeDetails.getPostalCode());
        restaurant.phone(placeDetails.getPhoneNumber());

        return restaurant;
    }

    private static String buildFeatures(DataSnapshot data)
    {
        // Build Comma Separated Features. feature1-feature10 /
        List<String> features = new ArrayList<>(
                Arrays.asList(
                        buildFeature(data, "feature1"),
                        buildFeature(data, "feature2"),
                        buildFeature(data, "feature3"),
                        buildFeature(data, "feature4"),
                        buildFeature(data, "feature5"),
                        buildFeature(data, "feature6"),
                        buildFeature(data, "feature7"),
                        buildFeature(data, "feature8"),
                        buildFeature(data, "feature9"),
                        buildFeature(data, "feature10")
                )
        );
        features.removeAll(Arrays.asList("", null));
        return TextUtils.join(", ", features);
    }

    // TODO investigate better ways to do this...
    // Firebase binds feature values to a Long
    // We can't use Long in a switch statement
    // We can't convert a Long to an int
    // We can't use a String in a switch statement in Android
    // Current solution: Long -> String -> int
    private static String buildFeature(DataSnapshot data, String name) {
        if (data.child(name).exists()) {

            String featureStr = data.child(name).getValue().toString();
            int featureInt = Integer.parseInt(featureStr);
            String featureName = null;
            switch (featureInt) {
                case 1:
                    featureName = "Wi-Fi";
                    break;
                case 2:
                    featureName = "Quiet";
                    break;
                case 3:
                    featureName = "Large Group-Friendly";
                    break;
                case 4:
                    featureName = "Delivery";
                    break;
                case 5:
                    featureName = "Gluten-Free Options";
                    break;
                case 6:
                    featureName = "Happy Hour";
                    break;
                case 7:
                    featureName = "Buffet";
                    break;
                case 8:
                    featureName = "(Mostly) Organic";
                    break;
                case 9:
                    featureName = "Brunch";
                    break;
                case 10:
                    featureName = "Live Music";
                    break;
                case 11:
                    featureName = "Outdoor Seating";
                    break;
                case 12:
                    featureName = "Romantic";
                    break;
                case 13:
                    featureName = "Kid-Friendly";
                    break;
                case 14:
                    featureName = "Large Group-Friendly";
                    break;
                case 15:
                    featureName = "Kosher";
                    break;
                case 16:
                    featureName = "Valet Parking";
                    break;
                case 17:
                    featureName = "Private Rooms";
                    break;
            }

            return featureName;
        } else {
            return null;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HEPRestaurant that = (HEPRestaurant) o;

        return key.equals(that.key);

    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }
}
