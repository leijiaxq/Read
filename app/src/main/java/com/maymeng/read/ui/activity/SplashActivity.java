package com.maymeng.read.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.maymeng.read.R;
import com.maymeng.read.api.Constants;
import com.maymeng.read.base.RxBaseActivity;

import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by  leijiaxq
 * Date        2017/4/11 18:20
 * Describe
 */

public class SplashActivity extends RxBaseActivity {
    @BindView(R.id.iv_pic)
    ImageView mIvPic;
    @BindView(R.id.tv_jump)
    TextView mTvJump;
    @BindView(R.id.activity_transition)
    RelativeLayout mActivityTransition;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        int i = new Random().nextInt(Constants.TRANSITION_URLS.length);
        Glide.with(this)
                .load(Constants.TRANSITION_URLS[i])
                .placeholder(R.drawable.img_transition_default)
                .centerCrop()
                .error(R.drawable.img_transition_default)
                .into(mIvPic);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                toMainActivity();
            }
        }, 3500);
    }

    @Override
    public void initToolBar() {

    }

    private boolean isIn;

    private void toMainActivity() {
        if (isIn) {
            return;
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
        finish();
        isIn = true;
    }

    @OnClick(R.id.tv_jump)
    void clickJump(View view) {
        toMainActivity();
    }
}
