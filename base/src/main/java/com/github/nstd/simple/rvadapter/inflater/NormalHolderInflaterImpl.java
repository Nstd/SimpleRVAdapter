package com.github.nstd.simple.rvadapter.inflater;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.nstd.simple.rvadapter.SimpleRecyclerViewHolder;

/**
 * Created by Nstd on 2019-08-23 15:11.
 */
public class NormalHolderInflaterImpl implements IHolderInflater {

    @Override
    public InflateResult get(LayoutInflater inflater, int layoutResId, ViewGroup parent) {
        InflateResult result = new InflateResult();
        result.targetObject = inflater.inflate(layoutResId, parent, false);
        result.targetClass = View.class;
        return result;
    }

    @Override
    public boolean canHandle(Class holderClazz) {
        return SimpleRecyclerViewHolder.class.isAssignableFrom(holderClazz);
    }
}
