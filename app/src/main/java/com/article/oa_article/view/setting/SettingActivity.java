package com.article.oa_article.view.setting;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.mvp.MVPBaseActivity;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 资料设置
 */

public class SettingActivity extends MVPBaseActivity<SettingContract.View, SettingPresenter>
        implements SettingContract.View {

    @BindView(R.id.person_img)
    RoundedImageView personImg;
    @BindView(R.id.person_name)
    TextView personName;
    @BindView(R.id.person_name_text)
    TextView personNameText;
    @BindView(R.id.person_phone)
    TextView personPhone;

    @Override
    protected int getLayout() {
        return R.layout.act_setting;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("资料设置");
    }


    @OnClick({R.id.person_name_layout, R.id.person_phone_layout, R.id.password_layout})
    public void clickLayout(View view) {
        switch (view.getId()) {
            case R.id.person_name_layout:

                break;
            case R.id.person_phone_layout:

                break;
            case R.id.password_layout:

                break;
        }
    }
}
