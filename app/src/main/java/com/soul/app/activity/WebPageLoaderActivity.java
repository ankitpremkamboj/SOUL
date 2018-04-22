package com.soul.app.activity;

import android.graphics.Bitmap;
import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.soul.app.R;
import com.soul.app.activity.*;
import com.soul.app.application.ApplicationController;
import com.soul.app.constants.AppConstant;
import com.github.mrengineer13.snackbar.SnackBar;
import com.soul.app.retrofit.ApiConstants;
import com.soul.app.utils.Utility;


/**
 * this class is used to open a web page
 * Created by omji on 29/12/15.
 */
public class WebPageLoaderActivity extends com.soul.app.activity.BaseActivity {
    private WebView mWebView;
    private ImageView eph_back_icon;
    private TextView titleText;
    //  private View mParent;

    @Override
    public int setLayout() {
        Utility.setStatusBarGradiant(this);
        return R.layout.activity_webpageloader;
    }

    @Override
    public void initUi() {

        int staticClassFlag = getIntent().getExtras().getInt(AppConstant.KEY_CLASS_CONSTANT);
        switch (staticClassFlag) {
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;
        }

        mWebView = (WebView) findViewById(R.id.webpageloader_webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        eph_back_icon = (ImageView) findViewById(R.id.eph_back_icon);
        titleText = (TextView) findViewById(R.id.titleText);
        String url = getIntent().getExtras().getString(AppConstant.KEY_WEB_URL);

        if (url.equalsIgnoreCase(ApiConstants.URL_TERMS)) {

            titleText.setText("Terms and Services");

        } else if (url.equalsIgnoreCase(ApiConstants.URL_CONTACT)) {
            titleText.setText("Contact Us");

        } else if (url.equalsIgnoreCase(ApiConstants.URL_PRIVACY)) {
            titleText.setText("Privacy policy");

        }        eph_back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        if (Build.VERSION.SDK_INT >= 11) {
            mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        // mWebView.getSettings().setLoadWithOverviewMode(true);
        // mWebView.getSettings().setUseWideViewPort(true);
        //   mParent = findViewById(R.id.webpageloader_parent);
        loadWebPage();
    }


    /**
     *
     */
    private void loadWebPage() {
        if (getIntent().getExtras() != null) {
            if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
                mWebView.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                        showProgressBar(true);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        showProgressBar(false);
                    }

                    @Override
                    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                        super.onReceivedError(view, request, error);
                        showProgressBar(false);
                    }
                });
                mWebView.loadUrl(getIntent().getExtras().getString(AppConstant.KEY_WEB_URL));
            } else {
                showProgressBar(false);
                //mSnackBar = SnackBarBuilder.make(mParent, getString(R.string.nointernetconnction)).build();
                new SnackBar.Builder(WebPageLoaderActivity.this)
                        .withMessage(getResources().getString(R.string.err_network)).show();
                finish();
            }
        } else
            // mSnackBar = SnackBarBuilder.make(mParent, getString(R.string.webpage_not_available)).build();
            new SnackBar.Builder(WebPageLoaderActivity.this)
                    .withMessage(getResources().getString(R.string.webpage_not_available)).show();
    }

    @Override
    public String getName() {
        return WebPageLoaderActivity.class.getName();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) { //if back key is pressed
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;

        }

        return super.onKeyDown(keyCode, event);

    }
}
