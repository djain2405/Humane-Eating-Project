package org.hep.afa.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.hep.afa.main.HEPApplication;
import org.hep.afa.R;
import org.hep.afa.model.FacebookPost;
import org.hep.afa.model.FacebookUser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by heather on 10/13/16.
 *
 * This class builds Facebook requests as needed and returns results via the specified
 * FacebookFeedListener.
 *
 * Any method called to request Post data from Facebook must provide a valid RequestQueue.
 *
 * If the nextRequestFeed method is called, the FacebookUtils object handling requests should live
 * for the lifetime of all requests in order to properly support pagination.
 *
 */

public class FacebookUtils {

    // hashtag and url scheme definitions
    public static final String HASHTAG_SCHEME =
        HEPApplication.getMyAppContext().getString(R.string.hashtag_scheme) + "://";
    public static final String HTTP_SHEME =
        HEPApplication.getMyAppContext().getString(R.string.url_scheme) + "://";

    // url with specified hashtag
    public static final String FB_HASHTAG_URL = "http://www.facebook.com/hashtag/%1s";
    public static String HEP_FB_URL = "http://www.facebook.com/Humaneeatingproject";

    // JSON Fields requested in Facebook Post Query
    public static final String MESSAGE = "message";
    public static final String CREATED_TIME = "created_time";
    public static final String LINK = "link";
    public static final String PICTURE = "picture";
    public static final String COMMENTS = "comments";

    private final String TAG = getClass().getSimpleName();

    // Use these values to test Facebook Graph API versions
    // Not including as part of release as this will require new app release when the specified
    // API is not supported
    private static final Boolean TEST_API_VERSION = false;
    private static final String  FB_API_LEVEL = "v2.8";

    // App Token == "App Id|App Secret"
    // App Secret should not be embedded in application for security reasons (issue noted in Trello)
    // For now, we implement the feature as in iOS
    private static final String APP_TOKEN = "241032352751451|c7d88e052422ff5c5d31c272ac9c4358";

    private static final String FB_AUTHORITY = "graph.facebook.com";
    private static final String USERNAME = "Humaneeatingproject";
    private static final String ACCESS_TOKEN = "access_token";
    private static final String POSTS = "posts";
    private static final String FIELDS = "fields";
    private static final String SINCE = "since";

    private static FacebookUser postedBy;

    private RequestQueue requestQueue;
    private String requestTag;
    private String nextRequest;

    public interface FacebookFeedListener {
        void feedRequestComplete(List<FacebookPost> posts);
        void nextFeedRequestComplete(List<FacebookPost> posts);
        void feedRefreshComplete(List<FacebookPost> posts);
        void feedError(VolleyError error);
    }


    /**
     * Constructor
     *
     * @param requestQueue  The RequestQueue to use for subsequent API calls
     * @param requestTag    All requests submitted to the requestQueue will be supplied
     *                      with the specified requestTag, allowing the caller to cancel any
     *                      outstanding requests if needed.
     */
    public FacebookUtils(RequestQueue requestQueue, String requestTag) {
        this.requestQueue = requestQueue;
        this.requestTag = requestTag;
    }


    /**
     * Retrieves the last time the user viewed the Facebook feed from shared preferences.
     *
     * @return timestamp in seconds the feed was last viewed
     */
    public static long getLastFeedTime() {
        Context context = HEPApplication.getMyAppContext();
        SharedPreferences prefs =
            context.getSharedPreferences(context.getString(R.string.prefs_name), Context.MODE_PRIVATE);
        return prefs.getLong(context.getString(R.string.last_feed_read), 0);
    }


    /**
     * Stores the last time the user viewed the Facebook feed in shared preferences.
     *
     * @param sinceTime timestamp in seconds the feed was last viewed
     */
    public static void setLastFeedTime(long sinceTime) {
        Context context = HEPApplication.getMyAppContext();
        SharedPreferences.Editor editor = context.getSharedPreferences(context.getString(R.string.prefs_name),
            Context.MODE_PRIVATE).edit();
        editor.putLong(context.getString(R.string.last_feed_read), sinceTime);
        editor.apply();
    }

    /**
     * Creates Facebook user profile url with the userId provided
     *
     * @param userId The user id
     * @return the url to the user's facebook profile picture
     */
    public static String createUserProfileImageUrl(String userId) {
        Uri.Builder builder = new Uri.Builder();

        builder.scheme("https")
            .authority(FB_AUTHORITY);

        if (TEST_API_VERSION) {
            builder.appendPath(FB_API_LEVEL);
        }

        builder.appendPath(userId)
            .appendPath(PICTURE);

        return builder.build().toString();
    }


    /**
     * Requests the most recent posts.  Posts will never be returned
     * as null, but may be empty.
     *
     * @param listener feedRequestComplete is called on success
     *                 feedError is called on error
     */
    public void requestFeed(final FacebookFeedListener listener) {

        if (!isPageInfoValid()) {
            // Get the HEP Page info before requesting posts
            requestPageInfo(listener, null);
        }
        else {
            requestNewFeed(listener, false);
        }
    }


    /**
     * Requests the next set of posts by using the 'next' field specified in the Facebook results.
     * The FacebooUtils class maintains the url specified and updates the url with the 'next'
     * field in each subsequent call (the first url is stored via the requestFeed call and is
     * updated with each call to nextRequestFeed).
     *
     * If the 'next' url is not specified or has not been configured, results from this call are
     * the same as calling requestFeed.
     *
     * Posts will never be returned as null, but may be empty.
     *
     * @param listener nextFeedRequestComplete is called on success
     *                 feedError is called on error
     */
    public void nextRequestFeed(final FacebookFeedListener listener) {
        requestNewFeed(listener, true);
    }


