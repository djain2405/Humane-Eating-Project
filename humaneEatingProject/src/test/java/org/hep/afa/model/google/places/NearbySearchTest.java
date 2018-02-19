package org.hep.afa.model.google.places;


import com.google.gson.JsonElement;

import org.hep.afa.constant.PlaceSearchStatusCode;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class NearbySearchTest {

    @Test
    public void oneResult() {
        NearbySearch search = new NearbySearch(ONE_RESULT);

        assertEquals(PlaceSearchStatusCode.OK, search.getStatus());
        assertEquals("ChIJ63nf-c0O3IARc7ASsIi2vG4", search.getPlaceIds().get(0));

        Iterator<JsonElement> itr = search.iterator();
        assertNotNull(itr);
        assertEquals(1, getSize(itr));
    }

    @Test
    public void zeroResults() {
        NearbySearch search = new NearbySearch(ZERO_RESULTS);

        assertEquals(PlaceSearchStatusCode.ZERO_RESULTS, search.getStatus());
        assertNotNull(search.getPlaceIds());

        Iterator<JsonElement> itr = search.iterator();
        assertNotNull(itr);
        assertEquals(0, getSize(itr));
    }

    @Test
    public void multipleResults() {
        NearbySearch search = new NearbySearch(MULTIPLE_RESULTS);

        assertEquals(PlaceSearchStatusCode.OK, search.getStatus());
        assertEquals("ChIJW9icSN1LtokRNBO0WmrZbUU", search.getPlaceIds().get(0));
        assertEquals("ChIJa78dH8NLtokRgYVapeVwWlQ", search.getPlaceIds().get(1));
        assertEquals("ChIJGXIT1dxLtokR3G-bFDbOsDk", search.getPlaceIds().get(2));

        Iterator<JsonElement> itr = search.iterator();
        assertNotNull(itr);
        assertEquals(3, getSize(itr));
    }

    // In production code the size shouldn't matter (and could change during iteration), but its
    // fine here where we're verifying implementation details
    private int getSize(Iterator<JsonElement> iterator) {
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        return count;
    }


    private static final String ZERO_RESULTS = "{\n" +
            "    \"html_attributions\" : [],\n" +
            "    \"results\" : [],\n" +
            "    \"status\" : \"ZERO_RESULTS\"\n" +
            "}";

    private static final String ONE_RESULT = "{\n" +
            "   \"html_attributions\" : [],\n" +
            "   \"results\" : [\n" +
            "      {\n" +
            "         \"geometry\" : {\n" +
            "            \"location\" : {\n" +
            "               \"lat\" : 32.9913868,\n" +
            "               \"lng\" : -117.2728032\n" +
            "            },\n" +
            "            \"viewport\" : {\n" +
            "               \"northeast\" : {\n" +
            "                  \"lat\" : 32.9927168802915,\n" +
            "                  \"lng\" : -117.2715473697085\n" +
            "               },\n" +
            "               \"southwest\" : {\n" +
            "                  \"lat\" : 32.9900189197085,\n" +
            "                  \"lng\" : -117.2742453302915\n" +
            "               }\n" +
            "            }\n" +
            "         },\n" +
            "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/restaurant-71.png\",\n" +
            "         \"id\" : \"a82bafbd717892b51fe5c8becdde585b39f6c2b8\",\n" +
            "         \"name\" : \"Naked Cafe\",\n" +
            "         \"opening_hours\" : {\n" +
            "            \"exceptional_date\" : [],\n" +
            "            \"open_now\" : true,\n" +
            "            \"weekday_text\" : []\n" +
            "         },\n" +
            "         \"photos\" : [\n" +
            "            {\n" +
            "               \"height\" : 2448,\n" +
            "               \"html_attributions\" : [\n" +
            "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/115027286096822577705/photos\\\"\\u003eZed star\\u003c/a\\u003e\"\n" +
            "               ],\n" +
            "               \"photo_reference\" : \"CoQBdwAAAKz4YYU6PYlFgGNepWF3E7ZwDqeT_EW_laAgbtyjpJVZue3iOXhPW2JpsxBRViVZ6Rm4wtSuwsNvHCFXO7SmGax6Sqlu-Tl8QTARIEJtkl0PJFwklZdHyX0_svo7qa-VH0XyLks6QSiuHahmcPq2vMtp24m-oVFecqFWZVSJ9DsoEhDmnbW1OAOJrW3p543knKFXGhTlv2_Iu0vGcsnQ4UTRhYWuiNszeA\",\n" +
            "               \"width\" : 3264\n" +
            "            }\n" +
            "         ],\n" +
            "         \"place_id\" : \"ChIJ63nf-c0O3IARc7ASsIi2vG4\",\n" +
            "         \"price_level\" : 2,\n" +
            "         \"rating\" : 4.4,\n" +
            "         \"reference\" : \"CmRRAAAAbLmYouGc8P7xKJpN7i8BP_A-BBCTPdcBOB2klroGQ75mifha7gqjQJOP8BIMYhPSrCQmmhv67QkG8mGzN04WQmOcwcbmC6rBot1rBJZcgOT8rbhTESt4-0N37gEjQ8JbEhDnwM5kMi__KOi-GZ0G6XhoGhTSc8Vup4uk_leutyCgJ1mklVlBZw\",\n" +
            "         \"scope\" : \"GOOGLE\",\n" +
            "         \"types\" : [ \"cafe\", \"restaurant\", \"food\", \"point_of_interest\", \"establishment\" ],\n" +
            "         \"vicinity\" : \"106 South Sierra Avenue, Solana Beach\"\n" +
            "      }\n" +
            "   ],\n" +
            "   \"status\" : \"OK\"\n" +
            "}\n";

    private static final String MULTIPLE_RESULTS = "{\n" +
            "   \"html_attributions\" : [],\n" +
            "   \"results\" : [\n" +
            "      {\n" +
            "         \"geometry\" : {\n" +
            "            \"location\" : {\n" +
            "               \"lat\" : 38.8930655,\n" +
            "               \"lng\" : -77.27541029999999\n" +
            "            },\n" +
            "            \"viewport\" : {\n" +
            "               \"northeast\" : {\n" +
            "                  \"lat\" : 38.8944317802915,\n" +
            "                  \"lng\" : -77.27396211970849\n" +
            "               },\n" +
            "               \"southwest\" : {\n" +
            "                  \"lat\" : 38.8917338197085,\n" +
            "                  \"lng\" : -77.27666008029151\n" +
            "               }\n" +
            "            }\n" +
            "         },\n" +
            "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/restaurant-71.png\",\n" +
            "         \"id\" : \"47c8e59e3b665b62cc2fd0efa590e6c6c6bfe9e7\",\n" +
            "         \"name\" : \"Sunflower Vegetarian Restaurant\",\n" +
            "         \"opening_hours\" : {\n" +
            "            \"exceptional_date\" : [],\n" +
            "            \"open_now\" : false,\n" +
            "            \"weekday_text\" : []\n" +
            "         },\n" +
            "         \"photos\" : [\n" +
            "            {\n" +
            "               \"height\" : 4032,\n" +
            "               \"html_attributions\" : [\n" +
            "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/100646400020165199911/photos\\\"\\u003eParshwa shah\\u003c/a\\u003e\"\n" +
            "               ],\n" +
            "               \"photo_reference\" : \"CoQBdwAAAOAO4tHKwKEVgfPfqH8pTOoa4UmxRJcflnbJS6Ai9RAF7PDGFAGaYtxw1irn-ybSp--ttLWknxlebQFDOuS5TJHAQMzEYzttz-beV6YBdRgVMY_84BjxVfOcZsowTKGhAzPsiGs10EFvg4UOnTTaSZykSnqSZ0zw4kOw28xLdrMvEhDjEqvuwvFCi0AEDsk84z-KGhQv_0_9mXurzyhv3-U6WKGfS4OWZA\",\n" +
            "               \"width\" : 3024\n" +
            "            }\n" +
            "         ],\n" +
            "         \"place_id\" : \"ChIJW9icSN1LtokRNBO0WmrZbUU\",\n" +
            "         \"price_level\" : 2,\n" +
            "         \"rating\" : 4.5,\n" +
            "         \"reference\" : \"CmRRAAAA1NEA-oZifbsE2mJf_CknCYaq6H7kqEhNT6ZE-Flw6JNQK5BxiVTIMrhg2YEWw1anQfSu-9WSa6b7lTj9lyh7wFloCYj7NB-NJ42fVBbUl6sjZPLghunmRi3p0De6X5blEhAs6bGxvRCmEBvAFdINGn0-GhSbELvbUlXAWwgMMNWYHLFvNSvvng\",\n" +
            "         \"scope\" : \"GOOGLE\",\n" +
            "         \"types\" : [ \"restaurant\", \"food\", \"point_of_interest\", \"establishment\" ],\n" +
            "         \"vicinity\" : \"2531 Chain Bridge Road, Vienna\"\n" +
            "      },\n" +
            "      {\n" +
            "         \"geometry\" : {\n" +
            "            \"location\" : {\n" +
            "               \"lat\" : 38.89438129999999,\n" +
            "               \"lng\" : -77.2733102\n" +
            "            },\n" +
            "            \"viewport\" : {\n" +
            "               \"northeast\" : {\n" +
            "                  \"lat\" : 38.89590078029149,\n" +
            "                  \"lng\" : -77.27215746970849\n" +
            "               },\n" +
            "               \"southwest\" : {\n" +
            "                  \"lat\" : 38.89320281970849,\n" +
            "                  \"lng\" : -77.27485543029151\n" +
            "               }\n" +
            "            }\n" +
            "         },\n" +
            "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/restaurant-71.png\",\n" +
            "         \"id\" : \"6bb3b5e1bf9e259dc8917e7ac1a9b8ee5623444a\",\n" +
            "         \"name\" : \"Tequila Grande\",\n" +
            "         \"opening_hours\" : {\n" +
            "            \"exceptional_date\" : [],\n" +
            "            \"open_now\" : false,\n" +
            "            \"weekday_text\" : []\n" +
            "         },\n" +
            "         \"photos\" : [\n" +
            "            {\n" +
            "               \"height\" : 3024,\n" +
            "               \"html_attributions\" : [\n" +
            "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/108481687707266110026/photos\\\"\\u003eRuchi Kapani\\u003c/a\\u003e\"\n" +
            "               ],\n" +
            "               \"photo_reference\" : \"CoQBdwAAAPejJKXFibZY96vdmq2L6gMKCl7AGw44JTskDK7h7xQXvjruSuIZRJyoCZakspt7Z6dob7YWMhCeo_i00u6LAMCYuU4WMKYzi_fHD_om5yW-Zvf0q9dQNoLoplXXIfav5S5iuKqtJ5s1Pw2SfQgWBwQclQVaEvjSLcADH4Kpl6oPEhB5gtrmjlAYlevR4FaasjI0GhReFhZl60ZM6y4DtY6N_cG2zIp4zQ\",\n" +
            "               \"width\" : 4032\n" +
            "            }\n" +
            "         ],\n" +
            "         \"place_id\" : \"ChIJa78dH8NLtokRgYVapeVwWlQ\",\n" +
            "         \"price_level\" : 2,\n" +
            "         \"rating\" : 3.7,\n" +
            "         \"reference\" : \"CmRRAAAAh3QK9qiCaa1_-r92Y0dphRjPqTV5SH3N9TToKXIJiJbjVxnknbS6ajVB7fIM6SaWz6outS6fYfLJnzKotBcsDMOx29amePMKXlNw60EdhQ0vCcm50YdIP1j3m1-DHT4vEhDyvls9Cd2EyR3phnCVIstsGhRlkUJmi7zy112E91ZzFrAiPhSxvQ\",\n" +
            "         \"scope\" : \"GOOGLE\",\n" +
            "         \"types\" : [ \"restaurant\", \"food\", \"point_of_interest\", \"establishment\" ],\n" +
            "         \"vicinity\" : \"444 Maple Avenue West, Vienna\"\n" +
            "      },\n" +
            "      {\n" +
            "         \"geometry\" : {\n" +
            "            \"location\" : {\n" +
            "               \"lat\" : 38.89446769999999,\n" +
            "               \"lng\" : -77.2751078\n" +
            "            },\n" +
            "            \"viewport\" : {\n" +
            "               \"northeast\" : {\n" +
            "                  \"lat\" : 38.8956536302915,\n" +
            "                  \"lng\" : -77.2735171697085\n" +
            "               },\n" +
            "               \"southwest\" : {\n" +
            "                  \"lat\" : 38.8929556697085,\n" +
            "                  \"lng\" : -77.2762151302915\n" +
            "               }\n" +
            "            }\n" +
            "         },\n" +
            "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/restaurant-71.png\",\n" +
            "         \"id\" : \"ee3109cd4f9346225c02b2dfbfdf8b172f555e57\",\n" +
            "         \"name\" : \"Istanbul Blue\",\n" +
            "         \"opening_hours\" : {\n" +
            "            \"exceptional_date\" : [],\n" +
            "            \"open_now\" : false,\n" +
            "            \"weekday_text\" : []\n" +
            "         },\n" +
            "         \"photos\" : [\n" +
            "            {\n" +
            "               \"height\" : 5312,\n" +
            "               \"html_attributions\" : [\n" +
            "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/117748877560634895217/photos\\\"\\u003eJudimi M\\u003c/a\\u003e\"\n" +
            "               ],\n" +
            "               \"photo_reference\" : \"CoQBdwAAADBAq2-G1ZbBoPiwnKjdgx8ScKpJ2Nmyb9U78hSDt1u1crWPsPQbsC3y0HJiXu_-AFot_aIg5qb3_jGLrl0JPyvARin4PPHeHMyB9-5q_tOc2JfwXnluT3FfUSMP5-_Be_EoWj2bIa6GmsxFLgMrZpji9DyRa6WrYXgksLExlqBuEhD_Q9VS5T5g9L9Mdycbej56GhRnxeiKelC_nBgkGjCr9xI6fGDnxA\",\n" +
            "               \"width\" : 2988\n" +
            "            }\n" +
            "         ],\n" +
            "         \"place_id\" : \"ChIJGXIT1dxLtokR3G-bFDbOsDk\",\n" +
            "         \"rating\" : 4.5,\n" +
            "         \"reference\" : \"CmRRAAAA6X-SZC-t8S5qXNEWHoQ-9HbTrfZHI7CJpkTEPOvZrYh_J8gMJwKkDqtF2QOK2XbZ6Cb_Il4ZYxyiC03S45RX3RkBfqJa-WF4tWok6FHruOKcSlktBXwWBMeYHkfct8GmEhD7PyqlaUjkv5wnBVUdNmSvGhRLsdkm3OGV1UK-H_qfNG2QtsLBwA\",\n" +
            "         \"scope\" : \"GOOGLE\",\n" +
            "         \"types\" : [ \"restaurant\", \"food\", \"point_of_interest\", \"establishment\" ],\n" +
            "         \"vicinity\" : \"523 Maple Avenue West, Vienna\"\n" +
            "      }\n" +
            "   ],\n" +
            "   \"status\" : \"OK\"\n" +
            "}\n";
}
