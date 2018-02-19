package org.hep.afa.model.google.places;


import org.hep.afa.constant.PlaceSearchStatusCode;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PlaceDetailsTest {

    @Test
    public void tenPhotos() {
        PlaceDetails details = new PlaceDetails(TEN_PHOTOS);

        assertEquals(PlaceSearchStatusCode.OK, details.getStatus());
        assertEquals("Naked Cafe", details.getName());
        assertEquals("106 South Sierra Avenue", details.getStreet());
        assertEquals("Solana Beach", details.getCity());
        assertEquals("CA", details.getState());
        assertEquals("92075", details.getPostalCode());
        assertEquals("US", details.getCountry());
        assertEquals("(858) 259-7866", details.getPhoneNumber());
        List<String> photoReferences = details.getPhotoReferences();
        assertEquals(10, photoReferences.size());
        assertTrue(photoReferences.contains("CoQBdwAAAPjGzVsL8CfyNVcg8qzZY4iESuqq4LsIcsP1yfcWJy7yvW28UxDTmCZyqFUPQKd8tagR0GAQVPzVtPeBozlmIgreKmcAlaI6bJf8LioNk2FS_PJyHDVH38WnYJkqYbv3JMc_fsdZKygKP00jue--pld9qWZdn9qCTXAMAG85m4zBEhDoOvLqp_lwrepS41dcZL34GhTglJPZl6X75aBkCOLuNhra8p2gOA"));
    }

    @Test
    public void fourPhotos() {
        PlaceDetails details = new PlaceDetails(FOUR_PHOTOS);

        assertEquals(PlaceSearchStatusCode.OK, details.getStatus());
        assertEquals("Chipotle Mexican Grill", details.getName());
        assertEquals("2946 Chain Bridge Road", details.getStreet());
        assertEquals("Oakton", details.getCity());
        assertEquals("VA", details.getState());
        assertEquals("22124", details.getPostalCode());
        assertEquals("US", details.getCountry());
        assertEquals("(571) 328-5279", details.getPhoneNumber());
        List<String> photoReferences = details.getPhotoReferences();
        assertEquals(4, photoReferences.size());
        assertTrue(photoReferences.contains("CoQBdwAAAKFLN6tLLQZphS-GT7Xz3-avIrRsRq1E4t1ZJGUR38Ld8wNxL2rSKjphtrk2unsrzD5QcDGepuYE8MWvyEVkWYA88A2OoPhqZ1cGfufmwkb2jq7KDKGGk8YMQDTLVRSNz5V1s40IFvTy9Zgh7JfFf7I0axA2DAXFxhiFjRRF8UqiEhAtVFMkcz5kMEcWZCi_prSrGhTR4fo-lmYqqricrgWroxAVfsXMlw"));
    }

    @Test
    public void zeroPhotos() {
        PlaceDetails details = new PlaceDetails(ZERO_PHOTOS);

        assertEquals(PlaceSearchStatusCode.OK, details.getStatus());
        assertEquals("Vespucci Fine Dining", details.getName());
        assertEquals("10579 Fairfax Boulevard", details.getStreet());
        assertEquals("Fairfax", details.getCity());
        assertEquals("VA", details.getState());
        assertEquals("22030", details.getPostalCode());
        assertEquals("US", details.getCountry());
        assertEquals("(703) 272-8113", details.getPhoneNumber());
        assertEquals(0, details.getPhotoReferences().size());
    }

    private static final String TEN_PHOTOS = "{\n" +
            "   \"html_attributions\" : [],\n" +
            "   \"result\" : {\n" +
            "      \"address_components\" : [\n" +
            "         {\n" +
            "            \"long_name\" : \"106\",\n" +
            "            \"short_name\" : \"106\",\n" +
            "            \"types\" : [ \"street_number\" ]\n" +
            "         },\n" +
            "         {\n" +
            "            \"long_name\" : \"South Sierra Avenue\",\n" +
            "            \"short_name\" : \"S Sierra Ave\",\n" +
            "            \"types\" : [ \"route\" ]\n" +
            "         },\n" +
            "         {\n" +
            "            \"long_name\" : \"Solana Beach\",\n" +
            "            \"short_name\" : \"Solana Beach\",\n" +
            "            \"types\" : [ \"locality\", \"political\" ]\n" +
            "         },\n" +
            "         {\n" +
            "            \"long_name\" : \"San Diego County\",\n" +
            "            \"short_name\" : \"San Diego County\",\n" +
            "            \"types\" : [ \"administrative_area_level_2\", \"political\" ]\n" +
            "         },\n" +
            "         {\n" +
            "            \"long_name\" : \"California\",\n" +
            "            \"short_name\" : \"CA\",\n" +
            "            \"types\" : [ \"administrative_area_level_1\", \"political\" ]\n" +
            "         },\n" +
            "         {\n" +
            "            \"long_name\" : \"United States\",\n" +
            "            \"short_name\" : \"US\",\n" +
            "            \"types\" : [ \"country\", \"political\" ]\n" +
            "         },\n" +
            "         {\n" +
            "            \"long_name\" : \"92075\",\n" +
            "            \"short_name\" : \"92075\",\n" +
            "            \"types\" : [ \"postal_code\" ]\n" +
            "         },\n" +
            "         {\n" +
            "            \"long_name\" : \"1811\",\n" +
            "            \"short_name\" : \"1811\",\n" +
            "            \"types\" : [ \"postal_code_suffix\" ]\n" +
            "         }\n" +
            "      ],\n" +
            "      \"adr_address\" : \"\\u003cspan class=\\\"street-address\\\"\\u003e106 S Sierra Ave\\u003c/span\\u003e, \\u003cspan class=\\\"locality\\\"\\u003eSolana Beach\\u003c/span\\u003e, \\u003cspan class=\\\"region\\\"\\u003eCA\\u003c/span\\u003e \\u003cspan class=\\\"postal-code\\\"\\u003e92075-1811\\u003c/span\\u003e, \\u003cspan class=\\\"country-name\\\"\\u003eUSA\\u003c/span\\u003e\",\n" +
            "      \"formatted_address\" : \"106 S Sierra Ave, Solana Beach, CA 92075, USA\",\n" +
            "      \"formatted_phone_number\" : \"(858) 259-7866\",\n" +
            "      \"geometry\" : {\n" +
            "         \"location\" : {\n" +
            "            \"lat\" : 32.9913868,\n" +
            "            \"lng\" : -117.2728032\n" +
            "         },\n" +
            "         \"viewport\" : {\n" +
            "            \"northeast\" : {\n" +
            "               \"lat\" : 32.9927168802915,\n" +
            "               \"lng\" : -117.2715473697085\n" +
            "            },\n" +
            "            \"southwest\" : {\n" +
            "               \"lat\" : 32.9900189197085,\n" +
            "               \"lng\" : -117.2742453302915\n" +
            "            }\n" +
            "         }\n" +
            "      },\n" +
            "      \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/restaurant-71.png\",\n" +
            "      \"id\" : \"a82bafbd717892b51fe5c8becdde585b39f6c2b8\",\n" +
            "      \"international_phone_number\" : \"+1 858-259-7866\",\n" +
            "      \"name\" : \"Naked Cafe\",\n" +
            "      \"opening_hours\" : {\n" +
            "         \"exceptional_date\" : [],\n" +
            "         \"open_now\" : false,\n" +
            "         \"periods\" : [\n" +
            "            {\n" +
            "               \"close\" : {\n" +
            "                  \"day\" : 0,\n" +
            "                  \"time\" : \"1430\"\n" +
            "               },\n" +
            "               \"open\" : {\n" +
            "                  \"day\" : 0,\n" +
            "                  \"time\" : \"0730\"\n" +
            "               }\n" +
            "            },\n" +
            "            {\n" +
            "               \"close\" : {\n" +
            "                  \"day\" : 1,\n" +
            "                  \"time\" : \"1430\"\n" +
            "               },\n" +
            "               \"open\" : {\n" +
            "                  \"day\" : 1,\n" +
            "                  \"time\" : \"0730\"\n" +
            "               }\n" +
            "            },\n" +
            "            {\n" +
            "               \"close\" : {\n" +
            "                  \"day\" : 2,\n" +
            "                  \"time\" : \"1430\"\n" +
            "               },\n" +
            "               \"open\" : {\n" +
            "                  \"day\" : 2,\n" +
            "                  \"time\" : \"0730\"\n" +
            "               }\n" +
            "            },\n" +
            "            {\n" +
            "               \"close\" : {\n" +
            "                  \"day\" : 3,\n" +
            "                  \"time\" : \"1430\"\n" +
            "               },\n" +
            "               \"open\" : {\n" +
            "                  \"day\" : 3,\n" +
            "                  \"time\" : \"0730\"\n" +
            "               }\n" +
            "            },\n" +
            "            {\n" +
            "               \"close\" : {\n" +
            "                  \"day\" : 4,\n" +
            "                  \"time\" : \"1430\"\n" +
            "               },\n" +
            "               \"open\" : {\n" +
            "                  \"day\" : 4,\n" +
            "                  \"time\" : \"0730\"\n" +
            "               }\n" +
            "            },\n" +
            "            {\n" +
            "               \"close\" : {\n" +
            "                  \"day\" : 5,\n" +
            "                  \"time\" : \"1430\"\n" +
            "               },\n" +
            "               \"open\" : {\n" +
            "                  \"day\" : 5,\n" +
            "                  \"time\" : \"0730\"\n" +
            "               }\n" +
            "            },\n" +
            "            {\n" +
            "               \"close\" : {\n" +
            "                  \"day\" : 6,\n" +
            "                  \"time\" : \"1430\"\n" +
            "               },\n" +
            "               \"open\" : {\n" +
            "                  \"day\" : 6,\n" +
            "                  \"time\" : \"0730\"\n" +
            "               }\n" +
            "            }\n" +
            "         ],\n" +
            "         \"weekday_text\" : [\n" +
            "            \"Monday: 7:30 AM – 2:30 PM\",\n" +
            "            \"Tuesday: 7:30 AM – 2:30 PM\",\n" +
            "            \"Wednesday: 7:30 AM – 2:30 PM\",\n" +
            "            \"Thursday: 7:30 AM – 2:30 PM\",\n" +
            "            \"Friday: 7:30 AM – 2:30 PM\",\n" +
            "            \"Saturday: 7:30 AM – 2:30 PM\",\n" +
            "            \"Sunday: 7:30 AM – 2:30 PM\"\n" +
            "         ]\n" +
            "      },\n" +
            "      \"photos\" : [\n" +
            "         {\n" +
            "            \"height\" : 2448,\n" +
            "            \"html_attributions\" : [\n" +
            "               \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/115027286096822577705/photos\\\"\\u003eZed star\\u003c/a\\u003e\"\n" +
            "            ],\n" +
            "            \"photo_reference\" : \"CoQBdwAAAB7TckQYbbsd9fF2EKya1nYtRJeh14GXuCeEvYW7GRPfNey8aygtXAo5IXTUkByGDWs-Zfs8BBtFWY91z2B_QQRJ0DB48V0HQE2PnPxUuRsC_9AvSv2wJO9XmkdwUESpEqSTKE7mInahq8qIAEdA_dgmCBhqDPO6nrLZyF9mAtfxEhBm2lQ00LMphmAFIwujXOSVGhQlhC8TD9P7tBe30c2DV5dpBd_AYQ\",\n" +
            "            \"width\" : 3264\n" +
            "         },\n" +
            "         {\n" +
            "            \"height\" : 2448,\n" +
            "            \"html_attributions\" : [\n" +
            "               \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/115027286096822577705/photos\\\"\\u003eZed star\\u003c/a\\u003e\"\n" +
            "            ],\n" +
            "            \"photo_reference\" : \"CoQBdwAAAK0YLJutWzpOZ_pti7-omRrYokOR7Bx549SRo8KLsGHbSHX8p3xJ9wjSamT_gk8slAZYG0p749jbZp4SEiJodXrbvHSDUSUaEH30yybs8PG4u8SuCVr1UT_UVx8Rc5_r3Nd6UzKqeoqRz8Sg4NXZEErsV-rXrvm1iIFFmrTK6XjhEhDaXzd2xcXlV168L70Dog-QGhT13LK9-bh8oxUaSrU2FeO5RpaNyQ\",\n" +
            "            \"width\" : 3264\n" +
            "         },\n" +
            "         {\n" +
            "            \"height\" : 2432,\n" +
            "            \"html_attributions\" : [\n" +
            "               \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/108791460074215164435/photos\\\"\\u003ecody schmieding\\u003c/a\\u003e\"\n" +
            "            ],\n" +
            "            \"photo_reference\" : \"CoQBdwAAAA09BadAmJGm0LnfU9PE0HBT6jzewTE7bLpPL0hssmYfAohgwNja3VwODPvkuqjl5zOo6ZHmB-v__u8NPiPPWDx3x-t4DuYHTgDNQRT0eB8sBt4J-tQY7qqkUk9pYas4cTR0sdGw1psmaywM3VQkAB7LXQTLVZyW1BPHG6vU5xLSEhAGx-DnixfV7tHrxX2c-fwsGhSpVscJXwt_W9CQlXDMTDQLOPx_3A\",\n" +
            "            \"width\" : 4320\n" +
            "         },\n" +
            "         {\n" +
            "            \"height\" : 2432,\n" +
            "            \"html_attributions\" : [\n" +
            "               \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/108791460074215164435/photos\\\"\\u003ecody schmieding\\u003c/a\\u003e\"\n" +
            "            ],\n" +
            "            \"photo_reference\" : \"CoQBdwAAAPjGzVsL8CfyNVcg8qzZY4iESuqq4LsIcsP1yfcWJy7yvW28UxDTmCZyqFUPQKd8tagR0GAQVPzVtPeBozlmIgreKmcAlaI6bJf8LioNk2FS_PJyHDVH38WnYJkqYbv3JMc_fsdZKygKP00jue--pld9qWZdn9qCTXAMAG85m4zBEhDoOvLqp_lwrepS41dcZL34GhTglJPZl6X75aBkCOLuNhra8p2gOA\",\n" +
            "            \"width\" : 4320\n" +
            "         },\n" +
            "         {\n" +
            "            \"height\" : 486,\n" +
            "            \"html_attributions\" : [\n" +
            "               \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/111610996082524903169/photos\\\"\\u003eTom C\\u003c/a\\u003e\"\n" +
            "            ],\n" +
            "            \"photo_reference\" : \"CoQBdwAAAEiZhiMLJ4ky0JO_wtAufYYvQ3hf-txVzuJbIlgLVma3KU-eXyR975rud3hX0QtGYMHSStB9xMfJhbL8LylUUXsJoXEk7qL9zUXYCL6gGeMzDN1fcGvpJktMuOSwK5aJY4EggD-fjYBIelzLLe2LsqNWmYHK8zfFaTPimPjc05KNEhBvtGEkmZDAaJ0rURtlV5Z7GhQwDU11nHTsD4pC1I_ab-BGvqkLeA\",\n" +
            "            \"width\" : 648\n" +
            "         },\n" +
            "         {\n" +
            "            \"height\" : 4032,\n" +
            "            \"html_attributions\" : [\n" +
            "               \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/106602121847522349941/photos\\\"\\u003eS Grim\\u003c/a\\u003e\"\n" +
            "            ],\n" +
            "            \"photo_reference\" : \"CoQBdwAAAGyaiKKOxgooJfC5V8oeeUtYH-H49jxl4uibu9NbJi-rNK3oncEzIYj2wIW_PWU06qH8IncTtr6M5i0J1uv2UepWobzcpp1YVBHYZDz_6ay4XU32fPOw1OLSvajC-lxj-wBYZOG65DIXZjSbUXOGmUm57WBAsV_R7vILuyAmu23zEhB_3wqZ9VBSegChmKaSojqoGhSiks3plnYwxaoOhUG8xAH0ERPKPQ\",\n" +
            "            \"width\" : 3024\n" +
            "         },\n" +
            "         {\n" +
            "            \"height\" : 1564,\n" +
            "            \"html_attributions\" : [\n" +
            "               \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/106602121847522349941/photos\\\"\\u003eS Grim\\u003c/a\\u003e\"\n" +
            "            ],\n" +
            "            \"photo_reference\" : \"CoQBdwAAALlzbRqqBqHoo3weX0L85pemxnaB5bmJnC6AH8IGPVLHqe3MRgSfSV33CvaYeNJ8OLZKE72Pb2bVvrKmmBNKtgjhM8YlT8fYSMq3z3UcXIhIsXeAoDNjg93v8m-eHwwLhOMoyqz2wmESokipfl4ViRqolontW-uqGoSBfpo4EhOBEhAFEabApowzJfPAeh97epG8GhSoIvW6OFT3kz5YBaAoIdQrIUdbLQ\",\n" +
            "            \"width\" : 1564\n" +
            "         },\n" +
            "         {\n" +
            "            \"height\" : 1224,\n" +
            "            \"html_attributions\" : [\n" +
            "               \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/101080365143289761280/photos\\\"\\u003eNate Maingard\\u003c/a\\u003e\"\n" +
            "            ],\n" +
            "            \"photo_reference\" : \"CoQBdwAAAJDp1u_8fOhoFC1QGsNuC_r7Oz885ebtyyJ-zGp_pE-ryZs2KVWJ6KWQEr5QHa3XqOdKnIESI18MHpA0Fj5kl4-BBGB6rPD7uyfb34VHa2XoAhn4xHkSjRGkYM21wHNC4nZy2CEtNrJGQ3GL3DC5SwsCdNdJsQQWf4Df-SkskQmWEhB7pjTt8x5tx3x3sPvT1VScGhQhWHQRa3scHEYlLaCkgXL7p2gQtg\",\n" +
            "            \"width\" : 1632\n" +
            "         },\n" +
            "         {\n" +
            "            \"height\" : 486,\n" +
            "            \"html_attributions\" : [\n" +
            "               \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/111610996082524903169/photos\\\"\\u003eTom C\\u003c/a\\u003e\"\n" +
            "            ],\n" +
            "            \"photo_reference\" : \"CoQBdwAAAJNjX6Y6pjwT1Woy_wKZbY1sMKEVyX-WiSewvQPccdTjlDFYxias4j9YMFhXpptQN8oesr-1X9W4rsQVQafQDZJ9clL1w0Y7-bNwNifweN_5o8vVuWE5R-exkva2t-JeAX9ErKDsajcnwnh7EObUlqMG9oeb5OGr0G05zzBZZqaeEhDlShi4Hb7kglqKJRC6QYpbGhQIwJYyp7K4eeNk6GVXH9pWXr7o9g\",\n" +
            "            \"width\" : 648\n" +
            "         },\n" +
            "         {\n" +
            "            \"height\" : 3024,\n" +
            "            \"html_attributions\" : [\n" +
            "               \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/115027286096822577705/photos\\\"\\u003eZed star\\u003c/a\\u003e\"\n" +
            "            ],\n" +
            "            \"photo_reference\" : \"CoQBdwAAAPtlbyqSGYE7ILLIJ6LU_2MYYalAx0okLlzCo5hfuo60kY5plGy55ot7IA6aH_RvKDWqC2lg4xsygFmj-y9ULsGp7tYTkkeeIRFMvsFGGRRoRQCpZIG4pylp2fEU_VPMkQXgreVrF4AAnlGtwOeRL9RhFemLPTLwMJSY8WZnEZK0EhCw-zMOgO2oYwUkyhcNNlyUGhQWtpbs9LpvRgflrzsvHQi4kBiByQ\",\n" +
            "            \"width\" : 4032\n" +
            "         }\n" +
            "      ],\n" +
            "      \"place_id\" : \"ChIJ63nf-c0O3IARc7ASsIi2vG4\",\n" +
            "      \"price_level\" : 2,\n" +
            "      \"rating\" : 4.4,\n" +
            "      \"reference\" : \"CmRRAAAAs9W0nr6jp7Gt5F1lyyNRYJyKhwqo4NhScS23QsDty9GO_5BFBuQ-OpPoKjqLJSxBwQKJBtiUcaKcN5QEARVp4Ux2gkWdAwydKg8BYWKM3DNSQ0oPAHSHsIcIfntV1gC7EhDTgu3nofxx71fBg-XokQVlGhR4orgqgOAIflbWBDAj20CNP7HOIg\",\n" +
            "      \"reviews\" : [\n" +
            "         {\n" +
            "            \"aspects\" : [\n" +
            "               {\n" +
            "                  \"rating\" : 3,\n" +
            "                  \"type\" : \"overall\"\n" +
            "               }\n" +
            "            ],\n" +
            "            \"author_name\" : \"Kevin Venable\",\n" +
            "            \"author_url\" : \"https://www.google.com/maps/contrib/113824459106238122535/reviews\",\n" +
            "            \"language\" : \"en\",\n" +
            "            \"profile_photo_url\" : \"//lh3.googleusercontent.com/-cZYnd_GyCgE/AAAAAAAAAAI/AAAAAAAAEts/utkbZjQR2BY/s128/photo.jpg\",\n" +
            "            \"rating\" : 5,\n" +
            "            \"relative_time_description\" : \"2 weeks ago\",\n" +
            "            \"text\" : \"Great location with excellent food and service.\\n\\nNew management. The service here is fantastic. I love their organic coffee and HUGE selection of vegetarian and vegan options. The portion sizes are very generous. I almost always have leftovers to enjoy later.\\n\\nAll their food is very fresh and well prepared. This is my absolute favorite breakfast spot in all of San Diego.\",\n" +
            "            \"time\" : 1487175518\n" +
            "         },\n" +
            "         {\n" +
            "            \"aspects\" : [\n" +
            "               {\n" +
            "                  \"rating\" : 2,\n" +
            "                  \"type\" : \"overall\"\n" +
            "               }\n" +
            "            ],\n" +
            "            \"author_name\" : \"Alyssa Lumpee\",\n" +
            "            \"author_url\" : \"https://www.google.com/maps/contrib/100318981971014151866/reviews\",\n" +
            "            \"language\" : \"en\",\n" +
            "            \"profile_photo_url\" : \"//lh4.googleusercontent.com/-4YPNlg6VCrM/AAAAAAAAAAI/AAAAAAAAABw/WS-9pH1BaTM/s128/photo.jpg\",\n" +
            "            \"rating\" : 4,\n" +
            "            \"relative_time_description\" : \"3 months ago\",\n" +
            "            \"text\" : \"So many wonderful healthy choices that it was hard to decide! This place is a relatively small restaurant but very open and bright as well. If you sit on the far end of the restaurant, there is a nice view of the beach. I went with my friend and we both enjoyed our lattes and meal. The wait staff was not quite as attentive as they could have been (it took 5-10 minutes after being seated to order drinks and then they were never refilled) so that's why I gave 4 stars. Overall though, if you want a delicious and health conscious meal, this place is a must visit! \",\n" +
            "            \"time\" : 1478926145\n" +
            "         },\n" +
            "         {\n" +
            "            \"aspects\" : [\n" +
            "               {\n" +
            "                  \"rating\" : 2,\n" +
            "                  \"type\" : \"overall\"\n" +
            "               }\n" +
            "            ],\n" +
            "            \"author_name\" : \"Carroll Perez\",\n" +
            "            \"author_url\" : \"https://www.google.com/maps/contrib/107944158664749370015/reviews\",\n" +
            "            \"language\" : \"en\",\n" +
            "            \"profile_photo_url\" : \"//lh5.googleusercontent.com/-a3O4psVufw0/AAAAAAAAAAI/AAAAAAAAAA8/LKD2R0JHBiQ/s128/photo.jpg\",\n" +
            "            \"rating\" : 4,\n" +
            "            \"relative_time_description\" : \"2 weeks ago\",\n" +
            "            \"text\" : \"Food was fantastic.  Had the organic, Italian bred beef with the Tennessee bacon and Oregon blue cheese.  Real yummy.  Definitely returning.\",\n" +
            "            \"time\" : 1487246404\n" +
            "         },\n" +
            "         {\n" +
            "            \"aspects\" : [\n" +
            "               {\n" +
            "                  \"rating\" : 3,\n" +
            "                  \"type\" : \"overall\"\n" +
            "               }\n" +
            "            ],\n" +
            "            \"author_name\" : \"Zed star\",\n" +
            "            \"author_url\" : \"https://www.google.com/maps/contrib/115027286096822577705/reviews\",\n" +
            "            \"language\" : \"en\",\n" +
            "            \"profile_photo_url\" : \"//lh3.googleusercontent.com/-1FLZL4ZEqEQ/AAAAAAAAAAI/AAAAAAAAAlo/NB2cVLBdzhQ/s128/photo.jpg\",\n" +
            "            \"rating\" : 5,\n" +
            "            \"relative_time_description\" : \"2 months ago\",\n" +
            "            \"text\" : \"THIS CAFE IS A DEAD SET WINNER!Terrific menu and an awesome ocean view...what more could you want?My wife and I thoroughly enjoyed our beautifully prepared breakfast meals this morning.Thanks to Krista and Darius for their personal attention.And they play great background music too........We're back again a few weeks later.Lovely Sofia is looking after us while we have lunch with our 2 year grand daughter.Fabulous service!\",\n" +
            "            \"time\" : 1482801696\n" +
            "         },\n" +
            "         {\n" +
            "            \"aspects\" : [\n" +
            "               {\n" +
            "                  \"rating\" : 3,\n" +
            "                  \"type\" : \"overall\"\n" +
            "               }\n" +
            "            ],\n" +
            "            \"author_name\" : \"S Grim\",\n" +
            "            \"author_url\" : \"https://www.google.com/maps/contrib/106602121847522349941/reviews\",\n" +
            "            \"language\" : \"en\",\n" +
            "            \"profile_photo_url\" : \"//lh6.googleusercontent.com/-rQbsY1bA7hA/AAAAAAAAAAI/AAAAAAAAFTk/nmXYOR_zUXM/s128/photo.jpg\",\n" +
            "            \"rating\" : 5,\n" +
            "            \"relative_time_description\" : \"5 months ago\",\n" +
            "            \"text\" : \"Delicious food!! Vegetarian and vegan food options too. Food presentation is lovely.  Friendly workers.  Clean setting. Quick service, unless they are understaffed at the time. Closing time is early (2:30pm), only bothers me because I'd like to eat there in the evenings too. \\nHopefully they'll get windows installed instead of a clear tarp, otherwise the decor was comfortable, pleasing to the eye, and laid-back. \",\n" +
            "            \"time\" : 1474315367\n" +
            "         }\n" +
            "      ],\n" +
            "      \"scope\" : \"GOOGLE\",\n" +
            "      \"types\" : [ \"cafe\", \"restaurant\", \"food\", \"point_of_interest\", \"establishment\" ],\n" +
            "      \"url\" : \"https://maps.google.com/?cid=7979453337979498611\",\n" +
            "      \"utc_offset\" : -480,\n" +
            "      \"vicinity\" : \"106 South Sierra Avenue, Solana Beach\",\n" +
            "      \"website\" : \"http://thenakedcafe.com/\"\n" +
            "   },\n" +
            "   \"status\" : \"OK\"\n" +
            "}\n";

    private static final String FOUR_PHOTOS = "{\n" +
            "   \"html_attributions\" : [],\n" +
            "   \"result\" : {\n" +
            "      \"address_components\" : [\n" +
            "         {\n" +
            "            \"long_name\" : \"2946\",\n" +
            "            \"short_name\" : \"2946\",\n" +
            "            \"types\" : [ \"street_number\" ]\n" +
            "         },\n" +
            "         {\n" +
            "            \"long_name\" : \"Chain Bridge Road\",\n" +
            "            \"short_name\" : \"Chain Bridge Rd\",\n" +
            "            \"types\" : [ \"route\" ]\n" +
            "         },\n" +
            "         {\n" +
            "            \"long_name\" : \"Oakton\",\n" +
            "            \"short_name\" : \"Oakton\",\n" +
            "            \"types\" : [ \"locality\", \"political\" ]\n" +
            "         },\n" +
            "         {\n" +
            "            \"long_name\" : \"Providence\",\n" +
            "            \"short_name\" : \"Providence\",\n" +
            "            \"types\" : [ \"administrative_area_level_3\", \"political\" ]\n" +
            "         },\n" +
            "         {\n" +
            "            \"long_name\" : \"Fairfax County\",\n" +
            "            \"short_name\" : \"Fairfax County\",\n" +
            "            \"types\" : [ \"administrative_area_level_2\", \"political\" ]\n" +
            "         },\n" +
            "         {\n" +
            "            \"long_name\" : \"Virginia\",\n" +
            "            \"short_name\" : \"VA\",\n" +
            "            \"types\" : [ \"administrative_area_level_1\", \"political\" ]\n" +
            "         },\n" +
            "         {\n" +
            "            \"long_name\" : \"United States\",\n" +
            "            \"short_name\" : \"US\",\n" +
            "            \"types\" : [ \"country\", \"political\" ]\n" +
            "         },\n" +
            "         {\n" +
            "            \"long_name\" : \"22124\",\n" +
            "            \"short_name\" : \"22124\",\n" +
            "            \"types\" : [ \"postal_code\" ]\n" +
            "         }\n" +
            "      ],\n" +
            "      \"adr_address\" : \"\\u003cspan class=\\\"street-address\\\"\\u003e2946 Chain Bridge Rd\\u003c/span\\u003e, \\u003cspan class=\\\"locality\\\"\\u003eOakton\\u003c/span\\u003e, \\u003cspan class=\\\"region\\\"\\u003eVA\\u003c/span\\u003e \\u003cspan class=\\\"postal-code\\\"\\u003e22124\\u003c/span\\u003e, \\u003cspan class=\\\"country-name\\\"\\u003eUSA\\u003c/span\\u003e\",\n" +
            "      \"formatted_address\" : \"2946 Chain Bridge Rd, Oakton, VA 22124, USA\",\n" +
            "      \"formatted_phone_number\" : \"(571) 328-5279\",\n" +
            "      \"geometry\" : {\n" +
            "         \"location\" : {\n" +
            "            \"lat\" : 38.8812489,\n" +
            "            \"lng\" : -77.3019053\n" +
            "         },\n" +
            "         \"viewport\" : {\n" +
            "            \"northeast\" : {\n" +
            "               \"lat\" : 38.8823467302915,\n" +
            "               \"lng\" : -77.30031046970849\n" +
            "            },\n" +
            "            \"southwest\" : {\n" +
            "               \"lat\" : 38.8796487697085,\n" +
            "               \"lng\" : -77.3030084302915\n" +
            "            }\n" +
            "         }\n" +
            "      },\n" +
            "      \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/restaurant-71.png\",\n" +
            "      \"id\" : \"3f58d30a61e77b834e5ea285976a07e3ff8b44fa\",\n" +
            "      \"international_phone_number\" : \"+1 571-328-5279\",\n" +
            "      \"name\" : \"Chipotle Mexican Grill\",\n" +
            "      \"opening_hours\" : {\n" +
            "         \"open_now\" : false,\n" +
            "         \"periods\" : [\n" +
            "            {\n" +
            "               \"close\" : {\n" +
            "                  \"day\" : 0,\n" +
            "                  \"time\" : \"2200\"\n" +
            "               },\n" +
            "               \"open\" : {\n" +
            "                  \"day\" : 0,\n" +
            "                  \"time\" : \"1045\"\n" +
            "               }\n" +
            "            },\n" +
            "            {\n" +
            "               \"close\" : {\n" +
            "                  \"day\" : 1,\n" +
            "                  \"time\" : \"2200\"\n" +
            "               },\n" +
            "               \"open\" : {\n" +
            "                  \"day\" : 1,\n" +
            "                  \"time\" : \"1045\"\n" +
            "               }\n" +
            "            },\n" +
            "            {\n" +
            "               \"close\" : {\n" +
            "                  \"day\" : 2,\n" +
            "                  \"time\" : \"2200\"\n" +
            "               },\n" +
            "               \"open\" : {\n" +
            "                  \"day\" : 2,\n" +
            "                  \"time\" : \"1045\"\n" +
            "               }\n" +
            "            },\n" +
            "            {\n" +
            "               \"close\" : {\n" +
            "                  \"day\" : 3,\n" +
            "                  \"time\" : \"2200\"\n" +
            "               },\n" +
            "               \"open\" : {\n" +
            "                  \"day\" : 3,\n" +
            "                  \"time\" : \"1045\"\n" +
            "               }\n" +
            "            },\n" +
            "            {\n" +
            "               \"close\" : {\n" +
            "                  \"day\" : 4,\n" +
            "                  \"time\" : \"2200\"\n" +
            "               },\n" +
            "               \"open\" : {\n" +
            "                  \"day\" : 4,\n" +
            "                  \"time\" : \"1045\"\n" +
            "               }\n" +
            "            },\n" +
            "            {\n" +
            "               \"close\" : {\n" +
            "                  \"day\" : 5,\n" +
            "                  \"time\" : \"2200\"\n" +
            "               },\n" +
            "               \"open\" : {\n" +
            "                  \"day\" : 5,\n" +
            "                  \"time\" : \"1045\"\n" +
            "               }\n" +
            "            },\n" +
            "            {\n" +
            "               \"close\" : {\n" +
            "                  \"day\" : 6,\n" +
            "                  \"time\" : \"2200\"\n" +
            "               },\n" +
            "               \"open\" : {\n" +
            "                  \"day\" : 6,\n" +
            "                  \"time\" : \"1045\"\n" +
            "               }\n" +
            "            }\n" +
            "         ],\n" +
            "         \"weekday_text\" : [\n" +
            "            \"Monday: 10:45 AM – 10:00 PM\",\n" +
            "            \"Tuesday: 10:45 AM – 10:00 PM\",\n" +
            "            \"Wednesday: 10:45 AM – 10:00 PM\",\n" +
            "            \"Thursday: 10:45 AM – 10:00 PM\",\n" +
            "            \"Friday: 10:45 AM – 10:00 PM\",\n" +
            "            \"Saturday: 10:45 AM – 10:00 PM\",\n" +
            "            \"Sunday: 10:45 AM – 10:00 PM\"\n" +
            "         ]\n" +
            "      },\n" +
            "      \"photos\" : [\n" +
            "         {\n" +
            "            \"height\" : 3024,\n" +
            "            \"html_attributions\" : [\n" +
            "               \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/109086084132652286603/photos\\\"\\u003eColin Reddig\\u003c/a\\u003e\"\n" +
            "            ],\n" +
            "            \"photo_reference\" : \"CoQBdwAAAKFLN6tLLQZphS-GT7Xz3-avIrRsRq1E4t1ZJGUR38Ld8wNxL2rSKjphtrk2unsrzD5QcDGepuYE8MWvyEVkWYA88A2OoPhqZ1cGfufmwkb2jq7KDKGGk8YMQDTLVRSNz5V1s40IFvTy9Zgh7JfFf7I0axA2DAXFxhiFjRRF8UqiEhAtVFMkcz5kMEcWZCi_prSrGhTR4fo-lmYqqricrgWroxAVfsXMlw\",\n" +
            "            \"width\" : 4032\n" +
            "         },\n" +
            "         {\n" +
            "            \"height\" : 2340,\n" +
            "            \"html_attributions\" : [\n" +
            "               \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/112747688298618461298/photos\\\"\\u003eRoberto Sanchez\\u003c/a\\u003e\"\n" +
            "            ],\n" +
            "            \"photo_reference\" : \"CoQBcwAAANccwAuduMQ2XbCNJmWZcUNmho4xFiqkgLjVkjSgGPqspxoWw-866o9cvb6BLRhtsikMegsIyc4SK9rRIkrlbv_BXNqMLfI2NuCxaJUJSPD44jtW2GemOiL1lPQzg7igEPMfivodc1ri0DanUcdkjNfdm75nqf8RMjwrAe6-QMU6EhA_kcc_YvuKe2ZfmSLo40kIGhTVvq3oqZDJGYEJ-Rhc5tHxbAvLww\",\n" +
            "            \"width\" : 4160\n" +
            "         },\n" +
            "         {\n" +
            "            \"height\" : 750,\n" +
            "            \"html_attributions\" : [\n" +
            "               \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/104465136491439120241/photos\\\"\\u003eAndrew Bywater\\u003c/a\\u003e\"\n" +
            "            ],\n" +
            "            \"photo_reference\" : \"CoQBdwAAADQetwdhQmNeoWd6znUCTGhSDNT48hfJg_brRksc46o3lBTFNi7ymGZf_msFmjFArlTd-0XaKT8TTYYtCN9xsq0Ua7Z3Jbp6Bl98f_Aq06pXZS6V74iFivmAo6DBUCnANlCqGVni6Zq_RPn3rwELrI3egWSwp3aSNSCsUsPam1caEhCb21696zgSqbrxYKWjQtAEGhQlw2-o4sQ9FJx8OocedihSsjqK7w\",\n" +
            "            \"width\" : 1334\n" +
            "         },\n" +
            "         {\n" +
            "            \"height\" : 750,\n" +
            "            \"html_attributions\" : [\n" +
            "               \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/104465136491439120241/photos\\\"\\u003eAndrew Bywater\\u003c/a\\u003e\"\n" +
            "            ],\n" +
            "            \"photo_reference\" : \"CoQBdwAAAPF17jRmhLABmM8p2CEaNBZd-us5ibLCZ0l4jutxxHXNiENYz7fMKzfzyiliazab3VdirUBg_D_UKIFMYKNLofM-M5ScuGL5lkP_4ODJFiZxMNybX1TXszZvu6XxeWiaaP3Gi-iDNiTEZkvnjlYKyutyMXNUlrRvWbbK01u9RmHHEhDAzRrsMoJtRXJViVYnHRolGhRSEbrqmmKYSQ2qfmDykkG58HFgHQ\",\n" +
            "            \"width\" : 1334\n" +
            "         }\n" +
            "      ],\n" +
            "      \"place_id\" : \"ChIJhfMz6E5JtokR0peFefvDW8s\",\n" +
            "      \"price_level\" : 1,\n" +
            "      \"rating\" : 3.1,\n" +
            "      \"reference\" : \"CmRSAAAAgGAOqlAmev90RXgLNiDVOUNEnp1u6E64WheSvgXEezQXxYF-j1QyzRkQ7sZcIU4xDGYkw_JWVHJ4jCNwl4fWg8TLsQP7vC6H6OZsXXiKJAe3Q7NX2JOJqI_1EiDwqRlyEhD9KiZmmLVYddrAbJ4_Z9QMGhTrSImCF0v-c4PZ5PyJBt9hdFke9g\",\n" +
            "      \"reviews\" : [\n" +
            "         {\n" +
            "            \"aspects\" : [\n" +
            "               {\n" +
            "                  \"rating\" : 3,\n" +
            "                  \"type\" : \"overall\"\n" +
            "               }\n" +
            "            ],\n" +
            "            \"author_name\" : \"Pamela Thompson\",\n" +
            "            \"author_url\" : \"https://www.google.com/maps/contrib/113467569801136598182/reviews\",\n" +
            "            \"language\" : \"en\",\n" +
            "            \"profile_photo_url\" : \"https://lh5.googleusercontent.com/-mJM4beu_1Sw/AAAAAAAAAAI/AAAAAAAAAAA/AMcAYi9BlGwUQWGfNigQyejb7LV8IEXXoQ/s128-c0x00000000-cc-rp-mo/photo.jpg\",\n" +
            "            \"rating\" : 5,\n" +
            "            \"relative_time_description\" : \"a month ago\",\n" +
            "            \"text\" : \"This place has been a family favorite for almost 30 years! The menu has had minor changes and they deliver a consistently great breakfast or lunch. I love their skillets the best but you can't go wrong with their pancakes, french toast or omlets. When we have guests from out of town, we always take them to TC Eggington's! And\",\n" +
            "            \"time\" : 1485592067\n" +
            "         },\n" +
            "         {\n" +
            "            \"aspects\" : [\n" +
            "               {\n" +
            "                  \"rating\" : 3,\n" +
            "                  \"type\" : \"overall\"\n" +
            "               }\n" +
            "            ],\n" +
            "            \"author_name\" : \"Mark Kuite\",\n" +
            "            \"author_url\" : \"https://www.google.com/maps/contrib/102575666981833509623/reviews\",\n" +
            "            \"language\" : \"en\",\n" +
            "            \"profile_photo_url\" : \"https://lh5.googleusercontent.com/-9ip80e6Xq0E/AAAAAAAAAAI/AAAAAAAAASY/iz_VPWlwKh8/s128-c0x00000000-cc-rp-mo-ba1/photo.jpg\",\n" +
            "            \"rating\" : 5,\n" +
            "            \"relative_time_description\" : \"3 months ago\",\n" +
            "            \"text\" : \"Food is good, portions are great and the staff is friendly. I've never had a bad meal here.\",\n" +
            "            \"time\" : 1480808129\n" +
            "         },\n" +
            "         {\n" +
            "            \"aspects\" : [\n" +
            "               {\n" +
            "                  \"rating\" : 1,\n" +
            "                  \"type\" : \"overall\"\n" +
            "               }\n" +
            "            ],\n" +
            "            \"author_name\" : \"Wilbur Landaverde\",\n" +
            "            \"author_url\" : \"https://www.google.com/maps/contrib/116662343568504392465/reviews\",\n" +
            "            \"language\" : \"en\",\n" +
            "            \"profile_photo_url\" : \"https://lh3.googleusercontent.com/-D0Hj-KP0s84/AAAAAAAAAAI/AAAAAAAACi4/Ju6kkc4UvmU/s128-c0x00000000-cc-rp-mo/photo.jpg\",\n" +
            "            \"rating\" : 3,\n" +
            "            \"relative_time_description\" : \"3 weeks ago\",\n" +
            "            \"text\" : \"The standard chipotle. Always light on the meats.\",\n" +
            "            \"time\" : 1488316225\n" +
            "         },\n" +
            "         {\n" +
            "            \"aspects\" : [\n" +
            "               {\n" +
            "                  \"rating\" : 3,\n" +
            "                  \"type\" : \"overall\"\n" +
            "               }\n" +
            "            ],\n" +
            "            \"author_name\" : \"Artin Yousefi\",\n" +
            "            \"author_url\" : \"https://www.google.com/maps/contrib/102354053264928516718/reviews\",\n" +
            "            \"language\" : \"en\",\n" +
            "            \"profile_photo_url\" : \"https://lh5.googleusercontent.com/-J2HVGg80btA/AAAAAAAAAAI/AAAAAAAAJeI/xpe4-U0x7JQ/s128-c0x00000000-cc-rp-mo/photo.jpg\",\n" +
            "            \"rating\" : 5,\n" +
            "            \"relative_time_description\" : \"3 months ago\",\n" +
            "            \"text\" : \"This Chipotle arguably has the best quality food among Chipotles in Fairfax area.\",\n" +
            "            \"time\" : 1481871788\n" +
            "         },\n" +
            "         {\n" +
            "            \"aspects\" : [\n" +
            "               {\n" +
            "                  \"rating\" : 0,\n" +
            "                  \"type\" : \"overall\"\n" +
            "               }\n" +
            "            ],\n" +
            "            \"author_name\" : \"John McLaughlin\",\n" +
            "            \"author_url\" : \"https://www.google.com/maps/contrib/116965907941478100472/reviews\",\n" +
            "            \"language\" : \"en\",\n" +
            "            \"profile_photo_url\" : \"https://lh5.googleusercontent.com/-CrnmkStRVPE/AAAAAAAAAAI/AAAAAAAAJ-A/882SkooARn0/s128-c0x00000000-cc-rp-mo-ba1/photo.jpg\",\n" +
            "            \"rating\" : 2,\n" +
            "            \"relative_time_description\" : \"3 weeks ago\",\n" +
            "            \"text\" : \"Food never tastes right. Employees slow and skimp on ingredients.\",\n" +
            "            \"time\" : 1488683439\n" +
            "         }\n" +
            "      ],\n" +
            "      \"scope\" : \"GOOGLE\",\n" +
            "      \"types\" : [ \"restaurant\", \"food\", \"point_of_interest\", \"establishment\" ],\n" +
            "      \"url\" : \"https://maps.google.com/?cid=14653521297423046610\",\n" +
            "      \"utc_offset\" : -240,\n" +
            "      \"vicinity\" : \"2946 Chain Bridge Road, Oakton\",\n" +
            "      \"website\" : \"http://www.chipotle.com/\"\n" +
            "   },\n" +
            "   \"status\" : \"OK\"\n" +
            "}";

    private static final String ZERO_PHOTOS = "{\n" +
            "   \"html_attributions\" : [],\n" +
            "   \"result\" : {\n" +
            "      \"address_components\" : [\n" +
            "         {\n" +
            "            \"long_name\" : \"10579\",\n" +
            "            \"short_name\" : \"10579\",\n" +
            "            \"types\" : [ \"street_number\" ]\n" +
            "         },\n" +
            "         {\n" +
            "            \"long_name\" : \"Fairfax Boulevard\",\n" +
            "            \"short_name\" : \"Fairfax Blvd\",\n" +
            "            \"types\" : [ \"route\" ]\n" +
            "         },\n" +
            "         {\n" +
            "            \"long_name\" : \"Fairfax\",\n" +
            "            \"short_name\" : \"Fairfax\",\n" +
            "            \"types\" : [ \"locality\", \"political\" ]\n" +
            "         },\n" +
            "         {\n" +
            "            \"long_name\" : \"Virginia\",\n" +
            "            \"short_name\" : \"VA\",\n" +
            "            \"types\" : [ \"administrative_area_level_1\", \"political\" ]\n" +
            "         },\n" +
            "         {\n" +
            "            \"long_name\" : \"United States\",\n" +
            "            \"short_name\" : \"US\",\n" +
            "            \"types\" : [ \"country\", \"political\" ]\n" +
            "         },\n" +
            "         {\n" +
            "            \"long_name\" : \"22030\",\n" +
            "            \"short_name\" : \"22030\",\n" +
            "            \"types\" : [ \"postal_code\" ]\n" +
            "         },\n" +
            "         {\n" +
            "            \"long_name\" : \"3138\",\n" +
            "            \"short_name\" : \"3138\",\n" +
            "            \"types\" : [ \"postal_code_suffix\" ]\n" +
            "         }\n" +
            "      ],\n" +
            "      \"adr_address\" : \"\\u003cspan class=\\\"street-address\\\"\\u003e10579 Fairfax Blvd\\u003c/span\\u003e, \\u003cspan class=\\\"locality\\\"\\u003eFairfax\\u003c/span\\u003e, \\u003cspan class=\\\"region\\\"\\u003eVA\\u003c/span\\u003e \\u003cspan class=\\\"postal-code\\\"\\u003e22030-3138\\u003c/span\\u003e, \\u003cspan class=\\\"country-name\\\"\\u003eUSA\\u003c/span\\u003e\",\n" +
            "      \"formatted_address\" : \"10579 Fairfax Blvd, Fairfax, VA 22030, USA\",\n" +
            "      \"formatted_phone_number\" : \"(703) 272-8113\",\n" +
            "      \"geometry\" : {\n" +
            "         \"location\" : {\n" +
            "            \"lat\" : 38.8570162,\n" +
            "            \"lng\" : -77.31001639999999\n" +
            "         },\n" +
            "         \"viewport\" : {\n" +
            "            \"northeast\" : {\n" +
            "               \"lat\" : 38.8583790302915,\n" +
            "               \"lng\" : -77.30872346970848\n" +
            "            },\n" +
            "            \"southwest\" : {\n" +
            "               \"lat\" : 38.8556810697085,\n" +
            "               \"lng\" : -77.31142143029149\n" +
            "            }\n" +
            "         }\n" +
            "      },\n" +
            "      \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/restaurant-71.png\",\n" +
            "      \"id\" : \"46739b6aed1d582d47680bb45cde78ee9ee26847\",\n" +
            "      \"international_phone_number\" : \"+1 703-272-8113\",\n" +
            "      \"name\" : \"Vespucci Fine Dining\",\n" +
            "      \"permanently_closed\" : true,\n" +
            "      \"place_id\" : \"ChIJrU9ph8BOtokR7ogSh-WBfPI\",\n" +
            "      \"rating\" : 3.3,\n" +
            "      \"reference\" : \"CmRSAAAA-W61pt59U4LZQktlaYHQW4qEWmcTXNVMSGQj7Kne49bnqwEGKvXo1m4hrkFtu_Sm2peVefDwOOlYceB_X8qQ_ijFkNIzFKp7guOF1D9KMZ2Y2CuF61llp8O91PdbhnRbEhCSE_7y5uZ5BWbwRKXiOFHjGhTOmkoWfkNrMGyUrHoTQ9xUcDQDmw\",\n" +
            "      \"reviews\" : [\n" +
            "         {\n" +
            "            \"aspects\" : [\n" +
            "               {\n" +
            "                  \"rating\" : 3,\n" +
            "                  \"type\" : \"overall\"\n" +
            "               }\n" +
            "            ],\n" +
            "            \"author_name\" : \"Daniel Williams\",\n" +
            "            \"author_url\" : \"https://www.google.com/maps/contrib/107117339686306922833/reviews\",\n" +
            "            \"language\" : \"en\",\n" +
            "            \"profile_photo_url\" : \"https://lh3.googleusercontent.com/-Aluha1Eb3tg/AAAAAAAAAAI/AAAAAAAAASM/aHh0XKgoR9o/s128-c0x00000000-cc-rp-mo/photo.jpg\",\n" +
            "            \"rating\" : 5,\n" +
            "            \"relative_time_description\" : \"5 years ago\",\n" +
            "            \"text\" : \"I live in San Diego, but have family in Fairfax and eating there when we visit is a kin to when my family comes to CA and wants an In-n-Out burger.  I know it’s different food, but this place is that genuinely classic and just amazing, no place like it.  We have Little Italy in downtown San Diego and I’ve eaten at almost all of the restaurants there and none of them come close to Vespucci.  The place is amazing and I wish they would come to San Diego and locate in our Little Italy section, then again if they did, they would put all the other restaurants out of business because nobody would go anywhere else but Vespucci.  THIS PLACE IS THE BEST!!!!!\",\n" +
            "            \"time\" : 1330226110\n" +
            "         },\n" +
            "         {\n" +
            "            \"aspects\" : [\n" +
            "               {\n" +
            "                  \"rating\" : 0,\n" +
            "                  \"type\" : \"overall\"\n" +
            "               }\n" +
            "            ],\n" +
            "            \"author_name\" : \"Lucas Cora\",\n" +
            "            \"author_url\" : \"https://www.google.com/maps/contrib/109450251861763419008/reviews\",\n" +
            "            \"language\" : \"en\",\n" +
            "            \"profile_photo_url\" : \"https://lh3.googleusercontent.com/-7MvAkxrAfWA/AAAAAAAAAAI/AAAAAAAAAAA/AMcAYi89ipdV74ByBMAhOVvldgzajqsDuQ/s128-c0x00000000-cc-rp-mo/photo.jpg\",\n" +
            "            \"rating\" : 1,\n" +
            "            \"relative_time_description\" : \"2 years ago\",\n" +
            "            \"text\" : \"When I asked for the Restuchi, all they did was give me a Restuchi action figure!! If your a Restuchi fan I do not recommend\",\n" +
            "            \"time\" : 1397758806\n" +
            "         },\n" +
            "         {\n" +
            "            \"aspects\" : [\n" +
            "               {\n" +
            "                  \"rating\" : 3,\n" +
            "                  \"type\" : \"overall\"\n" +
            "               }\n" +
            "            ],\n" +
            "            \"author_name\" : \"Saundra Jordan\",\n" +
            "            \"author_url\" : \"https://www.google.com/maps/contrib/103871202261766277964/reviews\",\n" +
            "            \"language\" : \"en\",\n" +
            "            \"profile_photo_url\" : \"https://lh5.googleusercontent.com/-xd0TBQFFFA0/AAAAAAAAAAI/AAAAAAAAABM/lbsl1LlkiAs/s128-c0x00000000-cc-rp-mo/photo.jpg\",\n" +
            "            \"rating\" : 5,\n" +
            "            \"relative_time_description\" : \"3 years ago\",\n" +
            "            \"text\" : \"Perfect food. Freshly made. Excellent choices!\",\n" +
            "            \"time\" : 1372176312\n" +
            "         },\n" +
            "         {\n" +
            "            \"aspects\" : [\n" +
            "               {\n" +
            "                  \"rating\" : 3,\n" +
            "                  \"type\" : \"overall\"\n" +
            "               }\n" +
            "            ],\n" +
            "            \"author_name\" : \"A Google User\",\n" +
            "            \"language\" : \"en\",\n" +
            "            \"rating\" : 5,\n" +
            "            \"relative_time_description\" : \"6 years ago\",\n" +
            "            \"text\" : \"Vespucci is excellent.  This is a wonderful place.  It has the feeling of Italian romance.  It has these wonderful tables for two that just make you feel you are going to a special place just for you and your partner.  The staff is attentive and they hit the mark for us each time we needed them to appear.  The food was served with precision.  It was hot off the grill.  My guess is that it was on our table in less than a minute after it the Chef put it on our plate.  The food was a delight.  I must tell you that Rosie and I will return againg for another romantic dinner.\",\n" +
            "            \"time\" : 1296961525\n" +
            "         },\n" +
            "         {\n" +
            "            \"aspects\" : [\n" +
            "               {\n" +
            "                  \"rating\" : 2,\n" +
            "                  \"type\" : \"overall\"\n" +
            "               }\n" +
            "            ],\n" +
            "            \"author_name\" : \"A Google User\",\n" +
            "            \"language\" : \"en\",\n" +
            "            \"rating\" : 4,\n" +
            "            \"relative_time_description\" : \"7 years ago\",\n" +
            "            \"text\" : \"Ate at this restaurant on a Sunday night.   The house wine was okay, but a bit overpriced.  The appetizer -- three tiny, triangular crepes -- tasted good, but were laughably inadequate.  The eggplant parmesan, like the wine and appetizer, was okay, but nothing to write home about.\\n\\nInterestingly, it was senior citizen open-mic night.  A large party of older amateur singers took turns singing odd songs from the distant past while accompanied by a guy with a keyboard who rambled obsessively about Elvis' birthday.\\n\\nDespite it being (ostensibly) an Italian joint, the staff seemed to consist primarily of Mexicans unable to speak English.  They were all very nice, but kept referring us to the one or two people who could speak the language.  The owner spoke English and seemed nice.\\n\\nNot bad, but I wouldn't recommend it over a proper Italian restaurant.  The flavor was average, but I'll give them an extra star for the selection.\",\n" +
            "            \"time\" : 1263176628\n" +
            "         }\n" +
            "      ],\n" +
            "      \"scope\" : \"GOOGLE\",\n" +
            "      \"types\" : [ \"bar\", \"restaurant\", \"food\", \"point_of_interest\", \"establishment\" ],\n" +
            "      \"url\" : \"https://maps.google.com/?cid=17472983477104314606\",\n" +
            "      \"utc_offset\" : -240,\n" +
            "      \"vicinity\" : \"10579 Fairfax Boulevard, Fairfax\"\n" +
            "   },\n" +
            "   \"status\" : \"OK\"\n" +
            "}";
}
