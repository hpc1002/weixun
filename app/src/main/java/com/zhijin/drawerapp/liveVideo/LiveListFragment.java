package com.zhijin.drawerapp.liveVideo;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter;
import com.xiaochao.lcrapiddeveloplibrary.container.DefaultHeader;
import com.xiaochao.lcrapiddeveloplibrary.viewtype.ProgressActivity;
import com.xiaochao.lcrapiddeveloplibrary.widget.SpringView;
import com.zhijin.drawerapp.Constants.Constant;
import com.zhijin.drawerapp.R;
import com.zhijin.drawerapp.adapter.LiveListAdapter;
import com.zhijin.drawerapp.base.BaseFragment;
import com.zhijin.drawerapp.bean.Lives;
import com.zhijin.drawerapp.utils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {

        return inflater.inflate(R.layout.fragment_live_list, container, false);
    }

    @Override
    protected void initListener() {
        getLiveVideo();
        liveListSpringView.setListener(this);
        liveListSpringView.setHeader(new DefaultHeader(getContext()));
    }

    private void getLiveVideo() {
        new RequestLivingTask().execute(Constant.LIVE_VIDEO_URL);
    }

    @Override
    protected void initData() {
        liveVideoList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mLivesList = new ArrayList<>();
        mAdapter = new LiveListAdapter(R.layout.item_live_video, mLivesList);
        liveVideoList.setAdapter(mAdapter);
        mAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), VideoLivePlayActivity.class);
                intent.putExtra("stream_addr", mLivesList.get(position).getStream_addr());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRefresh() {
        getLiveVideo();
        initData();
    }

    @Override
    public void onLoadmore() {

    }

    private class RequestLivingTask extends AsyncTask<String, Void, List<Lives>> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            liveListProgress.showLoading();
        }

        @Override
        protected List<Lives> doInBackground(String... params) {
            String url = params[0];
            mLivesList = new ArrayList<>();
            try {
                String livingList = HttpUtils.getInstance().get(url);
                JSONObject livingObj = new JSONObject(livingList);
                JSONArray livesArr = livingObj.optJSONArray("lives");
                for (int i = 0; i < livesArr.length(); i++) {
                    JSONObject live = livesArr.optJSONObject(i);

                    Lives lives = new Lives(live);
                    mLivesList.add(lives);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return mLivesList;
        }

        @Override
        protected void onPostExecute(List<Lives> lives) {
            super.onPostExecute(lives);
            mLivesList.addAll(lives);
            mAdapter.notifyDataSetChanged();
            liveListProgress.showContent();
        }
    }
}
