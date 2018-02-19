package org.hep.afa.utils;

import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * Provides factory collection of actionable(listener, webview client etc) items.
 * Created by Jaebin Lee on 4/3/2016.
 */
public class ActionUtils
{
    public static WebChromeClient createWebChromeClient(final ProgressBar progressBar)
    {
        return new WebChromeClient() {
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }

            @Override
            public void onProgressChanged(WebView view, int progress) {
                progressBar.setProgress(progress);
            }

        };
    }

    public static WebViewClient createWebViewClient(final ProgressBar progressBar)
    {
        return new WebViewClient()
        {
            @Override
            public void onPageFinished(WebView view, String url) {
                view.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        };
    }

//    public static void processWebView(WebView webView)
//    {
//        WebSettings settings = webView.getSettings();
//        settings.setJavaScriptEnabled(true);
//        settings.setJavaScriptCanOpenWindowsAutomatically(true);
//        settings.setGeolocationEnabled(true);
//
//        webView.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public void onGeolocationPermissionsShowPrompt(String origin,
//                                                           android.webkit.GeolocationPermissions.Callback callback) {
//                callback.invoke(origin, true, false);
//            }
//            @Override
//            public void onProgressChanged(WebView view, int progress) {
//                progressBar.setProgress(progress); }
//        });
//
//        webView.loadUrl(getString(R.string.url_legend));
//        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
//        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        webView.setWebViewClient(new WebViewClient(){
//            @Override
//            public void onPageFinished(WebView view, String url) {
////				legendActivityWebView.setVisibility(View.VISIBLE);
//                view.setVisibility(View.VISIBLE);
//                progressBar.setVisibility(View.GONE);
//            }
//        });
//    }
}
