package org.hep.afa.main;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.flurry.android.FlurryAgent;
import com.github.pengrad.mapscaleview.MapScaleView;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.rampo.updatechecker.UpdateChecker;
import com.testfairy.TestFairy;

import org.apache.commons.lang3.StringUtils;
import org.hep.afa.database.entities.Favorite;
import org.hep.afa.restaurantdetails.RestaurantListFragment;
import org.hep.afa.R;
import org.hep.afa.addlisting.AutoAddListingActivity;
import org.hep.afa.feed.FeedActivity;
import org.hep.afa.activity.FilterActivity;
import org.hep.afa.activity.FriendsActivity;
import org.hep.afa.activity.LearnActivity;
import org.hep.afa.activity.LegendActivity;
import org.hep.afa.restaurantdetails.RestaurantDetailsActivity;
import org.hep.afa.takeaction.TakeActionActivity;
import org.hep.afa.utils.GeneralUtils;
import org.hep.afa.view.ParentFrameLayout;
import org.hep.afa.viewmodel.FavoriteViewModel;
import org.hep.afa.welcome.WelcomePageActivity;
import org.hep.afa.constant.CuisineType;
import org.hep.afa.constant.HumaneStatus;
import org.hep.afa.constant.PriceRange;
import org.hep.afa.constant.ReviewFilter;
import org.hep.afa.model.DataModel;
import org.hep.afa.model.FacebookPost;
import org.hep.afa.model.HEPRestaurant;
import org.hep.afa.model.QueryBuilder;
import org.hep.afa.utils.FacebookUtils;
import org.hep.afa.utils.LaunchUtils;
import org.hep.afa.utils.LocationUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static org.hep.afa.constant.DistanceConstants.MILES_TO_KILOMETERS_CONVERSION;

/////////////////////////////////////////////////////////
// TODO we should refactor out exposure to firebase and geofire in this class..
// (1) Rename QueryBuilder to something more appropriate RestaurantSearchManager?
// (2) have a reset function that will perform a fresh search
// (3) have a updateLocation that passes in lat, long, and listener
/////////////////////////////////////////////////////////


/**
 * Activity class for Humane Eating App's main screen.
 */
