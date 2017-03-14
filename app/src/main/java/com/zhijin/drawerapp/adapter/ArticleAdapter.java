package com.zhijin.drawerapp.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter;
import com.xiaochao.lcrapiddeveloplibrary.BaseViewHolder;
import com.zhijin.drawerapp.R;
import com.zhijin.drawerapp.bean.Article;

import java.util.List;

/**
 * Created by hpc on 2017/3/8.
 */

public class ArticleAdapter extends BaseQuickAdapter<Article> {

    public ArticleAdapter(int layoutResId, List<Article> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Article item) {
        Glide.with(mContext)
                .load(item.getFirstImg())
                .crossFade()
                .placeholder(R.mipmap.default_0)
                .into((ImageView)helper.getView(R.id.news_icon));
        helper.setText(R.id.news_title,item.getTitle());
        helper.setText(R.id.author,item.getSource());
    }
}
