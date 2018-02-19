package org.hep.afa.constant;

public enum DistanceRange {
    ALL("All", 25), MILE1("1 mi", 1), MILE3("3 mi", 3), MILE5("5 mi", 5), MILE10("10 mi", 10), MILE15("15 mi", 15);

    String distanceRange;
    int value = 0;

    private DistanceRange(String distanceRange, int value) {
        this.distanceRange = distanceRange;
        this.value = value;
    }

    @Override
    public String toString() {
        return distanceRange;
    }

    public static String fromString(String str) {
        if (str != null) {
            for (DistanceRange ctf : DistanceRange.values()) {
                if (str.equalsIgnoreCase(ctf.distanceRange)) {
                    return ctf.distanceRange;
                }
            }
        }
        return null;
    }

    //get the integer value
    public static int intVal(String str) {

        if (str != null) {
            for (DistanceRange ctf : DistanceRange.values()) {
                if (str.equalsIgnoreCase(ctf.distanceRange)) {
                    return ctf.value;
                }
            }
        }
        return 0;
    }

    public static String[] names() {
        DistanceRange[] pr = DistanceRange.values();
        String[] names = new String[pr.length];

        for (int i = 0; i < pr.length; i++) {
            names[i] = pr[i].distanceRange;
        }

        return names;
    }


}
