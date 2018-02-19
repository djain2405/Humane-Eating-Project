package org.hep.afa.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.flurry.android.FlurryAgent;

import org.hep.afa.R;
import org.hep.afa.constant.HEPConstants;
import org.hep.afa.utils.ActionUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This is a common purpose WebView Activity.
 * Created by Jaebin Lee on 4/5/2016.
 */
public class WebViewActivity extends AppCompatActivity
{
    @BindView(R.id.webview) WebView webView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);

        // up navigation button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar_plain);
        getSupportActionBar().setTitle(R.string.update);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setGeolocationEnabled(true);

        webView.setWebChromeClient(ActionUtils.createWebChromeClient(progressBar));

        String url = getIntent().getStringExtra(HEPConstants.URL);
        // default URL.
        if(TextUtils.isEmpty(url)) { url = getString(R.string.url_home); }

        // Do Expect the URL argument passed through Intent extra.
        webView.loadUrl(url);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        webView.setWebViewClient(ActionUtils.createWebViewClient(progressBar));
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
