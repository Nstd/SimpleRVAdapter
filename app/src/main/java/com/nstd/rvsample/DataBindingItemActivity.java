package com.nstd.rvsample;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.nstd.rvsample.R;
import com.nstd.rvsample.databinding.ActRecyclerViewBinding;
import com.nstd.rvadapter.RecyclerViewHolderFactory;
import com.nstd.rvadapter.SimpleRVHolderFactory;
import com.nstd.rvsample.holders.DataBindingTextHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nstd on 2019-08-26 11:45.
 */
public class DataBindingItemActivity extends BaseActivity {

    private int mPrependIndex = 0;
    private int mAppendIndex  = 0;
    private int mInsertIndex  = 0;
    ActRecyclerViewBinding mBinding;

    @Override
    protected String getTitleStr() {
        return "DataBinding Item Sample";
    }

    @Override
    protected RecyclerViewHolderFactory getFactory() {
        return new SimpleRVHolderFactory(DataBindingTextHolder.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.bind(findViewById(R.id.root));
    }

    @Override
    protected void initSubViews() {
        View subViews = getLayoutInflater().inflate(R.layout.append_and_prepend_view, getTopLayer(), true);
        subViews.findViewById(R.id.btn_prepend).setOnClickListener(v -> prependItems());
        subViews.findViewById(R.id.btn_append).setOnClickListener(v -> appendItems());
        subViews.findViewById(R.id.btn_insert).setOnClickListener(v -> insertItems());
    }

    @Override
    protected void loadData() {
        List<String> data = new ArrayList<>();

        for(int i = 0; i < 20; i++) {
            data.add("Item " + i);
        }

        mAdapter.setData(data);
    }

    private void prependItems() {
        mAdapter.prepend(getItems("Prepend", mPrependIndex++, true));
        mBinding.recyclerView.scrollToPosition(0);
    }

    private void appendItems() {
        mAdapter.append(getItems("Append", mAppendIndex++, false));
        mBinding.recyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    private void insertItems() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) mBinding.recyclerView.getLayoutManager();
        int index = layoutManager.findFirstCompletelyVisibleItemPosition();
        mAdapter.insert(index + 1, getItems("Insert", mInsertIndex++, false));
    }

    private List<String> getItems(String action, int index, boolean isReversed) {
        List<String> list = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            String value = action + " Item " + index;
            if(isReversed) {
                list.add(0, value);
            } else {
                list.add(value);
            }
        }
        return list;
    }
}
