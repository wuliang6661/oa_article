package com.wul.oa_article.view.verificationlogin;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wul.oa_article.R;
import com.wul.oa_article.mvp.MVPBaseActivity;

import butterknife.BindView;


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
}
