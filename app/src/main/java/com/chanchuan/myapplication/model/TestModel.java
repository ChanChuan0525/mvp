package com.chanchuan.myapplication.model;

import com.chanchuan.frame.ApiConfig;
import com.chanchuan.frame.ICommonModel;
import com.chanchuan.frame.ICommonPresenter;
import com.chanchuan.frame.NetManager;

import java.util.Map;

public class TestModel implements ICommonModel {
    NetManager netManager = NetManager.getInstance();

    @Override
    public void getData(final ICommonPresenter presenter, final int whichApi, final Object[] params) {
        switch (whichApi) {
            case ApiConfig
                    .TEST_GET:
                int loadType = (int) params[0];
                Map param = (Map) params[1];
                final int pageId = (int) params[2];
                netManager.netWork(netManager.getService().getInfo(param, pageId), presenter, whichApi, loadType);
                break;
        }

    }
}
