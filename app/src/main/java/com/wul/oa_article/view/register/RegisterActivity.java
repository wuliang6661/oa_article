package com.wul.oa_article.view.register;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.wul.oa_article.R;
import com.wul.oa_article.mvp.MVPBaseActivity;

import butterknife.BindView;


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

    @Override
    protected int getLayout() {
        return R.layout.act_register;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("注册新用户");
    }
}
