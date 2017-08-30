package com.zw.happybuy.utils;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zw.happybuy.R;
import com.zw.happybuy.common.App;
import com.zw.happybuy.ui.base.BaseView;
import com.zw.happybuy.widget.CircleImageTransform;

/**
 * Created by Tok on 2017/8/27.
 */

public class ImageUtils {

    private ImageUtils(){}

    /**
     * 直接加载图片(没有占位图等其他处理，仅仅是加载一张图片)
     * @param imageView ImageView
     * @param imageUrl  图片地址
     */
    public static void loadImage(ImageView imageView, String imageUrl) {
        loadImage(App.getAppContext(), imageView, imageUrl);
    }

    /**
     * 直接加载图片(没有占位图等其他处理，仅仅是加载一张图片)
     * @param context    Context
     * @param imageView  ImageView
     * @param imageUrl   图片地址
     */
    public static void loadImage(Context context, ImageView imageView, String imageUrl) {
        Glide.with(context)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    /**
     * 加载图片，在加载过程中会显示占位图，失败也会显示占位图
     * @param context    Context
     * @param imageView  ImageView
     * @param imageUrl   图片地址
     */
    public static void loadImageWithPlaceHolder(Context context, ImageView imageView, String imageUrl) {
        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.mipmap.pic_loading)
                .error(R.mipmap.pic_error)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    /**
     * 加载圆形图片
     * @param context    Context
     * @param imageView  ImageView
     * @param imageUrl   图片地址
     */
    public static void loadCircleImage(Context context, ImageView imageView, String imageUrl) {
        Glide.with(context)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate()
                .transform(new CircleImageTransform(context))
                .into(imageView);
    }

    /**
     * 加载Gif图
     * @param context    Context
     * @param imageView  ImageView
     * @param gifUrl     gif图地址
     */
    public static void loadGif(Context context, ImageView imageView, String gifUrl) {
        Glide.with(context)
                .load(gifUrl)
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    /**
     * 根据特定的宽高加载图片
     * @param context    Context
     * @param imageView  ImageView
     * @param imageUrl   图片地址
     * @param width      图片的宽度
     * @param height     图片的高度
     */
    public static void loadImageWithSize(Context context, ImageView imageView, String imageUrl, int width, int height) {
        Glide.with(context)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .override(width, height)
                .into(imageView);
    }

    public static void loadImageWithUri(Context context, ImageView imageView, Uri uri) {
        Glide.with(context)
                .load(uri)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    public static void loadFromBytes(BaseView view,Byte[] bytes,ImageView imageView){
        if(view == null && bytes == null && imageView == null) return;
        if(view instanceof Fragment){
            Glide.with(((Fragment)view)).load(bytes).into(imageView);
        }else if(view instanceof Activity){
            Glide.with(((Activity)view)).load(bytes).into(imageView);
        }
    }

    public static void loadFromRes(BaseView view, @DrawableRes int resId, ImageView imageView){
        if(view == null && imageView == null) return;
        if(view instanceof Fragment){
            Glide.with(((Fragment)view)).load(resId).into(imageView);
        }else if(view instanceof Activity){
            Glide.with(((Activity)view)).load(resId).into(imageView);
        }
    }

    public static void loadImageViewLodingSize(Context mContext, String path, int width, int height, ImageView mImageView) {
        Glide.with(mContext).load(path).override(width, height).placeholder(R.mipmap.pic_loading).error(R.mipmap.pic_error).into(mImageView);
    }

    public static void loadImageViewCache(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).skipMemoryCache(true).into(mImageView);
    }

    public static void loadImageViewDiskCache(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mImageView);
    }

    public static void loadImageViewAnim(Context mContext, String path, int anim, ImageView mImageView) {
        Glide.with(mContext).load(path).animate(anim).into(mImageView);
    }

    public static void loadImageViewThumbnail(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).thumbnail(0.1f).into(mImageView);
    }

    public static void loadImageViewDynamicGif(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).asGif().into(mImageView);
    }

    public static void GuideClearDiskCache(Context mContext) {
        //理磁盘缓存 需要在子线程中执行
        Glide.get(mContext).clearDiskCache();
    }

    public static void GuideClearMemory(Context mContext) {
        //清理内存缓存  可以在UI主线程中进行
        Glide.get(mContext).clearMemory();
    }

}
