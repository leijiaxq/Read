package com.maymeng.read.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.maymeng.read.R;
import com.maymeng.read.base.RxLazyFragment;
import com.maymeng.read.ui.adapter.BookFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by  leijiaxq
 * Date        2017/4/7 15:16
 * Describe
 */
public class BooksFragment extends RxLazyFragment {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tab)
    TabLayout mTab;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    AppCompatActivity mAppCompatActivity;

    private List<String> mTitles = new ArrayList<>();
    private BookFragmentPagerAdapter mAdapter;
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_books;
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
            actionBar.setTitle("书籍");
        }
    }

    @Override
    public void loadData() {
        mTitles.add("文学");
        mTitles.add("文化");
        mTitles.add("生活");

        initContentFragment();
    }
    //初始化有几个fragment
    private void initContentFragment() {
        ArrayList<Fragment> mFragments = new ArrayList<>();

        mFragments.add(LifeFragmentInstance("文学"));
        mFragments.add(LifeFragmentInstance("文化"));
        mFragments.add(LifeFragmentInstance("生活"));

        mAdapter = new BookFragmentPagerAdapter(getChildFragmentManager(),mFragments, mTitles);
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(mAdapter);
        mTab.setupWithViewPager(mViewPager);

    }

    public  LifeFragment LifeFragmentInstance(String param1) {
        LifeFragment fragment = new LifeFragment();
        Bundle args = new Bundle();
        args.putString("param", param1);
        fragment.setArguments(args);
        return fragment;
    }

}
