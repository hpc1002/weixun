package com.zhijin.drawerapp.adapter;

import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter;
import com.xiaochao.lcrapiddeveloplibrary.BaseViewHolder;
import com.zhijin.drawerapp.R;
import com.zhijin.drawerapp.bean.Lives;

import java.util.List;

/**
 * Created by hpc on 2017/3/10.
 */

public class LiveListAdapter extends BaseQuickAdapter<Lives> {
    public LiveListAdapter(int layoutResId, List<Lives> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Lives lives) {
        helper.setText(R.id.tvNameAndCity, "来自" + lives.getCity() + "的" +
                (TextUtils.isEmpty(lives.getName()) ? " 未起名 " : lives.getName())
                + "距离您" + lives.getDistance());
        Glide.with(mContext)
                .load(lives.getCreator().getPortrait())
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .into((ImageView) helper.getView(R.id.ivPortrait));

    }
}
