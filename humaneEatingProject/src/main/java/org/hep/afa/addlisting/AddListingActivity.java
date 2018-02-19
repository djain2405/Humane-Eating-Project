package org.hep.afa.addlisting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.flurry.android.FlurryAgent;

import org.hep.afa.R;

/**
 * AddListingActivity is responsible for adding new restaurant categorizable in one of Humane status.
 */
public class AddListingActivity extends AppCompatActivity
{
    private static final String JAVASCRIPT_POPULATE =
            "javascript:(function() { document.getElementById('name').value = '%s';" +
                    "document.getElementById('street').value='%s';" +
                    "document.getElementById('city').value='%s';" +
                    "document.getElementById('state').value='%s';" +
                    "document.getElementById('country').value='%s';" +
                    "document.getElementById('postal').value='%s';" +
                    "document.getElementById('phone').value='%s' })();";
	WebView LegendActivityWebView;
	ProgressBar progressBar;

    private void initializeViews() {
        LegendActivityWebView = (WebView) findViewById(R.id.webview);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }

    @Override
	protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_legend);
		initializeViews();


		// up navigation button
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setCustomView(R.layout.add_listings_label_centre);
		actionBar.setDisplayShowCustomEnabled(true);
		FlurryAgent.logEvent("Add Listing View");

		WebSettings webSettings = LegendActivityWebView.getSettings();

		webSettings.setJavaScriptEnabled(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setDomStorageEnabled(true);
		webSettings.setGeolocationEnabled(true);

		LegendActivityWebView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onGeolocationPermissionsShowPrompt(String origin,
														   android.webkit.GeolocationPermissions.Callback callback) {
				callback.invoke(origin, true, false);
			}

			@Override
			public void onProgressChanged(WebView view, int progress) {
				progressBar.setProgress(progress);
			}
		});

		LegendActivityWebView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				LegendActivityWebView.setVisibility(View.VISIBLE);
				progressBar.setVisibility(View.GONE);

				// Populate the address if POPULATE flag is set.
				if (getIntent().getBooleanExtra(getString(R.string.POPULATE), false)) {
					// fetch address pieces, populate javascript string, and load...
					String javascript = populateJavaScript(getIntent());
					view.loadUrl(javascript);
				}
			}
		});
		LegendActivityWebView.loadUrl("http://mobileapp.humaneeatingproject.org/listings/addListing.html");
		LegendActivityWebView.getSettings().setRenderPriority(RenderPriority.HIGH);
		LegendActivityWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

    }

    /**
     * Populates the constant javascript parameter string.
     * @param intent: intent to get the passed parameters from.
     * @return: populated javascript parameter string.
     */
    private String populateJavaScript(Intent intent) {
        if(intent == null) { return ""; }
        String name = guard(intent.getStringExtra(getString(R.string.RESTAURANT_NAME)));
        String address = guard(intent.getStringExtra(getString(R.string.ADDRESS)));
        String city = guard(intent.getStringExtra(getString(R.string.CITY)));
        String state = guard(intent.getStringExtra(getString(R.string.STATE)));
        String country = guard(intent.getStringExtra(getString(R.string.COUNTRY)));
        String postal = guard(intent.getStringExtra(getString(R.string.POSTAL)));
        String phone = guard(intent.getStringExtra(getString(R.string.PHONE)));
        return String.format(JAVASCRIPT_POPULATE, name, address, city, state, country, postal, phone);
    }

    private static String guard(String string) {
        if (string == null) { return ""; }
        return string.trim();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	@Override
	public void onBackPressed() {
		if (LegendActivityWebView.canGoBack()) {
			LegendActivityWebView.goBack();
		}
		else {
			super.onBackPressed();
			overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		FlurryAgent.onStartSession(this, "Y8B9D2MQCFMCP2KJ5KVT");
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		 FlurryAgent.onEndSession(this);
	}

}
