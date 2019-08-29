package com.nstd.rvsample.holders;

import android.content.Context;

import com.nstd.rvsample.MultiTypeActivity;
import com.nstd.rvsample.R;
import com.nstd.rvsample.databinding.TextHolderViewBinding;
import com.nstd.rvadapter.SimpleHolderLayout;
import com.nstd.rvadapter.SimpleRVBindingHolder;

/**
 * Created by Nstd on 2019-08-26 14:28.
 */
@SimpleHolderLayout(value = R.layout.text_holder_view, viewType = MultiTypeActivity.Item.TYPE_TITLE)
public class TitleHolder extends SimpleRVBindingHolder<TextHolderViewBinding, MultiTypeActivity.Item> {

    public TitleHolder(TextHolderViewBinding binding) {
        super(binding);
    }

    @Override
    public void bindData(Context ctx) {
        mItemBinding.tvText.setText(mItemData.value);
        mItemBinding.tvText.setBackgroundColor(mItemData.color);
    }
}
