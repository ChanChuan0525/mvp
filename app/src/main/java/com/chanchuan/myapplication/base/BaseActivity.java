package com.chanchuan.myapplication.base;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chanchuan.frame.LoadTypeConfig;
import com.chanchuan.myapplication.interfaces.DataListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * recyclerview 在整个项目中使用的比较频繁，将公共代码进行抽取
     *
     * @param pRecyclerView  要操作的Recyclerview
     * @param pRefreshLayout 如果有刷新和加载更多的问题，所使用的的smartRefreshLayout
     * @param pDataListener  刷新和加载更多的监听，如果实际使用中不涉及到刷新和加载更多，直接传null
     */
    public void initRecyclerView(RecyclerView pRecyclerView, SmartRefreshLayout pRefreshLayout, DataListener pDataListener) {
        if (pRecyclerView != null) pRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (pRefreshLayout != null && pDataListener != null) {
            pRefreshLayout.setOnRefreshListener(refreshLayout -> pDataListener.dataType(LoadTypeConfig.REFRESH));
            pRefreshLayout.setOnLoadMoreListener(refreshLayout -> pDataListener.dataType(LoadTypeConfig.MORE));
        }
    }

    public void showLog(Object content) {
        Log.e("XXXXXXXXXX", content.toString());
    }

    public void showToast(Object content) {
        Toast.makeText(getApplicationContext(), content.toString(), Toast.LENGTH_SHORT).show();
    }

}
