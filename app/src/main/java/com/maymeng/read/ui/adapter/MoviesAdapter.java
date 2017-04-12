package com.maymeng.read.ui.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.maymeng.read.bean.HotMoviesBean;
import com.maymeng.read.ui.activity.MoviesDetailActivity;
import com.maymeng.read.ui.activity.MoviesListActivity;
import com.maymeng.read.utils.DataFormatUtil;
import com.maymeng.read.utils.ImageUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by  leijiaxq
 * Date       2017/3/15 11:27
 * Describe
 */
public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<HotMoviesBean.SubjectsBean> mDatas;


    public MoviesAdapter(Context context, List<HotMoviesBean.SubjectsBean> datas) {
        mContext = context;
        mDatas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == Constants.TYPE_ONE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movies_type1, parent, false);
            return new ViewHolderType1(view);
        } else if (viewType == Constants.TYPE_TWO) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movies_type2, parent, false);
            return new ViewHolderType2(view);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderType1) {
            setDataType1((ViewHolderType1) holder, position);
        } else if (holder instanceof ViewHolderType2) {
            setDataType2((ViewHolderType2) holder, position);
        }
    }


    private void setDataType1(ViewHolderType1 holder, final int position) {
//        ImageUtil.getInstance().displayImage(mContext, Constants.IMAGE_MOVIES_URL, holder.mItemIconIv1);
//        ImageUtil.getInstance().displayImage(mContext, Constants.IMAGE_MOVIES_URL2, holder.mItemIconIv2);

        holder.mItemLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MoviesListActivity.class);
                intent.putExtra("index", 0);
                mContext.startActivity(intent);
            }
        });
        holder.mItemLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MoviesListActivity.class);
                intent.putExtra("index", 1);
                mContext.startActivity(intent);
            }
        });

    }

    private void setDataType2(ViewHolderType2 holder, final int position) {
        final HotMoviesBean.SubjectsBean bean = mDatas.get(position - 1);
        holder.mItemTitleTv.setText(TextUtils.isEmpty(bean.title)?"":bean.title);
        String director = "";
        for (int i = 0, length = bean.directors.size(); i < length; i++) {
            HotMoviesBean.DirectorsBean bean1 = bean.directors.get(i);
            if (i == length - 1) {
                director += bean1.name;
            } else {
                director += bean1.name +"/";
            }
        }
        holder.mItemDirectorTv.setText("导演："+director);

        String star = "";
        for (int i = 0, length = bean.casts.size(); i < length; i++) {
            HotMoviesBean.CastsBean bean2 = bean.casts.get(i);
            if (i == length - 1) {
                star += bean2.name;
            } else {
                star += bean2.name +"/";
            }
        }
        holder.mItemStarTv.setText("主演："+star);

        String type = "";
        for (int i = 0, length = bean.genres.size(); i < length; i++) {
            String str = bean.genres.get(i);
            if (i == length - 1) {
                type += str;
            } else {
                type += str +"/";
            }
        }
        holder.mItemTypeTv.setText("类型："+type);
        String formatScore = DataFormatUtil.getFormatInstance2().format(bean.rating.average);
        holder.mItemScoreTv.setText("评分："+formatScore);

        if (position == getItemCount()) {
            holder.mItemBottomLine.setVisibility(View.GONE);
        } else {
            holder.mItemBottomLine.setVisibility(View.VISIBLE);

        }

        ImageUtil.getInstance().displayImage(mContext,bean.images.large,holder.mItemIconIv);


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


    @Override
    public int getItemCount() {

        return mDatas == null ? 1 : mDatas.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return Constants.TYPE_ONE;
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


    static class ViewHolderType1 extends RecyclerView.ViewHolder {
        @BindView(R.id.item_icon_iv1)
        ImageView mItemIconIv1;
        @BindView(R.id.item_layout1)
        LinearLayout mItemLayout1;
        @BindView(R.id.item_icon_iv2)
        ImageView mItemIconIv2;
        @BindView(R.id.item_layout2)
        LinearLayout mItemLayout2;

        ViewHolderType1(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolderType2 extends RecyclerView.ViewHolder {
        @BindView(R.id.item_icon_iv)
        ImageView mItemIconIv;
        @BindView(R.id.item_title_tv)
        TextView mItemTitleTv;
        @BindView(R.id.item_director_tv)
        TextView mItemDirectorTv;
        @BindView(R.id.item_star_tv)
        TextView mItemStarTv;
        @BindView(R.id.item_type_tv)
        TextView mItemTypeTv;
        @BindView(R.id.item_score_tv)
        TextView mItemScoreTv;
        @BindView(R.id.item_bottom_line)
        View mItemBottomLine;

        ViewHolderType2(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
