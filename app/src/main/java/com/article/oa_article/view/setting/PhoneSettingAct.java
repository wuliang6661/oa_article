package com.article.oa_article.view.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.base.BaseActivity;

import butterknife.BindView;

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
    @BindView(R.id.layout_phone)
    LinearLayout layoutPhone;
    @BindView(R.id.edit_new_phone)
    EditText editNewPhone;
    @BindView(R.id.edit_new_versition)
    EditText editNewVersition;
    @BindView(R.id.get_new_verfication)
    TextView getNewVerfication;
    @BindView(R.id.commit)
    Button commit;
    @BindView(R.id.layout_new_phone)
    LinearLayout layoutNewPhone;

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
}
