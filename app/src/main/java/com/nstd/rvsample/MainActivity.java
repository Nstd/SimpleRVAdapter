package com.nstd.rvsample;

import android.content.Intent;

import com.nstd.rvadapter.OnItemClickListener;
import com.nstd.rvadapter.RecyclerViewHolderFactory;
import com.nstd.rvadapter.SimpleRVHolderFactory;
import com.nstd.rvsample.holders.MainItemHolder;
import com.nstd.rvsample.holders.TextItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    List<Item> mData;

    @Override
    protected String getTitleStr() {
        return "SimpleRVAdapter Sample";
    }

    @Override
    protected RecyclerViewHolderFactory getFactory() {
        return new SimpleRVHolderFactory(MainItemHolder.class);
    }

    @Override
    protected OnItemClickListener getItemClickListener() {
        return (view, position) -> mData.get(position).action.trigger();
    }

    @Override
    protected void loadData() {
        mData = new ArrayList<>();
        mData.add(new Item("Simple Item", () -> showSampleActivity(SimpleItemActivity.class)));
        mData.add(new Item("DataBinding Item", () -> showSampleActivity(DataBindingItemActivity.class)));
        mData.add(new Item("Multi Type", () -> showSampleActivity(MultiTypeActivity.class)));

        mAdapter.setData(mData);
    }

    private void showSampleActivity(Class sampleActClass) {
        Intent intent = new Intent(this, sampleActClass);
        startActivity(intent);
    }


    public static class Item implements TextItem {
        String value;
        Action action;
        public Item(String value, Action action) {
            this.value = value;
            this.action = action;
        }

        @Override
        public String getTextItem() {
            return value;
        }
    }

    public interface Action {
        void trigger();
    }
}
