package com.maymeng.read.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.maymeng.read.R;
import com.maymeng.read.base.RxBaseActivity;
import com.maymeng.read.utils.ConvertUtil;
import com.maymeng.read.utils.ScreenUtil;

import butterknife.BindView;


/**
 * Created by  leijiaxq
 * Date        2017/4/11 10:21
 * Describe
 */
public class WebViewActivity extends RxBaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.web_view)
    WebView mWebView;

    ProgressBar mProgressBar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        String url = getIntent().getStringExtra("url");
        String name = getIntent().getStringExtra("name");

        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //            actionBar.setHomeAsUpIndicator(R.drawable.);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            if (!TextUtils.isEmpty(name)) {
                actionBar.setTitle(name);
            } else {
                actionBar.setTitle("");
            }
        }

        WebSettings webSetting = mWebView.getSettings();

        webSetting.setJavaScriptEnabled(true);
        webSetting.setSupportMultipleWindows(true);

        webSetting.setDefaultTextEncodingName("UTF-8");
        webSetting.setSupportZoom(true);

// 设置出现缩放工具
        webSetting.setBuiltInZoomControls(true);
//扩大比例的缩放
//        webSetting.setUseWideViewPort(true);
//自适应屏幕
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSetting.setLoadWithOverviewMode(true);
//        webSetting.setUserAgentString(Constant.UA);

        // 设置允许JS弹窗
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        //载入js
//        mWebView.addJavascriptInterface(new WebViewActivity.JavascriptInterface(this), "imagelistner");
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

//                Map<String, String> mMap = new HashMap<>();
//                mMap.put("X-Requested-With", BaseApplication.getInstance().getPackageName());
//                view.loadUrl(url, mMap);


//                android.webkit.WebView webView = new android.webkit.WebView(SecondWebActivity.this);
//                webView.loadUrl();

                view.loadUrl(url);
                return true;
            }
/*
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //这段js函数的功能就是注册监听，遍历所有的img标签，并添加onClick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
                mWebView.loadUrl("javascript:(function(){"
                        + "var objs = document.getElementsByTagName(\"img\"); "
                        + "for(var i=0;i<objs.length;i++)  " + "{"
                        + "    objs[i].onclick=function()  " + "    {  "
                        + "        window.imagelistner.openImage(this.src,objs);  "
                        + "    }  " + "}" + "})()");
            }*/

        });
        mProgressBar = new ProgressBar(this, null,
                android.R.attr.progressBarStyleHorizontal);
        mProgressBar.setLayoutParams(new ViewGroup.LayoutParams(ScreenUtil
                .getScreenWidth(this), ConvertUtil.px2dp(this,20)));
        Drawable drawable = this.getResources().getDrawable(
                R.drawable.progress_bar_states);
        mProgressBar.setProgressDrawable(drawable);
        mWebView.addView(mProgressBar);

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.INVISIBLE);
                } else {
                    if (View.INVISIBLE == mProgressBar.getVisibility()) {
                        mProgressBar.setVisibility(View.VISIBLE);
                    }
                    mProgressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }


           /* @Override
            public boolean onCreateWindow(WebView view, boolean dialog, boolean userGesture, android.os.Message resultMsg) {
                WebView.HitTestResult result = view.getHitTestResult();
                String data = result.getExtra();
                Context context = view.getContext();
                *//*Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(data));
                context.startActivity(browserIntent);*//*

                Intent intent = new Intent(ZhihuWebViewActivity.this, ZhihuWebViewActivity.class);
                intent.putExtra("url", data);
                context.startActivity(intent);

                return false;
            }*/
        });

        mWebView.loadUrl(url);
    }

    // js通信接口
    public class JavascriptInterface {

        private Context context;


        public JavascriptInterface(Context context) {
            this.context = context;
        }

        @android.webkit.JavascriptInterface
        public void openImage(String img, Object[] imags) {
            Intent intent = new Intent();
            intent.putExtra("img", img);
            intent.setClass(context, ImageActivity.class);
            context.startActivity(intent);
        }
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();// 返回前一个页面
            } else {
//                clearCookie();


                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        //        WebView scrollView = (WebView) findViewById(R.id.ch01);
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_DOWN) {
                    mWebView.pageUp(false);
                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {
                    mWebView.pageDown(false);
                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }
}
