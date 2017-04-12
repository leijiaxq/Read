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
import com.maymeng.read.bean.PerformerBean;
import com.maymeng.read.ui.activity.WebViewActivity;
import com.maymeng.read.utils.ImageUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by  leijiaxq
 * Date        2017/4/11 10:05
 * Describe
 */
public class PerformerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<PerformerBean> mDatas;
    private Context mContext;

    public PerformerAdapter(Context context, List<PerformerBean> datas) {
        mDatas = datas;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movies_performer, parent, false);

        return new ViewHolderType1(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolderType1 viewHolderType1 = (ViewHolderType1) holder;

         final PerformerBean bean = mDatas.get(position);
        ImageUtil.getInstance().displayImage(mContext,bean.icon,viewHolderType1.mItemIconIv);
        viewHolderType1.mItemNameTv.setText(TextUtils.isEmpty(bean.name)?"":bean.name);
        viewHolderType1.mItemTypeTv.setText(TextUtils.isEmpty(bean.Occupation)?"":bean.Occupation);

        viewHolderType1.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra("url", bean.url);
                intent.putExtra("name", bean.name);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    static class ViewHolderType1 extends RecyclerView.ViewHolder {
        @BindView(R.id.item_icon_iv)
        ImageView mItemIconIv;
        @BindView(R.id.item_name_tv)
        TextView mItemNameTv;
        @BindView(R.id.item_type_tv)
        TextView mItemTypeTv;

        ViewHolderType1(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
