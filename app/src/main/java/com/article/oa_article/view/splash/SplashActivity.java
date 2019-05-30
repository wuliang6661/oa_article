package com.article.oa_article.view.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.article.oa_article.R;
import com.article.oa_article.base.BaseActivity;
import com.article.oa_article.base.MyApplication;
import com.article.oa_article.view.login.LoginActivity;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/5/3013:43
 * desc   :
 * version: 1.0
 */
public class SplashActivity extends BaseActivity {


    //是否是第一次使用
    private boolean isFirstUse;

    @Override
    protected int getLayout() {
        return R.layout.act_splash;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isFirstUse = MyApplication.spUtils.getBoolean("isFirstUse", true);
        /**
         *如果用户不是第一次使用则直接调转到显示界面,否则调转到引导界面
         */
        if (isFirstUse) {
            gotoActivity(GuideActivity.class, true);
        } else {
            gotoActivity(LoginActivity.class, true);
        }
        //存入数据
        MyApplication.spUtils.put("isFirstUse", false);
    }
}
