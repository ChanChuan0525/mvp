package com.chanchuan.myapplication;

import androidx.recyclerview.widget.RecyclerView;

import com.chanchuan.data.TestInfo;
import com.chanchuan.frame.ApiConfig;
import com.chanchuan.frame.CommonPresenter;
import com.chanchuan.frame.ICommonModel;
import com.chanchuan.frame.ICommonView;
import com.chanchuan.frame.LoadTypeConfig;
import com.chanchuan.frame.ParamHashMap;
import com.chanchuan.myapplication.base.BaseMVPActivity;
import com.chanchuan.myapplication.model.TestModel;
import com.chanchuan.myapplication.adapter.TestAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.Map;

import butterknife.BindView;

public class MainActivity extends BaseMVPActivity implements ICommonView {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.smart)
    SmartRefreshLayout smart;
    int pageId = 0;
    private CommonPresenter basePresenter;
    private TestAdapter testAdapter;
    private Map<String, Object> params;


    @Override
    protected void setUpData() {
        basePresenter.getData(ApiConfig.TEST_GET, LoadTypeConfig.NORMAL, params, pageId);
    }

    @Override
    protected void setUpView() {
        params = new ParamHashMap()
                .add("c", "api")
                .add("a", "getList");

        TestModel testModel = new TestModel();
        basePresenter = new CommonPresenter(this, testModel);
        initRecyclerView(recycler, smart, mode -> {
            if (mode == LoadTypeConfig.REFRESH) {
                pageId = 0;
                basePresenter.getData(ApiConfig.TEST_GET, LoadTypeConfig.REFRESH, params, pageId);
            } else {
                pageId++;
                basePresenter.getData(ApiConfig.TEST_GET, LoadTypeConfig.MORE, params, pageId);
            }
        });

        testAdapter = new TestAdapter(this);
        recycler.setAdapter(testAdapter);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected ICommonModel setModel() {
        return new TestModel();
    }

    @Override
    protected void netSuccess(int whichApi, int loadType, Object[] objects) {

        switch (whichApi) {
            case ApiConfig.TEST_GET:
                if (loadType == LoadTypeConfig.MORE) {
                    smart.finishLoadMore();
                    testAdapter.setDatas(((TestInfo) objects[0]).datas);

                } else if (loadType == LoadTypeConfig.REFRESH) {
                    smart.finishRefresh();
                    testAdapter.refreshDatas(((TestInfo) objects[0]).datas);
                } else {
                    testAdapter.setDatas(((TestInfo) objects[0]).datas);
                }
                break;
        }
    }
}
