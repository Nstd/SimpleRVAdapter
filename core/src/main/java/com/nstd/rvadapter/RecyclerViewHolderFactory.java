package com.nstd.rvadapter;

import android.view.ViewGroup;

/**
 * Created by Nstd on 17/6/12.
 */

public interface RecyclerViewHolderFactory<D> {
    int getItemViewType(D data, int position);
    <D> SimpleRVHolder<D> create(ViewGroup parent, int viewType);
}
