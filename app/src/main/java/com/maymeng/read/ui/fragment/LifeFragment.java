package com.maymeng.read.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.maymeng.read.R;
import com.maymeng.read.api.RetrofitHelper;
import com.maymeng.read.base.RxLazyFragment;
import com.maymeng.read.bean.BaseBean;
import com.maymeng.read.bean.BookBean;
import com.maymeng.read.ui.adapter.LifeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by  leijiaxq
 * Date        2017/4/11 11:41
 * Describe
 */
public class LifeFragment extends RxLazyFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    List<BookBean.BooksBean> mDatas = new ArrayList<>();
    String mType;
    private int mStart = 0;
    private int mCount = 21;
    private boolean mIsAllLoaded = false;
    private LifeAdapter mAdapter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_life;
    }

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            mType = getArguments().getString("param");
        }

        mSwipeRefresh.setOnRefreshListener(this);
        mSwipeRefresh.setColorSchemeColors(getResources().getIntArray(R.array.gplus_colors));

        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager manager = new GridLayoutManager(mActivity, 3);

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
        mAdapter = new LifeAdapter(mActivity, mDatas);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void loadData() {
        if (!TextUtils.isEmpty(mType)) {
            getBooksByTagNet();
        }
    }

    @Override
    public void onRefresh() {
        mStart = 0;
        mIsAllLoaded = false;
        mAdapter.isAllLoad = false;
        getBooksByTagNet();
    }

    private void loadMore() {
        getBooksByTagNet();
    }


    private void getBooksByTagNet() {
        RetrofitHelper.getDouBanApi()
                .getBooksByTag(mType, mStart, mCount)
                .compose(this.<BookBean>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BookBean>() {
                    @Override
                    public void onCompleted() {
                        if (mSwipeRefresh != null) {
                            mSwipeRefresh.setRefreshing(false);
                        }
                        hideProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mSwipeRefresh != null) {
                            mSwipeRefresh.setRefreshing(false);
                        }
                        showNetError();
                    }

                    @Override
                    public void onNext(BookBean bookBean) {
                        finishTask(bookBean);
                    }
                });
    }

    @Override
    protected void finishTask(BaseBean bean) {
        if (bean instanceof BookBean) {
            setBookBeanData((BookBean) bean);
        }
    }

    private void setBookBeanData(BookBean bean) {
        if (bean == null || bean.books == null) {
            return;
        }
        if (mStart == 0) {
            mDatas.clear();
        }
        mStart += bean.books.size();

        if (bean.books.size() < mCount) {
            mIsAllLoaded = true;
            mAdapter.isAllLoad = true;
        }

        mDatas.addAll(bean.books);
        mAdapter.notifyDataSetChanged();
    }
}
