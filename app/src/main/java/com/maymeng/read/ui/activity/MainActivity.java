package com.maymeng.read.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.maymeng.read.R;
import com.maymeng.read.base.FragmentFactory;
import com.maymeng.read.base.RxBaseActivity;
import com.maymeng.read.base.RxLazyFragment;
import com.maymeng.read.utils.ActivityStackUtil;

import butterknife.BindView;

public class MainActivity extends RxBaseActivity implements RadioGroup.OnCheckedChangeListener {


    @BindView(R.id.main_rg)
    RadioGroup mMainRg;


    private RxLazyFragment mContent;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mMainRg.setOnCheckedChangeListener(this);
        replaceFragment(FragmentFactory.FRAGMENT_ONE);
    }

    @Override
    public void initToolBar() {

    }

    /**
     * fragment切换
     */
    public void replaceFragment(int index) {
        RxLazyFragment to = FragmentFactory.getFragment(index);
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (mContent != to) {
            if (mContent == null) {
                transaction.add(R.id.main_layout, to).commitAllowingStateLoss();
            } else {
                if (!to.isAdded()) {    // 先判断是否被add过
                    transaction.hide(mContent).add(R.id.main_layout, to).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
                } else {
                    transaction.hide(mContent).show(to).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
                }
            }
            mContent = to;
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.zhihu_rb:
                replaceFragment(FragmentFactory.FRAGMENT_ONE);
                break;
            case R.id.movies_rb:
                replaceFragment(FragmentFactory.FRAGMENT_TWO);
                break;
            case R.id.book_rb:
                replaceFragment(FragmentFactory.FRAGMENT_THREE);
                break;
            default:
                break;
        }
    }


    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(),
                        "再按一次退出",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                ActivityStackUtil.AppExit();
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

