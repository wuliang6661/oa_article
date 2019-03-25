package com.wul.oa_article.view.register;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.wul.oa_article.R;
import com.wul.oa_article.api.HttpResultSubscriber;
import com.wul.oa_article.api.HttpServerImpl;
import com.wul.oa_article.mvp.MVPBaseActivity;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class RegisterActivity extends MVPBaseActivity<RegisterContract.View, RegisterPresenter> implements RegisterContract.View {

    @BindView(R.id.edit_phone)
    EditText editPhone;
    @BindView(R.id.edit_versition)
    EditText editVersition;
    @BindView(R.id.get_verfication)
    TextView getVerfication;
    @BindView(R.id.edit_password)
    EditText editPassword;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.person_xieyi)
    TextView personXieyi;
    @BindView(R.id.register_button)
    Button registerButton;
    @BindView(R.id.go_login)
    TextView goLogin;
    @BindView(R.id.mima_visiable)
    CheckBox mimaVisiable;


    @Override
    protected int getLayout() {
        return R.layout.act_register;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("注册新用户");

        mimaVisiable.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                //如果选中，显示密码
                editPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                //否则隐藏密码
                editPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });
    }

    @OnClick(R.id.get_verfication)
    public void getVerfication(View view) {
        String phone = editPhone.getText().toString().trim();
        if (StringUtils.isEmpty(phone)) {
            showToast("请输入手机号码！");
            return;
        }
        timer.start();
        HttpServerImpl.sendMessage(phone, 0).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                showToast("验证码发送成功！");
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }

    @OnClick(R.id.register_button)
    public void registerButton(View view) {
        String phone = editPhone.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String code = editVersition.getText().toString().trim();
        if (StringUtils.isEmpty(phone)) {
            showToast("请输入手机号码！");
            return;
        }
        if (StringUtils.isEmpty(code)) {
            showToast("请输入验证码！");
            return;
        }
        if (StringUtils.isEmpty(password)) {
            showToast("请输入密码！");
            return;
        }
        if (!checkbox.isChecked()) {
            showToast("请先同意用户服务协议！");
            return;
        }
        HttpServerImpl.register(phone, code, password, 0).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                showToast("注册成功！");
                finish();
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    @OnClick(R.id.go_login)
    public void goLogin(View view) {
        finish();
    }


    CountDownTimer timer = new CountDownTimer(60 * 1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            getVerfication.setText(millisUntilFinished / 1000 + "秒");
            getVerfication.setClickable(false);
        }

        @Override
        public void onFinish() {
            getVerfication.setText("重新发送");
            getVerfication.setClickable(true);
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
