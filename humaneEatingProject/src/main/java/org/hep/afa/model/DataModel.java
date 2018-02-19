package org.hep.afa.model;


import org.hep.afa.constant.CuisineType;
import org.hep.afa.constant.HumaneStatus;
import org.hep.afa.constant.PriceRange;
import org.hep.afa.constant.ReviewFilter;

public class DataModel {
	public static final int DEFAULT_DISTANCE = 10;

	public static PriceRange pr = PriceRange.ALL;
	public static CuisineType ct = CuisineType.ALL;
	public static HumaneStatus hs = HumaneStatus.ALL;
	public static ReviewFilter reviewFilter = ReviewFilter.ALL;

	//update current location. do intelligently. use location change listener
	public static double lat;
	public static double lon;
	public static String name = "";
	public static int distance  = DEFAULT_DISTANCE;
	public static int rating = 0;
}
