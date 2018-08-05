package org.hep.afa.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.icu.text.Replaceable;

import org.hep.afa.database.entities.Favorite;

import java.util.List;

/**
 * Created by heatherlaurin on 2/24/18.
 */

@Dao
public interface FavoriteDao {

    @Query("SELECT * FROM favorite")
    LiveData<List<Favorite>> getAllFavorites();

    @Query("SELECT * FROM favorite WHERE restaurantKey IN (:restaurantKeys)")
    LiveData<List<Favorite>> loadAllByIds(String[] restaurantKeys);

    @Insert
    void insertFavorite(Favorite favorite);

    @Insert
    void insertAllFavorites(Favorite... favorites);

    @Delete
    void deleteFavorite(Favorite favorite);
}
