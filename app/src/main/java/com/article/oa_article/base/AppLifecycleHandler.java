package com.article.oa_article.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/7/1613:25
 * desc   :  监测程序中所有界面的生命周期
 * version: 1.0
 */
public class AppLifecycleHandler implements Application.ActivityLifecycleCallbacks {



    // 判断app 是在前台还是后台的
    private int refCount = 0;


    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        refCount++;
        MyApplication.AppInBack = false;
    }

    @Override
    public void onActivityResumed(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        refCount--;
        if (refCount == 0) {
            MyApplication.AppInBack = true;
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }
}
