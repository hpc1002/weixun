package com.zhijin.drawerapp.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import Decoder.BASE64Decoder;

/**
 * Created by hpc on 2017/3/15.
 */

public class Base64ToImage {
    /**
     * 1.base64字符串转化成图片（对字节数组字符串进行Base64解码并生成本地图片）
     * </p>
     * <p>
     * 2.首先要检查是否存在data:image/png;base64,(类似content-type),如果有的话， 去掉。
     * </p>
     * <p>
     * 3.通过BASE64Decoder 接口进行解码
     * 4.最后通过FileOutputStream 文件流生成文件
     * @param base64Str
     * @param destFileDir
     * @return
     */
    public static String generateImage(String base64Str, String destFileDir) {
        // 去掉前面的data:image/png;base64,
        if (base64Str.indexOf("data:image/png;base64,") != -1) {
            base64Str = base64Str.replace("data:image/png;base64,", "");
        }
        BASE64Decoder decoder = new BASE64Decoder();
        // 生成jpeg图片
        FileOutputStream out = null;
        try {
            // Base64解码
            byte[] b = decoder.decodeBuffer(base64Str);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            //保存图片
            File file = new File(destFileDir);
            if (!file.exists()) {
                file.mkdir();
            }
            File imageFile = new File(destFileDir, new Date().getTime() + ".jpg");
            out = new FileOutputStream(imageFile);
            out.write(b);
            out.flush();
        } catch (Exception e) {
            return null;
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return destFileDir;
    }

    /**
     * bytes将字节数组转换为ImageView可调用的Bitmap对象
     *
     * @param bytes
     * @param opts
     * @return
     */
    public static Bitmap getPicFromBytes(byte[] bytes,
                                         BitmapFactory.Options opts) {
        if (bytes != null)
            if (opts != null)
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
                        opts);
            else
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return null;
    }

    /**
     * 将Base64转换为bitmap
     * @param base64Str
     * @param opts
     * @return
     */
    public static Bitmap base64ToBitmap(String base64Str, BitmapFactory.Options opts) {
        // 去掉前面的data:image/png;base64,
        if (base64Str.indexOf("data:image/png;base64,") != -1) {
            base64Str = base64Str.replace("data:image/png;base64,", "");
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            byte[] b = decoder.decodeBuffer(base64Str);
            if (b != null) {
                if (opts != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length, opts);
                    return bitmap;
                } else {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
                    return bitmap;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
