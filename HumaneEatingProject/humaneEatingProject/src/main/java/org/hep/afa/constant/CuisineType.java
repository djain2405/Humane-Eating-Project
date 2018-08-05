package org.hep.afa.constant;

public enum CuisineType {

    ALL("All"),
    AFRICAN("African"),
    ETHIOPIANERITREAN("Ethiopian/Eritrean"),
    GHANAIAN("Ghanaian"),
    MOROCCAN("Moroccan"),
    NORTHAFRICAN("North African"),
    SOUTHAFRICAN("South African"),
    WESTAFRICAN("West African"),
    AMERICAN("American"),
    BURMESE("Burmese"),
    CAJUN("Cajun"),
    CAMBODIAN("Cambodian"),
    CARIBBEAN("Caribbean"),
    CENTRALAMERICAN("Central American"),
    CHINESE("Chinese"),
    CANTONESE("Cantonese"),
    DIMSUM("Dim Sum"),
    HAKKA("Hakka"),
    HOTPOT("Hot Pot"),
    HUNANESE("Hunanese"),
    NORTHERNBEIJING("Northern (Beijing)"),
    SZECHUAN("Szechuan"),
    TAIWANESE("Taiwanese"),
    DELI("Deli"),
    ECLECTICFUSION("Eclectic (Fusion)"),
    ECLECTICVARIED("Eclectic (Varied)"),
    ENGLISH("English"),
    FILIPINO("Filipino"),
    FRENCH("French"),
    GERMAN("German"),
    GREEK("Greek"),
    HMONG("Hmong"),
    HUNGARIAN("Hungarian"),
    INDIAN("Indian"),
    INDIANNORTHERN("Indian (Northern)"),
    INDIANSOUTHERN("Indian (Southern)"),
    INDOCHINESE("Indo-Chinese"),
    INDONESIAN("Indonesian"),
    ITALIAN("Italian"),
    ITALIANNORTHERN("Italian (Northern)"),
    ITALIANSOUTHERN("Italian (Southern)"),
    PIZZA("Pizza"),
    JAPANESE("Japanese"),
    JEWISHEASTERNEUROPEAN("Jewish (Eastern European)"),
    KOREAN("Korean"),
    LAOTIAN("Laotian"),
    MACROBIOTIC("Macrobiotic"),
    MALAYSIAN("Malaysian"),
    MEDITERRANEAN("Mediterranean"),
    MEXICAN("Mexican"),
    MIDDLEEASTERNPERSIAN("Middle Eastern/Persian"),
    AFGHANI("Afghani"),
    EGYPTIAN("Egyptian"),
    JEWISHSEPHARDIC("Jewish (Sephardic)"),
    KURDISH("Kurdish"),
    LEBANESE("Lebanese"),
    TURKISH("Turkish"),
    MONGOLIAN("Mongolian"),
    NEPALESE("Nepalese"),
    RAWFOOD("Raw Food"),
    ROMANIAN("Romanian"),
    RUSSIAN("Russian"),
    SOULFOOD("Soul Food"),
    SOUTHAMERICAN("South American"),
    BRAZILIAN("Brazilian"),
    SPANISH("Spanish"),
    SRILANKAN("Sri Lankan"),
    TEXMEX("Tex-Mex"),
    THAI("Thai"),
    TIBETAN("Tibetan"),
    VIETNAMESE("Vietnamese");

    String cuisineType;

    private CuisineType(String str) {
        this.cuisineType = str;
    }

    @Override
    public String toString() {
        return cuisineType;
    }

    public static CuisineType fromString(String str) {
        if (str != null) {
            for (CuisineType ctf : CuisineType.values()) {
                if (str.equalsIgnoreCase(ctf.cuisineType)) {
                    return ctf;
                }
            }
        }
        return null;
    }

    public static String[] names() {
        CuisineType[] ct = CuisineType.values();

        String[] names = new String[ct.length];

        for (int i = 0; i < ct.length; i++) {
            names[i] = ct[i].cuisineType;
        }
        return names;
    }
}
