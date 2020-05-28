package com.chanchuan.frame;

public class BasePresenter implements IBasePresenter {
    private IBaseView view;
    private IBaseModel model;

    public BasePresenter(IBaseView view, IBaseModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void getData(int whichApi, Object... objects) {
        model.getData(this, whichApi, objects);
    }

    @Override
    public void onSuccess(int whichApi, int loadType, Object... objects) {
        view.onSuccess(whichApi, loadType, objects);
    }

    @Override
    public void onFailed(int whichApi, Throwable throwable) {
        view.onFailed(whichApi, throwable);
    }
}
