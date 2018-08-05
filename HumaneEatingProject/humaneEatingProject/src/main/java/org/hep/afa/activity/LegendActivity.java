package org.hep.afa.activity;

import com.flurry.android.FlurryAgent;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebSettings.RenderPriority;
import android.widget.Button;
import android.widget.ProgressBar;

import org.hep.afa.R;
import org.hep.afa.utils.ActionUtils;

public class LegendActivity extends AppCompatActivity {
	WebView legendActivityWebView;
	ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_legend);
		initializeViews();
		// up navigation button
		if(getSupportActionBar() != null) {
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			getSupportActionBar().setCustomView(R.layout.action_bar_label_centre);
			getSupportActionBar().setDisplayShowCustomEnabled(true);
		}


		WebSettings webSettings = legendActivityWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setGeolocationEnabled(true);

        legendActivityWebView.setWebChromeClient(ActionUtils.createWebChromeClient(progressBar));

		legendActivityWebView.loadUrl(getString(R.string.url_legend));
		legendActivityWebView.getSettings().setRenderPriority(RenderPriority.HIGH);
		legendActivityWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        legendActivityWebView.setWebViewClient(ActionUtils.createWebViewClient(progressBar));
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
			finish();
			break;

		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_up,
				R.anim.slide_down);
	}

	private void initializeViews() {
		legendActivityWebView = (WebView) findViewById(R.id.webview);
		progressBar = (ProgressBar) findViewById(R.id.progress_bar);
	}
	
	//Flurry Setup 
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
