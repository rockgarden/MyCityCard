package com.eastcom.testaidl;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by rockgarden on 15/11/3.
 */
public class Adapter_Card extends RecyclerView.Adapter {

    // 重写的自定义ViewHolder
    class ViewHolder extends RecyclerView.ViewHolder
    {
        private View root;

        private TextView mTextView;

        private ImageView mImageView;

        public ViewHolder( View root )
        {
            super(root);
            mTextView = (TextView) root.findViewById(R.id.name);
            mImageView = (ImageView) root.findViewById(R.id.pic);
        }

        public TextView getmTextView() {
            return mTextView;
        }

        public ImageView getmImageView() {
            return mImageView;
        }
    }

    private List<RecyclerCardData> cardDatas;

    private Context mContext;

    public Adapter_Card( Context context , List<RecyclerCardData> cardDatas) {
        this.mContext = context;
        this.cardDatas = cardDatas;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int viewType )
    {
        // 给ViewHolder设置布局文件
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item_cardview, viewGroup, false);
        return new ViewHolder(v);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, int i )
    {
        ViewHolder viewHolder = (ViewHolder) holder;
        // 给ViewHolder设置元素
        RecyclerCardData cardData = cardDatas.get(i);
        viewHolder.getmTextView().setText(cardData.name);
        viewHolder.getmImageView().setImageDrawable(mContext.getResources().getDrawable(cardData.getImageResourceId(mContext)));
        //viewHolder.getmImageView().setImageDrawable(mContext.getDrawable(R.drawable.p1));
    }

    @Override
    public int getItemCount()
    {
        // 返回数据总数
        return cardDatas == null ? 0 : cardDatas.size();
    }


}
