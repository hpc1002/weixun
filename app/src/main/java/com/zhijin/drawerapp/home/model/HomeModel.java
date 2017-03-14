package com.zhijin.drawerapp.home.model;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.zhijin.drawerapp.Constants.Constant;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by hpc on 2017/3/8.
 */

public class HomeModel {
    public void LoadData(int currentPage, int perPageNumber, String type, final OnLoadDataListListener listener) {

        OkGo.get(Constant.BASEURL + "?pno=" + currentPage + "&ps=" + perPageNumber + "&dtype="
                + type + "&key=" + Constant.APPKEY)
                .tag(this)
                .cacheKey("cacheKey")
                .cacheMode(CacheMode.DEFAULT)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {

                        listener.onSuccess(s);
                    }
                });

    }
}
