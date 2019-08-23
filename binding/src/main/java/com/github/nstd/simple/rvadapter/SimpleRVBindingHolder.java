package com.github.nstd.simple.rvadapter;

import android.databinding.ViewDataBinding;

import com.github.nstd.simple.rvadapter.SimpleRecyclerViewHolder;

/**
 * Created by Nstd on 2019-08-23 14:02.
 */

public abstract class SimpleRVBindingHolder<T extends ViewDataBinding, D> extends SimpleRecyclerViewHolder<D> {

    protected T mItemBinding;

    public SimpleRVBindingHolder(T binding) {
        super(binding.getRoot());
        mItemBinding = binding;
        mContext = binding.getRoot().getContext();
    }
}
