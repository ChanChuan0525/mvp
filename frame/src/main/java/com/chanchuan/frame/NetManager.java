package com.chanchuan.frame;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetManager {
    private NetManager() {
    }

    public static NetManager netManager;

    public static NetManager getInstance() {
        if (netManager == null) {
            synchronized (NetManager.class) {
                netManager = new NetManager();
            }
        }
        return netManager;
    }

    /**
     * @param ts  如果传baseURL，用的话传过来，没用的话用默认
     * @param <T>
     * @return
     */
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

    /**
     * 使用observer观察者，抽取出网络请求及切换线程的过程
     *
     * @param localTestInfo
     * @param pPresenter
     * @param whichApi
     * @param dataType
     * @param os
     * @param <T>
     * @param <O>
     */
    public <T, O> void netWork(Observable<T> localTestInfo, final ICommonPresenter pPresenter, final int whichApi, final int dataType, final O... os) {
        localTestInfo.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver() {
                    @Override
                    protected void onSuccess(Object o) {
                        pPresenter.onSuccess(whichApi, dataType, o, os);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        super.onSubscribe(d);
                        pPresenter.addObserver(d);
                    }

                    @Override
                    protected void onFailed(Throwable e) {
                        pPresenter.onFailed(whichApi, e);
                    }
                });
    }

    /**
     * 使用consumer观察者，抽取出网络请求及切换线程的过程
     *
     * @param localTestInfo
     * @param pPresenter
     * @param whichApi
     * @param dataType
     * @param os
     * @param <T>
     * @param <O>
     */
    public <T, O> void netWorkByConsumer(Observable<T> localTestInfo, final ICommonPresenter pPresenter, final int whichApi, final int dataType, final O... os) {
        Disposable disposable = localTestInfo.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<T>() {
                    @Override
                    public void accept(T t) throws Exception {
                        pPresenter.onSuccess(whichApi, dataType, t, os);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        pPresenter.onFailed(whichApi, throwable);
                    }
                });
        pPresenter.addObserver(disposable);
    }
}
