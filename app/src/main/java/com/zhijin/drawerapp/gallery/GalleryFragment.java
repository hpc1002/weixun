package com.zhijin.drawerapp.gallery;

import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.zhijin.drawerapp.Constants.Constant;
import com.zhijin.drawerapp.R;
import com.zhijin.drawerapp.adapter.GalleryAdapter;
import com.zhijin.drawerapp.base.BaseFragment;
import com.zhijin.drawerapp.bean.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by hpc on 2017/3/8.
 */

public class GalleryFragment extends BaseFragment {
    @BindView(R.id.recyclerview_gallery)
    EasyRecyclerView recyclerviewGallery;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_gallery, container, false);
    }

    @Override
    protected void initListener() {
        recyclerviewGallery.setLayoutManager(new GridLayoutManager(getActivity(), 3));
    }

    @Override
    protected void initData() {
        OkGo.get(Constant.BASEURL + "?pno=" + 1 + "&ps=" + 50 + "&dtype="
                + "json" + "&key=" + Constant.APPKEY)
                .tag(this)
                .cacheKey("cacheKey")
                .cacheMode(CacheMode.DEFAULT)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {

                        //解析数据
                        ArrayList<Article> picture = parseData(s);
                        showDataInUi(picture);
                    }
                });
    }

    private void showDataInUi(ArrayList<Article> myList) {
        GalleryAdapter galleryAdapter = new GalleryAdapter(R.layout.item_gallery, myList);
        recyclerviewGallery.setAdapter(galleryAdapter);
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

}
