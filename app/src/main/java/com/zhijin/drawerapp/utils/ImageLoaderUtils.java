package com.zhijin.drawerapp.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.BitmapCallback;
import com.zhijin.drawerapp.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Response;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * description：图片加载工具类
 * author：pz
 * data：2016/10/24
 */
public class ImageLoaderUtils {

    public static void displayScaleImage(Context context, final ImageView imageView, String url, final PhotoViewAttacher photoViewAttacher) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }

        Glide.with(context).
                load(url)
                .placeholder(R.drawable.avatar_default)
                .error(R.drawable.image_default_rect)
                .into(new GlideDrawableImageViewTarget(imageView) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);
                        if (photoViewAttacher != null) {
                            photoViewAttacher.update();
                        }
                    }
                });
    }

    public static void downLoadImage(final String url, final String destFileDir, final Context context) {

        //根据url下载图片到本地
        OkGo.get(url)
//                .tag(this)  //?
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new BitmapCallback() {
                    @Override
                    public void onSuccess(Bitmap bitmap, Call call, Response response) {
                        FileOutputStream fos = null;
                        //保存图片
                        File file = new File(destFileDir);
                        if (!file.exists()) {
                            file.mkdir();
                        }
                        File imageFile = new File(destFileDir, new Date().getTime() + ".jpg");
                        try {
                            fos = new FileOutputStream(imageFile);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
                            ToastManager.show("保存成功");
                            //更新相册
                            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                            Uri uri = Uri.fromFile(file);
                            intent.setData(uri);
                            context.sendBroadcast(intent);
                            try {
                                fos.flush();
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


}
