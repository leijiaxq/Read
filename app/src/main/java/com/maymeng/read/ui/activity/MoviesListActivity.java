package com.maymeng.read.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.maymeng.read.R;
import com.maymeng.read.api.DouBanService;
import com.maymeng.read.api.RetrofitHelper;
import com.maymeng.read.base.RxBaseActivity;
import com.maymeng.read.bean.BaseBean;
import com.maymeng.read.bean.TopMoviesBean;
import com.maymeng.read.ui.adapter.MoviesListAdapter;
import com.maymeng.read.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by  leijiaxq
 * Date        2017/4/10 11:13
 * Describe
 */
public class MoviesListActivity extends RxBaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    private int mIndex;

    private int mStart = 0;
    private int mCount = 21;
    private boolean mIsAllLoaded = false;

    private List<TopMoviesBean.SubjectsBean> mDatas = new ArrayList<>();
    private MoviesListAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_movies_list;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        mIndex = getIntent().getIntExtra("index", 0);
        mSwipeRefresh.setOnRefreshListener(this);
        mSwipeRefresh.setColorSchemeColors(getResources().getIntArray(R.array.gplus_colors));

        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager manager = new GridLayoutManager(this, 3);

        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

            @Override

            public int getSpanSize(int position) {
                if (position == mDatas.size()) {
                    return 3;
                }
                return 1;

            }

        });
        mRecyclerView.setLayoutManager(manager);


        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

                int lastVisibleItemPosition = ((GridLayoutManager) layoutManager)
                        .findLastVisibleItemPosition();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();

                if (!mIsAllLoaded && visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition >= totalItemCount - 1 && mDatas.size() >= mCount) {
                    mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
                    loadMore();
                }
            }
        });

        initAdapter();
    }


    private void initAdapter() {
        mAdapter = new MoviesListAdapter(this, mDatas);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initToolBar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(mIndex == 0 ? "豆瓣电影Top250" : "即将上映");
        }
    }

    @Override
    public void onRefresh() {
        mStart = 0;
        mIsAllLoaded = false;
        mAdapter.isAllLoad = false;

        getMoviesDataNet();
    }

    private void loadMore() {
        getMoviesDataNet();
    }

    @Override
    public void loadData() {
        super.loadData();
        getMoviesDataNet();
    }


    private void getMoviesDataNet() {
        DouBanService api = RetrofitHelper.getDouBanApi();
        Observable<TopMoviesBean> observable;
        if (mIndex == 0) {
            observable = api.getTop250MoviesNet(mStart, mCount);
        } else {
            observable = api.getSoonMoviesNet(mStart, mCount);
        }
        observable.compose(this.<TopMoviesBean>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TopMoviesBean>() {
                    @Override
                    public void onCompleted() {
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
                    public void onNext(TopMoviesBean topMoviesBean) {
                        if (topMoviesBean != null) {
                            finishTask(topMoviesBean);
                        } else {
                            ToastUtil.showShort(MoviesListActivity.this, "数据有误");
                        }
                    }
                });
    }

    @Override
    public void finishTask(BaseBean bean) {
        if (bean instanceof TopMoviesBean) {
            setTopMoviesData((TopMoviesBean) bean);
        }
    }

    private void setTopMoviesData(TopMoviesBean bean) {
        if (bean == null || bean.subjects == null) {
            return;
        }
        if (mStart == 0) {
            mDatas.clear();
        }
        mStart += bean.subjects.size();

        if (bean.subjects.size() < mCount) {
            mIsAllLoaded = true;
            mAdapter.isAllLoad = true;
        }

        mDatas.addAll(bean.subjects);
        mAdapter.notifyDataSetChanged();

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
