package com.birdwind.init.view.customer;

import com.birdwind.init.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import androidx.annotation.Nullable;

import static com.birdwind.init.base.utils.Utils.dp2px;

public class ProgressWebView extends WebView {

    public static final int DEFAULT = 0;
    public static final int TERMS = 1;

    private ProgressWebViewListener progressWebViewListener;

    private ProgressBar progressbar;

    private int pageStatus;

    private boolean isDonateTranslate = false;

    public ProgressWebView(Context context) {
        this(context, null);
    }

    public ProgressWebView(Context context, AttributeSet attrs) {
        this(context, attrs, Resources.getSystem().getIdentifier("webViewStyle", "attr", "android"));
    }

    public ProgressWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ProgressWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initProgressBar(context);
        setWebView();
        addJSInterface();
        setWebChromeClient(new WebChromeClient(){});
        setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                view.loadUrl(
                        "javascript:window.defaultJavaScript.showHtml('<head>'+ document.getElementsByTagName('html')[0].innerHTML+'</head>');");
                super.onPageFinished(view, url);
            }

            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                // 獲得 Html Header
                return super.shouldInterceptRequest(view, request);
            }
        });
    }

    private void initProgressBar(Context context) {
        progressbar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        progressbar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, dp2px(context, 3), 0, 0));
        progressbar
                .setProgressDrawable(new ClipDrawable(new ColorDrawable(getResources().getColor(R.color.colorBlue_004EA2)),
                        Gravity.START, ClipDrawable.HORIZONTAL));
        addView(progressbar);
    }

    private void setWebView() {
        WebSettings webSettings = getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }

    public void addProgressWebViewListener(ProgressWebViewListener progressWebViewListener) {
        this.progressWebViewListener = progressWebViewListener;
    }

    public void setPageStatus(int pageStatus) {
        this.pageStatus = pageStatus;
    }

    @SuppressLint("JavascriptInterface")
    private void addJSInterface() {
        addJavascriptInterface(new DefaultJavaScript(), "defaultJavaScript");
    }

    final class DefaultJavaScript {
        @JavascriptInterface
        public void showHtml(String html) {
            // progressWebViewListener.getHtml(html);
        }
    }

    /**
     * 顯示WebView載入的進度情況
     */
    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progressbar.setVisibility(GONE);
            } else {
                if (progressbar.getVisibility() == GONE)
                    progressbar.setVisibility(VISIBLE);

                progressbar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }
    }

    public interface ProgressWebViewListener {
        void getHtml(String msg);

        void getIsWebViewSuccess(boolean isSuccess);

        void hideCloseButton();
    }
}
