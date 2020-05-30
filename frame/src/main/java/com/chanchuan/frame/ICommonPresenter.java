package com.chanchuan.frame;

import io.reactivex.disposables.Disposable;

public interface ICommonPresenter<P> extends ICommonView {
    //由中间层来进行网络请求，并将结果返回view层

    /**
     * @param whichApi 1.当一个页面中有多个网络请求的时候，用于区分哪一个任务执行完成 2. 望文生义，方便后期维护时了解接口的作用
     * @param ps       1.一般用于网络请求时，如果参数是从View层动态获取的，需要通过这个可变长度的数组进行传递
     *                 2.另外如果有其他参数传递的需求，也可以加入到该数组中，但切记，再取出参数的时候一定要确保存放的时候的顺序
     */
    void getData(int whichApi, P... ps);

    void addObserver(Disposable pDisposable);
}
