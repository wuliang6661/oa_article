package com.wul.oa_article.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.FragmentUtils;
import com.wul.oa_article.R;
import com.wul.oa_article.base.BaseActivity;
import com.wul.oa_article.module.order_details.Order_detailsFragment;
import com.wul.oa_article.module.task_allot.Task_allotFragment;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class OrderDetailsActivity extends BaseActivity {


    @BindView(R.id.shangji_task_check)
    CheckBox shangjiTaskCheck;
    @BindView(R.id.task_right_button)
    TextView taskRightButton;
    @BindView(R.id.shangji_task_bar)
    LinearLayout shangjiTaskBar;
    @BindView(R.id.task_name)
    TextView taskName;
    @BindView(R.id.task_date)
    TextView taskDate;
    @BindView(R.id.task_person_name)
    TextView taskPersonName;
    @BindView(R.id.shangji_layout)
    LinearLayout shangjiLayout;

    boolean isCreate = false;   //默认当前订单不是当前用户创建
    int orderId;

    @Override
    protected int getLayout() {
        return R.layout.act_order_details;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goBack();
        setTitleText("订单详情");
//        isCreate = getIntent().getExtras().getBoolean("isCreate", false);
        orderId = getIntent().getExtras().getInt("id");
//        if (isCreate) {
//            FragmentUtils.replace(getSupportFragmentManager(), CreateOrderFragment.newInstance(2, orderId), R.id.order_details);
//        }
        FragmentUtils.replace(getSupportFragmentManager(), Order_detailsFragment.newInstance(0, orderId), R.id.order_details);
        FragmentUtils.replace(getSupportFragmentManager(), new Task_allotFragment(), R.id.task_allot);
    }


    @OnClick(R.id.shangji_task_bar)
    public void BarClick() {
        if (shangjiLayout.getVisibility() == View.VISIBLE) {
            shangjiLayout.setVisibility(View.GONE);
            shangjiTaskCheck.setChecked(true);
        } else {
            shangjiLayout.setVisibility(View.VISIBLE);
            shangjiTaskCheck.setChecked(false);
        }
    }


}
