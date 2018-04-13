package com.hyphenate.chatuidemo.my.Untils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2018/2/2.
 * 水印工具类
 */

public class ImageUtil {

    private static final String TAG = "print";

    public static String photoAddText(Context context,String path, String text,String savePath) {
        Bitmap bitmap=getBitmap(path);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setTextSize(dp2px(context, 13));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        Bitmap textBitmap=drawTextToBitmap(context, bitmap, text, paint, bounds,
                (bitmap.getWidth() - bounds.width()) / 2,
                3*(bitmap.getHeight() + bounds.height()) / 4);

        //画好的Bitmap保存内存中
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        textBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(savePath));
            fileOutputStream.write(bytes);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return savePath;
    }

    //图片上绘制文字
    private static Bitmap drawTextToBitmap(Context context, Bitmap bitmap, String text,
                                           Paint paint, Rect bounds, int paddingLeft, int paddingTop) {
        android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();

        paint.setDither(true); // 获取跟清晰的图像采样
        paint.setFilterBitmap(true);// 过滤一些
        if (bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        bitmap = bitmap.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(bitmap);

        canvas.drawText(text, paddingLeft, paddingTop, paint);
        return bitmap;
    }

    /**
     * dip转pix
     * @param context
     * @param dp
     * @return
     */
    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    //根据文件名来获取图片
    public static Bitmap getBitmap(String path) {
        //判断文件 是否存在
        File file = new File(path);
        Log.e("取出路径", file.getAbsolutePath());
        if (file.exists()) {
            return createThumbnail(path,720,1280);
        }
        return null;
    }

    /*
      * *
        * 对图片进行二次采样，生成缩略图。放置加载过大图片出现内存溢出
       * 参数一：文件的路径
       * 参数二：缩略图的宽度(一般是需要展示的View的宽度)
       * 参数三：缩略图的高度(同上)
       */
    public static Bitmap createThumbnail(String filePath, int newWidth, int newHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//
        /**
         * BitmapFactory.Options 的inJustDecodeBounds 一旦设置为true，
         * BitmapFactory解码后返回值为null，只会
         * 通过Options的outHeight和outWidth可以获得图片的宽高。
         */
        BitmapFactory.decodeFile(filePath, options);//开始解析图片的边缘
        int imageHeight = options.outHeight;// 拿到图片的高度
        int imageWidth = options.outWidth;// 拿到图片的宽度
        Log.d(TAG, "imageHeight =" + imageHeight + "     imageWidth = " + imageWidth);
        Log.d(TAG, "newHeight =" + newHeight + "     newWidth = " + newWidth);
        // 计算缩放比
        int scole = 1;// 默认缩放比例为1；
        Log.d(TAG, "scole =" + scole);
        // Math.ceil---->取最近的整数。如：2.3=>3。7.1=>8（为了让得到的比例强转）
        int scoleY = (int) Math.ceil(imageHeight / newHeight);// 得到高度的缩放比例
        int scoleX = (int) Math.ceil(imageWidth / newWidth);// 得到缩放的宽度比例
        Log.d(TAG, "scoleY =" + scoleY + "     scoleX = " + scoleX);
        if (scoleY > 1 || scoleX > 1) {
            if (scoleY > scoleX) {
                scole = scoleY;
            } else {
                scole = scoleX;
            }
        }
        Log.d(TAG, "修改完比例之后 scole =" + scole);
        // 修改完比例之后。就把JustDecodeBounds设置为false。真正去解析图片里面的数据
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.ALPHA_8;// 设置图片的质量
        options.inSampleSize = scole;// 设置图片采样的比例
        return BitmapFactory.decodeFile(filePath, options);
    }
}
