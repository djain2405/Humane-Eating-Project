package org.hep.afa.model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.junit.Test;

import static org.junit.Assert.*;

public class QueryBuilderTest {
    private static final String TAG = "QueryBuilderTest";
    private static boolean shouldWait = true;
    private static HEPRestaurant restaurant;

    @Test
    public void testQueryRestaurantById() throws Exception {

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange");


                restaurant = HEPRestaurant.create(dataSnapshot);

                Log.d(TAG, "Restaurant ID: " + restaurant.key());
                Log.d(TAG, "Restaurant name: " + restaurant.name());

                shouldWait = false;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled");
            }
        };

        Log.d(TAG, "Calling");
        QueryBuilder.queryRestaurantById("9097", listener);
        while (shouldWait) {
            // this is a contrived while loop to wait until the async call returns... we should do something like this in production
        }
        assertNotNull(restaurant);
        Log.d(TAG, "Finishing");
    }

}