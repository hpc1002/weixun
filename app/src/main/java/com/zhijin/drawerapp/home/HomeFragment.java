package com.zhijin.drawerapp.home;

import android.content.Intent;
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
import com.zhijin.drawerapp.Constants.Constant;
import com.zhijin.drawerapp.R;
import com.zhijin.drawerapp.adapter.ArticleAdapter;
import com.zhijin.drawerapp.base.BaseFragment;
import com.zhijin.drawerapp.bean.Article;
import com.zhijin.drawerapp.home.presenter.HomePresenter;
import com.zhijin.drawerapp.home.view.HomeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by hpc on 2017/3/8.
 */

public class HomeFragment extends BaseFragment implements HomeView, SpringView.OnFreshListener {
    @BindView(R.id.news_list)
    RecyclerView newsList;
    @BindView(R.id.progress)
    ProgressActivity progress;
    @BindView(R.id.home_recommend_springview)
    SpringView homeRecommendSpringview;
    private HomePresenter presenter;

    private ArticleAdapter articleAdapter;

    //当前显示第几页
    private int currentPage = 1;
    //当前每页显示条数默认20条
    private int perPage = 20;
    private String type = "json";

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    protected void initListener() {
        homeRecommendSpringview.setListener(this);
        homeRecommendSpringview.setHeader(new DefaultHeader(getContext()));
        homeRecommendSpringview.setFooter(new DefaultFooter(getContext()));
    }

    @Override
    protected void initData() {
        presenter = new HomePresenter(this);
        presenter.LoadData(currentPage, perPage, type);
    }

    @Override
    public void showProgress() {
        progress.showLoading();
    }

    @Override
    public void hideProgress() {
        progress.showContent();
    }

    @Override
    public void newDatas(String data) {
        System.out.println("wwwwwwwwwwwwww" + data);

        //解析数据
        ArrayList<Article> newses = parseData(data);
        showDataInUi(newses);
    }

    private void showDataInUi(ArrayList<Article> myList) {
        articleAdapter = new ArticleAdapter(R.layout.item_article, myList);
        newsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        newsList.setAdapter(articleAdapter);
        articleAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), ArticleInfoActivity.class);
                intent.putExtra("url", articleAdapter.getItem(position).getUrl());
                intent.putExtra(Constant.TAG, articleAdapter.getItem(position));
                startActivity(intent);
            }
        });
    }

    private ArrayList<Article> parseData(String json) {
        try {
            if (json == null) {
                return null;
            }
            JSONObject jsonObject = new JSONObject(json);
            JSONObject result = jsonObject.getJSONObject("result");
            JSONArray newsListdata = result.getJSONArray("list");
            ArrayList<Article> newsDataList = new ArrayList<>();
            for (int i = 0; i < newsListdata.length(); i++) {
                JSONObject jsonObj = newsListdata.getJSONObject(i);
                Article article = new Article();
                article.title = jsonObj.getString("title");
                article.source = jsonObj.getString("source");
                article.url = jsonObj.getString("url");
                if (jsonObj.has("firstImg")) {
                    article.firstImg = jsonObj.getString("firstImg");
                }
                newsDataList.add(article);
            }
            return newsDataList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void showLoadFailMsg() {

    }

    @Override
    public void onRefresh() {
        if (currentPage <= 5) {
            currentPage++;
        } else {
            currentPage -= 4;
        }
        presenter.LoadData(currentPage, perPage, "json");
    }

    @Override
    public void onLoadmore() {
        if (perPage <= 50) {
            perPage += 10;
        }
        presenter.LoadData(currentPage, perPage, "json");
    }

}
