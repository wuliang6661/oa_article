package com.article.oa_article.wxapi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.article.oa_article.base.MyApplication;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXFileObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * 微信分享工具类
 */
public class WxShareUtils {


    private final String APP_ID = "wx8zq97f092818osua";
    private IWXAPI api;
    private ShareResultListener listener;

    private final String TRANSACTION_TEXT = "text";
    private final String TRANSACTION_WEBPAGE = "webpage";

    private final int THUMBNAIL_SIZE = 150;

    //分享类型
    public enum ShareType {
        FRIENDS, FRIENDSCIRCLE, FAVOURITE
    }

    public static class InstanceHolder {
        private static WxShareUtils INSTANCE = new WxShareUtils();
    }

    public static WxShareUtils get() {
        return InstanceHolder.INSTANCE;
    }

    /**
     * 初始化微信API 推荐在自定义的Application的onCreate方法中调用
     */
    public void init() {
        api = MyApplication.WXapi;
    }

    /**
     * @param shareText
     * @param title
     * @param description
     * @param type
     * @param listener
     * @return
     */
    public boolean shareText(@NonNull String shareText, @NonNull String title, @NonNull String description,
                             @NonNull ShareType type, @Nullable ShareResultListener listener) {
        this.listener = listener;
        WXTextObject obj = new WXTextObject(shareText);
        WXMediaMessage msg = buildMediaMesage(obj, title, description);
        BaseReq req = buildSendReq(msg, buildTransaction(TRANSACTION_TEXT), getWxShareType(type));
        return api.sendReq(req);
    }


    //mgr.shareWebPage("http://www.jianshu.com/", "简书", "这是简书官网", WXShareManager.ShareType.FRIENDS, listener);
    public boolean shareWebPage(@NonNull String weburl, @NonNull String title, @NonNull String description,
                                @NonNull ShareType type, @Nullable ShareResultListener listener) {
        this.listener = listener;

        WXWebpageObject obj = new WXWebpageObject(weburl);
        WXMediaMessage msg = buildMediaMesage(obj, title, description);
        BaseReq req = buildSendReq(msg, buildTransaction(TRANSACTION_WEBPAGE), getWxShareType(type));
        return api.sendReq(req);
    }

    public void handleIntent(Intent intent, IWXAPIEventHandler handler) {
        api.handleIntent(intent, handler);
    }

    public boolean performShareResult(boolean result) {
        if (listener != null) {
            Log.e("_share_", "performShareResult: " + result);
            listener.onShareResult(result);
            listener = null;
            return true;
        }

        return false;
    }

    private int getWxShareType(ShareType type) {
        if (type == ShareType.FRIENDS) {
            return SendMessageToWX.Req.WXSceneSession;
        } else if (type == ShareType.FRIENDSCIRCLE) {
            return SendMessageToWX.Req.WXSceneTimeline;
        } else if (type == ShareType.FAVOURITE) {
            return SendMessageToWX.Req.WXSceneFavorite;
        }

        throw new IllegalArgumentException("非法参数: 不识别的ShareType -> " + type.name());
    }

    private Bitmap getThumbnail(@NonNull String path, int thumbnailSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        Log.e("_share_", "outWidth=" + options.outWidth + ", outHeight=" + options.outHeight);
        int sourceSize = Math.min(options.outWidth, options.outHeight);
        options.inJustDecodeBounds = false;
        options.inSampleSize = sourceSize / thumbnailSize;
        return BitmapFactory.decodeFile(path, options);
    }

    private Bitmap getThumbnail(@NonNull byte[] imageData, int thumbnailSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(imageData, 0, imageData.length, options);
        options.inJustDecodeBounds = false;
        Log.e("_share_", "outWidth=" + options.outWidth + ", outHeight=" + options.outHeight);
        int sourceSize = Math.min(options.outWidth, options.outHeight);
        options.inSampleSize = sourceSize / thumbnailSize;
        return BitmapFactory.decodeByteArray(imageData, 0, imageData.length, options);
    }

    private BaseReq buildSendReq(WXMediaMessage msg, String transaction, int scene) {
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = msg;
        req.transaction = transaction;
        req.scene = scene;
        return req;
    }

    private WXMediaMessage buildMediaMesage(WXMediaMessage.IMediaObject obj, String title, String description) {
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = obj;
        msg.title = title;
        msg.description = description;
        return msg;
    }

    /**
     * @param type text/image/webpage/music/video
     * @return
     */
    private String buildTransaction(String type) {
        return TextUtils.isEmpty(type) ? String.valueOf(System.currentTimeMillis()) : (type + System.currentTimeMillis());
    }

    /**
     * 分享结果回调
     */
    public interface ShareResultListener {
        void onShareResult(boolean result);
    }
}

