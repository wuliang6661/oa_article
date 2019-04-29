package com.article.oa_article.view.register;


import android.graphics.Paint;
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

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.article.oa_article.R;
import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.HttpServerImpl;
import com.article.oa_article.base.MyApplication;
import com.article.oa_article.bean.UserBo;
import com.article.oa_article.bean.request.WechatRegisterRequest;
import com.article.oa_article.mvp.MVPBaseActivity;
import com.article.oa_article.util.AppManager;
import com.article.oa_article.util.MD5;
import com.article.oa_article.view.main.MainActivity;

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

    private boolean isWeChat;
    private String openId;
    private String nickName;
    private String nikeImg;

    @Override
    protected int getLayout() {
        return R.layout.act_register;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        isWeChat = getIntent().getBooleanExtra("isWeChat", false);
        if (isWeChat) {
            setTitleText("绑定手机号");
            openId = getIntent().getStringExtra("openId");
            nickName = getIntent().getStringExtra("nickName");
            nikeImg = getIntent().getStringExtra("nickImg");
        } else {
            setTitleText("注册新用户");
        }

        mimaVisiable.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                //如果选中，显示密码
                editPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                //否则隐藏密码
                editPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });
        goLogin.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    @OnClick(R.id.get_verfication)
    public void getVerfication(View view) {
        String phone = editPhone.getText().toString().trim();
        if (StringUtils.isEmpty(phone)) {
            showToast("请输入手机号码！");
            return;
        }
        if (!RegexUtils.isMobileExact(phone)) {
            showToast("请输入正确手机号！");
            return;
        }
        showProgress();
        if (isWeChat) {
            HttpServerImpl.sendWxMessage(phone, 0).subscribe(new HttpResultSubscriber<String>() {
                @Override
                public void onSuccess(String s) {
                    timer.start();
                    stopProgress();
                    showToast("验证码发送成功！");
                }

                @Override
                public void onFiled(String message) {
                    stopProgress();
                    showToast(message);
                }
            });
        } else {
            HttpServerImpl.sendMessage(phone, 0).subscribe(new HttpResultSubscriber<String>() {
                @Override
                public void onSuccess(String s) {
                    timer.start();
                    stopProgress();
                    showToast("验证码发送成功！");
                }

                @Override
                public void onFiled(String message) {
                    stopProgress();
                    showToast(message);
                }
            });
        }
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
        if (isWeChat) {
            weChatRegister(phone, code, password);
        } else {
            phoneRegister(phone, code, password);
        }
    }


    @OnClick(R.id.person_xieyi)
    public void xieyi() {
        gotoActivity(PersonXieyIAct.class, false);
    }


    /**
     * 手机号注册
     */
    private void phoneRegister(String phone, String code, String password) {
        showProgress();
        HttpServerImpl.register(phone, code, password, 0).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                stopProgress();
                showToast("注册成功！");
                finish();
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
    }


    /**
     * 微信注册
     */
    private void weChatRegister(String phone, String code, String password) {
        WechatRegisterRequest request = new WechatRegisterRequest();
        request.setOpenId(openId);
        request.setCode(code);
        request.setType("0");
        request.setImg(nikeImg);
        request.setNicName(nickName);
        request.setPhone(phone);
        request.setPassword(MD5.strToMd5Low32(MD5.strToMd5Low32(password) + "zxq"));
        showProgress();
        HttpServerImpl.registerWeChat(request).subscribe(new HttpResultSubscriber<String>() {
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
                gotoActivity(MainActivity.class, false);
                AppManager.getAppManager().goHome();
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
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
