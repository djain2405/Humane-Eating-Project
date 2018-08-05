package org.hep.afa.repository;

import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import org.hep.afa.database.HepDatabase;
import org.hep.afa.database.dao.FavoriteDao;
import org.hep.afa.database.entities.Favorite;

import java.util.List;

/**
 * Created by heatherlaurin on 3/1/18.
 */

public class FavoritesRepository {

    private FavoriteDao favoriteDao;
    private LiveData<List<Favorite>> allFavorites;

    public FavoritesRepository() {
        HepDatabase db = HepDatabase.getDatabase();
        favoriteDao = db.favoriteDao();
        allFavorites = favoriteDao.getAllFavorites();
    }

    public LiveData<List<Favorite>> getAllFavorites() {
        return allFavorites;
    }

    public void insert(Favorite favorite) {
        new insertAsyncTask(favoriteDao).execute(favorite);
    }

    private static class insertAsyncTask extends AsyncTask<Favorite, Void, Void> {

        private FavoriteDao asyncTaskDao;

        insertAsyncTask(FavoriteDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Favorite... params) {
            asyncTaskDao.insertFavorite(params[0]);
            return null;
        }
    }
}
