package com.maymeng.read.ui.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.maymeng.read.bean.MoviesDetailBean;
import com.maymeng.read.bean.PerformerBean;
import com.maymeng.read.ui.adapter.PerformerAdapter;
import com.maymeng.read.utils.DataFormatUtil;
import com.maymeng.read.utils.ImageUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.BlurTransformation;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by  leijiaxq
 * Date        2017/4/10 15:38
 * Describe
 */
public class MoviesDetailActivity extends RxBaseActivity {
    @BindView(R.id.header_bg_iv)
    ImageView mHeaderBgIv;
    @BindView(R.id.icon_iv)
    ImageView mIconIv;
    @BindView(R.id.score_tv)
    TextView mScoreTv;
    @BindView(R.id.director_tv)
    TextView mDirectorTv;
    @BindView(R.id.star_tv)
    TextView mStarTv;
    @BindView(R.id.type_tv)
    TextView mTypeTv;
    @BindView(R.id.date_tv)
    TextView mDateTv;
    @BindView(R.id.address_tv)
    TextView mAddressTv;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.appBar)
    AppBarLayout mAppBar;
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.content_tv)
    TextView mContentTv;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.title_toolbar_tv)
    TextView mTitleToobarTv;

    private String mId;
    private String mImageUrl;

    @Override
    public int getLayoutId() {
        return R.layout.activity_movies_detail;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mId = getIntent().getStringExtra("movies_id");
        mImageUrl = getIntent().getStringExtra("movies_url");
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


        if (!TextUtils.isEmpty(mId)) {

            getMoviesDetailNet();

           /* String formatScore = DataFormatUtil.getFormatInstance2().format(mBean.rating.average);
            mScoreTv.setText("评分：" + formatScore);

            String director = "";
            for (int i = 0, length = mBean.directors.size(); i < length; i++) {
                TopMoviesBean.SubjectsBean.DirectorsBean bean = mBean.directors.get(i);
                if (i == length - 1) {
                    director += bean.name;
                } else {
                    director += bean.name + "/";
                }
            }
            mDirectorTv.setText("导演：" + director);

            mStarTv.setText(mStar);

            String type = "";
            for (int i = 0, length = mBean.genres.size(); i < length; i++) {
                String str = mBean.genres.get(i);
                if (i == length - 1) {
                    type += str;
                } else {
                    type += str + "/";
                }
            }
            mTypeTv.setText("类型：" + type);

            mDateTv.setText(mBean.year);

*/
        }

    }

    private void getMoviesDetailNet() {
        RetrofitHelper.getDouBanApi()
                .getMoviesDetailNet(mId)
                .compose(this.<MoviesDetailBean>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MoviesDetailBean>() {
                    @Override
                    public void onCompleted() {
                        hideProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        showNetError();
                    }

                    @Override
                    public void onNext(MoviesDetailBean bean) {
                        finishTask(bean);
                    }
                });
    }

    @Override
    public void finishTask(BaseBean bean) {
        if (bean instanceof MoviesDetailBean) {
            setMoviesDetailData((MoviesDetailBean) bean);
        }
    }

    private void setMoviesDetailData(MoviesDetailBean bean) {
        if (bean != null) {
            if (bean.countries.size() > 0) {
                String str = bean.countries.get(0);
                mAddressTv.setText("制片国家：" + str);

            }

            if (bean.aka.size() > 0) {
                String s = bean.aka.get(0);
                mTitleTv.setText(s);
            }

            mContentTv.setText(bean.summary);

            String formatScore = DataFormatUtil.getFormatInstance2().format(bean.rating.average);
            mScoreTv.setText("评分：" + formatScore);

            String director = "";
            for (int i = 0, length = bean.directors.size(); i < length; i++) {
                MoviesDetailBean.DirectorsBean bean1 = bean.directors.get(i);
                if (i == length - 1) {
                    director += bean1.name;
                } else {
                    director += bean1.name + "/";
                }
            }
            mDirectorTv.setText("导演：" + director);


            String star = "主演：";
            for (int i = 0, length = bean.casts.size(); i < length; i++) {
                MoviesDetailBean.CastsBean bean2 = bean.casts.get(i);
                if (i == length - 1) {
                    star += bean2.name;
                } else {
                    star += bean2.name + "/";
                }
            }
            mStarTv.setText(star);

            String type = "";
            for (int i = 0, length = bean.genres.size(); i < length; i++) {
                String str1 = bean.genres.get(i);
                if (i == length - 1) {
                    type += str1;
                } else {
                    type += str1 + "/";
                }
            }
            mTypeTv.setText("类型：" + type);

            mDateTv.setText("上映时间：" + bean.year);


            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle(bean.title);

                actionBar.setSubtitle("主演：" + star);
            }

            ImageUtil.getInstance().displayImage(this, bean.images.large, mIconIv);

//            setImgHeaderBg(bean.images.medium);

            initAdapter(bean);

        }
    }

    private void initAdapter(MoviesDetailBean bean) {

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        List<PerformerBean> mList = new ArrayList<>();
        PerformerBean bean2;

        if (bean.directors != null) {
            for (MoviesDetailBean.DirectorsBean bean1 : bean.directors) {
                bean2 = new PerformerBean();
                bean2.url = bean1.alt;
                bean2.Occupation = "导演";
                bean2.icon = bean1.avatars.large;
                bean2.name = bean1.name;
                mList.add(bean2);
            }
        }

        if (bean.casts != null) {
            for (MoviesDetailBean.CastsBean bean1 : bean.casts) {
                bean2 = new PerformerBean();
                bean2.url = bean1.alt;
                bean2.Occupation = "主演";
                bean2.icon = bean1.avatars.large;
                bean2.name = bean1.name;
                mList.add(bean2);
            }
        }

        mTitleToobarTv.setText(TextUtils.isEmpty(bean.title)?"":bean.title);

        PerformerAdapter adapter = new PerformerAdapter(this,mList);
        mRecyclerView.setAdapter(adapter);


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
