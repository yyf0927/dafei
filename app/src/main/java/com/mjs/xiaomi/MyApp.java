package com.mjs.xiaomi;

import android.app.Application;

/**
 * Created by dafei on 2017/9/8.
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppProfile.init(this);
    }
}
