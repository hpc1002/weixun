package com.zhijin.drawerapp.share;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zhijin.drawerapp.Constants.Constant;
import com.zhijin.drawerapp.R;
import com.zhijin.drawerapp.base.BaseFragment;
import com.zhijin.drawerapp.utils.Base64ToImage;

import butterknife.BindView;

/**
 * Created by hpc on 2017/3/15.
 */

public class ShareFragment extends BaseFragment {
    @BindView(R.id.erweima)
    ImageView erweima;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_share, container, false);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

//        Base64ToImage.generateImage(Constant.ERWEIMA, Environment.getExternalStorageDirectory().getAbsolutePath() + "/Base64Img");
        Bitmap bitmap = Base64ToImage.base64ToBitmap(Constant.ERWEIMA, null);
        erweima.setImageBitmap(bitmap);
    }

}
