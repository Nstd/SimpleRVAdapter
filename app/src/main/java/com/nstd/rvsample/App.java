package com.nstd.rvsample;

import android.app.Application;

import com.nstd.rvsample.tools.ToolManager;

/**
 * Created by Nstd on 2019-08-26 14:46.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ToolManager.mContext = this;
    }
}
