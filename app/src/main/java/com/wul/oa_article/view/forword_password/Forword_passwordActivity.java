package com.wul.oa_article.view.forword_password;


import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.wul.oa_article.R;
import com.wul.oa_article.api.HttpResultSubscriber;
import com.wul.oa_article.api.HttpServerImpl;
import com.wul.oa_article.base.MyApplication;
import com.wul.oa_article.mvp.MVPBaseActivity;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class Forword_passwordActivity extends MVPBaseActivity<Forword_passwordContract.View, Forword_passwordPresenter> implements Forword_passwordContract.View {

    @BindView(R.id.one_phone)
    TextView onePhone;
    @BindView(R.id.two_setpassword)
    TextView twoSetpassword;
    @BindView(R.id.edit_phone)
    EditText editPhone;
    @BindView(R.id.edit_versition)
    EditText editVersition;
    @BindView(R.id.get_verfication)
    TextView getVerfication;
    @BindView(R.id.next_button)
    Button nextButton;
    @BindView(R.id.layout_phone)
    LinearLayout layoutPhone;
    @BindView(R.id.edit_new_password)
    EditText editNewPassword;
    @BindView(R.id.edit_password)
    EditText editPassword;
    @BindView(R.id.commit)
    Button commit;
    @BindView(R.id.layout_set_password)
    LinearLayout layoutSetPassword;

    @Override
    protected int getLayout() {
        return R.layout.act_forword_password;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("忘记密码");
    }


    @OnClick(R.id.get_verfication)
    public void getVerfication(View view) {
        String phone = editPhone.getText().toString().trim();
        if (StringUtils.isEmpty(phone)) {
            showToast("请输入手机号码！");
            return;
        }
        HttpServerImpl.sendMessage(phone, 2).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                timer.start();
                showToast("验证码发送成功！");
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }

    @OnClick(R.id.next_button)
    public void next(View view) {
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
        HttpServerImpl.checkModeMsg(phone, code, 2).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                layoutPhone.setVisibility(View.GONE);
                layoutSetPassword.setVisibility(View.VISIBLE);
                onePhone.setTextColor(Color.parseColor("#6F6F6F"));
                twoSetpassword.setTextColor(Color.parseColor("#5678FF"));
                MyApplication.token = s;
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    @OnClick(R.id.commit)
    public void commit(View view) {
        String newPassword = editNewPassword.getText().toString().trim();
        String comfimPassword = editPassword.getText().toString().trim();
        if (StringUtils.isEmpty(newPassword)) {
            showToast("请输入新密码！");
            return;
        }
        if (StringUtils.isEmpty(comfimPassword)) {
            showToast("请再次输入密码！");
            return;
        }
        HttpServerImpl.fordPassword(newPassword, comfimPassword).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                showToast("修改成功！");
                finish();
            }

            @Override
            public void onFiled(String message) {
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
