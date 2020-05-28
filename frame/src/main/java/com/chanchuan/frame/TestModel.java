package com.chanchuan.frame;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestModel implements IBaseModel {
    @Override
    public void getData(final IBasePresenter presenter, final int whichApi, final Object[] params) {
        final int loadType = (int) params[0];
        Map param = (Map) params[1];
        final int pageId = (int) params[2];
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://static.owspace.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofit.create(ApiService.class)
                .getInfo(param, pageId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {
                        presenter.onSuccess(whichApi, loadType, o);
                    }

                    @Override
                    public void onError(Throwable e) {
                        presenter.onFailed(whichApi, e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
