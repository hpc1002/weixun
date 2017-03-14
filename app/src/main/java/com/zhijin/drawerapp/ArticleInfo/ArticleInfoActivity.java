package com.zhijin.drawerapp.ArticleInfo;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.StrictMode;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.zhijin.drawerapp.ArticleInfo.view.ShowImageWebView;
import com.zhijin.drawerapp.Constants.Constant;
import com.zhijin.drawerapp.R;
import com.zhijin.drawerapp.base.BaseActivity;
import com.zhijin.drawerapp.bean.Article;
import com.zhijin.drawerapp.dao.DbCore;
import com.zhijin.drawerapp.utils.BitmapUtil;
import com.zhijin.drawerapp.utils.DBOperate;
import com.zhijin.drawerapp.utils.DBUtils;
import com.zhijin.drawerapp.utils.ToastManager;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;

public class ArticleInfoActivity extends BaseActivity {


    @BindView(R.id.AdLinearLayout)
    LinearLayout AdLinearLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.webView)
    ShowImageWebView webView;
    DBUtils mUtils;
    DbCore mCore;

    //应用和程序id
    private IWXAPI api;

    @Override
    protected void loadViewLayout() {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setContentView(R.layout.activity_article_info);
        api = WXAPIFactory.createWXAPI(this, Constant.WE_APP_ID, false);
        api.registerApp(Constant.WE_APP_ID);
    }


    @Override
    protected void setListener() {
        setSupportActionBar(toolbar);
        toolbar.setTitle("详情");
    }

    @Override
    protected void processLogic() {

        final String url = getIntent().getStringExtra("url");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDefaultTextEncodingName("UTF -8");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // web 页面加载完成，添加监听图片的点击 js 函数
                webView.setImageClickListner();
                //解析 HTML
                webView.parseHTML(view);
            }
        });
        webView.loadUrl(url);
        mUtils = new DBUtils(new DBOperate() {
            @Override
            public void notifySucess() {
                ToastManager.show("收藏成功");
            }

            @Override
            public void notifyFail() {
                ToastManager.show("您已收藏过此条新闻，切勿重复添加！！");
            }
        }, mCore);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final Article datas = (Article) getIntent().getSerializableExtra(Constant.TAG);
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = datas.getUrl();
        //创建一个对象
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = datas.title;
        msg.description = datas.source;
        //本地图片分享
//        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        //网络图片分享 微信分享缩略图（thumb）最大64KB 图片大小限制目前还存在小问题
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(BitmapUtil.GetLocalOrNetBitmap(datas.getFirstImg()), 100, 100, true);
        msg.thumbData = bmpToByteArray(scaledBitmap, true);
        //创建 SendMessageToWX.Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;

        switch (item.getItemId()) {
            case R.id.action_share_friend:

                req.scene = SendMessageToWX.Req.WXSceneSession;
                api.sendReq(req);
                break;
            case R.id.action_share_circle:
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                api.sendReq(req);
                break;
            case R.id.action_collect:
                mUtils.insertDB(datas);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected Context getActivityContext() {
        return this;
    }

    @Override
    public void onClick(View view) {

    }

    //将bitmap转换为byte[]格式
    private byte[] bmpToByteArray(final Bitmap bitmap, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bitmap.recycle();
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
