package com.github.nstd.simple.rvadapter;

/**
 * Created by Nstd on 17/6/12.
 */
public interface ConfigListener {

    <T> T get(int itemType, int position, String key);
}
