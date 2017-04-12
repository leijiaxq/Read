package com.maymeng.read.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.maymeng.read.R;
import com.maymeng.read.api.RetrofitHelper;
import com.maymeng.read.base.RxLazyFragment;
import com.maymeng.read.bean.BaseBean;
import com.maymeng.read.bean.ZhihuBean;
import com.maymeng.read.ui.activity.ZhihuWebViewActivity;
import com.maymeng.read.ui.adapter.ZhihuAdapter;
import com.maymeng.read.utils.DateUtil;
import com.maymeng.read.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by  leijiaxq
 * Date        2017/4/7 15:14
 * Describe
 */
public class ZhihuFragment extends RxLazyFragment implements SwipeRefreshLayout.OnRefreshListener {

    AppCompatActivity mAppCompatActivity;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    private boolean mIsAllLoaded;
    private List<ZhihuBean.StoriesBean> mDatas = new ArrayList<>();
    private List<ZhihuBean.TopStoriesBean> mTopDatas = new ArrayList<>();
    private ZhihuAdapter mAdapter;


    private long mCurrentTime;

    private int mPageIndex = 0;

    private long mOneDayMillis = (long) 1 * 24 * 60 * 60 * 1000;

    private String mTime;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_zhihu;
    }

    @Override
    public void initView(Bundle state) {
        setHasOptionsMenu(true);
        mAppCompatActivity = (AppCompatActivity) mActivity;
        mAppCompatActivity.setSupportActionBar(mToolbar);

        ActionBar actionBar = mAppCompatActivity.getSupportActionBar();
        if (actionBar != null) {
            //            actionBar.setHomeAsUpIndicator(R.drawable.);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setTitle("资讯");
        }

        mSwipeRefresh.setOnRefreshListener(this);
        mSwipeRefresh.setColorSchemeColors(getResources().getIntArray(R.array.gplus_colors));

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

                int lastVisibleItemPosition = ((LinearLayoutManager) layoutManager)
                        .findLastVisibleItemPosition();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();

                if (!mIsAllLoaded && visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition >= totalItemCount - 1 /*&& mDatas.size() >= Constants.SIZE*/) {
                    mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
                    loadMore();
                }
               /* int firstCompletelyVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
                if (firstCompletelyVisibleItemPosition != 0) {
                    mFab.setVisibility(View.VISIBLE);
                } else {
                    mFab.setVisibility(View.GONE);
                }*/
            }
        });

        initAdapter();

        mCurrentTime = System.currentTimeMillis();

    }

    private void initAdapter() {
        mAdapter = new ZhihuAdapter(mActivity, mDatas, mTopDatas);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ZhihuAdapter.OnItemClickListener() {
            @Override
            public void onItemHeaderClick(int position) {

                Intent intent = new Intent(mActivity, ZhihuWebViewActivity.class);
                ZhihuBean.TopStoriesBean bean = mTopDatas.get(position);

                intent.putExtra("zhihu_id", bean.id);

                startActivity(intent);

            }

            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(mActivity, ZhihuWebViewActivity.class);
                ZhihuBean.StoriesBean bean = mDatas.get(position - 1);

                intent.putExtra("zhihu_id", bean.id);

                startActivity(intent);
            }
        });
    }


    @Override
    public void loadData() {
        getNewZhihuNet();
    }

    //获取最新的知乎消息
    private void getNewZhihuNet() {
        RetrofitHelper.getZhihuApi()
                .getNewZhihuBeanNet()
                .compose(this.<ZhihuBean>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ZhihuBean>() {
                    @Override
                    public void onCompleted() {
                        hideProgressDialog();
                        if (mSwipeRefresh != null) {
                            mSwipeRefresh.setRefreshing(false);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mSwipeRefresh != null) {
                            mSwipeRefresh.setRefreshing(false);
                        }
                        showNetError();
                    }

                    @Override
                    public void onNext(ZhihuBean zhihuBean) {
                        if (!TextUtils.isEmpty(zhihuBean.date)) {
                            if (zhihuBean.date.equals(mTime)) {
                                return;
                            } else {
                                mTime = zhihuBean.date;
                            }
                            mDatas.clear();
                            finishTask(zhihuBean);
                        } else {
                            ToastUtil.showShort(mActivity, "网络请求错误");
                        }
                    }
                });
    }


    //获取过往的知乎消息
    private void getOldZhihuNet(String time) {
        RetrofitHelper.getZhihuApi()
                .getOldZhihuBeanNet(time)
                .compose(this.<ZhihuBean>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ZhihuBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mSwipeRefresh.setRefreshing(false);
                        showNetError();
                    }

                    @Override
                    public void onNext(ZhihuBean zhihuBean) {
                        if (!TextUtils.isEmpty(zhihuBean.date)) {
//                            mTime = zhihuBean.date;
                            hideProgressDialog();
                            mSwipeRefresh.setRefreshing(false);
                            finishTask(zhihuBean);
                        } else {
                            ToastUtil.showShort(mActivity, "网络请求错误");
                        }
                    }
                });
    }

    @Override
    public void onRefresh() {
        mSwipeRefresh.setRefreshing(true);
        mPageIndex = 0;
        mCurrentTime = System.currentTimeMillis();
        getNewZhihuNet();
    }

    private void loadMore() {
        mPageIndex++;

        long l = mCurrentTime - mOneDayMillis * mPageIndex;

        String s = DateUtil.millis2String(l);
        String[] split = s.split(" ");
        String time = split[0].replaceAll("-", "");
        getOldZhihuNet(time);
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
//        onRefresh();
    }

    @Override
    protected void finishTask(BaseBean bean) {
        if (bean instanceof ZhihuBean) {
            setZhiHuBeanData((ZhihuBean) bean);
        }
    }


    private void setZhiHuBeanData(ZhihuBean bean) {


        if (bean.top_stories != null) {
            mTopDatas.clear();
            mTopDatas.addAll(bean.top_stories);
        }

        if (bean.stories != null) {
            mDatas.addAll(bean.stories);
        }
        mAdapter.notifyDataSetChanged();

    }
}
