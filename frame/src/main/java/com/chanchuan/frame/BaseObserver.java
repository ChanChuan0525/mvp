package com.chanchuan.frame;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver implements Observer {
    public Disposable mDisposable;

    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;

    }

    @Override
    public void onNext(Object o) {
        onSuccess(o);
        dispose();
    }

    protected abstract void onSuccess(Object o);

    @Override
    public void onError(Throwable e) {
        onFailed(e);
    }

    protected abstract void onFailed(Throwable e);

    @Override
    public void onComplete() {
        dispose();
    }

    public void dispose() {
        if (mDisposable != null && mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
