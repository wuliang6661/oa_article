package com.wul.oa_article.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.FragmentUtils;
import com.wul.oa_article.R;
import com.wul.oa_article.base.BaseActivity;
import com.wul.oa_article.module.create_order.CreateOrderFragment;
import com.wul.oa_article.module.task_allot.Task_allotFragment;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 创建任务
 */

public class CreateTaskActivity extends BaseActivity {


    @BindView(R.id.kehu_order_check)
    CheckBox kehuOrderCheck;
    @BindView(R.id.order_edit)
    TextView orderEdit;
    @BindView(R.id.kehu_msg_bar)
    LinearLayout kehuMsgBar;
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;


    @Override
    protected int getLayout() {
        return R.layout.act_create_task;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("订单详情");

        FragmentUtils.replace(getSupportFragmentManager(), new Task_allotFragment(), R.id.task_allot);
    }

    @OnClick(R.id.kehu_msg_bar)
    public void barClick(View view) {
        fragmentContainer.setVisibility(View.VISIBLE);
        kehuMsgBar.setVisibility(View.GONE);
//        goToFragment(new CreateOrderFragment());
        FragmentUtils.replace(getSupportFragmentManager(), new CreateOrderFragment(), R.id.fragment_container);
    }

}
