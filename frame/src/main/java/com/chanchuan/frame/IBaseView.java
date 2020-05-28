package com.chanchuan.frame;

public interface IBaseView<D> {
    /**
     * 成功回调
     *
     * @param whichApi 成功接口的标识（哪个接口成功了）
     * @param loadType 类型的回调（正常加载，刷新，加载更多）
     * @param ds       一般是实体类的回调，但为了保证框架的灵活性，确保其他一些数据的偶发性回调，故未将长度写死
     */
    void onSuccess(int whichApi, int loadType, D... ds);

    /**
     * 失败的回调
     *
     * @param whichApi  是哪个接口失败的标识
     * @param throwable 失败的具体描述
     */
    void onFailed(int whichApi, Throwable throwable);
}