@SuppressLint("JavascriptInterface")
public class HumaneEatingMainActivity extends AppCompatActivity
    implements AnimationListener, FacebookUtils.FacebookFeedListener, OnMapReadyCallback {

    private final String TAG = getClass().getSimpleName();

    // zooming in slightly more than city level (10)
    // if changing this value, you should also change DataModel.DEFAULT_DISTANCE accordingly
    private final static float PREFERRED_CAMERA_ZOOM_LEVEL = 10.5f;

    private GoogleMap mMap;
    private MapScaleView scaleView;
    Marker marker;
    WebView humaneEatingMainActivityWebView;
    ImageButton buttonLegend;   // legend button
    ImageButton buttonFilter;
    ImageButton buttonAddListing;
    Button buttonFriends;    // restaurant add listing button
    Button buttonTakeAction; // donate button
    Button buttonLearn; // learn button
    RelativeLayout buttonFeed;  // feedback button
    Button actionbarSearch; // search action bar button
    SearchView simpleSearchView;
    Button actionbarList; // list action bar button
    TextView textViewFilter; // restaurant filters active overlay
    TextView textViewSearch; // restaurant name search overlay
    TextView feedNotification;

    @BindView(R.id.main_progress_bar)
    ProgressBar progress;

    @BindView(R.id.flip)
    RelativeLayout parentView;

    // These values calculate how much bottom padding to add to the map so that we don't obscure the
    // Google logo
    int mapButtonHeight;
    static final int MAP_BUTTON_HEIGHT_PADDING = 10;

    boolean isConnected;
    boolean checkLaunch;
    LatLng latLng;
    Location loc;
    static boolean camFlag;
    RestaurantListFragment listFragment;

    private Animation animation1;
    private Animation animation2;
    private boolean isBackOfCardShowing = true;
    CameraPosition camPosition;

    HashMap<Marker, HEPRestaurant> markerToRestaurant = new HashMap<Marker, HEPRestaurant>();
    HashMap<String, GeoLocation> locationIdToLocation = new HashMap<String, GeoLocation>();

    // Feed Support
    RequestQueue requestQueue;
    FacebookUtils fbUtils;
    String searchText = "";

    private Snackbar snackbar;

    private GeoQuery geoQuery = null;

    Handler progressHandler = new Handler();
    Runnable progressRunnable = new Runnable() {
        @Override
        public void run() {
            progress.setVisibility(GONE);
        }
    };

    // Favorites
    private FavoriteViewModel favoriteViewModel;
    List<Favorite> favoriteList;

    private void initializeViews() {
        ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
        humaneEatingMainActivityWebView = (WebView) findViewById(R.id.webview);
        buttonLegend = (ImageButton) findViewById(R.id.button_legend);
        buttonFilter = (ImageButton) findViewById(R.id.button_filter);

        buttonAddListing = (ImageButton) findViewById(R.id.button_add_listing);

        /* we must add padding to the map to ensure the Google logo is displayed on the map */
        ViewTreeObserver viewTreeObserver = buttonAddListing.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    mapButtonHeight = buttonAddListing.getHeight();

                    if (mMap != null) {
                        mMap.setPadding(0, 0, 0, mapButtonHeight + MAP_BUTTON_HEIGHT_PADDING);
                    }

                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        buttonAddListing.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                    else {
                        buttonAddListing.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            });
        }




        buttonFriends = (Button) findViewById(R.id.button_friends);
        buttonTakeAction = (Button) findViewById(R.id.button_take_action);
        buttonLearn = (Button) findViewById(R.id.button_learn);
        buttonFeed = (RelativeLayout) findViewById(R.id.button_feed_layout);
        textViewFilter = (TextView) findViewById(R.id.filter_on);
        textViewSearch = (TextView) findViewById(R.id.search_indicator);
        textViewSearch.setVisibility(GONE);
        feedNotification = (TextView) findViewById(R.id.feed_notification);
        feedNotification.setVisibility(GONE);
    }

    private void filterToggle(TextView filterIndicator) {
        if(FilterActivity.filterOn) { filterIndicator.setVisibility(VISIBLE); }
        else { filterIndicator.setVisibility(GONE); }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TestFairy.begin(this, "36f1e2f80554d02d3aa9ea214153b8efaed4704d");

        checkLaunch = true;
        setContentView(R.layout.activity_humane_eating_main);

        ButterKnife.bind(this);

        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        favoriteViewModel.getAllFavorites().observe(this, new Observer<List<Favorite>>() {
            @Override
            public void onChanged(@Nullable List<Favorite> favorites) {
                // update our list of favorites
                favoriteList = favorites;
                if (favorites == null) {
                    return;
                }
               /* // TODO: temp/remove
                for (Favorite favorite : favoriteList) {
                    Log.d("HHL", "favorite " + favorite.getRestaurantKey());
                } */
            }
        });

        animation1 = AnimationUtils.loadAnimation(this, R.anim.to_middle);
        animation1.setAnimationListener(this);
        animation2 = AnimationUtils.loadAnimation(this, R.anim.from_middle);
        animation2.setAnimationListener(this);
        initializeViews();
        WelcomePageActivity.flag = false;
        camFlag = true;

        requestQueue = Volley.newRequestQueue(this);
        fbUtils = new FacebookUtils(requestQueue, TAG);

        // Initialize Application Update Checker.
        UpdateChecker checker = new UpdateChecker(this);
        checker.setSuccessfulChecksRequired(1);
        checker.setNoticeIcon(R.drawable.icon_76);
        checker.start();

        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        // Custom actionbar buttons
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowHomeEnabled (false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.actionbar_custom_main_restuarants);
        Toolbar toolbar = (Toolbar) actionBar.getCustomView().getParent();
        toolbar.setContentInsetsAbsolute(0,0);

        actionbarSearch = (Button) findViewById(R.id.search);
        simpleSearchView = (SearchView) findViewById(R.id.simpleSearchView);
        actionbarList = (Button) findViewById(R.id.list);

        // Button Search Text
        final Animation slide_left_in = AnimationUtils.loadAnimation(
                getApplicationContext(), R.anim.edittext_slide_down);
        final Animation slide_right_out = AnimationUtils.loadAnimation(
                getApplicationContext(), R.anim.edittext_slide_up);

        final View searchViewParent = findViewById(R.id.search_view_parent);
        actionbarSearch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                textViewSearch.setVisibility(GONE);
                if(searchViewParent.getVisibility() == VISIBLE)
                {
                    searchViewParent.startAnimation(slide_right_out);
                    searchViewParent.setVisibility(GONE);
                    simpleSearchView.setVisibility(GONE);
                }
                else {
                    searchViewParent.setVisibility(VISIBLE);
                    simpleSearchView.setVisibility(VISIBLE);
                    simpleSearchView.setIconified(false);
                    searchViewParent.startAnimation(slide_left_in);

                    final ParentFrameLayout mapParent = (ParentFrameLayout) findViewById(R.id.map_parent);
                    mapParent.shouldInterceptTouch(true);
                    mapParent.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mapParent.setOnClickListener(null);
                            mapParent.shouldInterceptTouch(false);
                            actionbarSearch.callOnClick();
                        }
                    });
                }
            }
        });

        simpleSearchView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleSearchView.setIconified(false);
            }
        });

        // perform set on query text listener event
        simpleSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
            // do something on text submit
                searchText = query.trim();

                if (camPosition.zoom >= PREFERRED_CAMERA_ZOOM_LEVEL) {
                    if (!searchText.equals(DataModel.name)) // we have a new search term
                    {
                        if (snackbar != null && snackbar.isShown()) {
                            snackbar.dismiss();
                        }
                        DataModel.name = searchText;
                        mMap.clear();
                        geoQuery = null;
                        parseRestaurants(latLng.latitude, latLng.longitude);
                    }

                    if (!TextUtils.isEmpty(searchText)) { // we have a non empty search text.
                        textViewSearch.setText("Search: " + searchText);
                        textViewSearch.setVisibility(VISIBLE);
                    }
                } else {
                    showZoomSnackbar();
                }

                final ParentFrameLayout mapParent = (ParentFrameLayout) findViewById(R.id.map_parent);
                mapParent.shouldInterceptTouch(false);
                searchViewParent.startAnimation(slide_right_out);
                searchViewParent.setVisibility(GONE);
                simpleSearchView.setVisibility(GONE);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // ideally we would do this in an OnCloseListener, but
                // https://issuetracker.google.com/issues/36940858
                if (newText == null || newText.trim().isEmpty()) {
                    // reset search
                    searchText = "";
                    DataModel.name = searchText;
                    geoQuery = null;
                    mMap.clear();
                    parseRestaurants(latLng.latitude, latLng.longitude);
                }

                // this allows searchview to maintain focus after clearing
                return false;
            }
        });

        // Decide whether to render Filter: On text or not.
        filterToggle(textViewFilter);


        actionbarList.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager
                        .beginTransaction();
                // TODO do we really need to create a new listFragment every time?!?!?!
                listFragment = new RestaurantListFragment();

                if(actionbarList.getText() == "Map")    // From List -> Map View
                {
                    parentView.startAnimation(animation2);
                    fragmentTransaction.setCustomAnimations(R.anim.to_middle, R.anim.from_middle);
                    fragmentManager.popBackStack();
                    showMapInfo();

                    LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    textViewSearch.setLayoutParams(lp);
                }
                else // From Map -> List View
                {
                    parentView.startAnimation(animation2);
                    fragmentTransaction.add(R.id.map, listFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    showListInfo();

                    LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    textViewSearch.setLayoutParams(lp);
                }
            }
        });

        // custom actionbar complete
        isConnected = false;


        /*
        ********************** MAP BUTTONS SECTION ********************
        */
        buttonLegend.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),
                        LegendActivity.class));
                overridePendingTransition(R.anim.slide_up2,
                        R.anim.slide_down2);
            }
        });

        buttonFilter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent filterActivity = new Intent(HumaneEatingMainActivity.this, FilterActivity.class);
                startActivityForResult(filterActivity, 1);
            }
        });

        buttonAddListing.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AutoAddListingActivity.class));
                overridePendingTransition(R.anim.slide_up2, R.anim.slide_down2);
            }
        });

        /*
        ********************** BOTTOM BUTTONS SECTION ********************
        */
        // Button Add Listing
        buttonFriends.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FriendsActivity.class));
                overridePendingTransition(R.anim.slide_up2, R.anim.slide_down2);
            }
        });

        // Button Donate
        buttonTakeAction.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), TakeActionActivity.class));
                overridePendingTransition(R.anim.slide_up2, R.anim.slide_down2);
            }
        });

        // Button Learn
        buttonLearn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),
                        LearnActivity.class));
                overridePendingTransition(R.anim.slide_up2,
                        R.anim.slide_down2);
            }
        });

        // Button Feed
        buttonFeed.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(HumaneEatingMainActivity.this, FeedActivity.class));
                overridePendingTransition(R.anim.slide_up2,
                    R.anim.slide_down2);
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        requestQueue.start();
    }

    @SuppressLint("NewApi")
    @Override
    protected void onResume() {
        super.onResume();

        if (isConnected == false)   // still not sure what isConnected is for.
        {
        }
        else {
            humaneEatingMainActivityWebView.loadUrl(getString(R.string.url_home));
        }
//        boolean isWifiConn = false;
//        boolean isMobileConn = false;
//        final String DEBUG_TAG = "NetworkStatusExample";
//        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connMgr
//                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//        if (networkInfo != null) {
//            isWifiConn = networkInfo.isConnected();
//        }
//        networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//        if (networkInfo != null) {
//            isMobileConn = networkInfo.isConnected();
//        }
//        Log.d(DEBUG_TAG, "Wifi connected: " + isWifiConn);
//        Log.d(DEBUG_TAG, "Mobile connected: " + isMobileConn);
        if (GeneralUtils.isConnected()) {

            // GPS location
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                isConnected = false;
            }
            else
            {
                if(mMap != null) {
                    SharedPreferences preference = getSharedPreferences("LatLon", Context.MODE_PRIVATE);
                    String lat = preference.getString("LATITUDE", "0");
                    String lon = preference.getString("LONGITUDE", "0");
                    latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));

                    if (latLng.longitude == 0.0 && camFlag == true) {
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        camFlag = false;
                    } else if (latLng != null && camFlag == true) {
                        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(latLng, PREFERRED_CAMERA_ZOOM_LEVEL);
                        mMap.moveCamera(yourLocation);
                        camFlag = false;
                        if (snackbar != null && snackbar.isShown()) {
                            snackbar.dismiss();
                        }
                    }
                }
                showGPSDisabledAlertToUser();
            }
        } else {
            MyDialogFragment dialog = new MyDialogFragment();
            dialog.show(getFragmentManager(), WINDOW_SERVICE);
            isConnected = true;
        }

        // Decide whether to render Filter: On text or not.
        if (actionbarList.getText() == "List") {
            filterToggle(textViewFilter);
        }

        getFacebookNotifications();

        if (checkLaunch) {
            checkLaunch = false;
            Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = generateBackgroundBitmap();
                    LaunchUtils.setBlurredBackgroundBitmap(HumaneEatingMainActivity.this, bitmap);
                    LaunchUtils.getInstance().runLaunchChecks(HumaneEatingMainActivity.this);
                }
            });

        }
    }

    private void showListInfo() {
        if (snackbar != null && snackbar.isShown()) {
            snackbar.dismiss();
        }
        actionbarList.setText("Map");
        buttonLegend.setVisibility(GONE);
        buttonFilter.setVisibility(GONE);
        buttonAddListing.setVisibility(GONE);
        textViewFilter.setVisibility(GONE);
        scaleView.setVisibility(GONE);

    }

    private void showMapInfo() {
        if (camPosition != null && camPosition.zoom < PREFERRED_CAMERA_ZOOM_LEVEL) {
            showZoomSnackbar();
        }
        actionbarList.setText("List");
        buttonLegend.setVisibility(VISIBLE);
        buttonFilter.setVisibility(VISIBLE);
        buttonAddListing.setVisibility(VISIBLE);
        filterToggle(textViewFilter);
        scaleView.setVisibility(VISIBLE);
    }

    // this is painful to be sure - we aren't able to successfully generate a blurred bitmap using
    // the current screen because the map does not render properly - we've created a dummy layout
    // with as few as the HEP main activity elements as possible to inflate and render here
    private Bitmap generateBackgroundBitmap() {

        LayoutInflater mInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Inflate the layout into a view and configure it the way you like
        RelativeLayout view = new RelativeLayout(HumaneEatingMainActivity.this);
        mInflater.inflate(R.layout.activity_humane_eating_main_bitmap, view, true);

        // Get the display size so we know how big of a bitmap to generate
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // no parent, so match parent does not make sense here
        view.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        //Pre-measure the view, since we've precalculated the size, measure exactly
        view.measure(View.MeasureSpec.makeMeasureSpec(size.x, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(size.y, View.MeasureSpec.EXACTLY));

        //Assign a size and position to the view and all of its descendants
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        //Create the bitmap
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(),
                view.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);

        //Create a canvas with the specified bitmap to draw into
        Canvas c = new Canvas(bitmap);

        //Render the view
        view.draw(c);

        return bitmap;
    }

    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder
                .setMessage("Please turn-on GPS to access your location")
                .setCancelable(false)
                .setPositiveButton("Turn-On GPS",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    private ValueEventListener restaurantEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {


            for(DataSnapshot children : dataSnapshot.getChildren()) {
                HEPRestaurant restaurant = HEPRestaurant.create(children);
                GeoLocation gl = locationIdToLocation.get(restaurant.locationId());
                restaurant.geoLocation(gl);

                // TODO this filter code should live probably reside in another class
                //      also it could probably be cleaned up quite a bit...
                boolean matchPrice = true;
                boolean matchCuisine = true;
                boolean matchHumaneStatus = true;
                boolean matchName = true;
                boolean matchReviews = true;
                boolean matchRatings = true;

                if (DataModel.pr != PriceRange.ALL)
                    matchPrice = String.valueOf(DataModel.pr.value()).equals(restaurant.priceRangeNumber());
                if (DataModel.ct != CuisineType.ALL)
                    matchCuisine = DataModel.ct.toString().equals(restaurant.cuisine1());
                if (DataModel.hs != HumaneStatus.ALL)
                    matchHumaneStatus = DataModel.hs.toString().equals(restaurant.humaneStatus());
                if (!TextUtils.isEmpty(DataModel.name)) {
                    matchName = StringUtils.containsIgnoreCase(restaurant.name(), DataModel.name);
                }

                if (DataModel.reviewFilter != ReviewFilter.ALL) {
                    int reviewFilter = DataModel.reviewFilter.value();
                    matchReviews = (reviewFilter <= restaurant.yelpReviewCount());
                }

                if (DataModel.rating != 0) {
                    matchReviews = (DataModel.rating <= restaurant.yelpRating());
                }



                if (matchPrice &&
                    matchCuisine &&
                    matchHumaneStatus &&
                    matchName &&
                    matchReviews &&
                    !SearchQuery.getInstance().contains(restaurant)) {

                    Marker marker = createMarker(restaurant);

                    markerToRestaurant.put(marker, restaurant);
                    SearchQuery.getInstance().add(restaurant);

                    if (actionbarList.getText() == "Map") {
                        listFragment.refreshRestaurantList();
                    }
                }
            }
            progress.setVisibility(GONE);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.w(TAG, "Firebase database error: " + databaseError.getMessage());
        }
    };

    private void parseRestaurants(Double latitude, Double longitude)
    {
        boolean isNewPlace = false;

        if(Math.abs(DataModel.lat - latitude) > 2 || Math.abs(DataModel.lon - longitude) > 2)
        {
            isNewPlace = true;
        }

        if (LocationUtils.getInstance().distanceInMilesBetween(DataModel.lat, DataModel.lon, latitude, longitude) > 10) {

            progress.setVisibility(VISIBLE);

            // ensure we stop the spinner if we get no results in a particular area
            progressHandler.postDelayed(progressRunnable, 4000);
        }

        DataModel.lat = latitude;
        DataModel.lon = longitude;


        double d = DataModel.distance;


        if (geoQuery == null || isNewPlace == true) {  // a fresh search
            SearchQuery.getInstance().clearAll();
            if (actionbarList.getText() == "Map") {
                listFragment.refreshRestaurantList();
            }
            locationIdToLocation.clear();
            GeoFire geoFire = QueryBuilder.getGeoFire();
            geoQuery = geoFire.queryAtLocation(new GeoLocation(latitude, longitude), d * MILES_TO_KILOMETERS_CONVERSION);
            geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {

                @Override
                public void onKeyEntered(String key, GeoLocation location) {
                    locationIdToLocation.put(key, location);
                    QueryBuilder.queryRestaurant(key, restaurantEventListener);
                }

                @Override
                public void onKeyExited(String key) {
                    // Do nothing
                }

                @Override
                public void onKeyMoved(String key, GeoLocation location) {


                    // Do nothing
                }

                @Override
                public void onGeoQueryReady() {
                    // Do nothing
                }

                @Override
                public void onGeoQueryError(DatabaseError error) {
                    Log.w("GeoFire", "GeoFire database error: " + error.getMessage());
                }
            });
        } else {


            // update location on current search
            
            geoQuery.setLocation(new GeoLocation(latitude, longitude), d * MILES_TO_KILOMETERS_CONVERSION);

        }

        mMap.setInfoWindowAdapter(new MyInfoWindowAdapter());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        scaleView = (MapScaleView) findViewById(R.id.scaleView);
        //CameraPosition cameraPosition = mMap.getCameraPosition();

        if (mapButtonHeight > 0) {
            mMap.setPadding(0, 0, 0, mapButtonHeight + MAP_BUTTON_HEIGHT_PADDING);
        }

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);


        loc = LocationUtils.getInstance().currentLocation();

        if (loc != null) {
            LatLng latlng = new LatLng(loc.getLatitude(), loc.getLongitude()); // Get the current longitude/latitude.

            // Moves the camera to users current longitude and latitude
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, (float) PREFERRED_CAMERA_ZOOM_LEVEL));
        }

        // Camera Location Listener
        mMap.setOnCameraChangeListener(new OnCameraChangeListener() {

            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                camPosition = cameraPosition;
                latLng = cameraPosition.target;

                scaleView.update(camPosition.zoom, camPosition.target.latitude);

                if (cameraPosition.zoom >= PREFERRED_CAMERA_ZOOM_LEVEL) {
                    if (snackbar != null && snackbar.isShown()) {
                        snackbar.dismiss();
                    }
                    Double lat = latLng.latitude;
                    Double lon = latLng.longitude;
                    if(searchText.equals(""))
                    {
                        parseRestaurants(lat, lon);
                    }
                } else {
                    showZoomSnackbar();
                }
            }
        });

        /*
        ********************** MAP MARKERS SECTION ********************
        */
        mMap.setOnMarkerClickListener(new OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {
                if (HumaneEatingMainActivity.this.marker != null) {
                    HumaneEatingMainActivity.this.marker.hideInfoWindow();
                }
                mMap.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
                marker.showInfoWindow();
                HumaneEatingMainActivity.this.marker = marker;

               /* // TODO: temp insert any clicked marker into favorites as a test
                HEPRestaurant restaurant = markerToRestaurant.get(marker);
                if (restaurant != null) {
                    // using name for our initial tests because it will be easier to visually verify
                    Favorite favorite = new Favorite(restaurant.name());
                    favoriteViewModel.insert(favorite);
                } */

                return true;
            }

        });

        // Open RestaurantDetailsActivity
        mMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker marker)
            {
                HEPRestaurant hep = markerToRestaurant.get(marker);
                Intent intent = new Intent(HumaneEatingMainActivity.this, RestaurantDetailsActivity.class);
                intent.putExtra(RestaurantDetailsActivity.ARG_RESTAURANT, hep);

                // TODO: if we refactor the use of ParseGeoPoint, consider updating the mechanism
                // to get mileage information into the RestaurantDetailsActivity
                intent.putExtra(RestaurantDetailsActivity.ARG_MILES,
                    LocationUtils.getInstance().distanceInMilesString(hep.geoLocation()));
                startActivity(intent);
            }
        });

        mMap.setOnMyLocationButtonClickListener(new OnMyLocationButtonClickListener() {

            @Override
            public boolean onMyLocationButtonClick() {
                loc = mMap.getMyLocation();
                // GPS location
                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    if (loc != null) {
                        LatLng myLoc = new LatLng(loc.getLatitude(), loc.getLongitude());
                        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(myLoc, PREFERRED_CAMERA_ZOOM_LEVEL);
                        mMap.animateCamera(yourLocation);
                        if (snackbar != null && snackbar.isShown()) {
                            snackbar.dismiss();
                        }
                    }
                } else {
                    showGPSDisabledAlertToUser();
                }
                return true;
            }
        });

    }

    /*Marker Custom InfoWindow	 */
    private class MyInfoWindowAdapter implements InfoWindowAdapter
    {
        TextView humaneStatus;
        TextView humaneStatus2;
        TextView restaurantName;
        TextView restaurantDescription;
        TextView reviewCount;
        TextView cuisineType;
        TextView priceRange;
        TextView expense;
        TextView distance;
        ImageView ratingBar;

        private final View view;

        public MyInfoWindowAdapter()
        {
            view = getLayoutInflater().inflate(R.layout.marker_dialog, null);
            humaneStatus = (TextView) view.findViewById(R.id.humane_status);
            humaneStatus2 = (TextView) view.findViewById(R.id.humane_status2);
            restaurantName = (TextView) view.findViewById(R.id.restaurant_name);
            restaurantDescription = (TextView) view.findViewById(R.id.restaurant_description);
            reviewCount = (TextView) view.findViewById(R.id.ratings_count);
            cuisineType = (TextView) view.findViewById(R.id.cuisine);
            priceRange = (TextView) view.findViewById(R.id.price_range);
            expense = (TextView) view.findViewById(R.id.expense);
            distance = (TextView) view.findViewById(R.id.distance);
            ratingBar = (ImageView) view.findViewById(R.id.rating_bar);
        }

        @Override
        public View getInfoContents(Marker marker) {    return view;   }

        @Override
        public View getInfoWindow(Marker marker)
        {
            ImageView markerBackground = (ImageView) view.findViewById(R.id.marker_background);

            if (markerToRestaurant.containsKey(marker))
            {
                HEPRestaurant hep = markerToRestaurant.get(marker);
                restaurantName.setText(hep.name());

                if (hep.humaneStatus().equalsIgnoreCase(getString(R.string.status_watch_list))) {
                    return getWatchListInfoWindow(hep);
                }

                String description = hep.description();
                if (description == null || description.isEmpty()) {
                    restaurantDescription.setVisibility(GONE);
                }
                else {
                    restaurantDescription.setText(hep.description());
                    restaurantDescription.setVisibility(VISIBLE);
                }

                humaneStatus.setText(hep.humaneStatus());
                if (hep.humaneStatus().equals(getString(R.string.status_vegetarian)) ||
                    hep.humaneStatus().equals(getString(R.string.status_vegan)) ||
                    hep.humaneStatus().equals(getString(R.string.status_vegan_friendly)) ||
                    hep.humaneStatus().equals(getString(R.string.status_vegetarian_friendly)))
                {
                    humaneStatus.setTextColor(ContextCompat.getColor(HumaneEatingMainActivity.this, android.R.color.holo_green_dark));
                    markerBackground.setBackgroundResource(R.drawable.bubble_green2x);
                }
                else if (hep.humaneStatus().equals(getString(R.string.status_humane_friendly)))
                {
                    humaneStatus.setTextColor(ContextCompat.getColor(HumaneEatingMainActivity.this, R.color.brown));
                    markerBackground.setBackgroundResource(R.drawable.bubble_brown2x);
                }
                else
                {
                    markerBackground.setBackgroundResource(R.drawable.bubble_yellow_rollover2x);
                }

                String humaneStatus2Str = hep.humaneStatus2();
                if (humaneStatus2Str == null            ||
                    TextUtils.isEmpty(humaneStatus2Str) ||
                    humaneStatus2Str.equals(getString(R.string.status_optional))) {
                    humaneStatus2.setVisibility(GONE);
                }
                else {
                    humaneStatus2.setText(humaneStatus2Str);

                    if (humaneStatus2Str.equals(getString(R.string.status_vegetarian)) ||
                        humaneStatus2Str.equals(getString(R.string.status_vegan)) ||
                        humaneStatus2Str.equals(getString(R.string.status_vegan_friendly)) ||
                        humaneStatus2Str.equals(getString(R.string.status_vegetarian_friendly))) {
                        humaneStatus2.setTextColor(ContextCompat.getColor(HumaneEatingMainActivity.this, android.R.color.holo_green_dark));
                    }
                    else if (humaneStatus2Str.equals(getString(R.string.status_watch_list))) {
                        humaneStatus2.setTextColor(android.graphics.Color.RED);
                    } else if (humaneStatus2Str.equals(getString(R.string.status_humane_friendly))) {
                        humaneStatus2.setTextColor(android.graphics.Color.rgb(0xFF, 0, 0x07f00));
                    }
                }

                if (hep.priceRangeNumber() == null) {
                    priceRange.setText("");
                    expense.setText("");
                } else if (hep.priceRangeNumber().equals("1")) {
                    priceRange.setText("$");
                    expense.setText("Inexpensive");
                } else if (hep.priceRangeNumber().equals("2")) {
                    priceRange.setText("$$");
                    expense.setText("Average");
                } else if (hep.priceRangeNumber().equals("3")) {
                    priceRange.setText("$$$");
                    expense.setText("Expensive");
                } else {
                    priceRange.setText("");
                    expense.setText("");
                }

                // Set Cuisine Type
                if (hep.cuisine1() != null && !hep.cuisine1().equalsIgnoreCase(getString(R.string.invalid_cuisine))) {
                    cuisineType.setText(hep.cuisine1());
                } else {
                    cuisineType.setVisibility(GONE);
                }

                // Set Yelp Review Score
                if (hep.yelpRating().floatValue() != 0 &&
                    !hep.humaneStatus().equals(getString(R.string.status_watch_list))) {

                    float rating = hep.yelpRating().floatValue();
                    if(rating == 5)
                    {

                        ratingBar.setBackgroundResource(R.drawable.stars_small_5);

                    }
                    else if (rating < 5 && rating > 4)
                    {
                        ratingBar.setBackgroundResource(R.drawable.stars_small_4_half);
                    }
                    else if (rating == 4)
                    {
                        ratingBar.setBackgroundResource(R.drawable.stars_small_4);

                    }
                    else if (rating < 4 && rating > 3)
                    {
                        ratingBar.setBackgroundResource(R.drawable.stars_small_3_half);
                    }
                    else if (rating == 3)
                    {
                        ratingBar.setBackgroundResource(R.drawable.stars_small_3);
                    }
                    else if (rating < 3 && rating > 2)
                    {
                        ratingBar.setBackgroundResource(R.drawable.stars_small_2_half);
                    }
                    else if (rating == 2)
                    {
                        ratingBar.setBackgroundResource(R.drawable.stars_small_2);
                    }
                    else if (rating < 2 && rating > 1)
                    {
                        ratingBar.setBackgroundResource(R.drawable.stars_small_1_half);
                    }
                    else if (rating == 1)
                    {
                        ratingBar.setBackgroundResource(R.drawable.stars_small_1);
                    }

                } else {
                    ratingBar.setVisibility(GONE);
                }

                // Set Yelp Review Count
                if (hep.yelpReviewCount() != 0 &&
                    !hep.humaneStatus().equals(getString(R.string.status_watch_list))) {
                    reviewCount.setText(String.format(getString(R.string.yelp_reviews), hep.yelpReviewCount()));
                } else {
                    reviewCount.setText("");
                }

                // Set Distance(miles) to the restaurants.
                double miles = LocationUtils.getInstance().distanceInMilesTo(hep.geoLocation());

                distance.setText(String.format("%.2f mi", miles)); // set the miles text.
            }

            return view;
        }

        private View getWatchListInfoWindow(HEPRestaurant hep) {

            ImageView markerBackground = (ImageView) view.findViewById(R.id.marker_background);

            humaneStatus.setText(hep.humaneStatus());
            humaneStatus.setTextColor(android.graphics.Color.RED);
            markerBackground.setBackgroundResource(R.drawable.bubble_red2x);

            restaurantDescription.setVisibility(GONE);

            priceRange.setVisibility(GONE);
            expense.setVisibility(GONE);
            ratingBar.setVisibility(GONE);
            reviewCount.setVisibility(GONE);
            distance.setVisibility(GONE);
            humaneStatus2.setVisibility(GONE);
            cuisineType.setVisibility(GONE);

            return view;
        }
    }



    /**
     * Make marker object from restaurant, sets Name, Humane Category and etc.
     * @param restaurant : restaurant to make marker for.
     * @return: marker object with restaurant details.
     */
    private Marker createMarker(HEPRestaurant restaurant)
    {
        String humaneStatus = restaurant.humaneStatus();
        Boolean isSponsored = restaurant.getIsSponsored();
        LatLng latlong = new LatLng(restaurant.geoLocation().latitude, restaurant.geoLocation().longitude);
        Marker marker = mMap.addMarker(new MarkerOptions().position(latlong).title(restaurant.name()).infoWindowAnchor(3.0f, 0.0f));


        if (humaneStatus.equals("Vegetarian") || humaneStatus.equals("Vegan")
                || humaneStatus.equals("Vegan-Friendly") || humaneStatus.equals("Vegetarian-Friendly"))
        {
            if(isSponsored)
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.pin_green2x_starred));
            else
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.pin_green2x));
        }
        else if (humaneStatus.equals("Humane-Friendly"))
        {
            if(isSponsored)
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.pin_brown2x_starred));
            else
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.pin_brown2x));
        }
        else if (humaneStatus.equals("Watch List"))
        {
            if(isSponsored)
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.pin_red2x_starred));
            else
                marker.setInfoWindowAnchor(5.0f, 0.0f);
                marker.setIcon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(R.drawable.pin_red2x)));
        }
        return marker;
    }


    Bitmap resizeMapIcons(int resourceId){
        Bitmap imageBitmap =
                //BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(iconName, "drawable", getPackageName()));
                BitmapFactory.decodeResource(getResources(), resourceId);

        // scale our graphic by 60%
        int scaledWidth = (int) Math.round(imageBitmap.getWidth() * .6);
        int scaledHeight = (int) Math.round(imageBitmap.getHeight() * 0.6);

        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, scaledWidth, scaledHeight, false);
        return resizedBitmap;
    }

    //OnActivityResult for Filter class
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            mMap.clear();

            Double lat = latLng.latitude;
            Double lon = latLng.longitude;
            geoQuery = null;
            parseRestaurants(lat, lon);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (actionbarList.getText() == "Map") {
            showMapInfo();
        }
    }



    @Override
    public void onAnimationStart(Animation animation) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == animation1) {
            if (isBackOfCardShowing) {
//				((ImageView)findViewById(R.id.imageView1)).setImageResource(R.drawable.card_front);
            } else {
//				((ImageView)findViewById(R.id.imageView1)).setImageResource(R.drawable.card_back);
            }
            parentView.clearAnimation();
            parentView.setAnimation(animation2);
            parentView.startAnimation(animation2);
        } else {
            isBackOfCardShowing = !isBackOfCardShowing;
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) { }

    @Override
    protected void onPause() {
        super.onPause();
        if (latLng != null) {
            String latShared = String.valueOf(latLng.latitude);
            String lanShared = String.valueOf(latLng.longitude);
            SharedPreferences prefs = getSharedPreferences("LatLon", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("LATITUDE", latShared);
            editor.putString("LONGITUDE", lanShared);
            editor.commit();
        }
        // do this to ensure new screenshots will be generated for other activities
        LaunchUtils.setBlurredBackgroundBitmap(null, null);

        // cancel any potential outstanding runnable
        progressHandler.removeCallbacks(progressRunnable);
    }

    @Override
    protected void onStart() {
        super.onStart();
       FlurryAgent.onStartSession(this, "Y8B9D2MQCFMCP2KJ5KVT");
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG);
            requestQueue.stop();
        }
        FlurryAgent.onEndSession(this);
    }

    // Get posts to determine if we have any outstanding feed items to notify user of
    private void getFacebookNotifications() {
        fbUtils.requestFeed(this);
    }

    @Override
    public void feedRequestComplete(List<FacebookPost> posts) {
        if (posts.size() > 0) {
            long lastRead = FacebookUtils.getLastFeedTime();
            int unreadCount = 0;

            while (unreadCount < posts.size() && lastRead < posts.get(unreadCount).getSinceTime()) {
                unreadCount++;
            }

            if (unreadCount > 0) {
                feedNotification.setText(Integer.toString(unreadCount));
                feedNotification.setVisibility(VISIBLE);
            }
            else {
                feedNotification.setVisibility(GONE);
            }
        }
    }

    @Override
    public void nextFeedRequestComplete(List<FacebookPost> posts) {
        // Do nothing
    }

    @Override
    public void feedRefreshComplete(List<FacebookPost> posts) {
        // Do nothing
    }

    @Override
    public void feedError(VolleyError error) {
        Log.e(TAG, error.toString());
    }


    private void showZoomSnackbar() {

        if (snackbar == null || !snackbar.isShown()) {
            snackbar = Snackbar.make(findViewById(R.id.myCoordinatorLayout), getString(R.string.zoom_in), Snackbar.LENGTH_INDEFINITE);
            snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.primary));
            snackbar.setAction(R.string.zoom_in_action, new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(PREFERRED_CAMERA_ZOOM_LEVEL));
                    snackbar.dismiss();
                }
            });
            snackbar.show();
        }
    }
}
