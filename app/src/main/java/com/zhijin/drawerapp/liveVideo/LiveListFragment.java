package com.zhijin.drawerapp.liveVideo;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter;
import com.xiaochao.lcrapiddeveloplibrary.container.DefaultHeader;
import com.xiaochao.lcrapiddeveloplibrary.viewtype.ProgressActivity;
import com.xiaochao.lcrapiddeveloplibrary.widget.SpringView;
import com.zhijin.drawerapp.Constants.Constant;
import com.zhijin.drawerapp.R;
import com.zhijin.drawerapp.adapter.LiveListAdapter;
import com.zhijin.drawerapp.base.BaseFragment;
import com.zhijin.drawerapp.bean.Lives;
import com.zhijin.drawerapp.utils.ListDivisionUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by hpc on 2017/3/10.
 */

public class LiveListFragment extends BaseFragment implements SpringView.OnFreshListener {
    @BindView(R.id.live_video_list)
    RecyclerView liveVideoList;
    @BindView(R.id.live_list_springView)
    SpringView liveListSpringView;
    @BindView(R.id.live_list_progress)
    ProgressActivity liveListProgress;
    private LiveListAdapter mAdapter;
    private List<Lives> mLivesList;
    private List<Lives> mylive;
    private List<List<Lives>> lists;
    private List<Lives> newLivList;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {

        return inflater.inflate(R.layout.fragment_live_list, container, false);
    }

    @Override
    protected void initListener() {
//        getLiveVideo();
        liveListSpringView.setListener(this);
        liveListSpringView.setHeader(new DefaultHeader(getContext()));
    }

    private List<Lives> getLiveVideo() {
        mLivesList = new ArrayList<>();
        OkGo.get(Constant.LIVE_VIDEO_URL)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject livingObj = new JSONObject(s);
                            JSONArray jsonArray = livingObj.optJSONArray("lives");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject live = jsonArray.optJSONObject(i);

                                Lives lives = new Lives(live);
                                mLivesList.add(lives);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

//        newLivList = lists.get((int) (Math.random() * lists.size() + 1));

        return mLivesList;
    }

    @Override
    protected void initData() {
        liveVideoList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        final List<Lives> liveVideo = getLiveVideo();

        mAdapter = new LiveListAdapter(R.layout.item_live_video, liveVideo);
        liveVideoList.setAdapter(mAdapter);
        mAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), VideoLivePlayActivity.class);
                intent.putExtra("stream_addr", liveVideo.get(position).getStream_addr());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRefresh() {

        initData();
    }

    @Override
    public void onLoadmore() {

    }

}
