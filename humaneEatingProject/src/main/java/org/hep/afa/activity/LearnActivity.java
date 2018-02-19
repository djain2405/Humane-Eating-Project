package org.hep.afa.activity;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.widget.ProgressBar;
import com.flurry.android.FlurryAgent;

import org.hep.afa.R;
import org.hep.afa.utils.ActionUtils;

import static org.hep.afa.R.string.tweet;

/**
 * Activity class for Learn Section.
 */
public class LearnActivity extends AppCompatActivity {
    WebView learndActivityWebView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);
        initializeViews();

        // up navigation button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setCustomView(R.layout.actionbar_plain);
        actionBar.setDisplayShowCustomEnabled(true);
        Toolbar toolbar = (Toolbar) actionBar.getCustomView().getParent();
        toolbar.setContentInsetsAbsolute(0,0);

        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        WebSettings webSettings = learndActivityWebView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setGeolocationEnabled(true);

        learndActivityWebView.setWebChromeClient(ActionUtils.createWebChromeClient(progressBar));
        learndActivityWebView.setWebViewClient(ActionUtils.createWebViewClient(progressBar));

        learndActivityWebView.loadUrl(getString(R.string.url_learn));
        learndActivityWebView.getSettings().setRenderPriority(RenderPriority.HIGH);
        learndActivityWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        // MenuInflater inflater = getMenuInflater();
        // inflater.inflate(R.menu.legend_activity, menu);
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
        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
    }

    private void initializeViews() {
        learndActivityWebView = (WebView) findViewById(R.id.webview);
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
