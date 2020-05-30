package com.chanchuan.myapplication.base;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {
    private static MyApp mMyApp;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mMyApp = this;
    }

    public MyApp getApplication() {
        return mMyApp;
    }

    public static Context getContext() {
        return mMyApp.getApplication();
    }
}
