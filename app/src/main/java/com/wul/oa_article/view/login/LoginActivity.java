package com.wul.oa_article.view.login;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.wul.oa_article.R;
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

    @Override
    protected int getLayout() {
        return R.layout.act_login;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("登录");
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
        gotoActivity(MainActivity.class, true);
    }
}
