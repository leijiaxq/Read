package com.maymeng.read.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.maymeng.read.R;
import com.maymeng.read.api.RetrofitHelper;
import com.maymeng.read.base.RxBaseActivity;
import com.maymeng.read.bean.BaseBean;
import com.maymeng.read.bean.BookDetailBean;
import com.maymeng.read.utils.ImageUtil;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.BlurTransformation;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by  leijiaxq
 * Date        2017/4/11 13:20
 * Describe
 */
public class BookDetailActivity extends RxBaseActivity {
    @BindView(R.id.header_bg_iv)
    ImageView mHeaderBgIv;
    @BindView(R.id.icon_iv)
    ImageView mIconIv;
    @BindView(R.id.author_tv)
    TextView mAuthorTv;
    @BindView(R.id.score_tv)
    TextView mScoreTv;
    @BindView(R.id.date_tv)
    TextView mDateTv;
    @BindView(R.id.address_tv)
    TextView mAddressTv;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.content_tv)
    TextView mContentTv;
    @BindView(R.id.catalogo_tv)
    TextView mCatalogoTv;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.title_toolbar_tv)
    TextView mTitleToobarTv;

    private String mId;
    private String mImageUrl;

    @Override
    public int getLayoutId() {
        return R.layout.activity_book_detail;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mId = getIntent().getStringExtra("book_id");
        mImageUrl = getIntent().getStringExtra("book_url");
        setImgHeaderBg(mImageUrl);
    }

    @Override
    public void initToolBar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void loadData() {
        super.loadData();
        if (!TextUtils.isEmpty(mId)) {
            getBookDetailNet();
        }
    }

    private void getBookDetailNet() {
        RetrofitHelper.getDouBanApi()
                .getBookDetailNet(mId)
                .compose(this.<BookDetailBean>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BookDetailBean>() {
                    @Override
                    public void onCompleted() {
                        hideProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        showNetError();
                    }

                    @Override
                    public void onNext(BookDetailBean bookDetailBean) {
                        finishTask(bookDetailBean);
                    }
                });
    }

    @Override
    public void finishTask(BaseBean bean) {
        if (bean instanceof BookDetailBean) {
            setBookDetailData((BookDetailBean) bean);
        }
    }

    private void setBookDetailData(BookDetailBean bean) {
        String author = "";
        if (bean.author !=null && bean.author.size() >0) {
            author = bean.author.get(0);
            mAuthorTv.setText("作者："+author);
        }

        mScoreTv.setText("评分："+bean.rating.average);
        mDateTv.setText("出版时间："+bean.pubdate);
        mAddressTv.setText("出版社："+bean.publisher);

        ImageUtil.getInstance().displayImage(this,bean.images.large,mIconIv);

        mTitleTv.setText(TextUtils.isEmpty(bean.summary)?"":bean.summary);

        String author_text = author+"\n"+bean.author_intro;
        mContentTv.setText(author_text);

        mCatalogoTv.setText(bean.catalog);

        mTitleToobarTv.setText(TextUtils.isEmpty(bean.title)?"":bean.title);

    }

    private void setImgHeaderBg(String imgUrl) {
        if (!TextUtils.isEmpty(imgUrl)) {

            // 高斯模糊背景 原来 参数：12,5  23,4
            Glide.with(this).load(imgUrl)
                    .error(R.drawable.default_picture)
                    .bitmapTransform(new BlurTransformation(this, 23, 4)).listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                    bindingTitleView.tbBaseTitle.setBackgroundColor(Color.TRANSPARENT);
//                    bindingTitleView.ivBaseTitlebarBg.setImageAlpha(0);
//                    bindingTitleView.ivBaseTitlebarBg.setVisibility(View.VISIBLE);
                    return false;
                }
            }).into(mHeaderBgIv);
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
