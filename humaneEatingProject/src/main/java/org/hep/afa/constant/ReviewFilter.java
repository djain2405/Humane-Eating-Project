package org.hep.afa.constant;

public enum ReviewFilter {
    ALL("All", 0), REVIEWS25("25", 25), REVIEWS50("50", 50), REVIEWS75("75", 75), REVIEWS100("100", 100);

    String reviewFilter;
    int value = 0;

    private ReviewFilter(String reviewFilter, int value) {
        this.reviewFilter = reviewFilter;
        this.value = value;
    }

    @Override
    public String toString() {
        return reviewFilter;
    }

    public int value() {
        return value;
    }

    public static String fromString(String str) {
        if (str != null) {
            for (ReviewFilter filter : ReviewFilter.values()) {
                if (str.equalsIgnoreCase(filter.reviewFilter)) {
                    return filter.reviewFilter;
                }
            }
        }
        return null;
    }

    //get the integer value
    public static int intVal(String str) {

        if (str != null) {
            for (ReviewFilter ctf : ReviewFilter.values()) {
                if (str.equalsIgnoreCase(ctf.reviewFilter)) {
                    return ctf.value;
                }
            }
        }
        return 0;
    }

    public static String[] names() {
        ReviewFilter[] pr = ReviewFilter.values();
        String[] names = new String[pr.length];

        for (int i = 0; i < pr.length; i++) {
            names[i] = pr[i].reviewFilter;
        }

        return names;
    }

    public static ReviewFilter getReviewFilter(String string) {
        if (string != null) {
            for (ReviewFilter filter : ReviewFilter.values()) {
                if (string.equalsIgnoreCase(filter.toString())) {
                    return filter;
                }
            }
        }
        return ALL;
    }


}
