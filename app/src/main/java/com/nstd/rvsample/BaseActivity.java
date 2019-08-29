package com.nstd.rvsample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nstd.rvsample.R;
import com.nstd.rvadapter.OnItemClickListener;
import com.nstd.rvadapter.OnItemLongClickListener;
import com.nstd.rvadapter.RecyclerViewHolderFactory;
import com.nstd.rvadapter.SimpleRVAdapter;

/**
 * Created by Nstd on 2019-08-26 10:11.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected SimpleRVAdapter mAdapter;

    protected abstract String getTitleStr();
    protected abstract RecyclerViewHolderFactory getFactory();
    protected abstract void loadData();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_recycler_view);

        setTitle(getTitleStr());

        initViews();
        loadData();
    }

    private void initViews() {
        mAdapter = new SimpleRVAdapter()
                .setFactory(getFactory())
                .setOnItemClickListener(getItemClickListener())
                .setOnItemLongClickListener(getItemLongClickListener());
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(getLayoutManager());
        recyclerView.setAdapter(mAdapter);

        initSubViews();
    }

    protected void initSubViews() {

    }

    protected LinearLayout getTopLayer() {
        return findViewById(R.id.top_layer);
    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }

    protected OnItemClickListener getItemClickListener() {
        return null;
    }

    protected OnItemLongClickListener getItemLongClickListener() {
        return null;
    }

    protected void showMessage(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
