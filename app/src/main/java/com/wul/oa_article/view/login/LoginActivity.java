package com.wul.oa_article.view.login;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.StringUtils;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wul.oa_article.Config;
import com.wul.oa_article.R;
import com.wul.oa_article.api.HttpResultSubscriber;
import com.wul.oa_article.api.HttpServerImpl;
import com.wul.oa_article.base.MyApplication;
import com.wul.oa_article.bean.UserBo;
import com.wul.oa_article.mvp.MVPBaseActivity;
import com.wul.oa_article.view.forword_password.Forword_passwordActivity;
import com.wul.oa_article.view.main.MainActivity;
import com.wul.oa_article.view.register.RegisterActivity;
import com.wul.oa_article.view.verificationlogin.VerificationLoginActivity;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class LoginActivity extends MVPBaseActivity<LoginContract.View, LoginPresenter>
        implements LoginContract.View {

    @BindView(R.id.edit_phone)
    EditText editPhone;
    @BindView(R.id.edit_password)
    EditText editPassword;
    @BindView(R.id.login_button)
    Button loginButton;
    @BindView(R.id.wechat_login)
    LinearLayout wechatLogin;
    @BindView(R.id.mima_visiable)
    CheckBox mimaVisiable;

    @Override
    protected int getLayout() {
        return R.layout.act_login;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("登录");
        mimaVisiable.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                //如果选中，显示密码
                editPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                //否则隐藏密码
                editPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });
        requestPermission();
    }


    private void requestPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CALL_PHONE
                    }, 1);

        }
    }

    @OnClick(R.id.regis_text)
    public void click(View view) {
        gotoActivity(RegisterActivity.class, false);
    }

    @OnClick(R.id.forword_password)
    public void forwordPw(View view) {
        gotoActivity(Forword_passwordActivity.class, false);
    }

    @OnClick(R.id.verfication_login)
    public void goVerfication(View view) {
        gotoActivity(VerificationLoginActivity.class, false);
    }


    @OnClick(R.id.login_button)
    public void login(View view) {
        String phone = editPhone.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        if (StringUtils.isEmpty(phone)) {
            showToast("请输入手机号码！");
            return;
        }
        if (StringUtils.isEmpty(password)) {
            showToast("请输入密码！");
            return;
        }
        showProgress();
        HttpServerImpl.login(phone, password).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                MyApplication.token = s;
                getUserInfo();
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
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
                stopProgress();
                MyApplication.userBo = s;
                gotoActivity(MainActivity.class, true);
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
    }


    /**
     * 微信登录
     */
    @OnClick(R.id.wechat_login)
    public void wxLogin(View view) {
        requestPermission();
        if (MyApplication.WXapi == null) {
            MyApplication.WXapi = WXAPIFactory.createWXAPI(this, Config.WX_APP_ID, true);
            MyApplication.WXapi.registerApp(Config.WX_APP_ID);
        }
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo";
        MyApplication.WXapi.sendReq(req);
    }

}
