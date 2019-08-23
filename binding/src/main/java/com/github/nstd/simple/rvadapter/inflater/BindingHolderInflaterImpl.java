package com.github.nstd.simple.rvadapter.inflater;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.nstd.simple.rvadapter.SimpleRVBindingHolder;

/**
 * Created by Nstd on 2019-08-23 15:09.
 */
public class BindingHolderInflaterImpl implements IHolderInflater {
    @Override
    public InflateResult get(LayoutInflater inflater, int layoutResId, ViewGroup parent) {
        InflateResult result = new InflateResult();
        try {
            result.targetObject = DataBindingUtil.inflate(inflater, layoutResId, parent, false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        result.targetClass = result.targetObject.getClass();

        String name = result.targetClass.getSimpleName();
        if(name.endsWith("BindingImpl")) {
            result.targetClass = result.targetClass.getSuperclass();
        }

        return null;
    }

    @Override
    public boolean canHandle(Class holderClazz) {
        return SimpleRVBindingHolder.class.isAssignableFrom(holderClazz);
    }
}
