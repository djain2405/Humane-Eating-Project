package org.hep.afa.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import org.hep.afa.database.dao.FavoriteDao;
import org.hep.afa.database.entities.Favorite;
import org.hep.afa.main.HEPApplication;

/**
 * Created by heatherlaurin on 2/24/18.
 */
@Database(entities = {Favorite.class}, version = 1, exportSchema = false)
public abstract class HepDatabase extends RoomDatabase {
    private static String DATABASE_NAME = "hep-database";

    private static HepDatabase db;

    public abstract FavoriteDao favoriteDao();

    public static HepDatabase getDatabase() {
        if (db == null) {
            synchronized (HepDatabase.class) {
                if (db == null) {
                    db = Room.databaseBuilder(HEPApplication.getMyAppContext(), HepDatabase.class, DATABASE_NAME)
                             .build();
                }
            }
        }
        return db;
    }
}
