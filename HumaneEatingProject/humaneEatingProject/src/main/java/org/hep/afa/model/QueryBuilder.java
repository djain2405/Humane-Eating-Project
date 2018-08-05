package org.hep.afa.model;

import com.firebase.geofire.GeoFire;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QueryBuilder {

    private static DatabaseReference restaurantDatabase;
    private static GeoFire geoFire;
    private static boolean initialized = false;

    public static void initialize() {
        restaurantDatabase = FirebaseDatabase.getInstance().getReference("Restaurants");
        geoFire = new GeoFire(FirebaseDatabase.getInstance().getReference("GeoLocations"));

        initialized = true;
    }

    public static void queryRestaurant(String key, ValueEventListener listener) {
        if (!initialized) initialize();
        restaurantDatabase.orderByChild("locationID").equalTo(key).addListenerForSingleValueEvent(listener);
    }

    public static void queryRestaurantById(String id, ValueEventListener listener) {
        if (!initialized) initialize();
        restaurantDatabase.child(id).addListenerForSingleValueEvent(listener);
    }

    public static GeoFire getGeoFire() {
        if (!initialized) initialize();
        return geoFire;
    }

}
