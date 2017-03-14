package com.zhijin.drawerapp.home.view;

import com.zhijin.drawerapp.bean.Article;

/**
 * Created by hpc on 2017/3/8.
 */

public interface HomeView {
    //显示加载页
    void showProgress();

    //关闭加载页
    void hideProgress();

    //加载数据
    void newDatas(String data);

    //加载失败
    void showLoadFailMsg();
}
