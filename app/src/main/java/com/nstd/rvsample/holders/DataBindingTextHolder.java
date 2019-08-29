package com.nstd.rvsample.holders;

import android.content.Context;

import com.nstd.rvsample.R;
import com.nstd.rvsample.databinding.TextHolderViewBinding;
import com.nstd.rvadapter.SimpleHolderLayout;
import com.nstd.rvadapter.SimpleRVBindingHolder;

/**
 * Created by Nstd on 2019-08-26 11:46.
 */
@SimpleHolderLayout(R.layout.text_holder_view)
public class DataBindingTextHolder extends SimpleRVBindingHolder<TextHolderViewBinding, String> {

    public DataBindingTextHolder(TextHolderViewBinding binding) {
        super(binding);
    }

    @Override
    public void bindData(Context ctx) {
        mItemBinding.tvText.setText(mItemData);
    }
}
