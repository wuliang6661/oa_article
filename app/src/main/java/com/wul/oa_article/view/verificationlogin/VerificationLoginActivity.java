package com.wul.oa_article.view.verificationlogin;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.wul.oa_article.R;
import com.wul.oa_article.api.HttpResultSubscriber;
import com.wul.oa_article.api.HttpServerImpl;
import com.wul.oa_article.base.MyApplication;
import com.wul.oa_article.bean.UserBo;
import com.wul.oa_article.mvp.MVPBaseActivity;
import com.wul.oa_article.util.AppManager;
import com.wul.oa_article.view.main.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class VerificationLoginActivity extends MVPBaseActivity<VerificationLoginContract.View, VerificationLoginPresenter> implements VerificationLoginContract.View {

    @BindView(R.id.edit_phone)
    EditText editPhone;
    @BindView(R.id.edit_versition)
    EditText editVersition;
    @BindView(R.id.get_verfication)
    TextView getVerfication;
    @BindView(R.id.button_login)
    Button buttonLogin;

    @Override
    protected int getLayout() {
        return R.layout.act_verification_login;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("验证码登录");
    }


    @OnClick(R.id.get_verfication)
    public void getVerfication(View view) {
        String phone = editPhone.getText().toString().trim();
        if (StringUtils.isEmpty(phone)) {
            showToast("请输入手机号码！");
            return;
        }
        showProgress();
        HttpServerImpl.sendMessage(phone, 1).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                stopProgress();
                timer.start();
                showToast("验证码发送成功！");
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
    }


    @OnClick(R.id.button_login)
    public void login(View view) {
        String phone = editPhone.getText().toString().trim();
        String code = editVersition.getText().toString().trim();
        if (StringUtils.isEmpty(phone)) {
            showToast("请输入手机号码！");
            return;
        }
        if (StringUtils.isEmpty(code)) {
            showToast("请输入验证码！");
            return;
        }
        stopProgress();
        HttpServerImpl.checkModeMsg(phone, code, 1).subscribe(new HttpResultSubscriber<String>() {
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
                AppManager.getAppManager().goHome();
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
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
