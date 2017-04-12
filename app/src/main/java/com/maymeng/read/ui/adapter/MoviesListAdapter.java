package com.maymeng.read.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maymeng.read.R;
import com.maymeng.read.api.Constants;
import com.maymeng.read.bean.TopMoviesBean;
import com.maymeng.read.ui.activity.MoviesDetailActivity;
import com.maymeng.read.utils.DataFormatUtil;
import com.maymeng.read.utils.ImageUtil;
import com.maymeng.read.view.ProgressWheel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by  leijiaxq
 * Date       2017/3/15 11:27
 * Describe
 */
public class MoviesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<TopMoviesBean.SubjectsBean> mDatas;
    public boolean isAllLoad = false;


    public MoviesListAdapter(Context context, List<TopMoviesBean.SubjectsBean> datas) {
        mContext = context;
        mDatas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == Constants.TYPE_ONE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movies_list_type1, parent, false);
            return new ViewHolderType1(view);
        } else if (viewType == Constants.TYPE_FOOT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foot_layout, parent, false);
            return new ViewHolderFoot(view);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderType1) {
            setDataType1((ViewHolderType1) holder, position);
        } else if (holder instanceof ViewHolderFoot) {
            setDataFoot((ViewHolderFoot) holder);
        }
    }


    private void setDataType1(ViewHolderType1 holder, final int position) {
        if (mDatas.size() == 0) {
            return;
        }
        final TopMoviesBean.SubjectsBean bean = mDatas.get(position);

        ImageUtil.getInstance().displayImage(mContext,bean.images.large,holder.mItemIconIv);
        holder.mItemNameTv.setText(TextUtils.isEmpty(bean.title)?"":bean.title);
        String formatScore = DataFormatUtil.getFormatInstance2().format(bean.rating.average);
        holder.mItemScoreTv.setText("评分："+formatScore);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MoviesDetailActivity.class);
                intent.putExtra("movies_id", bean.id);
                intent.putExtra("movies_url", bean.images.medium);
                mContext.startActivity(intent);
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

        return mDatas == null ? 0 : mDatas.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return Constants.TYPE_FOOT;
        }
        return Constants.TYPE_ONE;
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
        @BindView(R.id.item_icon_iv)
        ImageView mItemIconIv;
        @BindView(R.id.item_name_tv)
        TextView mItemNameTv;
        @BindView(R.id.item_score_tv)
        TextView mItemScoreTv;

        ViewHolderType1(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
