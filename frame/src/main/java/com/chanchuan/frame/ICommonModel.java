package com.chanchuan.frame;

public interface ICommonModel<T> {
    /**
     * 用户model层来执行耗时任务
     * @param presenter
     * @param which
     * @param params
     */
    void getData(ICommonPresenter presenter, int which, T... params);
}
