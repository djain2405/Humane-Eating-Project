package org.hep.afa.utils;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.util.GeoUtils;

import org.hep.afa.main.HEPApplication;
import org.hep.afa.constant.DistanceConstants;

import java.util.List;

/**
 * Created by Jaebin Lee on 2015-10-06.
 */
public class LocationUtils
{
    private static LocationUtils lutils = new LocationUtils();
    private Context context;
    private LocationManager lm;

    private LocationUtils() {
        this.context = HEPApplication.getMyAppContext();
        this.lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public static LocationUtils getInstance() { return lutils; }

    /**
     * @Note: Utility method for getting best available location among GPS, Network etc..
     * This would need improvement in future (For more elegance).
     */
    public Location currentLocation()
    {
        Location bestLocation = null;
        float bestAccuracy = Float.MAX_VALUE;
//        long bestTime = Long.MIN_VALUE;
        List<String> matchingProviders = lm.getAllProviders();
        for (String provider: matchingProviders) {
            Location location = lm.getLastKnownLocation(provider);
            if (location != null) {
                float accuracy = location.getAccuracy();

                if (accuracy < bestAccuracy) {
                    bestLocation = location;
                    bestAccuracy = accuracy;
                }
            }
        }
        return bestLocation;
    }

    /**
     * Calculates and returns the distance between @param geoLocation and the current location in Miles.
     * @param geoLocation: GeoLocation instance (likely will be HEP restaurant location).
     * @return: distance between current location and the @param geoLocation.
     */
    public double distanceInMilesTo(GeoLocation geoLocation)
    {
        if(geoLocation == null) { return 0d; }
        Location currentLocation = currentLocation();
        if(currentLocation == null) { return 0d; }
        GeoLocation currentGeoLocation = new GeoLocation(currentLocation.getLatitude(), currentLocation.getLongitude());
        return distanceInMilesBetween(currentGeoLocation, geoLocation);
    }

    public double distanceInMilesBetween(double lat1, double lng1, double lat2, double lng2) {
        GeoLocation geoLocation1 = new GeoLocation(lat1, lng1);
        GeoLocation geoLocation2 = new GeoLocation(lat2, lng2);
        return distanceInMilesBetween(geoLocation1, geoLocation2);
    }

    public double distanceInMilesBetween(GeoLocation location1, GeoLocation location2) {
        return GeoUtils.distance(location1, location2) * DistanceConstants.METERS_TO_MILES_CONVERSION;
    }

    /**
     * Returns distance in miles as a String based on the specified GeoLocation
     */
    public String distanceInMilesString(GeoLocation geoLocation) {
        return String.format("%.2f mi", distanceInMilesTo(geoLocation)); // set the miles text.
    }
}
