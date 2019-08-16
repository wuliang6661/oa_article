package com.article.oa_article.view.splash;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.article.oa_article.R;
import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.HttpServerImpl;
import com.article.oa_article.base.BaseActivity;
import com.article.oa_article.base.MyApplication;
import com.article.oa_article.bean.UserBo;
import com.article.oa_article.view.login.LoginActivity;
import com.article.oa_article.view.main.MainActivity;
import com.blankj.utilcode.util.StringUtils;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/8/213:58
 * desc   : 启动页面
 * version: 1.0
 */
public class StartAct extends BaseActivity {


    @Override
    protected int getLayout() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.act_start;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String loginCode = MyApplication.spUtils.getString("loginCode");
        String passWord = MyApplication.spUtils.getString("password");

        if (StringUtils.isEmpty(loginCode) || StringUtils.isEmpty(passWord)) {
            new Handler().postDelayed(() -> gotoActivity(LoginActivity.class, true), 1500);
        } else {
            login(loginCode, passWord);
        }
    }


    private void login(String phone, String password) {
        HttpServerImpl.login(phone, password).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                MyApplication.token = s;
                MyApplication.spUtils.put("loginCode", phone);
                MyApplication.spUtils.put("password", password);
                getUserInfo();
            }

            @Override
            public void onFiled(String message) {
                gotoActivity(LoginActivity.class, true);
            }
        });
    }


    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        HttpServerImpl.getUserinfo().subscribe(new HttpResultSubscriber<UserBo>() {
            @Override
            public void onSuccess(UserBo s) {
                MyApplication.userBo = s;
                gotoActivity(MainActivity.class, true);
            }

            @Override
            public void onFiled(String message) {
                gotoActivity(LoginActivity.class, true);
            }
        });
    }


}

