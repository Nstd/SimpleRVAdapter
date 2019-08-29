package com.nstd.rvsample.holders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.nstd.rvsample.R;
import com.nstd.rvadapter.SimpleHolderLayout;
import com.nstd.rvadapter.SimpleRVHolder;

/**
 * Created by Nstd on 2019-08-26 10:22.
 */
@SimpleHolderLayout(R.layout.text_holder_view)
public class MainItemHolder extends SimpleRVHolder<TextItem> {

    TextView mTextView;

    public MainItemHolder(@NonNull View view) {
        super(view);

        mTextView = findViewById(R.id.tv_text);
    }

    @Override
    public void bindData(Context ctx) {
        mTextView.setText(mItemData.getTextItem());
    }
}
