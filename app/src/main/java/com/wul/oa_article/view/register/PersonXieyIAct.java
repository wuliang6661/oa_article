package com.wul.oa_article.view.register;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wul.oa_article.R;
import com.wul.oa_article.base.BaseActivity;


/**
 * Created by wuliang on 2018/12/30.
 * <p>
 * 用户协议的activity
 */

public class PersonXieyIAct extends BaseActivity {


    @Override
    protected int getLayout() {
        return R.layout.act_xieyi;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("用户服务协议");
    }
}
