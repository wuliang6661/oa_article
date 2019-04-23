package com.wul.oa_article.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.FragmentUtils;
import com.wul.oa_article.R;
import com.wul.oa_article.base.BaseActivity;
import com.wul.oa_article.bean.ClientOrderBo;
import com.wul.oa_article.module.create_order.CreateOrderFragment;

import java.util.Objects;

/**
 * 创建订单页面
 */
public class CreateActivity extends BaseActivity {


    ClientOrderBo clientOrderBo;

    @Override
    protected int getLayout() {
        return R.layout.act_create_order;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("创建订单");
        boolean isWaiBu = Objects.requireNonNull(getIntent().getExtras()).getBoolean("isWaibu", false);
        if (isWaiBu) {
            clientOrderBo = (ClientOrderBo) getIntent().getExtras().getSerializable("client");
        }
        CreateOrderFragment fragment = new CreateOrderFragment();
        FragmentUtils.replace(getSupportFragmentManager(), fragment, R.id.fragment_container);
        if (isWaiBu) {
            fragment.setClientData(clientOrderBo);
        }
    }

}