    /**
     * Requests posts that are newer than the specified mostRecentPost.  Posts will never be
     * returned as null, but may be empty.
     *
     *
     * @param listener  feedRefreshComplete is called on success
     *                  feedError is called on error
     *
     * @param mostRecentPost The query will be sent using the timestamp of the most recent post
     *                       to specify retrieving newer posts.
     */
    public void refreshFeed(final FacebookFeedListener listener, FacebookPost mostRecentPost) {
        if (!isPageInfoValid()) {
            // Get the HEP Page info before requesting refresh
            requestPageInfo(listener, mostRecentPost);
        }
        else {
            refreshNewFeed(listener, mostRecentPost);
        }
    }


    // Retrieves any posts prior to the mostRecentPost and forwards results to the specified listener
    private void refreshNewFeed(final FacebookFeedListener listener, FacebookPost mostRecentPost) {

        final List<FacebookPost> facebookPosts = new ArrayList<>();
        String url = buildFeedRefreshRequest(mostRecentPost);

        JsonObjectRequest refreshRequest = new JsonObjectRequest
            (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    JSONArray jsonAllPosts = response.optJSONArray("data");

                    if (jsonAllPosts != null) {

                        for (int i = 0; i < jsonAllPosts.length(); i++) {
                            FacebookPost newPost = new FacebookPost(jsonAllPosts.optJSONObject(i));
                            newPost.setPostedBy(postedBy);
                            facebookPosts.add(i, newPost);
                        }
                    }
                    listener.feedRefreshComplete(facebookPosts);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    listener.feedError(error);
                }
            });

        refreshRequest.setTag(requestTag);
        requestQueue.add(refreshRequest);
    }

    // requests the HEP Page info - mostRecentPost is non-null if this method should continue with
    // a feed refresh, otherwise a new feed is retrieved
    private void requestPageInfo(final FacebookFeedListener listener, final FacebookPost mostRecentPost) {

        String url = buildPageInfoRequest();

        JsonObjectRequest pageIdRequest = new JsonObjectRequest
            (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {

                    // Continue to show feed when we get a valid page info
                    postedBy = new FacebookUser(response);

                    if (isPageInfoValid()) {

                        if (mostRecentPost != null) {
                            refreshNewFeed(listener, mostRecentPost);
                        }
                        else {
                            requestNewFeed(listener, false);
                        }
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    listener.feedError(error);
                }
            });

        pageIdRequest.setTag(requestTag);
        requestQueue.add(pageIdRequest);
    }

    // requests a full set of posts - if getNext is true, this method supports pagination and
    // uses the stored 'next' url if non-null, otherwise it requests the latest posts
    private void requestNewFeed(final FacebookFeedListener listener, final boolean getNext) {

        String url;

        if (getNext && nextRequest != null) {
            url = nextRequest;
        }
        else {
            url = buildFeedRequest();
        }

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest
            (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                List<FacebookPost> facebookPosts = new ArrayList<>();

                @Override
                public void onResponse(JSONObject response) {

                    JSONArray jsonAllPosts = response.optJSONArray("data");
                    JSONObject paging = response.optJSONObject("paging");
                    if (paging != null) {
                        nextRequest = paging.optString("next", null);
                    }
                    else {
                        nextRequest = null;
                    }

                    if (jsonAllPosts != null) {
                        for (int i = 0; i < jsonAllPosts.length(); i++) {
                            FacebookPost newPost = new FacebookPost(jsonAllPosts.optJSONObject(i));
                            newPost.setPostedBy(postedBy);
                            facebookPosts.add(i, newPost);
                        }

                        if (getNext) {
                            listener.nextFeedRequestComplete(facebookPosts);
                        }
                        else {
                            listener.feedRequestComplete(facebookPosts);
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    listener.feedError(error);
                }
            });
        jsonArrayRequest.setTag(requestTag);
        requestQueue.add(jsonArrayRequest);

    }


    // Build request for Page ID
    private String buildPageInfoRequest() {
        Uri.Builder builder = new Uri.Builder();

        builder.scheme("https")
            .authority(FB_AUTHORITY);

        if (TEST_API_VERSION) {
            builder.appendPath(FB_API_LEVEL);
        }

        builder.appendPath(USERNAME)
            .appendQueryParameter(ACCESS_TOKEN, APP_TOKEN);

        return builder.build().toString();
    }

    // Create URI.Builder for requesting posts - the URI.Builder created here can be used
    // standalone for the latest set of posts
    private Uri.Builder buildFeedUri() {
        Uri.Builder builder = new Uri.Builder();

        builder.scheme("https")
            .authority(FB_AUTHORITY);

        if (TEST_API_VERSION) {
            builder.appendPath(FB_API_LEVEL);
        }

        builder.appendPath(postedBy.getUserId())
            .appendPath(POSTS)
            .appendQueryParameter(FIELDS,
                String.format("%s,%s,%s,%s,%s", MESSAGE, CREATED_TIME, LINK, PICTURE, COMMENTS))
            .appendQueryParameter(ACCESS_TOKEN, APP_TOKEN);

        return builder;
    }

    // Build request for latest posts
    private String buildFeedRequest() {

        return buildFeedUri().build().toString();
    }

    // Build request to get posts newer than the post specified by sincePost
    private String buildFeedRefreshRequest(FacebookPost sincePost) {
        Uri.Builder builder = buildFeedUri();
        builder.appendQueryParameter(SINCE, String.valueOf(sincePost.getSinceTime()));

        if (TEST_API_VERSION) {
            builder.appendPath(FB_API_LEVEL);
        }

        return builder.build().toString();
    }

    private boolean isPageInfoValid() {
        if (postedBy == null || postedBy.getUserId() == null || postedBy.getUserId().isEmpty()) {
            return false;
        }
        return true;
    }
}
