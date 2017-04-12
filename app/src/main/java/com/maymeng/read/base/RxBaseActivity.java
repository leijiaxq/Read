package com.maymeng.read.base;

import android.os.Bundle;

import com.maymeng.read.api.Constants;
import com.maymeng.read.bean.BaseBean;
import com.maymeng.read.ui.widget.dialog.ProgressXQ;
import com.maymeng.read.utils.ActivityStackUtil;
import com.maymeng.read.utils.ToastUtil;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Create by  leijiaxq
 * Date       2017/3/2 14:31
 * Describe
 */
public abstract class RxBaseActivity extends RxAppCompatActivity {

    private Unbinder bind;

    private ProgressXQ mProgressXQ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //设置布局内容
        setContentView(getLayoutId());
        ActivityStackUtil.addActivity(this);

        //初始化控件绑定框架
        bind = ButterKnife.bind(this);
        //初始化控件
        initViews(savedInstanceState);
        //初始化ToolBar
        initToolBar();

        loadData();
    }


    public abstract int getLayoutId();

    public abstract void initViews(Bundle savedInstanceState);

    public abstract void initToolBar();

    public void loadData() {
    }


    public void initRecyclerView() {
    }

    public void initRefreshLayout() {
    }

    public void finishTask(BaseBean bean) {
    }


    public void showProgressDialog(String message) {
        mProgressXQ = ProgressXQ.getInstance(RxBaseActivity.this);
        mProgressXQ.setMessage(message);
        mProgressXQ.show();
    }

    public void hideProgressDialog() {
        if (mProgressXQ != null && mProgressXQ.isShowing()) {
            mProgressXQ.dismiss();
        }
    }
    public void showNetError() {
        hideProgressDialog();
        ToastUtil.showShort(this, Constants.FAILURE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
        ActivityStackUtil.removeActivity(this);
    }
}
