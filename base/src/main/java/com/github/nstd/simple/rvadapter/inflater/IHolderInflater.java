package com.github.nstd.simple.rvadapter.inflater;

import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by maoting on 2019-08-23 15:05.
 */
public interface IHolderInflater {

    InflateResult get(LayoutInflater inflater, int layoutResId, ViewGroup parent);

    boolean canHandle(Class holderClazz);
}
