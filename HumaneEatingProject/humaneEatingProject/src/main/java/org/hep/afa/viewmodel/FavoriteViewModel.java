package org.hep.afa.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import org.hep.afa.database.entities.Favorite;
import org.hep.afa.main.HEPApplication;
import org.hep.afa.repository.FavoritesRepository;

import java.util.List;

/**
 * Created by heatherlaurin on 3/1/18.
 */

public class FavoriteViewModel extends AndroidViewModel {

    private FavoritesRepository repository;
    private LiveData<List<Favorite>> allFavorites;

    public FavoriteViewModel (Application application) {
        super(application);
        repository = new FavoritesRepository();
        allFavorites = repository.getAllFavorites();
    }

    public LiveData<List<Favorite>> getAllFavorites() { return allFavorites; }

    public void insert(Favorite favorite) { repository.insert(favorite); }
}
