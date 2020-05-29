package com.chanchuan.frame;

import java.lang.ref.SoftReference;

public class CommonPresenter<V extends ICommonView, M extends ICommonModel> implements ICommonPresenter {
    private SoftReference<V> mView;
    private SoftReference<M> mModel;

    public CommonPresenter(V pView, M pModel) {
        mView = new SoftReference<>(pView);
        mModel = new SoftReference<>(pModel);
    }

    @Override
    public void getData(int whichApi, Object... objects) {
        if (mModel != null && mModel.get() != null)
            mModel.get().getData(this, whichApi, objects);
    }

    @Override
    public void onSuccess(int whichApi, int loadType, Object... objects) {
        if (mView != null && mView.get() != null)
            mView.get().onSuccess(whichApi, loadType, objects);
    }

    @Override
    public void onFailed(int whichApi, Throwable throwable) {
        if (mView != null && mView.get() != null) mView.get().onFailed(whichApi, throwable);
    }

    public void clear() {
        if (mView != null) {
            mView.clear();
            mView = null;
        }
        if (mModel != null) {
            mModel.clear();
            mModel = null;
        }
    }
}
