package com.chanchuan.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chanchuan.data.TestInfo;
import com.chanchuan.frame.ApiConfig;
import com.chanchuan.frame.BasePresenter;
import com.chanchuan.frame.IBaseView;
import com.chanchuan.frame.LoadTypeConfig;
import com.chanchuan.frame.ParamHashMap;
import com.chanchuan.frame.TestModel;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IBaseView {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.smart)
    SmartRefreshLayout smart;
    int pageId = 0;
    private BasePresenter basePresenter;
    private TestAdapter testAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        TestModel testModel = new TestModel();
        basePresenter = new BasePresenter(this, testModel);
        final Map<String, Object> params = new ParamHashMap()
                .add("c", "api")
                .add("a", "getList");

        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        smart.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageId++;
                basePresenter.getData(ApiConfig.TEST_GET, LoadTypeConfig.MORE, params, pageId);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageId = 0;
                basePresenter.getData(ApiConfig.TEST_GET, LoadTypeConfig.REFRESH, params, pageId);
            }
        });

        testAdapter = new TestAdapter(this);
        recycler.setAdapter(testAdapter);
        basePresenter.getData(ApiConfig.TEST_GET, LoadTypeConfig.NORMAL, params, pageId);


    }
    

    private static final String TAG = "MainActivity";

    @Override
    public void onSuccess(int whichApi, int loadType, Object[] objects) {
        switch (whichApi) {
            case ApiConfig.TEST_GET:
                if (loadType == LoadTypeConfig.MORE) {
                    smart.finishLoadMore();
                    testAdapter.setDatas(((TestInfo) objects[0]).datas);
                } else if (loadType == LoadTypeConfig.REFRESH) {
                    smart.finishRefresh();
                    testAdapter.refreshDatas(((TestInfo) objects[0]).datas);
                }else {
                    testAdapter.setDatas(((TestInfo) objects[0]).datas);
                }
        }
    }

    @Override
    public void onFailed(int whichApi, Throwable throwable) {
        Toast.makeText(this, throwable.getMessage() != null ? throwable.getMessage() : "网络请求错误", Toast.LENGTH_SHORT).show();
    }
}
