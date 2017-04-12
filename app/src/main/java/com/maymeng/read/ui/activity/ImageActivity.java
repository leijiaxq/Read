package com.maymeng.read.ui.activity;

import android.os.Bundle;

import com.maymeng.read.R;
import com.maymeng.read.base.RxBaseActivity;
import com.maymeng.read.utils.ImageUtil;

import butterknife.BindView;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by  leijiaxq
 * Date        2017/4/10 10:21
 * Describe
 */
public class ImageActivity extends RxBaseActivity {
    @BindView(R.id.photo_view)
    PhotoView mPhotoView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_image;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        String img = getIntent().getStringExtra("img");
        ImageUtil.getInstance().displayImage(this,img,mPhotoView);
    }

    @Override
    public void initToolBar() {

    }

}
