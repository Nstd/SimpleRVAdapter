package com.nstd.rvadapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.nstd.simple.rvadapter.core.BuildConfig;
import com.github.nstd.simple.rvadapter.core.R;
import com.nstd.tools.CheckUtils;
import com.nstd.tools.LogUtils;

import java.util.List;

/**
 * Created by Nstd on 17/6/12.
 */

public class SimpleRVAdapter extends RecyclerView.Adapter<SimpleRVHolder> {

    private static final String TAG = "SimpleRVAdapter";

    protected List mData;
    protected RecyclerViewHolderFactory mFactory;
    protected OnItemClickListener mListener;
    protected OnItemLongClickListener mLongClickListener;

    public void setData(List data) {
        mData = data;
        notifyDataSetChanged();
    }

    public List getData() {
        return mData;
    }

    public void prepend(List data) {
        int size = CheckUtils.size(data);
        if(size > 0) {
            mData.addAll(0, data);
            notifyItemRangeInserted(0, size);
        }
    }

    public void append(List data) {
        int positionStart = CheckUtils.size(mData);
        int appendCount = CheckUtils.size(data);
        if(data != null && appendCount > 0) {
            mData.addAll(data);
        }
        if(positionStart > 0 && appendCount > 0) {
            notifyItemRangeInserted(positionStart, appendCount);
        } else {
            notifyDataSetChanged();
        }
    }

    public void remove(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public void insert(int position, List data) {
        int insertCount = CheckUtils.size(data);
        if(position >= 0 && position < mData.size()) {
            mData.addAll(position, data);
            if(insertCount == 1) {
                notifyItemInserted(position);
            } else {
                notifyItemRangeInserted(position, insertCount);
            }
        }
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    public SimpleRVAdapter setFactory(RecyclerViewHolderFactory factory) {
        this.mFactory = factory;
        return this;
    }

    @Override
    public int getItemViewType(int position) {
        return mFactory == null ? super.getItemViewType(position) : mFactory.getItemViewType(mData.get(position), position);
    }

    @Override
    public SimpleRVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mFactory != null ? mFactory.create(parent, viewType) : null;
    }

    @Override
    public void onBindViewHolder(SimpleRVHolder holder, int position) {
        holder.itemView.setTag(R.id.recycler_view_item_holder, holder);
        if(mListener != null) {
            holder.itemView.setOnClickListener(mItemClickListener);
        }
        if(mLongClickListener != null) {
            holder.itemView.setOnLongClickListener(mItemLongClickListener);
        }
        holder.setIsFirstItem(isFirst(position));
        holder.setIsLastItem(isLast(position));
        holder.setIsBorderTopItem(isBorderTop(position));
        holder.setIsBorderBottomItem(isBorderBottom(position));
        holder.setData(mData.get(position), position);
    }

    public boolean isFirst(int position) {
        return position == 0;
    }

    public boolean isLast(int position) {
        return CheckUtils.size(mData) - 1 == position;
    }

    public boolean isBorderTop(int position) {
        if(isFirst(position)) return true;
        return getItemViewType(position - 1) != getItemViewType(position);
    }

    public boolean isBorderBottom(int position) {
        if(isLast(position)) return true;
        return getItemViewType(position) != getItemViewType(position + 1);
    }

    public SimpleRVAdapter setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
        return this;
    }

    public SimpleRVAdapter setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.mLongClickListener = listener;
        return this;
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    private View.OnClickListener mItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                SimpleRVHolder holder = (SimpleRVHolder) v.getTag(R.id.recycler_view_item_holder);
                int position = holder.getLayoutPosition();
                if(mListener != null) {
                    if(position >= 0 && position < CheckUtils.size(mData)) {
                        mListener.onClick(v, position);
                    } else {
                        LogUtils.w(TAG, "invalidate click position:" + position + ", size=" + CheckUtils.size(mData));
                    }
                }
            } catch (Exception e) {
                LogUtils.e(e);
                if(BuildConfig.DEBUG) {
                    Toast.makeText(v.getContext(), "itemClick exception: \n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    private View.OnLongClickListener mItemLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            try {
                SimpleRVHolder holder = (SimpleRVHolder) v.getTag(R.id.recycler_view_item_holder);
                int position = holder.getLayoutPosition();
                if(mLongClickListener != null) {
                    if(position >= 0 && position < CheckUtils.size(mData)) {
                        mLongClickListener.onLongClick(v, position);
                    } else {
                        LogUtils.w(TAG, "invalidate long click position:" + position + ", size=" + CheckUtils.size(mData));
                    }
                }
            } catch (Exception e) {
                LogUtils.e(e);
            }
            return false;
        }
    };
}
