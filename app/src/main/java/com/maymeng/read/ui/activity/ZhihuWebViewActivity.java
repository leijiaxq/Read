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
import com.maymeng.read.api.RetrofitHelper;
import com.maymeng.read.base.RxBaseActivity;
import com.maymeng.read.bean.BaseBean;
import com.maymeng.read.bean.ZhihuDetailBean;
import com.maymeng.read.utils.ConvertUtil;
import com.maymeng.read.utils.ScreenUtil;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by  leijiaxq
 * Date        2017/4/7 17:53
 * Describe
 */
public class ZhihuWebViewActivity extends RxBaseActivity {

    @BindView(R.id.web_view)
    WebView mWebView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private int mZhihu_id;

    ProgressBar mProgressBar;
    @Override
    public int getLayoutId() {
        return R.layout.activity_zhihu_webview;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        showProgressDialog("");

        mZhihu_id = getIntent().getIntExtra("zhihu_id", 0);


        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //            actionBar.setHomeAsUpIndicator(R.drawable.);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
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
        mWebView.addJavascriptInterface(new JavascriptInterface(this), "imagelistner");

//        DocumentsContract.Document document = Jsoup.connect(mUrl).get();
//        document.getElementsByClass("header-container").remove();
//        document.getElementsByClass("footer").remove();
//        WebSettings ws = mWebView.getSettings();
//        ws.setJavaScriptEnabled(true);
//        //mWebView.loadData(document.toString(),"text/html","utf-8");
//        mWebView.loadDataWithBaseURL(mUrl,document.toString(),"text/html","utf-8","");


//        mClient = new OkHttpClient();

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
            }

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
    }

    // js通信接口
    public class JavascriptInterface {

        private Context context;


        public JavascriptInterface(Context context) {
            this.context = context;
        }

        @android.webkit.JavascriptInterface
        public void openImage(String img,Object[] imags) {
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
    public void loadData() {
        super.loadData();
        getZhihuH5Net();
    }


    private void getZhihuH5Net() {
        RetrofitHelper.getZhihuApi()
                .getZhihuDetailNet(mZhihu_id)
                .compose(this.<ZhihuDetailBean>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ZhihuDetailBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        showNetError();
                    }

                    @Override
                    public void onNext(ZhihuDetailBean zhihuDetailBean) {
                        hideProgressDialog();
                        finishTask(zhihuDetailBean);
                    }
                });
    }


    @Override
    public void finishTask(BaseBean bean) {
        if (bean instanceof ZhihuDetailBean) {
            setZhihuDetailBeanData((ZhihuDetailBean) bean);
        }
    }


    private void setZhihuDetailBeanData(ZhihuDetailBean bean) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null) {
            actionBar.setTitle(TextUtils.isEmpty(bean.title)?"":bean.title);
        }
        mWebView.loadData(bean.body, "text/html; charset=UTF-8", null);
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
