package org.hep.afa.restaurantdetails;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.flurry.android.FlurryAgent;

import org.hep.afa.main.HEPApplication;
import org.hep.afa.R;
import org.hep.afa.main.WebViewActivity;
import org.hep.afa.constant.HEPConstants;
import org.hep.afa.model.HEPRestaurant;
import org.hep.afa.utils.GeneralUtils;
import org.hep.afa.utils.IconUtils;
import org.hep.afa.view.CategoryLabel;
import org.hep.afa.view.DetailsItemView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;


/**
 * Created by Bogdan Melnychuk on 8/23/15.
 * Modified by Jaebin Lee since 9/1/2015
 */
public class RestaurantDetailsActivity extends AppCompatActivity
{
    private static final String TAG = "RestaurantDetailsAct";

    public static final String ARG_RESTAURANT = "_argRestaurant";
    public static final String ARG_MILES = "_argMiles";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fabButton)
    FloatingActionButton mFab;

    @BindView(R.id.sponsor)
    DetailsItemView sponsor;

    @BindView(R.id.menu_view)
    DetailsItemView detailsMenu;

    @BindView(R.id.telephone)
    DetailsItemView detailsPhone;

    @BindView(R.id.address)
    DetailsItemView detailAddress;

    @BindView(R.id.view_pages_parent_layout)
    View pagesParentView;

    @BindView(R.id.pages_detail_view)
    DetailsItemView pagesDetailView;

    @BindView(R.id.website_details)
    TextView websiteDetails;

    @BindView(R.id.facebook_details)
    TextView facebookDetails;

    @BindView(R.id.twitter_details)
    TextView twitterDetails;

    @BindView(R.id.other_cuisines)
    DetailsItemView otherCuisines;

    @BindView(R.id.features)
    DetailsItemView features;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.category_label1)
    CategoryLabel categoryLabel1;

    @BindView(R.id.category_label2)
    CategoryLabel categoryLabel2;

    @BindView(R.id.restaurant_description)
    TextView restaurantDescription;

    @BindView(R.id.cuisine)
    TextView cuisineType;

    @BindView(R.id.price_range)
    TextView priceRange;

    @BindView(R.id.expense)
    TextView expense;

    @BindView(R.id.ratings)
    ImageView ratingBar;

    @BindView(R.id.ratings_count)
    Button ratingsLabel;

    @BindView(R.id.facebook_share)
    ShareButton facebookShare;

    @BindView(R.id.twitter_tweet)
    View twitterTweet;

    @BindView(R.id.contact_us)
    DetailsItemView contactUs;

    @BindView(R.id.submitted_by)
    DetailsItemView submittedBy;

    @BindView(R.id.restaurant_photo_carousel)
    ViewPager restaurantPhotoCarousel;

    @BindView(R.id.next_photo_btn)
    ImageButton nextPhotoButton;

    @BindView(R.id.previous_photo_btn)
    ImageButton previousPhotoButton;

    private HEPRestaurant restaurant;
    private String tweetText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        setContentView(R.layout.activity_restaurant_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        FlurryAgent.logEvent("Restaurant Detail Activity");
        restaurant = (HEPRestaurant) getIntent().getExtras().getSerializable(ARG_RESTAURANT);

        // Initialize restaurant photo carousel
        RestaurantPhotosAdapter restaurantPhotosAdapter = new RestaurantPhotosAdapter(this, restaurant);
        restaurantPhotoCarousel.setOffscreenPageLimit(3);
        restaurantPhotoCarousel.setAdapter(restaurantPhotosAdapter);
        restaurantPhotoCarousel.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(final int position) {
                refreshCarouselNavigationButtonsVisibility();
            }
        });
        restaurantPhotosAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                if (restaurantPhotoCarousel.getAdapter().getCount() > 1) {
                    nextPhotoButton.setVisibility(View.VISIBLE);
                }
            }
        });

        collapsingToolbarLayout.setTitle(restaurant.name());

        // Floating Action Button.
        mFab.setImageDrawable(IconUtils.getIcon(this, R.string.ic_directions));
        mFab.setOnClickListener(new NavigateOnClickListener(restaurant.address1(), restaurant.city()));

        //Sponsor section
        sponsor.setIconImage(getResources().getDrawable(R.drawable.gold_star_list));
        sponsor.setValue(getString(R.string.hep_sponsor));
        setOrHide(restaurant.getIsSponsored(), sponsor);

        // Menu section
        if (restaurant.getMenuUrl() == null || restaurant.getMenuUrl().isEmpty()) {
            detailsMenu.setVisibility(GONE);
        }
        else {
            detailsMenu.setIcon(R.string.ic_book);
            detailsMenu.setValue(getString(R.string.menu_section));
            detailsMenu.setOnClickListener(new OpenWebClickListener(restaurant.getMenuUrl()));
        }


        // Telephone Section

        // set up fake intent to see if our device can make calls (note this can be done
        // via provider like skype for tablets, etc. - we'll leave it to them to worry about
        // whether or not the user is online)
        Intent testIntent = new Intent(Intent.ACTION_CALL);
        testIntent.setData(Uri.parse("tel:" + "123"));

        if (GeneralUtils.isIntentSafe(testIntent)) {

            detailsPhone.setIcon(R.string.ic_phone);
            //detailsPhone.setLabel(getString(R.string.telephone));
            detailsPhone.setOnClickListener(new CallOnClickListener(restaurant.phone()));
            setOrHide(restaurant.phone(), detailsPhone);
        }
        else {
            detailsPhone.setVisibility(GONE);
        }

        // Address Section
        detailAddress.setIcon(R.string.ic_explore);

        String mileage = getIntent().getExtras().getString(ARG_MILES);
        detailAddress.setAdditionalInfo(mileage);

        detailAddress.setOnClickListener(new NavigateOnClickListener(restaurant.address1(), restaurant.city()));
        setOrHide(restaurant.address1() + ", " + restaurant.city(), detailAddress);

        // Pages Section
        pagesDetailView.setIcon(R.string.ic_public);
        pagesDetailView.setValue(getString(R.string.pages_section));

        setOrHide(restaurant.otherCuisines(), otherCuisines);

        features.setIcon(R.string.ic_favorite);
        setOrHide(restaurant.features(), features);

        // HumaneStatus Categories..
        boolean isWatchlist = GeneralUtils.isWatchList(this, restaurant.humaneStatus());

        categoryLabel1.setColor(getStatusColor(restaurant.humaneStatus()));

        if (isWatchlist) {
            String reason = restaurant.issue1();
            if (reason != null && !TextUtils.isEmpty(reason)) {
                reason = getString(R.string.humane_reason) + " " + reason;
                categoryLabel1.setText(restaurant.humaneStatus() + " " + "(" + reason + ")");
            }
            else {
                categoryLabel1.setText(restaurant.humaneStatus());
            }
            categoryLabel2.setVisibility(GONE);
        }
        else {
            setOrHide(restaurant.humaneStatus(), categoryLabel1);
            categoryLabel2.setColor(getStatusColor(restaurant.humaneStatus2()));
            setOrHide(restaurant.humaneStatus2(), categoryLabel2);
        }


        // Set Restaurant Description
        String description;

        if (isWatchlist) {
            restaurantDescription.setVisibility(GONE);
        }
        else {
            description = restaurant.description();

            if (description == null || TextUtils.isEmpty(description)) {
                restaurantDescription.setVisibility(GONE);
            } else {
                restaurantDescription.setText(description);
            }
        }

        // Set Cuisine Type
        if (restaurant.cuisine1() != null && !TextUtils.isEmpty(restaurant.cuisine1()) &&
            !restaurant.cuisine1().equalsIgnoreCase(getString(R.string.invalid_cuisine))) {
            cuisineType.setText(restaurant.cuisine1());
        } else {
            cuisineType.setVisibility(GONE);
        }

        // Set Price Range
        if (restaurant.priceRangeNumber() == null) {
            priceRange.setText("");
            expense.setText("");
        } else if (restaurant.priceRangeNumber().equals("1")) {
            priceRange.setText("$ -");
            expense.setText("Inexpensive");
        } else if (restaurant.priceRangeNumber().equals("2")) {
            priceRange.setText("$$ -");
            expense.setText("Average");
        } else if (restaurant.priceRangeNumber().equals("3")) {
            priceRange.setText("$$$ -");
            expense.setText("Expensive");
        } else {
            priceRange.setText("");
            expense.setText("");
        }

        //ratingBar.setBackgroundResource(R.drawable.stars_large_0);
        if (restaurant.yelpReviewCount() > 0 && !isWatchlist)
        {
            float rating = restaurant.yelpRating().floatValue();
           // ratingBar.setVisibility(View.INVISIBLE);
            if(rating == 5)
            {

                ratingBar.setBackgroundResource(R.drawable.stars_large_5);

            }
            else if (rating < 5 && rating > 4)
            {
                ratingBar.setBackgroundResource(R.drawable.stars_large_4_half);
            }
            else if (rating == 4)
            {
                ratingBar.setBackgroundResource(R.drawable.stars_large_4);

            }
            else if (rating < 4 && rating > 3)
            {
                ratingBar.setBackgroundResource(R.drawable.stars_large_3_half);
            }
            else if (rating == 3)
            {
                ratingBar.setBackgroundResource(R.drawable.stars_large_3);
            }
            else if (rating < 3 && rating > 2)
            {
                ratingBar.setBackgroundResource(R.drawable.stars_large_2_half);
            }
            else if (rating == 2)
            {
                ratingBar.setBackgroundResource(R.drawable.stars_large_2);
            }
            else if (rating < 2 && rating > 1)
            {
                ratingBar.setBackgroundResource(R.drawable.stars_large_1_half);
            }
            else if (rating == 1)
            {
                ratingBar.setBackgroundResource(R.drawable.stars_large_1);
            }

            ratingsLabel.setText(String.format(getString(R.string.yelp_reviews), restaurant.yelpReviewCount()));
            ratingsLabel.setOnClickListener(new YelpOnClickListener(restaurant.yelpId()));

        }
        else {
            ratingsLabel.setVisibility(GONE);
        }

        if (restaurant.twitterPage() != null && !restaurant.twitterPage().isEmpty() && !isWatchlist) {
            pagesParentView.setVisibility(View.VISIBLE);
            twitterDetails.setVisibility(View.VISIBLE);
            twitterDetails.setOnClickListener(new OpenWebClickListener(restaurant.twitterPage()));
        }
        if (restaurant.homepage() != null && !restaurant.homepage().isEmpty() && !isWatchlist) {
            pagesParentView.setVisibility(View.VISIBLE);
            websiteDetails.setVisibility(View.VISIBLE);
            websiteDetails.setOnClickListener(new OpenWebClickListener(restaurant.homepage()));
        }
        if (restaurant.facebookPage() != null && !restaurant.facebookPage().isEmpty() && !isWatchlist) {

            // support pages
            pagesParentView.setVisibility(View.VISIBLE);
            facebookDetails.setVisibility(View.VISIBLE);
            facebookDetails.setOnClickListener(new OpenWebClickListener(restaurant.facebookPage()));

            // support like
            facebookShare.setVisibility(View.VISIBLE);
            ShareDialog shareDialog = new ShareDialog(this);
            if (shareDialog.canShow(ShareLinkContent.class)) {
                ShareLinkContent content = new ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse(restaurant.facebookPage()))
                        .build();
                facebookShare.setShareContent(content);
            }
        }

        // try to find some data for a restaurant tweet, if we can make a string short enough, we'll
        // show the tweet button
        tweetText = String.format(getString(R.string.tweet_restaurant_body_long), restaurant.name());
        if (tweetText.length() > 140) {
            tweetText = String.format(getString(R.string.tweet_restaurant_body_short), restaurant.name());
        }
        if (tweetText.length() <= 140) {
            twitterTweet.setVisibility(View.VISIBLE);
            twitterTweet.setOnClickListener(new TweetClickListener());
        }

        // Contact Us Section
        contactUs.setIcon(R.string.ic_mail);
        contactUs.setOnClickListener(new EmailOnClickListener(restaurant.email()));
        setOrHide(restaurant.email(), contactUs);

        // Submitted By Section
        submittedBy.setIcon(R.string.ic_thumbs_up);
        setOrHide(restaurant.submittedBy(), submittedBy);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_restaurant_details, menu);
        return true;
    }

    private int getStatusColor(String status) {
        if (TextUtils.isEmpty(status)) {
            return 0;
        }
        if (status.equals(getString(R.string.status_watch_list))) {
            return getResources().getColor(R.color.red);
        } else if (status.equals(getString(R.string.status_humane_friendly))) {
            return getResources().getColor(R.color.brown);
        } else {
            return getResources().getColor(R.color.green);
        }
    }

    // On Click Listener for making Google Map Direct Request..
    private class NavigateOnClickListener implements View.OnClickListener
    {
        private String street;
        private String city;

        public NavigateOnClickListener(String street, String city) {
            this.street = street;
            this.city = city;
        }

        @Override
        public void onClick(View view)
        {
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse(String.format(HEPConstants.GOOGLE_NAVIGATION_TEMPLATE, Uri.encode(street + "," + city))));
            intent.setPackage("com.google.android.apps.maps");
            if(intent.resolveActivity(getPackageManager()) != null)
            {
                startActivity(intent);
            }
        }
    }

    // On Click Listener for Yelp Restaurant details..
    // @TODO: These OnClickListener need to be refactored to provide reusable templates.
    private class YelpOnClickListener implements View.OnClickListener
    {
        private String yelpId;

        public YelpOnClickListener(String yelpId) {
            this.yelpId = yelpId;
        }

        @Override
        public void onClick(View view)
        {
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse(String.format(HEPConstants.YELP_BIZ_TEMPLATE, Uri.encode(yelpId))));
            startActivity(intent);
        }
    }

    // On Click Listener for making Phone Call.
    private class CallOnClickListener implements View.OnClickListener
    {
        private String phoneNumber;

        public CallOnClickListener(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        @Override
        public void onClick(View view)
        {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(intent);
        }
    }

    // On Click Listener for sending Email..
    private class EmailOnClickListener implements View.OnClickListener
    {
        private String email;

        public EmailOnClickListener(String email) {
            this.email = email;
        }

        @Override
        public void onClick(View view)
        {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("plain/text");
            intent.putExtra(intent.EXTRA_EMAIL, new String[] { this.email });
            intent.putExtra(intent.EXTRA_SUBJECT, getString(R.string.email_contact_subject));
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.email_contact_body));
            startActivity(intent.createChooser(intent, getString(R.string.email_choose)));
        }
    }

    /**
     * On Click Listener for Facebook button to open the official Facebook app.
     * If the Facebook app is not installed then the default web browser will be used.
     */
    private class FacebookClickListener implements View.OnClickListener
    {
        private Uri uri;

        public FacebookClickListener(String url)
        {
            this.uri = Uri.parse(url);
            try
            {
                PackageManager pm = HEPApplication.getMyAppContext().getPackageManager();
                ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
                if (applicationInfo.enabled)
                {
                    // http://stackoverflow.com/a/24547437/1048340
                    this.uri = Uri.parse("fb://facewebmodal/f?href=" + url);
                }
            }
            catch (PackageManager.NameNotFoundException ignored) {
                ignored.printStackTrace();
            }
        }

        @Override
        public void onClick(View view)
        {
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
    }

    /**
     * On Click Listener for Redirecting to URL.
     */
    private class OpenWebClickListener implements View.OnClickListener
    {
        private String url;

        public OpenWebClickListener(String url)
        {
            if(!TextUtils.isEmpty(url) && !url.startsWith("http")) {
                url = "http://" + url;
            }

            this.url = url;
        }

        @Override
        public void onClick(View view)
        {
            // this WEB_URL pattern matcher returns false for many of our valid URLs, so we're going
            // to rely on the wholesomeness of our data instead....
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            if (GeneralUtils.isIntentSafe(webIntent)) {
                startActivity(webIntent);
            }
        }
    }

    private class TweetClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String tweetUrl = "https://twitter.com/intent/tweet?text=" + tweetText;
            Uri uri = Uri.parse(tweetUrl);
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
    }

    private static void setOrHide(String value, DetailsItemView view) {
        if (TextUtils.isEmpty(value) || value.equals(HEPConstants.NONE)) {
            view.setVisibility(GONE);
        } else {
            if(view.getId() == R.id.submitted_by)
            {
                value = value + " (Submitted By)";
            }
            view.setValue(value);
        }
    }

    private static void setOrHide(Boolean value, DetailsItemView view) {
        if (value == false) {
            view.setVisibility(GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }
    private static void setOrHide(String value, CategoryLabel view) {
        if (TextUtils.isEmpty(value)) {
            view.setVisibility(GONE);
        } else {
            view.setText(value);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: // go back.
                finish();
                break;
            case R.id.update_restaurant:
                // Redirect to update screen URL.
                Intent intent = new Intent(RestaurantDetailsActivity.this, WebViewActivity.class);
                String url = getString(R.string.url_edit_listing) + "?rowID=" + restaurant.key();
                intent.putExtra(HEPConstants.URL, url);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void nextPhotoButtonPressed(View view) {
        restaurantPhotoCarousel.setCurrentItem(restaurantPhotoCarousel.getCurrentItem() + 1, true);
    }

    public void previousPhotoButtonPressed(View view) {
        restaurantPhotoCarousel.setCurrentItem(restaurantPhotoCarousel.getCurrentItem() - 1, true);
    }

    private void refreshCarouselNavigationButtonsVisibility() {
        if (restaurantPhotoCarousel.getCurrentItem() < restaurantPhotoCarousel.getAdapter().getCount() - 1) {
            nextPhotoButton.setVisibility(View.VISIBLE);
        } else {
            nextPhotoButton.setVisibility(View.INVISIBLE);
        }

        if (restaurantPhotoCarousel.getCurrentItem() > 0) {
            previousPhotoButton.setVisibility(View.VISIBLE);
        } else {
            previousPhotoButton.setVisibility(View.INVISIBLE);
        }
    }

}
