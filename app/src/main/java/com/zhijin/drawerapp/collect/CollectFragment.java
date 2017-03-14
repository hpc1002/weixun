package com.zhijin.drawerapp.collect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter;
import com.xiaochao.lcrapiddeveloplibrary.container.DefaultFooter;
import com.xiaochao.lcrapiddeveloplibrary.container.DefaultHeader;
import com.xiaochao.lcrapiddeveloplibrary.viewtype.ProgressActivity;
import com.xiaochao.lcrapiddeveloplibrary.widget.SpringView;
import com.zhijin.drawerapp.ArticleInfo.ArticleInfoActivity;
import com.zhijin.drawerapp.R;
import com.zhijin.drawerapp.adapter.ArticleAdapter;
import com.zhijin.drawerapp.base.BaseFragment;
import com.zhijin.drawerapp.bean.Article;
import com.zhijin.drawerapp.dao.DbCore;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hpc on 2017/3/14.
 */

public class CollectFragment extends BaseFragment implements SpringView.OnFreshListener {
    DbCore mCore;
    @BindView(R.id.recyclerview_collect)
    RecyclerView recyclerviewCollect;
    @BindView(R.id.collect_springview)
    SpringView collectSpringview;
    @BindView(R.id.progress)
    ProgressActivity progress;
    private ArticleAdapter articleAdapter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_collect, container, false);
    }

    @Override
    protected void initListener() {
        recyclerviewCollect.setLayoutManager(new LinearLayoutManager(getActivity()));
        collectSpringview.setListener(this);
        collectSpringview.setHeader(new DefaultHeader(getActivity()));
    }

    @Override
    protected void initData() {
        final List<Article> been = mCore.getDaoSession().getDataBeanDao().loadAll();
        articleAdapter = new ArticleAdapter(R.layout.item_article, null);
        articleAdapter.setNewData(been);
        articleAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), ArticleInfoActivity.class);
                intent.putExtra("url", been.get(position).getUrl());
                startActivity(intent);
            }
        });
        recyclerviewCollect.setAdapter(articleAdapter);
    }

    @Override
    public void onRefresh() {
        initData();
    }

    @Override
    public void onLoadmore() {

    }
}
