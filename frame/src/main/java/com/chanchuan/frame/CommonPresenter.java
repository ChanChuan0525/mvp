package com.chanchuan.frame;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class CommonPresenter<V extends ICommonView, M extends ICommonModel> implements ICommonPresenter {
    private SoftReference<V> mView;
    private SoftReference<M> mModel;
    private List<Disposable> mDisposableList;

    /**
     * 构造中，接受View和Model的对象
     *
     * @param pView
     * @param pModel
     */
    public CommonPresenter(V pView, M pModel) {
        mDisposableList = new ArrayList<>();
        mView = new SoftReference<>(pView);     //软引用包裹，当内存不足的时候确保能够回收，避免内存溢出
        mModel = new SoftReference<>(pModel);
    }

    /**
     * 发起普通的网络请求
     *
     * @param whichApi 接口表示
     * @param objects  作为公共封装的方法，并不知道未来穿的是什么类型的数据，也不知道会传递多少个，所有通过泛型可变参数来声明形参
     */
    @Override
    public void getData(int whichApi, Object... objects) {
        if (mModel != null && mModel.get() != null)
            mModel.get().getData(this, whichApi, objects);
    }

    /**
     * 将所有网络请求的订阅关系，同意到中间层的集合，用于View销毁时，统一处理
     *
     * @param pDisposable
     */
    @Override
    public void addObserver(Disposable pDisposable) {
        if (mDisposableList == null) return;
        mDisposableList.add(pDisposable);
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

    /**
     * 当activity被销毁时，代表已经没有数据要展示啦。所以首先把该页面的所有网络请求终止，以免gc回收时发现View仍被持有不能回收导致内存泄漏
     * 当然这个泄露很短暂，当gc下一次检测到该对象时，网络任务如果已经完成，并不影响activity的回收
     * <p>
     * 在MVP使用中，为了显示视图和数据的解耦，我们通过中间层presenter来进行双向绑定和处理，但是当View销毁时，由于P层仍然持有View的引用，
     * 也可能会发生View不能被回收导致内存泄漏的情况，所以当页面销毁时，当presenter同view和model进行解耦。
     */
    public void clear() {
        for (Disposable disposable : mDisposableList) {
            if (disposable != null && !disposable.isDisposed()) disposable.dispose();
        }
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
