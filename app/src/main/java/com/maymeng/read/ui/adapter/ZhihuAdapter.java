package com.maymeng.read.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maymeng.read.R;
import com.maymeng.read.api.Constants;
import com.maymeng.read.bean.ZhihuBean;
import com.maymeng.read.utils.ImageUtil;
import com.maymeng.read.view.ProgressWheel;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by  leijiaxq
 * Date       2017/3/15 11:27
 * Describe
 */
public class ZhihuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<ZhihuBean.StoriesBean> mDatas;
    private List<ZhihuBean.TopStoriesBean> mTopDatas;
    public boolean isAllLoad = false;


    public ZhihuAdapter(Context context, List<ZhihuBean.StoriesBean> datas, List<ZhihuBean.TopStoriesBean> topDatas) {
        mContext = context;
        mDatas = datas;
        mTopDatas = topDatas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == Constants.TYPE_ONE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_zhihu_header, parent, false);
            return new ViewHolderType1(view);
        } else if (viewType == Constants.TYPE_FOOT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foot_layout, parent, false);
            return new ViewHolderFoot(view);
        } else if (viewType == Constants.TYPE_TWO) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_zhihu, parent, false);
            return new ViewHolderType2(view);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderType1) {
            setDataType1((ViewHolderType1) holder, position);
        } else if (holder instanceof ViewHolderFoot) {
            setDataFoot((ViewHolderFoot) holder);
        } else if (holder instanceof ViewHolderType2) {
            setDataType2((ViewHolderType2) holder, position);
        }
    }


    private void setDataType1(ViewHolderType1 holder, final int position) {

        List<String> mImages = new ArrayList<>();
        List<String> mTitles = new ArrayList<>();
        for (int i = 0, length = mTopDatas.size(); i < length; i++) {
            ZhihuBean.TopStoriesBean bean = mTopDatas.get(i);
            mImages.add(bean.image);
            mTitles.add(bean.title);
        }


        holder.mItemBanner.setImages(mImages)
                .setBannerTitles(mTitles)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
//                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setIndicatorGravity(BannerConfig.CENTER)
                .setDelayTime(5000)
                .setImageLoader(new ImageUtil.GlideImageLoader())
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        if (mListener != null) {
                            mListener.onItemHeaderClick(position);
                        }
                    }
                })
                .start();
    }

    private void setDataType2(ViewHolderType2 holder, final int position) {
        ZhihuBean.StoriesBean bean = mDatas.get(position - 1);
        String imageUrl = bean.images.get(0);

        ImageUtil.getInstance().displayImage(mContext, imageUrl, holder.mItemIconIv);

        holder.mItemContentTv.setText(TextUtils.isEmpty(bean.title) ? "" : bean.title);

        holder.mItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(v, position);
                }
            }
        });


    }

    //设置底部foot
    private void setDataFoot(ViewHolderFoot holder) {
        if (isAllLoad) {
            holder.mItemFootPb.setVisibility(View.GONE);
            holder.mItemFootTv.setText("所有数据已经加载完");
        } else {
            holder.mItemFootPb.setVisibility(View.VISIBLE);
            holder.mItemFootTv.setText("正在加载...");
        }
    }

    @Override
    public int getItemCount() {

        return mDatas == null ? 0 : mDatas.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return Constants.TYPE_ONE;
        }
        if (position + 1 == getItemCount()) {
            return Constants.TYPE_FOOT;
        }
        return Constants.TYPE_TWO;
    }

    public OnItemClickListener mListener;

    public interface OnItemClickListener {

        void onItemHeaderClick(int position);

        void onItemClick(View view, int position);

    }

    public void setOnItemClickListener(OnItemClickListener l) {
        mListener = l;
    }


    static class ViewHolderFoot extends RecyclerView.ViewHolder {
        @BindView(R.id.item_foot_pb)
        ProgressWheel mItemFootPb;
        @BindView(R.id.item_foot_tv)
        TextView mItemFootTv;

        ViewHolderFoot(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    static class ViewHolderType1 extends RecyclerView.ViewHolder {
        @BindView(R.id.item_banner)
        Banner mItemBanner;

        ViewHolderType1(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolderType2 extends RecyclerView.ViewHolder {
        @BindView(R.id.item_icon_iv)
        ImageView mItemIconIv;
        @BindView(R.id.item_content_tv)
        TextView mItemContentTv;
        @BindView(R.id.item_layout)
        LinearLayout mItemLayout;

        ViewHolderType2(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
