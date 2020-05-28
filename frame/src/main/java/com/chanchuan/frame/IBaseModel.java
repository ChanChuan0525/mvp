package com.chanchuan.frame;

public interface IBaseModel<T> {
    /**
     * 用户model层来执行耗时任务
     * @param presenter
     * @param which
     * @param params
     */
    void getData(IBasePresenter presenter, int which, T... params);
}
