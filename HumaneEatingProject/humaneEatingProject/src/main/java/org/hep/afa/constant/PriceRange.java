package org.hep.afa.constant;

public enum PriceRange
{
    ALL("All", 1), INEXPENSIVE("$-InExpensive", 1), AVERAGE("$$-Average", 2), EXPENSIVE("$$$-Expensive", 3);

    private String priceRange;
    private int value = 0;

    private PriceRange(String priceRange, int value) {
        this.priceRange = priceRange;
        this.value = value;
    }

    @Override
    public String toString() {
        return priceRange;
    }

    public int value() {
        return value;
    }

    public static String fromString(String str) {
        if (str != null) {
            for (PriceRange ctf : PriceRange.values()) {
                if (str.equalsIgnoreCase(ctf.priceRange)) {
                    return ctf.priceRange;
                }
            }
        }
        return null;
    }

    public static String[] names() {
        PriceRange[] pr = PriceRange.values();
        String[] names = new String[pr.length];

        for (int i = 0; i < pr.length; i++) {
            names[i] = pr[i].priceRange;
        }

        return names;
    }


}
