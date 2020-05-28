package com.chanchuan.frame;

public interface IBasePresenter<P> extends IBaseView {
    //由中间层来进行网络请求，并将结果返回view层
    void getData(int whichApi, P... ps);

}
