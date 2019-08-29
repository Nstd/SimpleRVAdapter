package com.nstd.rvsample;

import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nstd.rvadapter.OnItemClickListener;
import com.nstd.rvadapter.RecyclerViewHolderFactory;
import com.nstd.rvadapter.SimpleRVHolderFactory;
import com.nstd.rvsample.holders.MultiItemHolder;
import com.nstd.rvsample.holders.TitleHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Nstd on 2019-08-26 14:20.
 */
public class MultiTypeActivity extends BaseActivity {

    List<Item> mData = new ArrayList<>();

    @Override
    protected String getTitleStr() {
        return "Multi Type Sample";
    }

    @Override
    protected RecyclerViewHolderFactory getFactory() {
        return new SimpleRVHolderFactory()
                .addViewTypeInfo(TitleHolder.class) //use @SimpleHolderLayout to set ItemType
                .addViewTypeInfo(Item.TYPE_ITEM, MultiItemHolder.class) //or just specify one by addViewTypeInfo()
                .setViewTypeGetter((SimpleRVHolderFactory.ViewTypeClassifier<Item>) (data, position) -> data.type);
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                return mData.get(i).type == Item.TYPE_TITLE ? 3 : 1;
            }
        });
        return manager;
    }

    @Override
    protected OnItemClickListener getItemClickListener() {
        return (view, position) -> showMessage("Click: " + mData.get(position).value);
    }

    @Override
    protected void loadData() {
        for(int i = 0; i < 10; i++) {
            Random random = new Random();
            int r = random.nextInt(256);
            int g = random.nextInt(256);
            int b = random.nextInt(256);
            int color = Color.rgb(r, g, b);
            mData.add(Item.getTitle("Title " + i, color));
            for(int j = 0; j < 10; j++) {
                mData.add(Item.getItem("Item " + i + "" + j, color));
            }
        }
        mAdapter.setData(mData);
    }

    public static class Item {
        public static final int TYPE_TITLE = 0;
        public static final int TYPE_ITEM  = 1;

        public int type;
        public String value;
        public int color;

        public Item(int type, String value) {
            this.type = type;
            this.value = value;
        }

        public Item(int type, String value, int color) {
            this(type, value);
            this.color = color;
        }

        public static Item getTitle(String title, int color) {
            return new Item(TYPE_TITLE, title, color);
        }

        public static Item getItem(String value, int color) {
            return new Item(TYPE_ITEM, value, color);
        }
    }
}
