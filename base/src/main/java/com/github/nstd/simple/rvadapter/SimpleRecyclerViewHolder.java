package com.github.nstd.simple.rvadapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Nstd on 17/6/12.
 */

public abstract class SimpleRecyclerViewHolder<D> extends RecyclerView.ViewHolder {

    protected Context mContext;
    protected D mItemData;
    protected int mPosition;
    protected ConfigListener mConfig;

    /**
     * !!! 如果有插入或者删除数据，做局部更新的时候，这些值可能没有被更新，所以必须要刷新整个数据源
     */
    private boolean mIsFirstItem;         //是否是第一个元素
    private boolean mIsLastItem;          //是否是最后一个元素
    private boolean mIsBorderTopItem;     //是否是viewType边界上的元素
    private boolean mIsBorderBottomItem;  //是否是viewType边界下的元素

    public SimpleRecyclerViewHolder(@NonNull View view){
        super(view);
        mContext = view.getContext();
    }

    public void setConfig(ConfigListener listener) {
        mConfig = listener;
    }

    public <E> E getConfig(String key) {
        if(mConfig == null) return null;
        return mConfig.get(getItemViewType(), getAdapterPosition(), key);
    }

    public <E> E getConfig(String key, E defaultValue) {
        E value = getConfig(key);
        return value == null ? defaultValue : value;
    }

    public void setData(D data, int position) {
        mItemData = data;
        mPosition = position;
        bindData(itemView.getContext());
    }

    public void removeItemClickListener() {
        if(itemView != null) {
            itemView.setOnClickListener(null);
        }
    }

    public void removeItemLongClickListener() {
        if(itemView != null) {
            itemView.setOnLongClickListener(null);
        }
    }

    public abstract void bindData(Context ctx);

    protected String getString(int resId) {
        return mContext.getResources().getString(resId);
    }

    protected String getString(int resId, Object... formatArgs) {
        return mContext.getResources().getString(resId, formatArgs);
    }

    protected String[] getStringArray(int resId){
        return mContext.getResources().getStringArray(resId);
    }

    protected Drawable getDrawable(int resId) {
        return mContext.getResources().getDrawable(resId);
    }

    protected int getColor(int resId) {
        return mContext.getResources().getColor(resId);
    }

    public void setIsFirstItem(boolean isFirstItem) {
        this.mIsFirstItem = isFirstItem;
    }

    public void setIsLastItem(boolean isLastItem) {
        this.mIsLastItem = isLastItem;
    }

    public void setIsBorderTopItem(boolean isBorderTopItem) {
        this.mIsBorderTopItem = isBorderTopItem;
    }

    public void setIsBorderBottomItem(boolean isBorderBottomItem) {
        this.mIsBorderBottomItem = isBorderBottomItem;
    }

    public boolean isFirstItem() {
        return mIsFirstItem;
    }

    public boolean isLastItem() {
        return mIsLastItem;
    }

    public boolean isBorderTopItem() {
        return mIsBorderTopItem;
    }

    public boolean isBorderBottomItem() {
        return mIsBorderBottomItem;
    }
}
