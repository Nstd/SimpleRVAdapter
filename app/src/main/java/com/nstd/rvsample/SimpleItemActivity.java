package com.nstd.rvsample;

import android.view.View;
import android.widget.CheckBox;

import com.nstd.rvsample.R;
import com.nstd.rvadapter.ConfigListener;
import com.nstd.rvadapter.OnItemClickListener;
import com.nstd.rvadapter.OnItemLongClickListener;
import com.nstd.rvadapter.RecyclerViewHolderFactory;
import com.nstd.rvadapter.SimpleRVHolderFactory;
import com.nstd.rvsample.holders.SimpleTextHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nstd on 2019-08-26 11:03.
 */
public class SimpleItemActivity extends BaseActivity {

    private boolean mIsShowIndex = false;

    @Override
    protected String getTitleStr() {
        return "Simple Item Sample";
    }

    @Override
    protected RecyclerViewHolderFactory getFactory() {
        return new SimpleRVHolderFactory(SimpleTextHolder.class)
                .setConfigListener(new ConfigListener() {
                    @Override
                    public Object get(int itemType, int position, String key) {
                        if(SimpleTextHolder.CONF_SHOW_INDEX.equals(key)) {
                            return mIsShowIndex;
                        }
                        return null;
                    }
                });
    }

    @Override
    protected void initSubViews() {
        View subViews = getLayoutInflater().inflate(R.layout.show_index_check_view, getTopLayer(), true);
        ((CheckBox) subViews.findViewById(R.id.check_box)).setOnCheckedChangeListener((buttonView, isChecked) -> {
            mIsShowIndex = isChecked;
            mAdapter.notifyDataSetChanged();
        });
    }

    @Override
    protected void loadData() {
        List<String> data = new ArrayList<>();
        String[] origin = {"I can be clicked", "I can be long clicked", "I'm disabled"};

        for(int i = 0; i < 10; i++) {
            for(String s : origin) {
                data.add(s);
            }
        }

        mAdapter.setData(data);
    }

    @Override
    protected OnItemClickListener getItemClickListener() {
        return (view, position) -> showMessage("click: " + position);
    }

    @Override
    protected OnItemLongClickListener getItemLongClickListener() {
        return (view, position) -> showMessage("long click: " + position);
    }
}
