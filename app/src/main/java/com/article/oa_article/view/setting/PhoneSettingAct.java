package com.article.oa_article.view.setting;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.HttpServerImpl;
import com.article.oa_article.api.http.PersonServiceImpl;
import com.article.oa_article.base.BaseActivity;
import com.article.oa_article.base.MyApplication;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 修改手机号码
 */
public class PhoneSettingAct extends BaseActivity {


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

    private String oldPhone;
    private String newPhone;
    private boolean isFrist = true;   //默认在第一步


    @Override
    protected int getLayout() {
        return R.layout.act_person_phone;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("修改手机号码");
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
        if (isFrist) {
            PersonServiceImpl.updatePhoneSendMessage(phone, 2).subscribe(new HttpResultSubscriber<String>() {
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
        } else {
            HttpServerImpl.sendMessage(phone, 2).subscribe(new HttpResultSubscriber<String>() {
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
    }


    @OnClick(R.id.next_button)
    public void next(View view) {
        String phone = editPhone.getText().toString().trim();
        String code = editVersition.getText().toString().trim();
        if (StringUtils.isEmpty(phone)) {
            showToast("请输入手机号码！");
            return;
        }
        if (!RegexUtils.isMobileExact(phone)) {
            showToast("请输入正确手机号！");
            return;
        }
        if (StringUtils.isEmpty(code)) {
            showToast("请输入验证码！");
            return;
        }
        if (isFrist) {
            checkData(phone, code);
        } else {
            updatePhone(phone, code);
        }
    }

    /**
     * 验证短信验证码
     */
    private void checkData(String phone, String code) {
        HttpServerImpl.checkModeMsg(phone, code, 2).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                onePhone.setTextColor(Color.parseColor("#6F6F6F"));
                twoSetpassword.setTextColor(Color.parseColor("#5678FF"));
                nextButton.setText("确定");
                editPhone.setText("");
                editVersition.setText("");
                timer.cancel();
                getVerfication.setText("获取验证码");
                getVerfication.setClickable(true);
                MyApplication.token = s;
                isFrist = false;
                oldPhone = phone;
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    /**
     * 修改手机号码
     */
    private void updatePhone(String phone, String code) {
        PersonServiceImpl.updatePhone(phone, code).subscribe(new HttpResultSubscriber<String>() {
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
