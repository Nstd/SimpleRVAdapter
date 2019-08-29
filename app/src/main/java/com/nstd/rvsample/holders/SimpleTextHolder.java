package com.nstd.rvsample.holders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.nstd.rvsample.R;
import com.nstd.rvadapter.SimpleHolderLayout;
import com.nstd.rvadapter.SimpleRVHolder;

/**
 * Created by Nstd on 2019-08-26 11:08.
 */
@SimpleHolderLayout(R.layout.text_holder_view)
public class SimpleTextHolder extends SimpleRVHolder<String> {

    public static final String CONF_SHOW_INDEX = "conf_show_index";

    TextView mTextView;
    Boolean mShowIndex;

    public SimpleTextHolder(@NonNull View view) {
        super(view);

        mTextView = findViewById(R.id.tv_text);
    }

    @Override
    public void bindData(Context ctx) {
        mShowIndex = getConfig(CONF_SHOW_INDEX, false);

        int mode = (getAdapterPosition() + 1) % 3;

        switch (mode) {
            case 0:
                removeItemClickListener();
                removeItemLongClickListener();
                break;
            case 1:
                removeItemLongClickListener();
                break;
            case 2:
                removeItemClickListener();
                break;
        }

        String prefix = mShowIndex ? getAdapterPosition() + " " : "";
        mTextView.setText(prefix + mItemData);
    }
}
