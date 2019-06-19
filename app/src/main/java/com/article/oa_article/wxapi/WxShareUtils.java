package com.article.oa_article.wxapi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.article.oa_article.R;
import com.article.oa_article.base.MyApplication;
import com.article.oa_article.bean.ShareBo;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;

/**
 * 微信分享工具类
 */
public class WxShareUtils {


    private IWXAPI api;

    static class InstanceHolder {
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
     * 微信分享 （这里仅提供一个分享网页的示例，其它请参看官网示例代码）
     *
     * @param flag(0:分享到微信好友，1：分享到微信朋友圈)
     */
    public void wechatShare(Context context, int flag, ShareBo shareBo) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = shareBo.getUrl();
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = shareBo.getTitle();
        msg.description = shareBo.getContent();
        //这里替换一张自己工程里的图片资源
        Bitmap thumb = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        msg.setThumbImage(thumb);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);
    }


}

