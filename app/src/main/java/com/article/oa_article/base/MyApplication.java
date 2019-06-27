package com.article.oa_article.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.article.oa_article.Config;
import com.article.oa_article.bean.UserBo;
import com.article.oa_article.wxapi.WxShareUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import cn.jpush.android.api.JPushInterface;

/**
 * 作者 by wuliang 时间 16/10/26.
 * <p>
 * 程序的application
 */

public class MyApplication extends Application {

    private static final String TAG = "MyApplication";

    public static SPUtils spUtils;

    public static String token;

    public static UserBo userBo;

    public static IWXAPI WXapi;

    public static int selectComplan = 0;    //默认选择第一家公司

    public static boolean isSplash = false;  //从引导页进入的指引


    @Override
    public void onCreate() {
        super.onCreate();
        CustomActivityOnCrash.install(this);
//        CustomActivityOnCrash.setErrorActivityClass(CustomErrorActivity.class);
        /***初始化工具类*/
        Utils.init(this);
        spUtils = SPUtils.getInstance(TAG);
//        Fragmentation.getDefault().setMode(Fragmentation.BUBBLE);
        WXapi = WXAPIFactory.createWXAPI(this, Config.WX_APP_ID, true);
        WXapi.registerApp(Config.WX_APP_ID);
        WxShareUtils.get().init();

        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
    }


    public static String getCommonId() {
        if (MyApplication.userBo.getCompanys() == null || MyApplication.userBo.getCompanys().size() == 0) {
            return "0";
        } else {
            return MyApplication.userBo.getCompanys().get(selectComplan).getId() + "";
        }
    }


    /**
     * 当前用户选择的公司
     */
    public static UserBo.CompanysBean getCommon() {
        if (MyApplication.userBo.getCompanys() == null || MyApplication.userBo.getCompanys().size() == 0) {
            return new UserBo.CompanysBean();
        } else {
            if (selectComplan > MyApplication.userBo.getCompanys().size()) {
                selectComplan = 0;
                return MyApplication.userBo.getCompanys().get(selectComplan);
            } else {
                return MyApplication.userBo.getCompanys().get(selectComplan);
            }
        }
    }


    /**
     * 判断当前用户是否有公司
     */
    public static boolean isHaveCommon() {
        if ("0".equals(getCommonId())) {
            return false;
        }
        return true;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
