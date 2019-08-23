package com.github.nstd.simple.rvadapter;

import android.view.ViewGroup;

import com.github.nstd.simple.rvadapter.SimpleRecyclerViewHolder;

/**
 * Created by Nstd on 17/6/12.
 */

public interface RecyclerViewHolderFactory<D> {
    int getItemViewType(D data, int position);
    <D> SimpleRecyclerViewHolder<D> create(ViewGroup parent, int viewType);
}
