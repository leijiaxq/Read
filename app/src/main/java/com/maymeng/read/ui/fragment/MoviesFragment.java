package com.maymeng.read.ui.fragment;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import com.maymeng.read.R;
import com.maymeng.read.api.RetrofitHelper;
import com.maymeng.read.base.RxLazyFragment;
import com.maymeng.read.bean.BaseBean;
import com.maymeng.read.bean.HotMoviesBean;
import com.maymeng.read.ui.adapter.MoviesAdapter;
import com.maymeng.read.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by  leijiaxq
 * Date        2017/4/7 15:16
 * Describe
 */
public class MoviesFragment extends RxLazyFragment {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    AppCompatActivity mAppCompatActivity;

    private List<HotMoviesBean.SubjectsBean> mDatas = new ArrayList<>();
    private MoviesAdapter mAdapter;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_movies;
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
            actionBar.setTitle("电影");

        }

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        initAdapter();
    }

    private void initAdapter() {
        mAdapter = new MoviesAdapter(mActivity, mDatas);
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public void loadData() {
        showProgressDialog("");
        getHotMoviesNet();
    }

    //获取正在上映的电影
    private void getHotMoviesNet() {
        RetrofitHelper.getDouBanApi()
                .getHotMoviesNet()
                .compose(this.<HotMoviesBean>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HotMoviesBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        showNetError();
                    }

                    @Override
                    public void onNext(HotMoviesBean hotMoviesBean) {
                        hideProgressDialog();
                        if (!TextUtils.isEmpty(hotMoviesBean.title)) {
                            finishTask(hotMoviesBean);
                        } else {
                            ToastUtil.showShort(mActivity,"数据有误");
                        }
                    }
                });
    }

    @Override
    protected void finishTask(BaseBean bean) {
        if (bean instanceof HotMoviesBean) {
            setHotMoviesBeanData((HotMoviesBean)bean);
        }
    }


    private void setHotMoviesBeanData(HotMoviesBean bean) {
        if (bean ==null) {
            return;
        }
        mDatas.clear();
        mDatas.addAll(bean.subjects);
        mAdapter.notifyDataSetChanged();

    }
}
