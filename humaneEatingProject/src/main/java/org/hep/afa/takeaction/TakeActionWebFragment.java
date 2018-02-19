package org.hep.afa.takeaction;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.hep.afa.R;

/**
 * Created by heather on 10/31/16.
 *
 * Use this class to display URL in WebView
 */

public class TakeActionWebFragment extends Fragment {

    private static final String URL = "url";

    WebView webView;

    String url;

    public static TakeActionWebFragment newInstance(String url) {
        TakeActionWebFragment fragment = new TakeActionWebFragment();

        Bundle args = new Bundle();
        args.putString(URL, url);
        fragment.setArguments(args);

        return fragment;
    }

    public boolean canGoBack() {
        if (webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return false;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getArguments().getString(URL);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_takeaction_web, container, false);

        webView = (WebView) rootView.findViewById(R.id.takeaction_webview);


        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new myWebClient());
        webView.loadUrl(url);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        webView.loadUrl(url);
    }

    public class myWebClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
            webView.setVisibility(View.INVISIBLE);


        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;

        }
        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            // TODO Auto-generated method stub
           // Toast.makeText(, "Oh no! " + description, Toast.LENGTH_SHORT).show();
            //super.onReceivedError(view, errorCode, description, failingUrl);
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            System.out.println("Divya Page finished");
            super.onPageFinished(view, url);
            webView.setVisibility(View.VISIBLE);

        }
    }
}
