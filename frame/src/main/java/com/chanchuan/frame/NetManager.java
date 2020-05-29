package com.chanchuan.frame;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetManager {
    private NetManager() {
    }

    public static NetManager netManager;

    public static NetManager getInstance() {
        if (netManager == null) {
            synchronized (NetManager.class)
            netManager = new NetManager();
        }
        return netManager;
    }

    public <T> ApiService getService(T... ts) {
        String baseUrl = ServerAddressConfig.BASE_URL;
        if (ts != null && ts.length != 0) {
            baseUrl = (String) ts[0];
        }
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ApiService.class);
    }
}
