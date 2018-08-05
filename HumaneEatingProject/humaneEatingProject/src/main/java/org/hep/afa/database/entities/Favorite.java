package org.hep.afa.database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by heatherlaurin on 2/24/18.
 */

@Entity
public class Favorite {

    @NonNull
    @PrimaryKey
    private String restaurantKey;

    public Favorite(String restaurantKey) {
        this.restaurantKey = restaurantKey;
    }

    public String getRestaurantKey() {
        return restaurantKey;
    }

    public void setRestaurantKey(String restaurantKey) {
        this.restaurantKey = restaurantKey;
    }
}
