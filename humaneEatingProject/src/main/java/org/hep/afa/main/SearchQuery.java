package org.hep.afa.main;

import java.util.ArrayList;
import java.util.List;

import org.hep.afa.model.HEPRestaurant;

public class SearchQuery {
    private List<HEPRestaurant> restuarantsList = new ArrayList<HEPRestaurant>();

    private static SearchQuery query = new SearchQuery();

    public static SearchQuery getInstance() { return query; }

    private SearchQuery() {}

    public List<HEPRestaurant> get() {
//        List<HEPRestaurant> copy = new ArrayList<>(this.restuarantsList);
//        return copy;
        return restuarantsList;
    }

    public HEPRestaurant get(int position) {
        if(position < 0 || position > restuarantsList.size()-1) {
            throw new IllegalArgumentException("Position index out of range!");
        }
        return restuarantsList.get(position);
    }

    public void add(HEPRestaurant restaurant) {
        this.restuarantsList.add(restaurant);
    }

    public int size() { return this.restuarantsList.size(); }

    public void clearAll() {
        this.restuarantsList.clear();
    }

    public boolean contains(HEPRestaurant restaurant) {
        return restuarantsList.contains(restaurant);
    }

}
