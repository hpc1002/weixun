package com.zhijin.drawerapp.home.presenter;

import com.zhijin.drawerapp.bean.Article;
import com.zhijin.drawerapp.home.model.HomeModel;
import com.zhijin.drawerapp.home.model.OnLoadDataListListener;
import com.zhijin.drawerapp.home.view.HomeView;

/**
 * Created by hpc on 2017/3/8.
 */

public class HomePresenter implements OnLoadDataListListener<String> {
    private HomeModel mModel;
    private HomeView mView;

    public HomePresenter(HomeView mView) {
        this.mModel = new HomeModel();
        this.mView = mView;
        mView.showProgress();
    }

    public void LoadData(int currentPage, int perPageNumber, String type) {
        mModel.LoadData(currentPage, perPageNumber, type, this);
    }


    @Override
    public void onSuccess(String data) {
        mView.newDatas(data);
        mView.hideProgress();
    }

    @Override
    public void onFailure(Throwable e) {
        mView.showLoadFailMsg();
    }
}
