package com.wul.oa_article.view.forword_password;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wul.oa_article.R;
import com.wul.oa_article.mvp.MVPBaseActivity;

import butterknife.BindView;


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
}
